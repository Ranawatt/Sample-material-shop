package com.example.sample_material_shop.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.sample_material_shop.R

val ProductColors = mapOf(
    "Fiery Red"        to Color(0.634f, 0.000f, 0.000f),
    "Deep Blue"        to Color(0.000f, 0.480f, 0.596f),
    "Swirly Orange"    to Color(0.874f, 0.522f, 0.080f),
    "Fantastic Yellow" to Color(0.859f, 0.808f, 0.020f)
)

val ProductColorProgression = mapOf(
    "Fiery Red"        to "Deep Blue",
    "Deep Blue"        to "Swirly Orange",
    "Swirly Orange"    to "Fantastic Yellow",
    "Fantastic Yellow" to "Fiery Red"
)

val ProductColorSampleImages = mapOf(
    "Fiery Red"        to R.drawable.sample_red,
    "Deep Blue"        to R.drawable.sample_blue,
    "Swirly Orange"    to R.drawable.sample_orange,
    "Fantastic Yellow" to R.drawable.sample_yellow
)

fun nextProductColor(color: String) = ProductColorProgression.getOrElse(color) { color }

fun productColor(product: Product) =
    ProductColors.getOrElse(product.color) { Color(1.0f, 1.0f, 1.0f) }

val String.isProductColor: Boolean
    get() = this != "N/A"

@Composable
fun SampleImage(color: String) {
    Image(
        modifier = Modifier.fillMaxWidth().height(180.dp),
        contentScale = ContentScale.Crop,
        asset = imageResource(
            ProductColorSampleImages.getOrElse(color) { R.drawable.sample_orange }
        )
    )
}