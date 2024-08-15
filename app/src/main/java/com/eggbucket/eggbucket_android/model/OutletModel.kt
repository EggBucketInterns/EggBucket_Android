//class DeliveryPartner {
//    var _id: String? = null
//    var firstName: String? = null
//    var lastName: String? = null
//}
//
//class OutletPartner {
//    var _id: String? = null
//    var firstName: String? = null
//    var lastName: String? = null
//}
//
//class OutletModel {
//    var _id: String? = null
//    var outletNumber: String? = null
//    var outletArea: String? = null
//    var phoneNumber: String? = null
//    var outletPartner: OutletPartner? = null
//    var deliveryPartner: ArrayList<DeliveryPartner>? = null
//    var img: String? = null
//    var __v: Int = 0
//}
data class OutletResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: List<Outlet>
)

data class Outlet(
    val _id: String,
    val outletNumber: String,
    val outletArea: String,
    val phoneNumber: String,
    val outletPartner: OutletPartner,
    val deliveryPartner: List<DeliveryPartner>,
    val img: String,
    val __v: Int
)

data class OutletPartner(
    val _id: String,
    val firstName: String,
    val lastName: String
)

data class DeliveryPartner(
    val _id: String,
    val firstName: String,
    val lastName: String
)