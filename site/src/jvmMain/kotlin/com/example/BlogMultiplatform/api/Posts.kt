package com.example.BlogMultiplatform.api

import com.example.BlogMultiplatform.data.MongoDB
import com.example.BlogMultiplatform.models.ApiListResponse
import com.example.BlogMultiplatform.models.ApiResponse

import com.example.BlogMultiplatform.models.Constants.AUTHOR_PARAM
import com.example.BlogMultiplatform.models.Constants.POST_ID_PARAM
import com.example.BlogMultiplatform.models.Constants.QUERY_PARAM
import com.example.BlogMultiplatform.models.Constants.SKIP_PARAM
import com.example.BlogMultiplatform.models.Post
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.serialization.json.Json
import org.bson.codecs.ObjectIdGenerator


@Api(routeOverride = "addpost")
suspend fun addPost(context: ApiContext) {
    try {
       val post = context.req.body?.decodeToString()?.let { Json.decodeFromString<Post>(it) }

        val newPost= post?.copy(_id = ObjectIdGenerator().generate().toString())
        context.res.setBodyText(
            newPost?.let {
                context.data.getValue<MongoDB>().addPost(it).toString()
            }?:false.toString()
        )

    }catch(e:Exception){

        context.logger.info("API EXCEPTION $e")
        context.res.setBodyText(Json.encodeToString(e.message))
    }

}
@Api(routeOverride = "readmyposts")
suspend fun readMyPosts(context: ApiContext) {
    try {
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val author = context.req.params[AUTHOR_PARAM] ?: ""
        val myPosts = context.data.getValue<MongoDB>().readMyPosts(
            skip = skip,
            author = author
        )
        context.res.setBodyText(
            Json.encodeToString(ApiListResponse.Success(data = myPosts))
        )
    } catch (e: Exception) {
        context.res.setBodyText(
            Json.encodeToString(ApiListResponse.Error(message = e.message.toString()))
        )
    }
}
@Api(routeOverride = "deleteselectedposts")
suspend fun deleteSelectedPosts(context: ApiContext) {
    try {
        val request =
            context.req.body?.decodeToString()?.let{ Json.decodeFromString<List<String>>(it)}
        context.res.setBodyText(
            request?.let{
                context.data.getValue<MongoDB>().deleteSelectedPosts(ids = it).toString()
            }?:"false"
        )
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(e.message))
    }
}

@Api(routeOverride = "searchposts")
suspend fun searchPostsByTitle(context: ApiContext) {
    try {
        val query = context.req.params[QUERY_PARAM] ?: ""
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val request = context.data.getValue<MongoDB>().searchPostsByTittle(
            query = query,
            skip = skip
        )
        context.res.setBodyText(Json.encodeToString(ApiListResponse.Success(data= request)))

    } catch (e: Exception) {
        context.res.setBodyText(
            Json.encodeToString(ApiListResponse.Error(message = e.message.toString()))
        )
    }
}

@Api(routeOverride = "readselectedpost")
suspend fun readSelectedPost(context: ApiContext) {

    val postId = context.req.params[POST_ID_PARAM]

    if (!postId.isNullOrEmpty()) {
        try {
            val selectedPost = context.data.getValue<MongoDB>().readSelectedPost(id = postId)
            context.res.setBodyText(Json.encodeToString(ApiResponse.Success(data= selectedPost)))
        } catch (e: Exception) {
            context.res.setBodyText(Json.encodeToString(ApiResponse.Error(message = e.message.toString())))
        }
    } else {
        context.res.setBodyText(Json.encodeToString(ApiResponse.Error(message = "SELECTED POST DOES NOT EXIST")))
    }
}