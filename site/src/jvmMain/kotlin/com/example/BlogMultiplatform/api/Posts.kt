package com.example.BlogMultiplatform.api

import com.example.BlogMultiplatform.data.MongoDB
import com.example.BlogMultiplatform.models.ApiListResponse
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
        val skip = context.req.params["skip"]?.toInt() ?: 0
        val author = context.req.params["author"] ?: ""
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
