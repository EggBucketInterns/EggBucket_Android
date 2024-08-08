package com.eggbucket.eggbucket_android.model

data class PendingOrder(
    val orderId: String,
    val customerId: String,
    val customerIdNumber: String,
    val numberOfTrays: Int,
    val createdAt: String,
    val deliveredAt: String,
    val orderAmount: Double,
    val orderStatus: String
)
