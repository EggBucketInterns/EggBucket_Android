package com.eggbucket.eggbucket_android.model

data class Orders(
    val orderId: String,
    val customerId: String,
    val customerImageResId: Int, // Resource ID for the customer image
    val numberOfTrays: Int,
    val createdAt: String,
    val deliveredAt: String,
    val orderAmount: Double,
    val orderStatus: String
)
