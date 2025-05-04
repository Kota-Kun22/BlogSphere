package com.example.BlogMultiplatform.styles



import com.varabyte.kobweb.compose.ui.Modifier

import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.scale

import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle

import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba



val PostPreviewStyle = CssStyle {
    base {
        Modifier
            .scale(100.percent)
            .styleModifier {
                property("transition", "all 100ms ease-in-out")
                property("will-change", "transform")
            }
    }

    cssRule(":hover") {
        Modifier
            .boxShadow(
                offsetY = 0.px,
                offsetX = 0.px,
                blurRadius = 8.px,
                spreadRadius = 6.px,
                color = rgba(0, 0, 0, 0.06)
            )
            .scale(102.percent)
    }
}

val MainPostPreviewStyle = CssStyle {
    base {
        Modifier
            .scale(100.percent)
            .styleModifier {
                property("transition", "all 100ms ease-in-out")
                property("will-change", "transform")
            }
    }

    cssRule(":hover") {
        Modifier
            .boxShadow(
                offsetY = 0.px,
                offsetX = 0.px,
                blurRadius = 8.px,
                spreadRadius = 6.px,
                color = rgba(0, 162, 255, 0.06)
            )
            .scale(102.percent)
    }
}