package com.eggbucket.eggbucket_android.model

data class VendorItem(
    val coordinates: Coordinates,
    val _id: String,
    val vendorName: String,
    val vendorType: String,
    val location: String,
    val phoneNumber: String,
    val openingTime: String,
    val closingTime: String,
    val deliveryRadius: Int,
    val paymentMethods: List<String>,
    val __v: Int
)

data class Coordinates(
    val type: String,
    val coordinates: List<Any>
)
data class VendorResponse(
    val result: VendorItem
)