package com.example.BlogMultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class User (
    actual  val _id: String ="",
    actual val username: String=" ",
    actual val password: String=" "

)

@Serializable
actual data class UserWithOutPassword(
    actual val _id: String =" ",
    actual val username: String=" ", )