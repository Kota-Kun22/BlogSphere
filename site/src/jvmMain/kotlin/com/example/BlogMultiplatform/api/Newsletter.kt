package com.example.BlogMultiplatform.api

import com.example.BlogMultiplatform.data.MongoDB
import com.example.BlogMultiplatform.models.NewsLetter
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

@Api(routeOverride = "subscribe")
suspend fun subscribeNewsletter(context: ApiContext) {
    try {
        val newsletter = context.req.getBody<NewsLetter>()
        context.res.setBody(newsletter?.let {
            context.data.getValue<MongoDB>().subscribe(newsletter = it)
        })
    } catch (e: Exception) {
        context.res.setBody(e.message)
    }
}