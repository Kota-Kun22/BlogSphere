package com.example.BlogMultiplatform.models

import org.bson.types.ObjectId
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class User(
    @SerialName(value = "_id")
    val id: String =ObjectId().toHexString() ,
    val username: String,
    val password: String

)