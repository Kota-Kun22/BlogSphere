package com.example.BlogMultiplatform.models

import org.bson.types.ObjectId
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
actual data class User (
    @SerialName(value = "_id")
    actual  val id: String =ObjectId().toHexString() ,
    actual val username: String=" ",
    actual val password: String=" "

)

@Serializable
actual data class UserWithOutPassword(
    @SerialName(value = "_id")
    actual val id: String =ObjectId().toHexString() ,
    actual val username: String=" ",


)