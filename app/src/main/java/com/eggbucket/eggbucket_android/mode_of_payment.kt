package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.adapters.OrderViewModel
import com.eggbucket.eggbucket_android.model.UpdateReturnAmtResponse
import com.eggbucket.eggbucket_android.model.data.OrderDetailsResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.UpdateReturnAmountRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class mode_of_payment : AppCompatActivity() {
    private lateinit var orderViewModel: OrderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_mode_of_payment)
       val discountAmount=findViewById<EditText>(R.id.discount_amount)
        val amountR=findViewById<EditText>(R.id.amount_rs)
        val deli_completed = findViewById<Button>(R.id.deli_completed)
       // orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        val orderId = intent.getStringExtra("orderId")?:"";
        getOrderDetails(orderId)


        deli_completed.setOnClickListener {
            // Get the value from EditText
            val discount=discountAmount.text.toString().toInt()
            val amount = amountR.text.toString().toInt()
            var finalAmount=(amount - discount).toString()
           // orderViewModel.setAmount(finalAmount)

            // Create an Intent to start SecondActivity
            val intent = Intent(this, delivery_dashboard::class.java)
            intent.putExtra("amount",finalAmount)

            // Start SecondActivity
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

private fun getOrderDetails(orderId: String) {
    val apiService = RetrofitInstance.apiService
    val call = apiService.getOrderDetails(orderId)

    call.enqueue(object : Callback<OrderDetailsResponse> {
        override fun onResponse(call: Call<OrderDetailsResponse>, response: Response<OrderDetailsResponse>) {
            if (response.isSuccessful) {
                val orderDetails = response.body()

            } else {
                Log.e("OrderDetails", "Failed to fetch order details: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<OrderDetailsResponse>, t: Throwable) {
            Log.e("OrderDetails", "Error fetching order details", t)
        }
    })
}
fun updateReturnAmount(orderId: String, amount: Int) {
    val requestBody = UpdateReturnAmountRequest(orderId, amount)

    RetrofitInstance.instance.updateReturnAmount(requestBody).enqueue(object :
        Callback<UpdateReturnAmtResponse> {
        override fun onResponse(call: Call<UpdateReturnAmtResponse>, response: Response<UpdateReturnAmtResponse>) {
            if (response.isSuccessful) {
                val deliveryPartner = response.body()?.deleveryPartner

                deliveryPartner?.let {
                    println("Delivery Partner ID: ${it._id}")
                    println("Name: ${it.firstName} ${it.lastName}")
                    println("Driver License: ${it.driverLicenceNumber}")
                    println("Phone Number: ${it.phoneNumber}")
                    println("Image URL: ${it.img}")
                    println("Payments: ${it.payments}")
                }
            } else {
                println("Response error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<UpdateReturnAmtResponse>, t: Throwable) {
            // Handle error
            t.printStackTrace()
        }
    })
}

}