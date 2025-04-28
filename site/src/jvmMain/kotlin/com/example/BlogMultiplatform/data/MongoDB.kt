package com.example.BlogMultiplatform.data

import com.example.BlogMultiplatform.models.Post
import com.example.BlogMultiplatform.models.PostWithoutDetails
import com.example.BlogMultiplatform.models.User
import com.example.BlogMultiplatform.utils.Constants.DATABASE_NAME
import com.example.BlogMultiplatform.utils.Constants.POST_PER_PAGE
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Indexes.descending
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList


@InitApi
fun initMongoDB(context:InitApiContext)
{
    System.setProperty(
        "org.mongodb.driver.lazyLoadingEnabled",
        "true"
    )
    context.data.add(MongoDB(
        context = context
    ))
}
class MongoDB(private val context: InitApiContext):MongoRepository {

    private val client= MongoClient.create()
    private val database = client.getDatabase(DATABASE_NAME)
    private val userCollection = database.getCollection<User>("user")//name of the collection like it will contain the user with their id and pass
    private val postCollection= database.getCollection<Post>("post")


    override suspend fun addPost(post: Post): Boolean {

        return postCollection.insertOne(post).wasAcknowledged()
    }


    override suspend fun readMyPosts(skip: Int, author: String): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(Filters.eq(PostWithoutDetails::author.name, author))
            .sort(descending(PostWithoutDetails::date.name))
            .skip(skip)
            .limit(POST_PER_PAGE)
            .toList()
    }

    override suspend fun deleteSelectedPosts(ids: List<String>): Boolean {
        return postCollection
            .deleteMany(Filters.`in`(Post::_id.name, ids))
            .wasAcknowledged()
    }

    override suspend fun checkUserExists(user: User): User? {
        return try {
            userCollection
                .find(
                    and(
                        Filters.eq(User::username.name, user.username),
                        Filters.eq(User::password.name, user.password)
                    )
                ).firstOrNull()
        } catch (e: Exception) {
            context.logger.error("CURRENT_USER")
            context.logger.error(e.message.toString())
            null
        }
    }

    override suspend fun checkUserId(id: String): Boolean {
        return try {
            val documentCount = userCollection.countDocuments(Filters.eq(User::_id.name, id))
            documentCount>0

        }catch (e:Exception)
        {
            context.logger.error("Checking user ID")
            context.logger.error(e.message.toString())
            false

        }
    }

    override suspend fun searchPostsByTittle(query: String, skip: Int): List<PostWithoutDetails> {
        val regexQuery = query.toRegex(RegexOption.IGNORE_CASE)
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(Filters.regex(PostWithoutDetails::title.name, regexQuery.pattern))
            .sort(descending(PostWithoutDetails::date.name))
            .skip(skip)
            .limit(POST_PER_PAGE)
            .toList()

    }
}