package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.PendingOrderAdapter
import com.eggbucket.eggbucket_android.model.PendingOrder

class PendingOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pending_orders)
        val backDeliveryDash=findViewById<ImageView>(R.id.back_to_deliveryDashboard)

        // Sample data
        val pendingOrderList = listOf(
            PendingOrder(
                orderId = "#3577",
                customerId = "Customer ID",
                customerIdNumber = "72382378",
                numberOfTrays = 12,
                createdAt = "2023-08-01",
                deliveredAt = "2023-08-03",
                orderAmount = 567.88,
                orderStatus = "Pending"
            ),
            PendingOrder(
                orderId = "#3578",
                customerId = "Customer ID",
                customerIdNumber = "12345678",
                numberOfTrays = 10,
                createdAt = "2023-08-02",
                deliveredAt = "2023-08-04",
                orderAmount = 345.67,
                orderStatus = "Pending"
            ),
            PendingOrder(
                orderId = "#3579",
                customerId = "Customer ID",
                customerIdNumber = "87654321",
                numberOfTrays = 8,
                createdAt = "2023-08-03",
                deliveredAt = "2023-08-05",
                orderAmount = 765.45,
                orderStatus = "Pending"
            ),
            PendingOrder(
                orderId = "#3580",
                customerId = "Customer ID",
                customerIdNumber = "24681357",
                numberOfTrays = 15,
                createdAt = "2023-08-04",
                deliveredAt = "2023-08-06",
                orderAmount = 890.12,
                orderStatus = "Pending"
            )
            // Add more dummy items if needed
        )
        var pending=pendingOrderList.size.toString()

        // Find the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.pendingOrder_recyclerview)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set the adapter
        recyclerView.adapter = PendingOrderAdapter(this, pendingOrderList)

        // Create an Intent to start SecondActivity
        backDeliveryDash.setOnClickListener {
            val intent = Intent(this, delivery_dashboard::class.java)

            // Put the EditText value into the Intent
            intent.putExtra("pendingItem", pending)

            // Start SecondActivity
            startActivity(intent)
        }


    }
}