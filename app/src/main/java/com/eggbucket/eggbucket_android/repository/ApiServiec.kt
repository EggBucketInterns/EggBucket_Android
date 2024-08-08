package com.eggbucket.eggbucket_android.repository

import com.eggbucket.eggbucket_android.model.DeliveryPartners
import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiServiec {
    @GET("egg-bucket-b2b/displayAll-delivery_partner")
    suspend fun getDeliveryPartnersData(DeliveryPartener: DeliveryPartners): ArrayList<DeliveryPartnersItem>
}
object ApiClient {
    private const val BASE_URL = "https://eb-trial.onrender.com/deliveryDrivers/"

    fun create(): ApiServiec {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(ApiServiec::class.java)
    }

}