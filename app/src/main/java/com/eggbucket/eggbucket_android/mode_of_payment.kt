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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class mode_of_payment : AppCompatActivity() {
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var orderId: String
    private var Amount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_mode_of_payment)

        val discountAmount = findViewById<EditText>(R.id.discount_amount)
        val deli_completed = findViewById<Button>(R.id.deli_completed)
        // orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        // Retrieve the order ID from the intent
        orderId = intent.getStringExtra("order_ID") ?: ""
        if (orderId.isEmpty()) {
            Log.e("OrderID", "Order ID is missing!")
            finish()
            return
        }
        Log.d("OrderID", "Order ID in mode_of_payment: $orderId")

        getOrderDetails(orderId)
        Log.d("OrderDetails", "Order Details fetched ${getOrderDetails(orderId)}")

        // Set up the listener for the 'Delivery Completed' button
        deli_completed.setOnClickListener {
            val discount = discountAmount.text.toString().toIntOrNull() ?: 0
//            val amount = intent.getStringExtra("amount")?.toIntOrNull() ?: 0
            val finalAmount:Int = Amount - discount

            // Update the order and return/collection amounts
            updateReturnAmount(orderId, finalAmount)
            updateOrderStatus(orderId)
            updateCollectionAmount(orderId, finalAmount)

            // Navigate to the delivery dashboard
            val intent = Intent(this, delivery_dashboard::class.java)
            intent.putExtra("amount", finalAmount.toString())
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateOrderStatus(orderId: String) {
        val statusUpdate = mapOf("status" to "delivered")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.apiService.updateOrderStatus(orderId, statusUpdate)
                Log.d("OrderUpdate", "Order updated successfully: $response")
            } catch (e: Exception) {
                Log.e("OrderUpdate", "Failed to update order status: ${e.message}")
            }
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
                        Toast.makeText(this@mode_of_payment, "Order details fetched successfully", Toast.LENGTH_SHORT).show()
                        Log.d("OrderDetails", "Order details fetched successfully: $orderDetails")
                        populateOrderDetails(orderDetails)
                        // Update finalAmount after fetching data
                        Amount = orderDetails.amount.toInt()
                    } else {
                        Toast.makeText(this@mode_of_payment, "Order details are null", Toast.LENGTH_SHORT).show()
                        Log.e("OrderDetails", "Order details are null.")
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

    private fun updateReturnAmount(orderId: String, amount: Int) {
        val requestBody = UpdateReturnAmountRequest(orderId, amount)

        RetrofitInstance.apiService.updateReturnAmount(requestBody).enqueue(object :
            Callback<UpdateReturnAmtResponse> {
            override fun onResponse(
                call: Call<UpdateReturnAmtResponse>,
                response: Response<UpdateReturnAmtResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("ReturnAmount", "Successfully updated return amount for delivery partner.")
                    Log.d("CollectionAmount", "Response: ${response.body()}")
                } else {
                    Log.e("ReturnAmount", "Failed to update return amount: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UpdateReturnAmtResponse>, t: Throwable) {
                Log.e("ReturnAmount", "Error updating return amount", t)
            }
        })
    }

    private fun updateCollectionAmount(orderId: String, amount: Int) {
        val requestBody = UpdateReturnAmountRequest(orderId, amount)

        RetrofitInstance.apiService.updateCollectionAmount(requestBody).enqueue(object :
            Callback<UpdateReturnAmtResponse> {
            override fun onResponse(
                call: Call<UpdateReturnAmtResponse>,
                response: Response<UpdateReturnAmtResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("CollectionAmount", "Successfully updated collection amount for outlet partner.")
                    Log.d("CollectionAmount", "Response: ${response.body()}")
                } else {
                    Log.e("CollectionAmount", "Failed to update collection amount: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UpdateReturnAmtResponse>, t: Throwable) {
                Log.e("CollectionAmount", "Error updating collection amount", t)
            }
        })
    }

    private fun populateOrderDetails(orderDetails: OrderDetailsResponse) {
        // Update the UI elements with data from orderDetails
        findViewById<EditText>(R.id.amount_rs).setText(orderDetails.amount)
    }
}