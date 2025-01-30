package com.example.BlogMultiplatform.models

import org.bson.types.ObjectId
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import org.bson.codecs.ObjectIdGenerator


@Serializable
actual data class User (
    actual  val _id: String =ObjectIdGenerator().generate().toString() ,
    actual val username: String=" ",
    actual val password: String=" "

)

@Serializable
actual data class UserWithOutPassword(
    actual val _id: String =ObjectIdGenerator().generate().toString() ,
    actual val username: String=" ",


)