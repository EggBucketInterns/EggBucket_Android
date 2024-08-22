package com.eggbucket.eggbucket_android.network

import OutletResponse
import com.eggbucket.eggbucket_android.model.Customer
import com.eggbucket.eggbucket_android.model.DeliveryPartnerModel
import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem
import com.eggbucket.eggbucket_android.model.Order
import com.eggbucket.eggbucket_android.model.OrderCreate
import com.eggbucket.eggbucket_android.model.StatusUpdate
import com.eggbucket.eggbucket_android.model.UpdateReturnAmtResponse
import com.eggbucket.eggbucket_android.model.VendorItem
import com.eggbucket.eggbucket_android.model.allcustomer.CustomerDetailsItem
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.data.DeliveryPartnerrr
import com.eggbucket.eggbucket_android.model.data.OrderDetailsResponse
import com.eggbucket.eggbucket_android.model.data.OrderUpdateResponse
import com.eggbucket.eggbucket_android.model.data.OutletPartnerResponse

import com.eggbucket.eggbucket_android.model.login.LoginRequest
import com.eggbucket.eggbucket_android.model.login.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("outletPartners/egg-bucket-b2b/outlet_partner/{id}")
    fun getOutletPartner(@Path("id") id: String): Call<OutletPartnerResponse>

    @GET("deliveryDrivers/egg-bucket-b2b/delivery_partner/{id}")
    fun getDeliveryPartner(@Path("id") id: String): Call<DeliveryPartnerrr>

    @GET("deliveryDrivers/egg-bucket-b2b/displayAll-delivery_partner")
    suspend fun getAllDeliveryPartners(): List<DeliveryPartnersItem>

    @GET("vendors/egg-bucket-b2b/getAllVendor")
    suspend fun getAllVendors(): ArrayList<VendorItem>

    @POST("orders/egg-bucket-b2b/create-order")
    fun createOrder(@Body order: OrderCreate): Call<Void>

    @GET("orders/egg-bucket-b2b/getAllOrder")
    suspend fun getAllOrders(): ArrayList<GetAllOrdersItem>

    @GET("orders/egg-bucket-b2b/order/{orderId}")
    fun getOrderDetails(
        @Path("orderId") orderId: String
    ): Call<OrderDetailsResponse>

    @POST("auth/egg-bucket-b2b/OutletPartnerLogin")
    fun outletPartnerLogin(@Body request: LoginRequest): Call<LoginResponse?>

    @POST("auth/egg-bucket-b2b/driverLogin")
    fun deliveryPartnerLogin(@Body request: LoginRequest): Call<LoginResponse?>

    @GET("orders/egg-bucket-b2b/getAllOrder")
    suspend fun getOrdersByOutletId(@Query("outletId") outletId: String): ArrayList<GetAllOrdersItem>


    @GET("orders/egg-bucket-b2b/getAllOrder")
    suspend fun getOrderByOutletIdByStatus(
        @Query("outletId") outletId: String,
        @Query("status") status: String
    ): ArrayList<GetAllOrdersItem>

//    @PATCH("orders/egg-bucket-b2b/order/{id}")
//    suspend fun updateOrderStatus(
//        @Path("id") orderId: String,
//        @Body statusUpdate: Map<String, String>): GetAllOrdersItem
//
//        @PATCH("orders/egg-bucket-b2b/order/{id}")
//        suspend fun updateOrderStatus(
//            @Path("id") orderId: String,
//            @Body status: StatusUpdate
//        ):Call<Order>

    @PATCH("orders/egg-bucket-b2b/order/{id}")
    suspend fun updateStatusCompleted(
        @Path("id") orderId: String,
        @Body statusUpdate: Map<String, String>
    ): Order

    @GET("egg-bucket-b2b/get-all-outlets")
    suspend fun getDeliveryPartnerByOutlet(@Query("outletPartner") outletId: String): DeliveryPartnerModel

    @PATCH("orders/egg-bucket-b2b/order/{id}")
    suspend fun updateOrderStatus(
        @Path("id") orderId: String,
        @Body statusUpdate: Map<String, String>
    ): GetAllOrdersItem

    @PATCH("orders/egg-bucket-b2b/order/{id}")
    suspend fun updateOrderStatus(
        @Path("id") orderId: String,
        @Body status: StatusUpdate
    ): Call<Order>

    @GET("orders/egg-bucket-b2b/getAllOrder")
    suspend fun getOrdersByDeliveryId(@Query("deliveryId") deliveryId: String): ArrayList<GetAllOrdersItem>


    //@GET("orders/egg-bucket-b2b/getAllOrder")
  //  suspend fun getOrdersByDeliveryIdByStatus(@Query("deliveryId") deliveryId: String,@Query("status") status: String): ArrayList<GetAllOrdersItem>


    @GET("orders/egg-bucket-b2b/getAllOrder")
    suspend fun getOrderByDeliveryIdByStatus(
        @Query("deliveryId") deliveryId: String,
        @Query("status") status: String
    ): ArrayList<GetAllOrdersItem>

    @PATCH("payment/egg-bucket-b2b/incReturnAmt")
    fun updateReturnAmount(@Body body: UpdateReturnAmountRequest): Call<UpdateReturnAmtResponse>

    @PATCH("payment/egg-bucket-b2b/incCollectionAmt")
    fun updateCollectionAmount(@Body body: UpdateReturnAmountRequest): Call<UpdateReturnAmtResponse>

//    @PATCH("payment/egg-bucket-b2b/decReturnAmt")
//    fun decreaseReturnAmount(@Body body: UpdateReturnAmountRequest): Call<UpdateReturnAmtResponse>

    @PATCH("payment/egg-bucket-b2b/decReturnAmt")
    suspend fun decreaseReturnAmount(@Body body: UpdateReturnAmountRequest1): Response<UpdateReturnAmtResponse>

    @GET("customers/egg-bucket-b2b/getAllCustomer")
    suspend fun getCustomerByID(
        @Query("customerId") customerId: String
    ) : ArrayList<Customer>

    @GET("customers/egg-bucket-b2b/getAllCustomer")
     fun getCustomerImageByID(
        @Query("customerId") customerId: String?
    ) : Call<ArrayList<CustomerDetailsItem>>

    @GET("customers/egg-bucket-b2b/customer/{customerId}")
    fun getCustomerImageById(
        @Path("customerId") customerId: String
    ): Call<CustomerDetailsItem>


//    @GET("egg-bucket-b2b/get-all-outlets")
//    suspend fun getOutletByOutletPartnerID(
//        @Query("outletPartner") outletPartner : String
//    ) : Array<OutletModel>

    @GET("egg-bucket-b2b/get-all-outlets")
    suspend fun getOutletByOutletPartnerID(
        @Query("outletPartner") outletPartner: String
    ): OutletResponse

    @PATCH("orders/egg-bucket-b2b/order/{orderId}")
    fun updateOrderAmount(
        @Path("orderId") orderId: String,
        @Body requestBody: Map<String, String>
    ): Call<OrderUpdateResponse>

    @GET("customers/egg-bucket-b2b/getAllCustomer")
    suspend fun getCustomerByOutletId(
        @Query("outlet") outlet: String
    ) : ArrayList<Customer>


    @GET("customers/egg-bucket-b2b/getAllCustomer")
    suspend fun getDeliveryPartnerByDeliveryPartnerName(
        @Query("firstName") firstName:String
    ) : ArrayList<DeliveryPartnerrr>
}

data class UpdateReturnAmountRequest(
    val orderId: String,
    val amount: Int
)

data class UpdateReturnAmountRequest1(
    val orderId: String,
    val amount: String
)




