package com.example.BlogMultiplatform.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.BlogMultiplatform.models.Theme
import com.example.BlogMultiplatform.models.User
import com.example.BlogMultiplatform.models.UserWithOutPassword
import com.example.BlogMultiplatform.styles.loginInputStyle
import com.example.BlogMultiplatform.util.Constants.FONT_FAMILY
import com.example.BlogMultiplatform.util.Id
import com.example.BlogMultiplatform.util.Res
import com.example.BlogMultiplatform.util.checkUserExistence
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.style.KobwebComposeStyleSheet.attr
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onFocus
import com.varabyte.kobweb.compose.ui.modifiers.onFocusIn
import com.varabyte.kobweb.compose.ui.modifiers.onFocusOut
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Button
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Storage
import org.w3c.dom.set

@Page
@Composable
fun LoginScreen()
{
    val context= rememberPageContext()
    val scope= rememberCoroutineScope()
    var isUsernameFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var errorText by remember{mutableStateOf(" ")}

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
                   .id(Id.usernameInput)
                   .width(350.px)
                   .height(54.px)
                   .fontSize(14.px)
                   .padding(leftRight = 20.px)
                   .backgroundColor(Color.white)
                   .onFocusIn{ isUsernameFocused = true }
                   .onFocusOut { isUsernameFocused = false  }
                   .loginInputStyle(isFocused = isUsernameFocused)
                   .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                   .toAttrs{attr("PlaceHolder","Username")}
           )

            Input(
                type= InputType.Password,
                attrs = Modifier
                    .margin(bottom = 24.px)
                    .id(Id.passwordInput)
                    .width(350.px)
                    .height(54.px)
                    .fontSize(14.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Color.white)
                    .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .onFocusIn{ isPasswordFocused = true }
                    .onFocusOut { isPasswordFocused = false  }
                    .loginInputStyle(isFocused = isPasswordFocused)
                    .toAttrs{ attr("PlaceHolder","Password")}
            )
           Button(
               attrs = Modifier
                   .width(350.px)
                   .height(54.px)
                   .backgroundColor(Theme.Primary.rgb)
                   .color(Color.white)
                   .border(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                   .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                   .borderRadius(r = 4.px)
                   .fontFamily(FONT_FAMILY)
                   .cursor(Cursor.Pointer)
                   .fontWeight(FontWeight.Medium)
                   .fontSize(14.px)
                   .onClick {
                       scope.launch {

                           val username= (document.getElementById(Id.usernameInput) as HTMLInputElement).value
                           val password= (document.getElementById(Id.passwordInput) as HTMLInputElement).value

                           if(username.isNotEmpty()&& password.isNotEmpty())
                           {
                               val user= checkUserExistence(user = User(username = username, password = password))
                               if(user!=null){
                                   rememberLoggedIn(remember = true, user = user)
                                   context.router.navigateTo("/admin/home")

                               }else
                               {
                                   errorText="The user doesn't exist"
                                   delay(3000)
                                   errorText=""
                               }
                           }
                           else
                           {
                               errorText="Input fields are empty"
                               delay(3000)
                               errorText=""

                           }

                       }

                   }
                   .toAttrs()

           ) {
               SpanText(text = "Let's Write")
           }

            SpanText(modifier =Modifier.width(350.px)
                .color(Color.red)
                .textAlign(TextAlign.Center),
                text = errorText
            )

        }
    }

}
private fun rememberLoggedIn(remember:Boolean,user:UserWithOutPassword?=null)
{
    localStorage["remember"]=remember.toString()
    if(user!=null)
    {
        localStorage["userId"]=user.id
        localStorage["username"]=user.username

    }

}