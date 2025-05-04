package com.example.BlogMultiplatform.styles
import com.example.BlogMultiplatform.models.Theme
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.style.KobwebComposeStyleSheet.anyLink
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.DefaultStyleSheet.anyLink
import com.varabyte.kobweb.core.DefaultStyleSheet.hover
import com.varabyte.kobweb.silk.SilkStyleSheet.anyLink
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.active
import com.varabyte.kobweb.silk.style.selectors.visited
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.selectors.CSSSelector.PseudoClass.anyLink
import org.jetbrains.compose.web.dom.Col

//
//val CategoryItemStyle = CssStyle {
//    // Base link style
//    cssRule("a") {
//        Modifier
//            .color(Theme.White.rgb)
//            .styleModifier {
//                property("color", Theme.White.hex + " !important") // Force overridek
//                property("transition", "color 200ms ease-in-out")
//            }
//    }
//
//    // Hover state
//    cssRule("a:hover") {
//        Modifier
//            .color(Theme.Primary.rgb)
//            .styleModifier {
//                property("transition", "color 200ms ease-in-out")
//            }
//    }
//
//    // Visited state (keep white)
//    cssRule("a:visited") {
//        Modifier.color(Theme.White.rgb)
//    }
//
//    // Active state (while clicking)
//    cssRule("a:active") {
//        Modifier.color(Theme.Primary.rgb)
//    }
//}
//val CategoryItemStyle = CssStyle {
//    base {
//        Modifier
//            .color(Theme.White.rgb)
//            .styleModifier {
//                property("color", Theme.White.hex + " !important")
//                property("transition", "color 200ms ease-in-out")
//            }
//    }
//
//    cssRule(":hover") {
//        Modifier
//            .color(Theme.Primary.rgb)
//            .styleModifier {
//                property("transition", "color 200ms ease-in-out")
//            }
//    }
//
//    cssRule(":active") {
//        Modifier.color(Theme.Primary.rgb)
//    }
//
//    cssRule(":visited") {
//        Modifier.color(Theme.White.rgb)
//    }
//}

//val CategoryItemStyle = CssStyle {
//    base {
//        Modifier
//            .color(Theme.White.rgb)
//            .styleModifier {
//                property("color", Theme.White.hex + " !important") // ensures override
//                property("transition", "color 200ms ease-in-out")
//                property("cursor", "pointer")
//            }
//    }
//    // Optional: All link states (any link)
//    cssRule("a:any-link") {
//        Modifier.color(Theme.White.rgb)
//    }
//
//    // Handles hover effect
//    cssRule(":hover") {
//        Modifier
//            .color(Theme.Primary.rgb)
//            .styleModifier {
//                property("transition", "color 200ms ease-in-out")
//            }
//    }
//
//    // Handles visited links
//    cssRule(":visited") {
//        Modifier.color(Theme.White.rgb)
//    }
//
//    // Handles active click state
//    cssRule(":active") {
//        Modifier.color(Theme.Primary.rgb)
//    }
//
//
//}

//val CategoryItemStyle = CssStyle {
//    cssRule("a") {
//        Modifier
//            .styleModifier {
//                property("color", "${Theme.White.hex} !important")
//                property("transition", "color 200ms ease-in-out")
//                property("text-decoration", "none !important")
//            }
//    }
//
//    cssRule("a:hover") {
//        Modifier
//            .styleModifier {
//                property("color", "${Theme.Primary.hex} !important")
//            }
//    }
//
//    cssRule("a:visited") {
//        Modifier
//            .styleModifier {
//                property("color", "${Theme.White.hex} !important")
//            }
//    }
//
//    cssRule("a:active") {
//        Modifier
//            .styleModifier {
//                property("color", "${Theme.Primary.hex} !important")
//            }
//    }
//}

val CategoryItemStyle = CssStyle {
    base {
        Modifier
            .color(Colors.White)
            .styleModifier {
                property("transition", "color 200ms ease-in-out")
                property("will-change", "color")
            }
    }

    // For link states (replaces anyLink)
    cssRule("a:any-link") {
        Modifier.color(Colors.White)
    }

    // Hover state
    cssRule(":hover") {
        Modifier.color(Colors.White)
    }

    // Optional: Add focus state if needed
    cssRule(":focus") {
        Modifier.color(Theme.Secondary.rgb)
    }
}
