package com.eggbucket.eggbucket_android.model

data class Order(
    val _id: String,
    val outletId: String,
    val customerId: String,
    val vendorId: String,
    val deliveryId: String,
    val numTrays: String,
    val amount: String,
    val isUrgent: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val status: String
)

data class OrderCreate(
    val outletId: String,
    val customerId: String,
    val vendorId: String,
    val deliveryId: String,
    val numTrays: String,
    val amount: String,
    val isUrgent: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val status : String,
)


