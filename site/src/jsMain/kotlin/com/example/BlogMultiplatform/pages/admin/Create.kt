package com.example.BlogMultiplatform.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.BlogMultiplatform.components.AdminPageLayout
import com.example.BlogMultiplatform.models.Category
import com.example.BlogMultiplatform.models.Theme
import com.example.BlogMultiplatform.util.Constants.FONT_FAMILY
import com.example.BlogMultiplatform.util.Constants.SIDE_PANEL_WIDTH
import com.example.BlogMultiplatform.util.isUserLoggedIn
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.px
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.dom.svg.SVGColorChannel
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.toAttrs

import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul


@Page
@Composable
fun CreatePage()
{
    isUserLoggedIn {
        CreateScreen()
    }

}
@Composable
fun CreateScreen()
{
    val breakpoint= rememberBreakpoint()
    var popularSwitch by remember { mutableStateOf(false) }
    var mainSwitch by remember { mutableStateOf(false) }
    var sponsorSwitch by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(Category.Programing) }
    AdminPageLayout {

        Box(modifier= Modifier
            .fillMaxSize()
            .margin(topBottom = 50.px)
            .padding( left = if(breakpoint>Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            contentAlignment = Alignment.TopCenter
        ){
            Column(
                modifier= Modifier
                    .fillMaxSize()
                    .maxWidth(700.px),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                SimpleGrid(numColumns = numColumns(base=1,sm=3)){

                    Row(
                        modifier=Modifier
                            .height(40.px)
                            .margin(
                                right= if(breakpoint <Breakpoint.SM) 0.px else 24.px,
                                bottom = if(breakpoint<Breakpoint.SM) 12.px else 0.px),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Switch(
                            modifier = Modifier.margin(right=8.px),
                            checked= popularSwitch,
                            onCheckedChange = { popularSwitch = it},
                            size= SwitchSize.LG,
                        )
                        SpanText(
                            modifier= Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Theme.HalfBlack.rgb),
                            text = "Popular"
                        )
                    }
                    Row(
                        modifier=Modifier
                            .margin(
                                right= if(breakpoint <Breakpoint.SM) 0.px else 24.px,
                                bottom = if(breakpoint<Breakpoint.SM) 12.px else 0.px),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Switch(
                            modifier = Modifier.margin(right=8.px),
                            checked= mainSwitch,
                            onCheckedChange = {mainSwitch=it},
                            size= SwitchSize.LG
                        )
                        SpanText(
                            modifier= Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Theme.HalfBlack.rgb),
                            text = "Main"
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Switch(
                            modifier = Modifier.margin(right=8.px),
                            checked= sponsorSwitch,
                            onCheckedChange = {sponsorSwitch = it},
                            size= SwitchSize.LG
                        )
                        SpanText(
                            modifier= Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Theme.HalfBlack.rgb),
                            text = "Sponsored"
                        )
                    }
                }
                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(topBottom = 12.px)
                        .padding(leftRight = 20.px)
                        .backgroundColor(Theme.LightGray.rgb)
                        .borderRadius(r = 4.px)
                        .border (
                            width=8.px,
                            style=LineStyle.None,
                            color= Color.transparent
                            )
                        .outline(
                            width = 0.px,
                            style = LineStyle.None,
                            color = Color.transparent,

                        )
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .toAttrs {
                            attr("placeholder", "Title")

                        }
                )
                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .backgroundColor(Theme.LightGray.rgb)
                        .borderRadius(r = 4.px)
                        .border (
                            width=8.px,
                            style=LineStyle.None,
                            color= Color.transparent
                        )
                        .outline(
                            width = 0.px,
                            style = LineStyle.None,
                            color = Color.transparent,

                            )
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .toAttrs {
                            attr("placeholder", "Subtitle")

                        }
                )
                CategoryDropDown(selectedCategory = Category.Programing, onCategorySelect ={selectedCategory=it} )


            }


        }
    }
}
@Composable
fun CategoryDropDown(
    selectedCategory:Category,
    onCategorySelect:(Category)->Unit)
{
    Box(
        modifier = Modifier
            .margin(topBottom = 12.px)
            .classNames("dropdown")
            .fillMaxWidth()
            .height(54.px)
            .backgroundColor(Theme.LightGray.rgb)
            .cursor(Cursor.Pointer)
            .attrsModifier {
                attr("data-bs-toggle", "dropdown")
            }
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(leftRight = 20.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .fontSize(16.px)
                    .fontFamily(FONT_FAMILY),
                text = selectedCategory.name
            )
            Box(modifier = Modifier.classNames("dropdown-toggle"))
        }
        Ul(
            attrs = Modifier
                .fillMaxWidth()
                .classNames("dropdown-menu")
                .toAttrs()
        ){
            Category.entries.forEach { category ->
                Li {
                   A(
                        attrs = Modifier
                            .classNames("dropdown-item")
                            .color(Colors.Black)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(16.px)
                            .onClick { onCategorySelect(category) }
                            .toAttrs()
                    ) {
                        Text(value = category.name)
                    }
                }
            }
        }

    }
}