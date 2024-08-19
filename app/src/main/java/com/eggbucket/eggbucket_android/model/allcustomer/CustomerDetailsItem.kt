package com.eggbucket.eggbucket_android.model.allcustomer

data class CustomerDetailsItem(
    val __v: Int,
    val _id: String,
    val businessName: String,
    val customerId: String,
    val customerName: String,
    val img: String,
    val location: String,
    val outlet: Outlet,
    val phoneNumber: String
)