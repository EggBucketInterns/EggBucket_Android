package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.data.DeliveryPartnerrr
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CashToReturn : AppCompatActivity() {
    private lateinit var adapter: CashToReturnAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DeliveryPartnerrr>
    private lateinit var OutletNameList: ArrayList<String>
    private lateinit var amountList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_cash_to_return)
        dataList = arrayListOf()


        // Find the RecyclerView
        recyclerView = findViewById(R.id.cashRecyclerView)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set the adapter and fetch data
        fetchDataAndBindRecyclerview()
    }
    private fun getUserId():String?{
        val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPref?.getString("USER_ID",null)
    }

    private fun fetchDataAndBindRecyclerview() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("FetchData", "Fetching data")
                val deliveryPartner = RetrofitInstance.api.getDeliveryPartnerById(getUserId().toString())
                withContext(Dispatchers.Main) {
                    // Create a list with the single object to pass to the adapter
                    val deliveryPartnerList = arrayListOf(deliveryPartner)
                    adapter = CashToReturnAdapter(this@CashToReturn, deliveryPartnerList)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                Log.e("FetchDataError", "Error fetching data: ${e.message}")
            }
        }
    }



    private fun getOutletName(id : String):String{
        val name : String="";
        //parameter me jo id aa rha h usko idhr getOutletwale function me call krke outlet name return kr do function me se/
        return name;
    }
}