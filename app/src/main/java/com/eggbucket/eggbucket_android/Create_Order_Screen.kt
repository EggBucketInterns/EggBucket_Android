package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.DeliveryPartnerAdapter
import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Create_Order_Screen : AppCompatActivity() {
    public val assignedDeliveryPartners = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_order_screen)
        val CreateOrderLayout = findViewById<LinearLayout>(R.id.createOrderLayout);
        val SearchVendorLayout = findViewById<LinearLayout>(R.id.selectVendorLayout);
        val AssignDeliveryPartnerLayout = findViewById<LinearLayout>(R.id.assignDeliveryPartnerLayout);
        val assignDeliveryPartnerBtn = findViewById<TextView>(R.id.assignDelivreyPartnerBtn);
        val selectVendorBtn = findViewById<LinearLayout>(R.id.search_vendor_btn);
        val closeDeliveryPartnerBtn = findViewById<ImageView>(R.id.closeLayout2);
        val closeSelectVendorBtn = findViewById<ImageView>(R.id.closeLayout1);
        val SearchVendorText = findViewById<TextView>(R.id.search_vendor_text);
        lateinit var recyclerView: RecyclerView
        lateinit var adapter: DeliveryPartnerAdapter
        val deliveryPartnerList = mutableListOf<DeliveryPartnersItem>()

        recyclerView = findViewById(R.id.deliveryPartnerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DeliveryPartnerAdapter(deliveryPartnerList){ selectedId ,selectedName->
            // Handle the selected delivery partner's ID
            Log.d("SelectedID", "Selected Delivery Partner ID: $selectedId");
            assignDeliveryPartnerBtn.setText("$selectedName");
        }

        recyclerView.adapter = adapter

        fun showCreateOrderLayout(){
            CreateOrderLayout.visibility = View.VISIBLE;
            SearchVendorLayout.visibility = View.GONE;
            AssignDeliveryPartnerLayout.visibility = View.GONE
        }

        fun showAssignDeliveryPartnerLayout(){
            CreateOrderLayout.visibility = View.GONE;
            SearchVendorLayout.visibility = View.GONE;
            AssignDeliveryPartnerLayout.visibility = View.VISIBLE;
        }

        fun showSelectVendorLayout(){
            CreateOrderLayout.visibility = View.GONE;
            SearchVendorLayout.visibility = View.VISIBLE;
            AssignDeliveryPartnerLayout.visibility = View.GONE;
        }

        fun fetchDataFromApi() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val deliveryPartners = apiService.getAllDeliveryPartners()
                    withContext(Dispatchers.Main) {
//                        deliveryPartnerList.clear()
                        deliveryPartnerList.addAll(deliveryPartners)
                        Log.d("checkResponse", deliveryPartnerList.toString());
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        fetchDataFromApi();
        assignDeliveryPartnerBtn.setOnClickListener {

            showAssignDeliveryPartnerLayout();
        }
        selectVendorBtn.setOnClickListener {
            showSelectVendorLayout();
        }
        closeDeliveryPartnerBtn.setOnClickListener {
            showCreateOrderLayout();
        }
        closeSelectVendorBtn.setOnClickListener {
            showCreateOrderLayout();
        }
        SearchVendorText.setOnClickListener{
            showSelectVendorLayout();
        }
    }
}