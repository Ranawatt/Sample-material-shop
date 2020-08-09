package com.example.sample_material_shop.shop

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val StoreColors = lightColors(
    primary = Color(0xffdd0d3e),
    onPrimary = Color.White,
    secondary = Color(0xfffbdf61),
    onSecondary = Color.Black,
    surface = Color(0xff060730),
    onSurface = Color.White
)

@Composable
fun StoreTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = StoreColors,
        content = content
    )
}