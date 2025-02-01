package com.example.BlogMultiplatform.util

import com.example.BlogMultiplatform.models.User
import com.example.BlogMultiplatform.models.UserWithOutPassword
import com.varabyte.kobweb.browser.api
import kotlinx.browser.window

import kotlinx.serialization.json.Json



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
suspend fun checkUserId(id:String):Boolean
{
    return try {
        val result= window.api.tryPost(
            apiPath="checkuserid",
            body=Json.encodeToString(id).encodeToByteArray()
        )
        result?.decodeToString()?.let { Json.decodeFromString<Boolean>(it) }?:false

    }catch(e:Exception){
        println(e.message.toString())
        false

    }

}
inline fun <reified T> String?.parseData(): T {
    if (this.isNullOrBlank()) throw IllegalArgumentException("Invalid JSON: null or empty")
    return try {
        Json.decodeFromString(this)
    } catch (e: Exception) {
        throw IllegalArgumentException("Failed to parse JSON: ${this}")
    }
}
