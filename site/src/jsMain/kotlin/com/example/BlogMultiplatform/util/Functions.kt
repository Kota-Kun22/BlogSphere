package com.example.BlogMultiplatform.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.BlogMultiplatform.navigation.Screen
import com.varabyte.kobweb.core.rememberPageContext
import kotlinx.browser.localStorage
import org.w3c.dom.get
import org.w3c.dom.set

@Composable
fun isUserLoggedIn(content: @Composable () -> Unit) {
    val context = rememberPageContext()
    val remembered = remember { localStorage["remember"].toBoolean() }
    val userId = remember { localStorage["userId"] }
    var userIdExists by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        userIdExists = if (!userId.isNullOrEmpty()) checkUserId(id = userId) else false
        if (!remembered || !userIdExists) {
            context.router.navigateTo(Screen.AdminLogin.route)//"/admin/login"
        }
    }

    if (remembered && userIdExists) {
        content()
    } else {
        println("Loading...")
    }
}
fun logout()
{
    localStorage["remember"]="false"
    localStorage["userId"]=""
    localStorage["username"]=""

}