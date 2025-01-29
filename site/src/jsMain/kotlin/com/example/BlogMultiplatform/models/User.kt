package com.example.BlogMultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class User (
    @SerialName(value = "_id")
    actual  val id: String ="",
    actual val username: String=" ",
    actual val password: String=" "

)

@Serializable
actual data class UserWithOutPassword(
    @SerialName(value = "_id")
    actual val id: String =" ",
    actual val username: String=" ", )