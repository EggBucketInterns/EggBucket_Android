package com.eggbucket.eggbucket_android


import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eggbucket.eggbucket_android.model.UpdateReturnAmtResponse
import com.eggbucket.eggbucket_android.model.data.OrderDetailsResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.UpdateReturnAmountRequest

class PaymentCollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_payment_collection)

    }
}