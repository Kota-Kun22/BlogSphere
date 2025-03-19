package com.example.BlogMultiplatform.models

expect class Post
{
    val _id:String
    val author:String
    val date:Long
    val title:String
    val subtitle:String
    val thumbnail:String
    val category:Category
    val content:String
    val popular:Boolean
    val main:Boolean
    val sponsored:Boolean
}