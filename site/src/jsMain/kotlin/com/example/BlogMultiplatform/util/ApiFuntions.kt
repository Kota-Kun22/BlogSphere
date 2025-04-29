package com.example.BlogMultiplatform.util

import com.example.BlogMultiplatform.models.ApiListResponse
import com.example.BlogMultiplatform.models.ApiResponse
import com.example.BlogMultiplatform.models.Constants.AUTHOR_PARAM
import com.example.BlogMultiplatform.models.Constants.POST_ID_PARAM
import com.example.BlogMultiplatform.models.Constants.QUERY_PARAM
import com.example.BlogMultiplatform.models.Constants.SKIP_PARAM
import com.example.BlogMultiplatform.models.Post
import com.example.BlogMultiplatform.models.RandomJoke
import com.example.BlogMultiplatform.models.User
import com.example.BlogMultiplatform.models.UserWithOutPassword

import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.http
import kotlinx.browser.localStorage
import kotlinx.browser.window

import kotlinx.serialization.json.Json
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date


//suspend fun checkUserExistence(user: User):UserWithOutPassword?
//{
//    return try {
//        val result= window.api.tryPost(
//            apiPath = "userCheck",
//            body = Json.encodeToString(user).encodeToByteArray()
//        )
//        Json.decodeFromString<UserWithOutPassword>(result.toString())
//    }catch (e:Exception)
//    {
//        println("CURRENT_USER")
//        println(e.message)
//        null
//    }
//
//}
//----------------------------below here is the stephan's updated version------------------//
suspend fun checkUserExistence(user: User): UserWithOutPassword? {


    println("entering checkUserExistence")

    return try {
         val jsonResponse= window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )?.decodeToString()

        println("Received response : $jsonResponse") // Debugging output

        if (jsonResponse.isNullOrBlank()) {
            println("Error: Received null or empty response from API")
            return null
        }

//        val users: List<UserWithOutPassword> = jsonResponse.parseData()
//        users.firstOrNull() // Return the first user if exists
        Json.decodeFromString<UserWithOutPassword>(jsonResponse)
    } catch (e: Exception) {
        println("CURRENT_USER")
        println(e.message)
        null
    }
}

suspend fun checkUserId(id: String): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "checkuserid",
            body = Json.encodeToString(id).encodeToByteArray()
        )?.decodeToString().parseData()
    } catch (e: Exception) {
        println(e.message.toString())
        false
    }
}

suspend fun updatePost(post: Post): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "updatepost",
            body = Json.encodeToString(post).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}

suspend fun fetchRandomJoke(onComplete: (RandomJoke) -> Unit) {
    val date = localStorage["date"]
    if (date != null) {
        val difference = (Date.now() - date.toDouble())
        val dayHasPassed = difference >= 86400000
        if (dayHasPassed) {
            try {
                val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
                onComplete(result.parseData())
                localStorage["date"] = Date.now().toString()
                localStorage["joke"] = result
            } catch (e: Exception) {
                onComplete(RandomJoke(id = -1, message = e.message.toString()))
                println(e.message)
            }
        } else {
            try {
                localStorage["joke"]?.parseData<RandomJoke>()?.let { onComplete(it) }
            } catch (e: Exception) {
                onComplete(RandomJoke(id = -1, message = e.message.toString()))
                println(e.message)
            }
        }
    } else {
        try {
            val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
            onComplete(result.parseData())
            localStorage["date"] = Date.now().toString()
            localStorage["joke"] = result
        } catch (e: Exception) {
            onComplete(RandomJoke(id = -1, message = e.message.toString()))
            println(e.message)
        }
    }
}


//changed
suspend fun addPost(post: Post): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "addpost",
            body = Json.encodeToString(post).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}

//change
suspend fun fetchMyPosts(
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "readmyposts?${SKIP_PARAM}=$skip&${AUTHOR_PARAM}=${localStorage["username"]}"
        )?.decodeToString()
        onSuccess(result.parseData())
    } catch (e: Exception) {
        println(e)
        onError(e)
    }
}

//changed
suspend fun deleteSelectedPosts(ids: List<String>): Boolean {
    return try {
        val result = window.api.tryPost(
            apiPath = "deleteselectedposts",
            body = Json.encodeToString(ids).encodeToByteArray()
        )?.decodeToString()
        result.toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}

//changed
suspend fun searchPostsByTitle(
    query: String,
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "searchposts?${QUERY_PARAM}=$query&${SKIP_PARAM}=$skip"
        )?.decodeToString()
        onSuccess(result.parseData())
    } catch (e: Exception) {
        println(e.message)
        onError(e)
    }
}

//changed
suspend fun fetchSelectedPost(id: String): ApiResponse {
    return try {
        val result = window.api.tryGet(
            apiPath = "readselectedpost?${POST_ID_PARAM}=$id"
        )?.decodeToString()
        result?.parseData() ?: ApiResponse.Error(message = "Result is null")
    } catch (e: Exception) {
        println(e)
        ApiResponse.Error(message = e.message.toString())
    }
}
//inline fun <reified T> String?.parseData(): T {
//    if (this.isNullOrBlank()) throw IllegalArgumentException("Invalid JSON: null or empty")
//    return try {
//        Json.decodeFromString(this)
//    } catch (e: Exception) {
//        throw IllegalArgumentException("Failed to parse JSON: ${this}")
//    }
//}

inline fun <reified T> String?.parseData(): T {
    return Json.decodeFromString(this.toString())
}
