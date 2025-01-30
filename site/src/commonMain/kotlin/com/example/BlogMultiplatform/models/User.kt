package com.example.BlogMultiplatform.models




expect class User {
    val _id: String
    val username: String
    val password: String
}

expect class UserWithOutPassword{
    val _id: String
    val username: String
}
