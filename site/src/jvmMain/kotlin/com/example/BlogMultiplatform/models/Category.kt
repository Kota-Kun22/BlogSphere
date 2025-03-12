package com.example.BlogMultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
actual enum class Category(val color:String) {
    Technology(color = Theme.Green.hex),
    Programing(color=Theme.Yellow.hex),
    Design(color= Theme.Purple.hex)
}