package com.example.BlogMultiplatform.styles

import com.example.BlogMultiplatform.models.Theme
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.DefaultStyleSheet.focus
import com.varabyte.kobweb.silk.SilkStyleSheet.focus
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba

//val NewsletterInputStyle by ComponentStyle {
//    base {
//        Modifier
//            .outline(
//                width = 1.px,
//                style = LineStyle.Solid,
//                color = Colors.Transparent
//            )
//            .border(
//                width = 1.px,
//                style = LineStyle.Solid,
//                color = Colors.Transparent
//            )
//            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
//    }
//    focus {
//        Modifier
//            .outline(
//                width = 1.px,
//                style = LineStyle.Solid,
//                color = Theme.Primary.rgb
//            )
//            .border(
//                width = 1.px,
//                style = LineStyle.Solid,
//                color = Theme.Primary.rgb
//            )
//    }
//}
val NewsletterInputStyle = CssStyle {
    base {
        Modifier
            .outline(
                width = 1.px,
                style = LineStyle.Solid,
                color = Colors.Transparent
            )
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = Colors.Transparent
            )
            .styleModifier {
                property("transition", "all 300ms ease-in-out")
                property("will-change", "border, outline")
            }
    }

    // New hover effect
    cssRule(":hover") {
        Modifier
            .outline(
                width = 1.px,
                style = LineStyle.Solid,
                color = rgba(r = 0, g = 162, b = 255, a = 0.3)
            )
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color =rgba(r = 0, g = 162, b = 255, a = 0.3)
            )
    }

    cssRule(":focus") {
        Modifier
            .outline(
                width = 1.px,
                style = LineStyle.Solid,
                color = Theme.Primary.rgb
            )
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = Theme.Primary.rgb
            )
    }
}