package com.eggbucket.eggbucket_android.model.data

data class DeliveryPartner(
    val __v: Int,
    val _id: String,
    val driverLicenceNumber: String,
    val firstName: String,
    val img: String,
    val lastName: String,
    val phoneNumber: String
)

data class DeliveryPartnerResponse(
    val result: DeliveryPartner
)