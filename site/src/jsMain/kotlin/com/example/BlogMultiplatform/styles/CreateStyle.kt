package com.example.BlogMultiplatform.styles

import com.example.BlogMultiplatform.models.Theme
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.DefaultStyleSheet.hover
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.ms

val EditorKeyStyle = CssStyle {

    base {
        Modifier.styleModifier {
            property("background-color", "transparent")
            property("transition", "background 300ms ease-in-out")
        }
    }

    cssRule(":hover") {
        Modifier.styleModifier {
            property("background-color", Theme.Primary.hex)
            property("position", "relative")
        }
    }
}


