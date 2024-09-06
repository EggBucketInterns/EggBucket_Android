package com.eggbucket.eggbucket_android

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.eggbucket.eggbucket_android.model.allcustomer.CustomerDetailsItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailsOutletActivity : AppCompatActivity() {
    private var orderId: String? = null
    private var trays: String? = null
    private var amount: String? = null
    private var name: String? = null
    private var createdAt: String? = null
    private var id: String? = null
    private var customerName:String?=null
    private var deliveryName:String?=null
    private var outletId: String? = null
    private var outletName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details_outlet)
       val outletImage= findViewById<ImageView>(R.id.im_outlet_image)

        val customerImage= findViewById<ImageView>(R.id.im_customer_image)
        // Retrieve the order ID from intent or use a default one
        orderId = intent.getStringExtra("ORDER_ID")
        Log.d("OrderDetailOutletActivity", "Order ID: $orderId")
        trays = intent.getStringExtra("TRAYS")
        amount = intent.getStringExtra("AMOUNT")
        name = intent.getStringExtra("NAME")
        createdAt = intent.getStringExtra("CREATED_AT")
        id = intent.getStringExtra("id")
        Log.d("OrderDetailOutletActivity", "Order ID: $id")
        deliveryName=intent.getStringExtra("DELIVERY_NAME")
        Log.d("OrderDetailOutletActivity", "Delivery Name: $deliveryName")
        customerName=intent.getStringExtra("CUSTOMER_NAME")
        Log.d("OrderDetailOutletActivity", "Customer Name: $customerName")
        outletId = intent.getStringExtra("OUTLET_ID")
        Log.d("OrderDetailOutletActivity", "Outlet ID: $outletId")

        if (outletId != null) {
            fetchOutletDetails(outletId!!) { outletName, _ ->
                findViewById<TextView>(R.id.order_detail_outlet_name).text = outletName ?: "Unknown Outlet"
            }
        }

        val customer = RetrofitInstance.api.getCustomerImageByID(id)
        Log.d("OrderDetailOutletActivity", "Customer Image: $customer and  Customer ID: $id")
        customer.enqueue(object : Callback<ArrayList<CustomerDetailsItem>> {
            override fun onResponse(
                call: Call<ArrayList<CustomerDetailsItem>>,
                response: Response<ArrayList<CustomerDetailsItem>>
            ) {
                if (response.isSuccessful) {
                    val partner = response.body()
                    if (partner != null) {
                        for(list in partner){
                            if (id==list.customerId){
                                Picasso.get().load(list.img).into(customerImage)
                                Picasso.get().load(list.outlet.img).into(outletImage)
                            }
                        }
                        Log.d("OrderDetailOutletActivity", "Success to load image")

                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<CustomerDetailsItem>>, t: Throwable) {
                Log.d("OrderDetailOutletActivity", "Failed to load image ${t.message}")
            }

        })

        populateOrderDetails()
    }

    private fun fetchOutletDetails(outletId: String, onResult: (String?, Any?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val outletResponse = RetrofitInstance.apiService.getOutletByOutletId(outletId)
                if (outletResponse.status == "success") {
                    Log.d("OrderDetailOutletActivity", "Outlet Response fetched successfully")
                    val outlet = outletResponse.data.firstOrNull()
                    val outletPartnerName = outlet?.outletArea
                    Log.d("OrderDetailOutletActivity", "Outlet Name: $outletPartnerName")
                    withContext(Dispatchers.Main) {
                        onResult(outletPartnerName, null)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResult(null, null)
                        Log.d("OrderDetailOutletActivity", "Outlet Response is null")
                    }
                }
            } catch (e: Exception) {
                Log.e("OrderDetailOutletActivity", "Error fetching outlet details: ${e.message}")
                withContext(Dispatchers.Main) {
                    onResult(null, null)
                }
            }
        }
    }

    fun populateOrderDetails() {
        // Update the UI elements with data
        findViewById<TextView>(R.id.orderIdTextView1).text = orderId
        findViewById<TextView>(R.id.numTraysTextView1).text = trays
        findViewById<TextView>(R.id.amountTextView1).text = "₹ $amount"
        findViewById<TextView>(R.id.vendorNameTextView1).text = name
        findViewById<TextView>(R.id.txt_order_created).text = "createdAt $createdAt"
        findViewById<TextView>(R.id.outlet_customer_name).text=customerName
        findViewById<TextView>(R.id.outlet_delivery_name).text=deliveryName
    }

}