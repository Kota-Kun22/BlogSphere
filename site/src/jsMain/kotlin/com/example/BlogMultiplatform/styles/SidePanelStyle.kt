package com.example.BlogMultiplatform.styles
import com.example.BlogMultiplatform.models.Theme
import com.example.BlogMultiplatform.util.Id
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle


val NavigationItemStyle = CssStyle {

    base {
        Modifier.styleModifier {
            property("transition", "color 300ms ease-in-out, stroke 200ms ease-in-out") //  Global transition
            property("will-change", "color, stroke") // ✅ Hint browser to optimize updates
        }
    }

    // Smooth hover effect on text
    cssRule(":hover> #${Id.navigationText}")
    {
        Modifier.color(Theme.Primary.rgb)
    }

    //Default icon color
    cssRule("> #${Id.svgParent} >#${Id.vectorIcon}")
    {
        Modifier.styleModifier {
            property("stroke", Theme.White.hex)
            property("transition", "stroke 200ms ease-in-out") //  Correct transition syntax
        }
    }
    //default text color
    cssRule("> #${Id.navigationText}"){
        Modifier.styleModifier {
            property("color", Theme.White.hex)
            property("transition", "color 200ms ease-in-out") //  Correct transition syntax
        }
    }
    //smooth hover effect on icon
    cssRule(":hover > #${Id.svgParent} > #${Id.vectorIcon}"){
        Modifier.styleModifier {
            property("stroke", Theme.Primary.hex)
            property("transition", "stroke 200ms ease-in-out") //  Correct transition syntax
            property("will-change", "stroke") // ✅ Helps prevent lag
        }
    }
}