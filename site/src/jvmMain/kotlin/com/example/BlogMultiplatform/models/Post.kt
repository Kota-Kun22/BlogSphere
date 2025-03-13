package com.example.BlogMultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.ObjectIdGenerator


@Serializable
actual data class Post(


    actual  val _id:String=ObjectIdGenerator().generate().toString(),
    actual val author:String,
    actual val date:Long,
    actual val title:String,
    actual val subtitle:String,
    actual val thumbnail:String,
    actual val content:String,
    actual val category:String,
    actual val popular:Boolean= false,
    actual val main:Boolean=false,
    actual val sponsored:Boolean=false


)
