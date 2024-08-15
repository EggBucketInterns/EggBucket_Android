package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eggbucket.eggbucket_android.model.data.OrderDetailsResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Order_Details_Screen : AppCompatActivity() {
    private lateinit var orderId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details_screen)

        // Retrieve the order ID from intent or use a default one
        orderId = intent.getStringExtra("ORDER_ID") ?: "66b764ddf2476d8c6f2770cb"
        intent.putExtra("orderId",orderId);
        // Call API to fetch order details
        getOrderDetails(orderId)

        val reached = findViewById<TextView>(R.id.reached)
        reached.setOnClickListener {
            startActivity(Intent(this, mode_of_payment::class.java))

            finish()
        }
    }

    private fun getOrderDetails(orderId: String) {
        val apiService = RetrofitInstance.apiService
        val call = apiService.getOrderDetails(orderId)

        call.enqueue(object : Callback<OrderDetailsResponse> {
            override fun onResponse(call: Call<OrderDetailsResponse>, response: Response<OrderDetailsResponse>) {
                if (response.isSuccessful) {
                    val orderDetails = response.body()
                    if (orderDetails != null) {
                        // Update UI with the fetched order details
                        populateOrderDetails(orderDetails)
                    }
                } else {
                    Log.e("OrderDetails", "Failed to fetch order details: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<OrderDetailsResponse>, t: Throwable) {
                Log.e("OrderDetails", "Error fetching order details", t)
            }
        })
    }

    private fun populateOrderDetails(orderDetails: OrderDetailsResponse) {
        // Update the UI elements with data
        findViewById<TextView>(R.id.orderIdTextView).text = orderDetails._id
        findViewById<TextView>(R.id.numTraysTextView).text = orderDetails.numTrays
        findViewById<TextView>(R.id.amountTextView).text = orderDetails.amount
        findViewById<TextView>(R.id.vendorNameTextView).text = orderDetails.deliveryId.firstName
        findViewById<TextView>(R.id.shopNameTextView).text = orderDetails.customerId.customerName
    }
}