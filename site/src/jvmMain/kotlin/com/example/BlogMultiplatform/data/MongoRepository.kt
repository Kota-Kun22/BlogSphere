package com.example.BlogMultiplatform.data

import com.example.BlogMultiplatform.models.User

interface MongoRepository {

    suspend fun checkUserExists(user: User): User?
    suspend fun checkUserId(id:String):Boolean

}