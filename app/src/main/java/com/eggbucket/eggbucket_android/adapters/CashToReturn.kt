package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.os.Bundle
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
        enableEdgeToEdge()
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
            dataList = RetrofitInstance.api.getDeliveryPartnerById(getUserId().toString())
            for(data in dataList){
                val payment = data.payments;
                for(obj in payment){
                    OutletNameList.add(getOutletName(obj.oId))
                    amountList.add(obj.returnAmt.toString());
                }
            }

            // Update the RecyclerView on the main thread
            withContext(Dispatchers.Main) {
                adapter = CashToReturnAdapter(this@CashToReturn, dataList);
                recyclerView.adapter = adapter
            }
        }
    }
    private fun getOutletName(id : String):String{
        val name : String="";
        //parameter me jo id aa rha h usko idhr getOutletwale function me call krke outlet name return kr do function me se/
        return name;
    }
}