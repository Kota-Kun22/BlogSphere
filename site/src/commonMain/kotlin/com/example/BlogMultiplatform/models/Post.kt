package com.example.BlogMultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
 data class Post(


     val _id:String="",
     val author:String,
     val date:Long,
     val title:String,
     val subtitle:String,
     val thumbnail:String,
     val content:String,
     val category:Category,
     val popular:Boolean=false,
     val main:Boolean=false,
     val sponsored:Boolean=false


)

@Serializable
 data class PostWithoutDetails(


      val _id:String="",
     val author:String,
     val date:Long,
     val title:String,
     val subtitle:String,
     val thumbnail:String,

     val category:Category,



    )
