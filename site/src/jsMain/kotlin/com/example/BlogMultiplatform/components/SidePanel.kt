package com.example.BlogMultiplatform.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.BlogMultiplatform.models.Theme
import com.example.BlogMultiplatform.navigation.Screen
import com.example.BlogMultiplatform.styles.NavigationItemStyle
import com.example.BlogMultiplatform.util.Constants.COLLAPSED_SIDE_PANEL_HEIGHT
import com.example.BlogMultiplatform.util.Constants.FONT_FAMILY
import com.example.BlogMultiplatform.util.Constants.SIDE_PANEL_WIDTH
import com.example.BlogMultiplatform.util.Id
import com.example.BlogMultiplatform.util.Res
import com.example.BlogMultiplatform.util.logout
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh




@Composable
fun SidePanel(onMenuClick: () -> Unit)
{
    val breakpoint= rememberBreakpoint()//it is trigged when the size of the screen changes
    if(breakpoint>Breakpoint.MD)
    {
        SidePanelInternal()
    }else {
        CollapsedSidePanel(onMenuClick)
    }

}

@Composable
private fun SidePanelInternal(){
    Column(
        modifier =Modifier
            .padding(leftRight = 40.px, topBottom = 50.px)
            .width(SIDE_PANEL_WIDTH.px)
            .height(100.vh)
            .position(Position.Fixed)
            .backgroundColor(Theme.Secondary.rgb)
            .zIndex(9)
    ) {
        Image(
            modifier=Modifier
                .margin(bottom = 60.px),
            src = Res.Image.logo,
            description = "Logo Image"
        )
        NavigationItems()

    }
}
@Composable
fun NavigationItems()
{
    val context= rememberPageContext()
    SpanText(
        modifier = Modifier
            .fontFamily(FONT_FAMILY)
            .margin(30.px)
            .color(Theme.HalfWhite.rgb)
            .fontSize(16.px),
        text="Dashboard"
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Home",
        selected=context.route.path==(Screen.AdminHome.route),
        icon=Res.PathIcon.home,
        onClick = {
            context.router.navigateTo(Screen.AdminHome.route)
        }
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Create Post",
        selected=context.route.path==(Screen.AdminCreate.route),
        icon=Res.PathIcon.create,
        onClick = {
            context.router.navigateTo(Screen.AdminCreate.route)
        }
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected=context.route.path==(Screen.AdminMyPost.route),
        title = "My Post",
        icon=Res.PathIcon.posts,
        onClick = {
            context.router.navigateTo(Screen.AdminMyPost.route)
        }
    )
    NavigationItem(
        title = "Logout",
        icon=Res.PathIcon.logout,
        onClick = {
            logout()
            context.router.navigateTo(Screen.AdminLogin.route)
        }
    )


}
@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    title: String,
    icon: String,
    onClick: () -> Unit
) {
    Row(
        modifier = NavigationItemStyle.toModifier()
            .then(modifier)
            .cursor(Cursor.Pointer)
            .onClick { onClick() }
            .padding(8.px),//added
        verticalAlignment = Alignment.CenterVertically
    ) {
        VectorIcon(
            modifier = Modifier.margin(right = 10.px),
           pathData =icon,
            selected = selected
        )
        SpanText(modifier = Modifier
            .id(Id.navigationText)
            .fontFamily(FONT_FAMILY)
            .fontSize(16.px)
            .thenIf(
                condition = selected,
                other=Modifier.color(Theme.Primary.rgb)
            ),
            text = title
        )
        }
    }


@Composable
private fun VectorIcon(
    modifier: Modifier = Modifier,
    selected: Boolean,

    pathData: String,
) {
    Svg(
        attrs = modifier
            .id(Id.svgParent)
            .width(24.px)
            .height(24.px)
            .toAttrs {
                attr("viewBox", "0 0 24 24")
                attr("fill", "none")
            }
    ) {
        Path(
            attrs = Modifier
                .id(Id.vectorIcon)
                .thenIf(
                    condition = selected,
                    other= Modifier.styleModifier {
                        property("stroke", Theme.Primary.hex)
                    }
                )
                .toAttrs{
                    attr("d",pathData)
                    attr("stroke-width","2")
                    attr("stroke-linecap","round")
                    attr("stroke-linejoin","round")

                }
        )
    }
}
@Composable
private fun CollapsedSidePanel(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(COLLAPSED_SIDE_PANEL_HEIGHT.px)
            .padding(leftRight = 24.px)
            .backgroundColor(Theme.Secondary.rgb),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FaBars(
            modifier = Modifier
                .margin(right = 24.px)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .onClick { onMenuClick() },
            size = IconSize.XL
        )
        Image(
            modifier = Modifier.width(80.px),
            src = Res.Image.logo,
            alt = "Logo Image"
        )
    }
}
@Composable
fun OverflowSidePanel(onMenuClose: () ->Unit )
{
    val scope = rememberCoroutineScope()
    val breakpoint= rememberBreakpoint()
    var translateX by remember { mutableStateOf((-100).percent) }
    var opacity by remember { mutableStateOf(0.percent) }

    LaunchedEffect(key1 = breakpoint){
        translateX=0.percent
        opacity=100.percent
        if(breakpoint>Breakpoint.MD){
        scope.launch {
            translateX= (-100).percent
            opacity=0.percent
            delay(500)
            onMenuClose()
        } }
    }


    Box(
        modifier=Modifier
            .fillMaxWidth()
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(9)
            .opacity(opacity)
            .styleModifier {
                property("transition", "opacity 300ms ease-in-out")
            }
            .backgroundColor(Theme.HalfBlack.rgb)
    ) {
        Column(
            modifier=Modifier
                .padding(all = 24.px)
                .fillMaxHeight()
                .translateX(translateX)
                .styleModifier {
                    property("transition", "translate 300ms ease-in-out")
                }
                .width(
                    if(breakpoint < Breakpoint.MD) 50.percent
                    else 25.percent)
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .backgroundColor(Theme.Secondary.rgb)
        ){
            Row(
                modifier = Modifier
                    .margin(bottom = 60.px,top=24.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaXmark(
                    modifier = Modifier
                        .margin(right = 20.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick {
                            scope.launch {
                                translateX= (-100).percent
                                opacity=0.percent
                                delay(500)
                                onMenuClose()
                            }
                        },
                    size = IconSize.LG
                )
                Image(
                    modifier = Modifier
                        .width(80.px)
                        .cursor(Cursor.Pointer),
                    src = Res.Image.logo,
                    alt = "Logo Image"
                )
            }
            NavigationItems()

        }


    }

}
