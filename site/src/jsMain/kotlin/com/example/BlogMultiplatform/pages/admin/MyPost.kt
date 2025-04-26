package com.example.BlogMultiplatform.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.BlogMultiplatform.components.AdminPageLayout
import com.example.BlogMultiplatform.components.Posts
import com.example.BlogMultiplatform.components.SearchBar
import com.example.BlogMultiplatform.models.ApiListResponse
import com.example.BlogMultiplatform.models.PostWithoutDetails

import com.example.BlogMultiplatform.models.Theme
import com.example.BlogMultiplatform.util.Constants.FONT_FAMILY

import com.example.BlogMultiplatform.util.Constants.POST_PER_PAGE
import com.example.BlogMultiplatform.util.Constants.SIDE_PANEL_WIDTH
import com.example.BlogMultiplatform.util.fetchMyPosts
import com.example.BlogMultiplatform.util.isUserLoggedIn
import com.example.BlogMultiplatform.util.noBorder
import com.example.BlogMultiplatform.util.parseSwitchText
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page

import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Page(routeOverride = "/admin/myposts")
@Composable
fun MyPostsPage()
{
    isUserLoggedIn {
        MyPostScreen()
    }

}

@Composable
fun MyPostScreen()
{

    val breakpoint=rememberBreakpoint()
    val myPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    val scope = rememberCoroutineScope()

    val selectedPosts= remember{ mutableStateListOf<String>() }

    var postToSkip by remember { mutableStateOf(0) }
    var showMoreVisibility by remember{ mutableStateOf(false) }


    var selectable by remember { mutableStateOf(false) }
    var switchText by remember { mutableStateOf("Select") }

    LaunchedEffect(Unit){
        fetchMyPosts(
            skip=postToSkip,
            onSuccess = {
                if(it is ApiListResponse.Success){
                    myPosts.clear()
                    myPosts.addAll(it.data)
                    postToSkip += POST_PER_PAGE
                    showMoreVisibility = it.data.size >= POST_PER_PAGE
                }
            },
            onError = {
                println(it)
            },
        )
    }

    AdminPageLayout {
        Column(
            modifier= Modifier
                .fillMaxSize()
                .margin(topBottom = 50.px)
                .padding(left = if (breakpoint>Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier= Modifier
                .margin(bottom = 24.px)
                .fillMaxWidth(if(breakpoint>=rememberBreakpoint())30.percent else 50.percent
                ),
                contentAlignment = Alignment.Center
            ){
                SearchBar(onEnterClick = {}, onSearchIconClick = {}, breakpoint = breakpoint, darkTheme = false)

            }
            Row(
                modifier= Modifier
                    .fillMaxWidth(if(breakpoint>Breakpoint.MD) 80.percent else 90.percent)
                    .margin(bottom= 24.px),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Switch(
                        modifier = Modifier.margin(right = 8.px),
                        size = SwitchSize.LG,
                        checked = selectable,
                        onCheckedChange = {
                            selectable= it
                            if(!selectable){
                                switchText="Select"
                                selectedPosts.clear()
                            }else{
                                switchText= "0 Posts Selected"
                            }

                        }
                    )
                    SpanText(
                        modifier = Modifier.color(if(selectable) Color.black else Theme.HalfBlack.rgb),
                        text = switchText
                    )
                }
                Button(attrs = Modifier
                    .margin(right = 20.px)
                    .height(54.px)
                    .padding(leftRight = 24.px)
                    .backgroundColor(Theme.red.rgb)
                    .color(Color.white)
                    .noBorder()
                    .borderRadius(r= 4.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .fontWeight(FontWeight.Medium)
                    .onClick {}
                    .toAttrs()
                ) {
                    SpanText(text="DELETE")
                }
            }

            Posts(
                breakpoint=breakpoint,
                posts = myPosts,
                selectable= selectable,
                onSelect={
                    selectedPosts.add(it)
                    switchText= parseSwitchText(selectedPosts.toList())
                },
                onDeselect = {
                    selectedPosts.remove(it)
                    switchText = parseSwitchText(selectedPosts.toList())
                },
                showMoreVisibility = showMoreVisibility,
                onShowMore = {
                    scope.launch {
                        fetchMyPosts(
                            skip=postToSkip,
                            onSuccess = {
                                if(it is ApiListResponse.Success){
                                    if(it.data.isNotEmpty())
                                    {
                                        myPosts.addAll(it.data)
                                        postToSkip += POST_PER_PAGE
                                        if(it.data.size< POST_PER_PAGE)showMoreVisibility=false
                                    }
                                    else{
                                        showMoreVisibility= false
                                    }

                                }
                            },
                            onError = {
                                println(it)
                            },
                        )
                    }

                },
            )
        }
    }
}