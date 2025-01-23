package com.example.BlogMultiplatform.pages.admin

import androidx.compose.runtime.Composable
import com.example.BlogMultiplatform.models.Theme
import com.example.BlogMultiplatform.util.Res
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.graphics.Image
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input

@Page
@Composable
fun LoginScreen()
{
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .backgroundColor(Theme.LightGray.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

           Image(
               modifier = Modifier
                   .margin(bottom = 50.px)
                   .width(100.px),
               src = Res.Image.logo,
               description = "Logo Image"
           )

           Input(
               type= InputType.Text,
               attrs = Modifier.margin(bottom = 12.px)
                   .width(350.px)
                   .height(54.px)
                   .padding(leftRight = 20.px)
                   .backgroundColor(Color.white)
                   .toAttrs{attr("PlaceHolder","Password")}
           )

        }
    }

}