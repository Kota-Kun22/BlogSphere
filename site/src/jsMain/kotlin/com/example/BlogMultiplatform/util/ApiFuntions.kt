package com.example.BlogMultiplatform.util

import com.example.BlogMultiplatform.models.User
import com.example.BlogMultiplatform.models.UserWithOutPassword
import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToDynamic

suspend fun checkUserExistence(user: User):UserWithOutPassword?
{
    return try {
        val result= window.api.tryPost(
            apiPath = "userCheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )
        Json.decodeFromString<UserWithOutPassword>(result.toString())
    }catch (e:Exception)
    {
        println(e.message)
        null
    }

}