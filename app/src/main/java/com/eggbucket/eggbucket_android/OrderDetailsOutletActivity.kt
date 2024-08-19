package com.eggbucket.eggbucket_android

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.data.OrderDetailsResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
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
    private var createdAt:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details_outlet)
        // Retrieve the order ID from intent or use a default one
        orderId = intent.getStringExtra("ORDER_ID")
        trays = intent.getStringExtra("TRAYS")
        amount = intent.getStringExtra("AMOUNT")
        name = intent.getStringExtra("NAME")
        createdAt=intent.getStringExtra("CREATED_AT")
//        intent.putExtra("order_ID",orderId);
        populateOrderDetails()
    }

    fun populateOrderDetails() {
        // Update the UI elements with data
        findViewById<TextView>(R.id.orderIdTextView1).text = orderId
        findViewById<TextView>(R.id.numTraysTextView1).text = trays
        findViewById<TextView>(R.id.amountTextView1).text = "₹ $amount"
        findViewById<TextView>(R.id.vendorNameTextView1).text = name
        findViewById<TextView>(R.id.txt_order_created).text = "createdAt $createdAt"

       // findViewById<TextView>(R.id.shopNameTextView1).text = customerName
    }

}