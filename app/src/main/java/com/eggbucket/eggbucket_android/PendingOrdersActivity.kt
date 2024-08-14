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
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.adapters.PendingOrderAdapter
import com.eggbucket.eggbucket_android.model.PendingOrder
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PendingOrdersActivity : AppCompatActivity() {
    lateinit var adapter: OrdersAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var dataList:ArrayList<GetAllOrdersItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pending_orders)
        val backDeliveryDash=findViewById<ImageView>(R.id.back_to_deliveryDashboard)
        /*backDeliveryDash.setOnClickListener {
            startActivity(Intent(this@PendingOrdersActivity,delivery_dashboard::class.java))
        }*/

        dataList= arrayListOf()
        // Find the RecyclerView
        recyclerView = findViewById(R.id.pendingOrder_recyclerview)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set the adapter
        fetchDataAndBindRecyclerview()

    }
    fun fetchDataAndBindRecyclerview(){
        CoroutineScope(Dispatchers.IO).launch {
            val dataList = RetrofitInstance.api.getAllOrders()

            withContext(Dispatchers.Main) {
                adapter = OrdersAdapter(this@PendingOrdersActivity,dataList)
                recyclerView.adapter = adapter
            }
        }
    }
}