package com.eggbucket.eggbucket_android

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.CashToCollectAdapter
import com.eggbucket.eggbucket_android.adapters.CollectedCashAdapter
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectedCashDelivery : AppCompatActivity() {
    private lateinit var adapter: CollectedCashAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<GetAllOrdersItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_collected_cash_delivery)

        /*dataList = arrayListOf()


        // Find the RecyclerView
        recyclerView = findViewById(R.id.collected_RecyclerView)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set the adapter and fetch data
        fetchDataAndBindRecyclerview()*/
    }
  /*  private fun getOutletId():String?{
        val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPref?.getString("OUTLET_ID",null)
    }*/
    /*private fun fetchDataAndBindRecyclerview() {
        CoroutineScope(Dispatchers.IO).launch {
            // Fetch data from the API

            //dataList = RetrofitInstance.api.getOrderByOutletIdByStatus(getOutletId().toString(),"delivered")
            // Update the RecyclerView on the main thread
            withContext(Dispatchers.Main) {
                adapter = CollectedCashAdapter(this@CollectedCashDelivery, dataList,lifecycleScope);
                recyclerView.adapter = adapter
            }
        }
    }*/
}