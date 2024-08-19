package com.eggbucket.eggbucket_android.model.data

data class OrderDetailsResponse(
    val _id: String,
    val outletId: Any?,
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

data class OrderStatusUpdate(
    val status: String
)

data class OrderUpdateResponse(
    val _id: String,
    val outletId: String,
    val customerId: String,
    val deliveryId: String,
    val numTrays: String,
    val amount: String,
    val isUrgent: Boolean,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)


