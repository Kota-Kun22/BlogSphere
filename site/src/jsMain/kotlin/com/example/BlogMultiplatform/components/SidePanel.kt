package com.example.BlogMultiplatform.components

import androidx.compose.runtime.Composable
import com.example.BlogMultiplatform.models.Theme
import com.example.BlogMultiplatform.styles.NavigationItemStyle
import com.example.BlogMultiplatform.util.Constants.FONT_FAMILY
import com.example.BlogMultiplatform.util.Constants.SIDE_PANEL_WIDTH
import com.example.BlogMultiplatform.util.Id
import com.example.BlogMultiplatform.util.Res
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier

import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

@Composable
fun SidePanel(){
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
            selected=true,
            icon=Res.PathIcon.home,
            onClick = {}
        )

        NavigationItem(
            modifier = Modifier.margin(bottom = 24.px),
            title = "Create Post",
            icon=Res.PathIcon.create,
            onClick = {}
        )

        NavigationItem(
            modifier = Modifier.margin(bottom = 24.px),
            title = "My Post",
            icon=Res.PathIcon.posts,
            onClick = {}
        )
        NavigationItem(
            title = "Logout",
            icon=Res.PathIcon.logout,
            onClick = {}
        )


    }
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
            .id("navigationText")
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
            .id("svgParent")
            .width(24.px)
            .height(24.px)
            .toAttrs {
                attr("viewBox", "0 0 24 24")
                attr("fill", "none")
            }
    ) {
        Path(
            attrs = Modifier
                .id("vectorIcon")
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