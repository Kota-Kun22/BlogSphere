package com.example.BlogMultiplatform.pages.admin

import androidx.compose.runtime.Composable
import com.example.BlogMultiplatform.components.AdminPageLayout
import com.example.BlogMultiplatform.components.SidePanel
import com.example.BlogMultiplatform.util.Constants.PAGE_WIDTH
import com.example.BlogMultiplatform.util.isUserLoggedIn
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.px

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
    AdminPageLayout {

    }
}