package com.example.BlogMultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class RandomJoke(val id:Int, val message:String)
