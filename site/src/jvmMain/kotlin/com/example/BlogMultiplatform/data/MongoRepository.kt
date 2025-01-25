package com.example.BlogMultiplatform.data

interface MongoRepository {
    fun checkUserExists(username: String): Boolean
}