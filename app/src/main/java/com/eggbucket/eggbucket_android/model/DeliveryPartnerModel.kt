package com.eggbucket.eggbucket_android.model

class DeliveryPartner {
    var _id: String? = null
    var firstName: String? = null
    var lastName: String? = null
}

class OutletPartner {
    var _id: String? = null
    var firstName: String? = null
    var lastName: String? = null
}

class DeliveryPartnerModel {
    var _id: String? = null
    var outletNumber: String? = null
    var outletArea: String? = null
    var phoneNumber: String? = null
    var outletPartner: OutletPartner? = null
    var deliveryPartner: ArrayList<DeliveryPartner>? = null
    var img: String? = null
    var __v: Int = 0
}
