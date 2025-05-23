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
import com.example.BlogMultiplatform.components.PostsView
import com.example.BlogMultiplatform.components.SearchBar
import com.example.BlogMultiplatform.models.ApiListResponse
import com.example.BlogMultiplatform.models.Constants.POST_PER_PAGE
import com.example.BlogMultiplatform.models.Constants.QUERY_PARAM
import com.example.BlogMultiplatform.models.PostWithoutDetails
import com.example.BlogMultiplatform.models.Theme
import com.example.BlogMultiplatform.navigation.Screen
import com.example.BlogMultiplatform.util.Constants.FONT_FAMILY
import com.example.BlogMultiplatform.util.Constants.SIDE_PANEL_WIDTH
import com.example.BlogMultiplatform.util.Id
import com.example.BlogMultiplatform.util.deleteSelectedPosts
import com.example.BlogMultiplatform.util.fetchMyPosts
import com.example.BlogMultiplatform.util.isUserLoggedIn
import com.example.BlogMultiplatform.util.noBorder
import com.example.BlogMultiplatform.util.parseSwitchText
import com.example.BlogMultiplatform.util.searchPostsByTitle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Visibility
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
import com.varabyte.kobweb.compose.ui.modifiers.visibility
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.w3c.dom.HTMLInputElement

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
    val context= rememberPageContext()
    val breakpoint=rememberBreakpoint()
    val myPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    val scope = rememberCoroutineScope()

    val selectedPosts= remember{ mutableStateListOf<String>() }

    var postToSkip by remember { mutableStateOf(0) }
    var showMoreVisibility by remember{ mutableStateOf(false) }


    var selectableMode by remember { mutableStateOf(false) }
    var switchText by remember { mutableStateOf("Select") }

    val hasParams= remember(key1= context.route){context.route.params.containsKey(QUERY_PARAM)}
    val query= remember(key1= context.route) {
        try{
            context.route.params.getValue(QUERY_PARAM)

        }catch (e:Exception){
            ""
        }
    }


    LaunchedEffect(context.route){
        postToSkip=0;

        (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value = query.replace("%20", " ")

        if(hasParams){
            searchPostsByTitle(
                query= query,
                skip = postToSkip,
                onSuccess = {
                    if(it is ApiListResponse.Success){
                        myPosts.clear()
                        myPosts.addAll(it.data)
                        postToSkip += POST_PER_PAGE
                        showMoreVisibility=it.data.size >= POST_PER_PAGE
                    }
                },
                onError = {
                    println(it)
                }

            )
        }else{
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
                SearchBar(
                    modifier = Modifier
                        .visibility( if(selectableMode) Visibility.Hidden else Visibility.Visible)
                        .styleModifier { property("transition","all 200ms") },
                    onEnterClick = {
                    val query =
                        (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value

                    if(query.isNotEmpty()){
                        context.router.navigateTo(Screen.AdminMyPost.searchByTitle(query= query))
                    }else{
                        context.router.navigateTo(Screen.AdminMyPost.route)
                    }
                },
                    onSearchIconClick = {},
                    breakpoint = breakpoint,
                    darkTheme = false
                )

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
                        checked = selectableMode,
                        onCheckedChange = {
                            selectableMode= it
                            if(!selectableMode){
                                switchText="Select"
                                selectedPosts.clear()
                            }else{
                                switchText= "0 Posts Selected"
                            }

                        }
                    )
                    SpanText(
                        modifier = Modifier.color(if(selectableMode) Color.black else Theme.HalfBlack.rgb),
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
                    .visibility(if(selectedPosts.isNotEmpty()) Visibility.Visible else Visibility.Hidden)
                    .onClick {
                       scope.launch {
                           val result = deleteSelectedPosts(ids= selectedPosts)
                          if(result){
                              selectableMode= false
                              switchText= "Select"
                              postToSkip -= selectedPosts.size
                              selectedPosts.forEach {deletedPostId->
                                  myPosts.removeAll{
                                      it._id==deletedPostId
                                  }
                              }
                              selectedPosts.clear()
                          }
                       }
                    }
                    .toAttrs()
                ) {
                    SpanText(text="DELETE")
                }
            }

            PostsView(
                breakpoint=breakpoint,
                posts = myPosts,
                selectableMode= selectableMode,
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
                        if(hasParams){
                            searchPostsByTitle(
                                query= query,
                                skip= postToSkip,
                                onSuccess = {
                                    if(it is ApiListResponse.Success){
                                        if(it.data.isNotEmpty())
                                        {
                                            myPosts.addAll(it.data)
                                            postToSkip+= POST_PER_PAGE
                                            if(it.data.size < POST_PER_PAGE)showMoreVisibility= false //////check this once lect 57..

                                        }else{
                                            showMoreVisibility= false
                                        }
                                    }
                                },
                                onError = {
                                    println(it)
                                },
                            )

                        }else{
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
                    }

                },
                onClick = {
                    context.router.navigateTo(Screen.AdminCreate.passPostId(id=it ))
                }
            )
        }
    }
}