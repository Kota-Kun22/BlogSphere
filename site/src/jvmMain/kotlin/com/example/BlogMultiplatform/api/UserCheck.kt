package com.example.BlogMultiplatform.api

import com.example.BlogMultiplatform.data.MongoDB
import com.example.BlogMultiplatform.models.User
import com.example.BlogMultiplatform.models.UserWithOutPassword
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Api(routeOverride = "userCheck")
suspend fun userCheck(context: ApiContext) {

    try{
        val userRequest= context.req.body?.decodeToString()?.let { /** here in this variable we are getting the body form the post request
         and decoding the bitarray into the string and if not null then decoding it and then using or can say saving it */
            Json.decodeFromString<User>(it)
        }
        val user= userRequest?.let {
            context.data.getValue<MongoDB>().checkUserExists(User(username = it.username, password = hashPassword(it.password)))
        }/**here if the user not null i am checking in my database and retrieving it from the database and storing it in user */

        if(user!=null)
        {
            context.res.setBodyText(
                Json.encodeToString(UserWithOutPassword(id = user.id, username = user.username)
                )
            )
        }else
        {
            context.res.setBodyText(
                Json.encodeToString(Exception("User Not Found"))
            )
        }

    }catch (e:Exception)
    {
        context.res.setBodyText(Json.encodeToString(Exception(e.message)))

    }

}
private fun hashPassword(password:String):String
{
    val messageDigest= MessageDigest.getInstance("SHA-256")
    val hashBytes= messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
    val hexString= StringBuffer()

    for(byte in hashBytes){
        hexString.append(String.format("%02x",byte) )/** here 02 means that each byte will be represented with exactly two character and X means that it will be represented in hexadecimal format */
    }
    return hexString.toString()

}