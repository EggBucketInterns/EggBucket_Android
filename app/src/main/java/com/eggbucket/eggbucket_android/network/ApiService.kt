package com.eggbucket.eggbucket_android.network

import com.eggbucket.eggbucket_android.model.data.OutletPartnerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("outletPartners/egg-bucket-b2b/outlet_partner/{id}")
    fun getOutletPartner(@Path("id") id: String): Call<OutletPartnerResponse>
}