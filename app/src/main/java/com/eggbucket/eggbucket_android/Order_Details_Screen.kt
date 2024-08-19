package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private  var status:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details_screen)
        val reaches=findViewById<TextView>(R.id.reached)


        status=intent.getStringExtra("STATUS")

        if(status.equals("completed") || status.equals("delivered")){
            reaches.visibility= View.GONE
        }

        // Retrieve the order ID from intent or use a default one
        orderId = intent.getStringExtra("ORDER_ID") ?: "66b764ddf2476d8c6f2770cb"
//        intent.putExtra("order_ID",orderId);

        // Call API to fetch order details
        try {
            getOrderDetails(orderId)
        }
        catch (e : Exception){
            Log.e("ExceptionError", e.message.toString() )
        }

        val reached = findViewById<TextView>(R.id.reached)
        reached.setOnClickListener {
            // Pass the order ID to the mode_of_payment screen
            val paymentIntent = Intent(this, mode_of_payment::class.java)
            paymentIntent.putExtra("order_ID", orderId)  // Pass the order ID correctly
            startActivity(paymentIntent)
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
        val customerName = orderDetails.customerId?.customerName ?: "Unknown Customer"
        // Update the UI elements with data
        findViewById<TextView>(R.id.orderIdTextView).text = orderDetails._id
        findViewById<TextView>(R.id.numTraysTextView).text = orderDetails.numTrays
        findViewById<TextView>(R.id.amountTextView).text = "₹ ${orderDetails.amount}"
        findViewById<TextView>(R.id.vendorNameTextView).text = orderDetails.deliveryId.firstName
        findViewById<TextView>(R.id.shopNameTextView).text = customerName
        findViewById<TextView>(R.id.txt_created_at).text="createdAt ${orderDetails.createdAt}"
       // findViewById<TextView>(R.id.delivery_order_created).text=orderDetails.createdAt
    }
}