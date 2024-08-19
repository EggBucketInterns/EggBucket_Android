package com.eggbucket.eggbucket_android

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderDetailDeliveryActivity : AppCompatActivity() {
    private var orderId: String? = null
    private var trays: String? = null
    private var amount: String? = null
    private var name: String? = null
    private var createdAt:String?=null
    private var status:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_detail_delivery)
        val reached=findViewById<TextView>(R.id.reached2)
        // Retrieve the order ID from intent or use a default one
        orderId = intent.getStringExtra("ORDER_ID")
        trays = intent.getStringExtra("TRAYS")
        amount = intent.getStringExtra("AMOUNT")
        name = intent.getStringExtra("NAME")
        createdAt=intent.getStringExtra("CREATED_AT")
        status=intent.getStringExtra("STATUS")

        if(status.equals("completed") || status.equals("delivered")){
            reached.visibility=View.GONE
        }
//        intent.putExtra("order_ID",orderId);
        populateOrderDetails()

    }
    fun populateOrderDetails() {
        // Update the UI elements with data
        findViewById<TextView>(R.id.orderIdTextView2).text = orderId
        findViewById<TextView>(R.id.numTraysTextView2).text = trays
        findViewById<TextView>(R.id.amountTextView2).text = amount
        findViewById<TextView>(R.id.vendorNameTextView2).text = name
        findViewById<TextView>(R.id.delivery_order_created1).text = "createdAt $createdAt"

        // findViewById<TextView>(R.id.shopNameTextView1).text = customerName
    }
}