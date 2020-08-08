package com.example.sample_material_shop.shop

import androidx.compose.Composable
import androidx.compose.mutableStateOf
import androidx.compose.remember
import androidx.compose.runtime.*
import androidx.ui.tooling.preview.Preview

@Preview
@Composable
fun CartItemPreview() {
    StoreTheme {
        var quantity by remember { mutableStateOf(1) }
        var color by remember { mutableStateOf("Fiery Red") }

        ShoppingCartItem(
            Product(17, "Car paint", color, 1_50, AmountUnit.LITER, quantity),
            increase = { quantity++ },
            decrease = { quantity-- },
            updateColor = { color = nextProductColor(color) }
        ) {
            SampleImage(color)
        }
    }
}