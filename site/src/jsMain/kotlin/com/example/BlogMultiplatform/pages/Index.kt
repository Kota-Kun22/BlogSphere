package com.example.BlogMultiplatform.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.BlogMultiplatform.components.CategoryNavigationItems
import com.example.BlogMultiplatform.components.OverflowSidePanel
import com.example.BlogMultiplatform.models.ApiListResponse
import com.example.BlogMultiplatform.models.Constants.POST_PER_PAGE
import com.example.BlogMultiplatform.models.PostWithoutDetails
import com.example.BlogMultiplatform.navigation.Screen
import com.example.BlogMultiplatform.sections.HeaderSection
import com.example.BlogMultiplatform.sections.MainSection
import com.example.BlogMultiplatform.sections.NewsletterSection
import com.example.BlogMultiplatform.sections.PostsSection
import com.example.BlogMultiplatform.sections.SponsoredPostsSection
import com.example.BlogMultiplatform.util.Res
import com.example.BlogMultiplatform.util.fetchLatestPosts
import com.example.BlogMultiplatform.util.fetchMainPosts
import com.example.BlogMultiplatform.util.fetchPopularPosts
import com.example.BlogMultiplatform.util.fetchSponsoredPosts
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch


@Page
@Composable
fun HomePage(){
    println("HELLO HARSH>>>>>>")
    val breakpoint= rememberBreakpoint()
    val scope= rememberCoroutineScope()
    var overflowOpened by remember{ mutableStateOf(false) }
    var mainPosts by remember{ mutableStateOf<ApiListResponse>(ApiListResponse.Idle) }
    val latestPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    var latestPostsToSkip by remember{ mutableStateOf(0) }
    val sponsoredPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    val popularPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    var showMoreLatest by remember { mutableStateOf(false) }
    var showMorePopular by remember { mutableStateOf(false) }
    var popularPostsToSkip by remember { mutableStateOf(0) }

    LaunchedEffect(Unit){
        fetchMainPosts(
            onSuccess = {
                mainPosts= it
                //println(mainPosts)
            },
            onError = {}
        )

        fetchLatestPosts(
            skip = latestPostsToSkip,
            onSuccess = { response ->
                if (response is ApiListResponse.Success) {
                    latestPosts.addAll(response.data)
                    latestPostsToSkip += POST_PER_PAGE
                    if (response.data.size >= POST_PER_PAGE) showMoreLatest = true
                }
            },
            onError = {}
        )

        fetchSponsoredPosts(
            onSuccess = { response ->
                if (response is ApiListResponse.Success) {
                    sponsoredPosts.addAll(response.data)
                }
            },
            onError = {}
        )

        fetchPopularPosts(
            skip = popularPostsToSkip,
            onSuccess = { response ->
                if (response is ApiListResponse.Success) {
                    popularPosts.addAll(response.data)
                    popularPostsToSkip += POST_PER_PAGE
                    if (response.data.size >= POST_PER_PAGE) showMorePopular = true
                }
            },
            onError = {}
        )


    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (overflowOpened) {
            OverflowSidePanel( onMenuClose = {
                overflowOpened = false
            },
                content={
                    CategoryNavigationItems(vertical= true)
                })
        }
        HeaderSection(
            breakpoint = breakpoint,
            selectedCategory = null,//
            logo = Res.Image.logoHome,//
            onMenuOpen ={overflowOpened= true}
        )
        MainSection(breakpoint=breakpoint,posts=mainPosts,onClick = {})
        PostsSection(
            breakpoint = breakpoint,
            posts = latestPosts,
            title = "Latest Posts",
            showMoreVisibility = showMoreLatest,
            onShowMore = {
                scope.launch {
                    fetchLatestPosts(
                        skip = latestPostsToSkip,
                        onSuccess={ response->
                            if(response is ApiListResponse.Success){
                                if(response.data.isNotEmpty()){
                                    if(response.data.size < POST_PER_PAGE){
                                        showMoreLatest= false
                                    }
                                    latestPosts.addAll(response.data)
                                    latestPostsToSkip += POST_PER_PAGE
                                }else{
                                    showMoreLatest= false
                                }
                            }

                        },
                        onError={

                        }
                    )
                }

            },
            onClick = {
            }
        )
        SponsoredPostsSection(
            breakpoint= breakpoint,
            posts= sponsoredPosts,
            onClick = {  }
        )

        PostsSection(
            breakpoint = breakpoint,
            posts = popularPosts,
            title = "Popular Posts",
            showMoreVisibility = showMorePopular,
            onShowMore = {
                scope.launch {
                    fetchPopularPosts(
                        skip = popularPostsToSkip,
                        onSuccess = { response ->
                            if (response is ApiListResponse.Success) {
                                if (response.data.isNotEmpty()) {
                                    if (response.data.size < POST_PER_PAGE) {
                                        showMorePopular = false
                                    }
                                    popularPosts.addAll(response.data)
                                    popularPostsToSkip += POST_PER_PAGE
                                } else {
                                    showMorePopular = false
                                }
                            }
                        },
                        onError = {}
                    )
                }
            },
            onClick = {  }
        )
        NewsletterSection(breakpoint = breakpoint)

    }
}