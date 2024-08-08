package com.eggbucket.eggbucket_android.model.data

data class OutletPartner(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val aadharNumber: String,
    val img: String,
    val __v: Int
)

data class OutletPartnerResponse(
    val result: OutletPartner
)