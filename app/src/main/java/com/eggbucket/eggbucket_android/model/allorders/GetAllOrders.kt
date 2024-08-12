package com.eggbucket.eggbucket_android.model.allorders
data class GetAllOrdersItem(
    val __v: Int,
    val _id: String,
    val amount: String,
    val createdAt: String,
    val customerId: CustomerId,
    val deliveryId: DeliveryId,
    val isUrgent: Boolean,
    val numTrays: String,
    val outletId: OutletId,
    val status:String,
    val updatedAt: String,
    val vendorId: VendorId
)
data class CustomerId(
    val _id: String,
    val customerId: String,
    val customerName: String,
    val phoneNumber: String
)
 class GetAllOrders:ArrayList<GetAllOrdersItem>()
data class DeliveryId(
    val _id: String,
    val firstName: String,
    val phoneNumber: String
)
data class OutletId(
    val _id: String,
    val outletNumber: String,
    val phoneNumber: String
)
data class VendorId(
    val _id: String,
    val phoneNumber: String,
    val vendorName: String
)