package com.eggbucket.eggbucket_android.model

import java.util.Date

class CustomerId {
    var _id: String? = null
    var customerId: String? = null
    var customerName: String? = null
    var phoneNumber: String? = null
}

class DeliveryId {
    var _id: String? = null
    var firstName: String? = null
    var phoneNumber: String? = null
}

class OutletId {
    var _id: String? = null
    var outletNumber: String? = null
    var phoneNumber: String? = null
}

class AllOrders {
    var _id: String? = null
    var outletId: OutletId? = null
    var customerId: CustomerId? = null
    var vendorId: VendorId? = null
    var deliveryId: DeliveryId? = null
    var numTrays: String? = null
    var amount: String? = null
    var isUrgent: Boolean = false
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var __v: Int = 0
    var status: String? = null
}

class VendorId {
    var _id: String? = null
    var vendorName: String? = null
    var phoneNumber: String? = null
}