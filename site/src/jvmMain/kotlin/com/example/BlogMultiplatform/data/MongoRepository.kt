package com.example.BlogMultiplatform.data

import com.example.BlogMultiplatform.models.Post
import com.example.BlogMultiplatform.models.User

interface MongoRepository {


    suspend fun addPost(post: Post): Boolean
    suspend fun checkUserExists(user: User): User?
    suspend fun checkUserId(id:String):Boolean

}