package com.example.BlogMultiplatform.api

import com.example.BlogMultiplatform.data.MongoDB
import com.example.BlogMultiplatform.models.User
import com.example.BlogMultiplatform.models.UserWithOutPassword
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.json.Json
import java.lang.constant.ConstantDescs.NULL
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {

    println("Received request in userCheck")

    try{
        val userRequest= context.req.body?.decodeToString()?.let { /** here in this variable we are getting the body form the post request
         and decoding the bitarray into the string and if not null then decoding it and then using or can say saving it */
            Json.decodeFromString<User>(it)

        }
        val user= userRequest?.let {
            context.data.getValue<MongoDB>().checkUserExists(User(username = it.username, password = hashPassword(it.password)))
        }/**here if the user not null i am checking in my database and retrieving it from the database and storing it in user */

        //my way which was wrong
//        val response = if (user != null) {
//                UserWithOutPassword(_id = user._id, username = user.username)
//            } else {
//                UserWithOutPassword(_id = " ", username = "User Not Found") // Use null instead of empty string
//            }

        // // Fix: Return an empty response if authentication fails
        val response = user?.let {
                UserWithOutPassword(_id = it._id, username = it.username)
            } ?: run {
                context.res.setBodyText("") // Return an empty response
                return
            }
        context.res.setBodyText(Json.encodeToString(response))

    }catch (e:Exception)
    {
        //context.res.setBodyText(Json.encodeToString(e.message))//changed
        context.res.setBodyText(Json.encodeToString(mapOf("error" to e.message)))

    }
}
@Api(routeOverride = "checkuserid")
suspend fun checkUserId(context: ApiContext)
{
    try{
        val idRequest= context.req.body?.decodeToString()?.let {
            Json.decodeFromString<String>(it)
        }
        val result= idRequest?.let {
            context.data.getValue<MongoDB>().checkUserId(it)
        }
        if(result!=null){
            context.res.setBodyText(Json.encodeToString(result))
        }else{
            context.res.setBodyText(Json.encodeToString(false))
        }

    }
    catch (e:Exception)
    {
        context.res.setBodyText(Json.encodeToString(Boolean))
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