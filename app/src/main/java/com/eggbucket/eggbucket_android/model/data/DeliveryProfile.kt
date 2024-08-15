package com.eggbucket.eggbucket_android.model.data

data class DeliveryPartnerrr(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val driverLicenceNumber: String,
    val phoneNumber: String,
    val img: String,
    val payments: List<Payment>,
    val __v: Int
)

data class Payment(
    val oId: String,
    val returnAmt: Int,
    val _id: String
)
