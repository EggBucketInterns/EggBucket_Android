package com.eggbucket.eggbucket_android

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompletedOrders : AppCompatActivity() {
    private lateinit var adapter: OrdersAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<GetAllOrdersItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_completed_orders)
        dataList = arrayListOf()


        // Find the RecyclerView
        recyclerView = findViewById(R.id.CompletedOrdersRecyclerview)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set the adapter and fetch data
        fetchDataAndBindRecyclerview()
    }
    private fun getOutletId():String?{
        val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPref?.getString("OUTLET_ID",null)
    }
    private fun fetchDataAndBindRecyclerview() {
        CoroutineScope(Dispatchers.IO).launch {
            // Fetch data from the API

            dataList = RetrofitInstance.api.getOrderByOutletIdByStatus(getOutletId().toString(),"completed")
            // Update the RecyclerView on the main thread
            withContext(Dispatchers.Main) {
                adapter = OrdersAdapter(this@CompletedOrders, dataList)
                recyclerView.adapter = adapter
            }
        }
    }
}