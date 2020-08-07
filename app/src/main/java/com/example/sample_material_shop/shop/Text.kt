package com.example.sample_material_shop.shop

fun formatAmount(product: Product): String {
    val v = product.amount * product.quantity
    val i = v / 100
    val f = "%02d".format( v - 100 * i)
    return "${i}.${f} ${product.unit}"
}