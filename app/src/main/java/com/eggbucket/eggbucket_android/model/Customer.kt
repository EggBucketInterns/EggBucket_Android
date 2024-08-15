package com.eggbucket.eggbucket_android.model


class Outlet {
    var _id: String? = null
    var outletNumber: String? = null
    var outletArea: String? = null
    var phoneNumber: String? = null
    var outletPartner: String? = null
    var deliveryPartner: ArrayList<String>? = null
    var img: String? = null
    var __v: Int = 0
}

class Customer {
    var _id: String? = null
    var customerId: String? = null
    var customerName: String? = null
    var location: String? = null
    var phoneNumber: String? = null
    var outlet: Outlet? = null
    var img: String? = null
    var __v: Int = 0
}

