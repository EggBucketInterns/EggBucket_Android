package com.eggbucket.eggbucket_android.network

import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem
import com.eggbucket.eggbucket_android.model.Order
import com.eggbucket.eggbucket_android.model.OrderCreate
import com.eggbucket.eggbucket_android.model.VendorItem
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.data.DeliveryPartner
import com.eggbucket.eggbucket_android.model.data.OutletPartnerResponse
import com.eggbucket.eggbucket_android.model.login.LoginRequest
import com.eggbucket.eggbucket_android.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("outletPartners/egg-bucket-b2b/outlet_partner/{id}")
    fun getOutletPartner(@Path("id") id: String): Call<OutletPartnerResponse>

    @GET("deliveryDrivers/egg-bucket-b2b/delivery_partner/{id}")
    fun getDeliveryPartner(@Path("id") id: String): Call<DeliveryPartner>

    @GET("deliveryDrivers/egg-bucket-b2b/displayAll-delivery_partner")
    suspend fun getAllDeliveryPartners(): List<DeliveryPartnersItem>

    @GET("vendors/egg-bucket-b2b/getAllVendor")
    suspend fun getAllVendors():ArrayList<VendorItem>

    @POST("orders/egg-bucket-b2b/create-order")
    fun createOrder(@Body order: OrderCreate): Call<Void>

    @GET("orders/egg-bucket-b2b/getAllOrder")
    suspend fun getAllOrders():ArrayList<GetAllOrdersItem>

    @POST("auth/egg-bucket-b2b/OutletPartnerLogin")
    fun outletPartnerLogin(@Body request: LoginRequest): Call<LoginResponse?>

    @POST("auth/egg-bucket-b2b/driverLogin")
    fun deliveryPartnerLogin(@Body request: LoginRequest): Call<LoginResponse?>

}