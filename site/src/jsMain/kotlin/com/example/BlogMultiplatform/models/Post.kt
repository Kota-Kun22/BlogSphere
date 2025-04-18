package com.example.BlogMultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class Post(


    actual  val _id:String="",
    actual val author:String,
    actual val date:Long,
    actual val title:String,
    actual val subtitle:String,
    actual val thumbnail:String,
    actual val content:String,
    actual val category:Category,
    actual val popular:Boolean=false,
    actual val main:Boolean=false,
    actual val sponsored:Boolean=false


)

@Serializable
actual data class PostWithoutDetails(


    actual  val _id:String="",
    actual val author:String,
    actual val date:Long,
    actual val title:String,
    actual val subtitle:String,
    actual val thumbnail:String,

    actual val category:Category,



)
