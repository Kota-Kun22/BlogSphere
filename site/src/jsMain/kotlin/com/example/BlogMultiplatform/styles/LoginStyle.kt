package com.example.BlogMultiplatform.styles

import com.example.BlogMultiplatform.models.Theme
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms

import org.jetbrains.compose.web.css.px


fun Modifier.loginInputStyle(isFocused: Boolean) = this
    .border(2.px, LineStyle.Solid, color = if (isFocused) Theme.Primary.rgb else Color.gray)
    .borderRadius(8.px)
    .padding(12.px)
    .transition(Transition.Companion.of("border-color", 300.ms))
