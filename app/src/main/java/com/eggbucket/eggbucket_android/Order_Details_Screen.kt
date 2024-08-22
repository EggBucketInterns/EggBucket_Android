package com.eggbucket.eggbucket_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eggbucket.eggbucket_android.model.allcustomer.CustomerDetailsItem
import com.eggbucket.eggbucket_android.model.data.DeliveryPartnerrr
import com.eggbucket.eggbucket_android.model.data.OrderDetailsResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Order_Details_Screen : AppCompatActivity() {
    /*<<<<<<< HEAD
    private  var orderId: String?=null
    private  var status:String?=null
    private var id:String?=null
=======*/
    private lateinit var orderId: String
    private var status: String? = null
    private var id: String? = null
    private var outletID: String = ""


    private var trays: String? = null
    private var amount: String? = null
    private var name: String? = null
    private var createdAt: String? = null
    private var customerName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details_screen)
        val reaches = findViewById<TextView>(R.id.reached)
        val customerImage = findViewById<ImageView>(R.id.detail_image_customer)
        val outletImage = findViewById<ImageView>(R.id.details_image_outlet)
        val openMapButton = findViewById<CardView>(R.id.openMapBtn)

        status = intent.getStringExtra("STATUS")
        //  id=intent.getStringExtra("id")

        // orderId = intent.getStringExtra("ORDER_ID")
        trays = intent.getStringExtra("TRAYS")
        amount = intent.getStringExtra("AMOUNT")
        name = intent.getStringExtra("NAME")
        createdAt = intent.getStringExtra("CREATED_AT")
        id = intent.getStringExtra("id")
        customerName = intent.getStringExtra("CUSTOMER_NAME")

        val customer = RetrofitInstance.api.getCustomerImageByID(id)
        customer.enqueue(object : Callback<ArrayList<CustomerDetailsItem>> {
            override fun onResponse(
                call: Call<ArrayList<CustomerDetailsItem>>,
                response: Response<ArrayList<CustomerDetailsItem>>
            ) {
                if (response.isSuccessful) {
                    val partner = response.body()
                    if (partner != null) {
                        // Picasso.get().load(partner.img).into(profileImageView)
                        for (list in partner) {
                            if (id == list.customerId) {
                                Picasso.get().load(list.img).into(customerImage)
                                Picasso.get().load(list.outlet.img).into(outletImage)
                            }
                        }

                        Log.d("Customer Image", "Success to load image")
                        //outletImage= partner[0].img
                        // customerImage=partner[0].outlet.img

                        /*for (list in partner){
                            if (id ==list.customerId){
                                outletImage=list.outlet.img
                                customerImage=list.img
                            }
                        }*/
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<CustomerDetailsItem>>, t: Throwable) {
                Log.d("Customer Image", "Failed to load image ${t.message}")
            }

        })

        populateOrderDetails()




        status = intent.getStringExtra("STATUS")
      //  id = intent.getStringExtra("id")
        orderId = intent.getStringExtra("ORDER_ID") ?: "66b764ddf2476d8c6f2770cb"


        val googleMapLink = "https://maps.app.goo.gl/4WUTPMRmegN5rCw2A"
        // findViewById<TextView>(R.id.shopNameTextView1).text = customerName
        openMapButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapLink))
            intent.setPackage("com.google.android.apps.maps") // Optional: Open specifically in Google Maps
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // Handle the case where Google Maps is not installed
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(googleMapLink)))
            }
        }

        if (status.equals("completed") || status.equals("delivered")) {
            reaches.visibility = View.GONE
        }

        // Retrieve the order ID from intent or use a default one
        orderId = intent.getStringExtra("ORDER_ID") ?: "66b764ddf2476d8c6f2770cb"
//        intent.putExtra("order_ID",orderId);

        // Call API to fetch order details
        /* try {
            getOrderDetails(orderId)

        } catch (e: Exception) {
            Log.e("ExceptionError", e.message.toString())
        }*/

        Log.d("OrderDetails", "Order details fetched for ID: $orderId")
        Log.d("OrderDetails", "Order details are ${getOrderDetails(orderId)}")
        /*catch (e: Exception)
    {
        Log.e("ExceptionError", e.message.toString())
    }*/


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
            override fun onResponse(
                call: Call<OrderDetailsResponse>,
                response: Response<OrderDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    val orderDetails = response.body()
                    Log.d("OrderDetails", "Order Details: $orderDetails")
                    if (orderDetails != null) {
                        //retrieve customer id from order detail
                        val customerID = orderDetails.customerId._id
                        Log.d("OrderDetails", "Customer ID: $customerID")
                        //retrieve outlet id from order detail
                        outletID = orderDetails.outletId._id
                        Log.d("OrderDetails", "Outlet ID: $outletID")
                        // Update UI with the fetched order details
                        populateOrderDetails(orderDetails)

                        fetchCustomerImage(customerID)
//                        fetchOutletImage1(outletID)
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


    private fun populateOrderDetails() {
        // val customerName = orderDetails.customerId?.customerName ?: "Unknown Customer"
        // Update the UI elements with data
        findViewById<TextView>(R.id.orderIdTextView).text = id
        findViewById<TextView>(R.id.numTraysTextView).text = trays
        findViewById<TextView>(R.id.amountTextView).text = amount
        findViewById<TextView>(R.id.vendorNameTextView).text = name
        findViewById<TextView>(R.id.shopNameTextView).text = customerName
        findViewById<TextView>(R.id.txt_created_at).text = createdAt
        // findViewById<TextView>(R.id.delivery_order_created).text=orderDetails.createdAt
    }

    //Retrieve data from customer id
    private fun fetchCustomerImage(customerId: String) {
        Log.d("fetchCustomerImage", "Customer ID: $customerId")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Use the correct API method to fetch customer details by ID
                val response =
                    RetrofitInstance.apiService.getCustomerImageById(customerId).execute()
                Log.d("fetchCustomerImage", "Response: $response")

                if (response.isSuccessful) {
                    val customer = response.body() // Directly retrieves the CustomerDetailsItem
                    withContext(Dispatchers.Main) {
                        if (customer != null) {
                            // Load the customer image into the ImageView
                            Picasso.get().load(customer.img)
                                .into(findViewById<ImageView>(R.id.detail_image_customer))
                        } else {
                            Log.e("fetchCustomerImage", "No customer found")
                        }
                    }
                } else {
                    Log.e("fetchCustomerImage", "API call failed: ${response.code()}")
                    Log.e("fetchCustomerImage", "Response: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("fetchCustomerImage", "Error: ${e.message}")
            }
        }
    }


    private fun populateOrderDetails(orderDetails: OrderDetailsResponse) {
        val customerName = orderDetails.customerId?.customerName ?: "Unknown Customer"
        // Update the UI elements with data
        findViewById<TextView>(R.id.orderIdTextView).text = orderDetails._id
        findViewById<TextView>(R.id.numTraysTextView).text = orderDetails.numTrays
        findViewById<TextView>(R.id.amountTextView).text = "₹ ${orderDetails.amount}"
        findViewById<TextView>(R.id.vendorNameTextView).text = customerName
        findViewById<TextView>(R.id.shopNameTextView).text = orderDetails.deliveryId.firstName
        findViewById<TextView>(R.id.txt_created_at).text = "createdAt ${orderDetails.createdAt}"
        // findViewById<TextView>(R.id.delivery_order_created).text=orderDetails.createdAt

    }
}
