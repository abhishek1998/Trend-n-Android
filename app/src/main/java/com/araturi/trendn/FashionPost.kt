package com.araturi.trendn

import java.io.Serializable

class FashionPost(
    var category: String,
    var price: Float,
    var brand: String,
    var style: String,
    var quantity: Int,
    var rating: Int,
    var size: String,
    var img: String
) : Serializable {
    override fun toString(): String {
        return "${brand} \t\t ${style} \t\t CAD ${price}"
    }
}