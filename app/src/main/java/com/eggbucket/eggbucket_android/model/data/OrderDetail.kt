package com.eggbucket.eggbucket_android.model.data

data class OrderDetailsResponse(
    val _id: String,
    val outletId: Any?, // Since outletId is null, we use Any? to accommodate any future data type
    val customerId: CustomerId,
    val deliveryId: DeliveryId,
    val numTrays: String,
    val amount: String,
    val isUrgent: Boolean,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class CustomerId(
    val _id: String,
    val customerId: String,
    val customerName: String,
    val phoneNumber: String
)

data class DeliveryId(
    val _id: String,
    val firstName: String,
    val phoneNumber: String
)
