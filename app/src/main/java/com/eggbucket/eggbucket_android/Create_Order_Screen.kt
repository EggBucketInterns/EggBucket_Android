package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.DeliveryPartnerAdapter
import com.eggbucket.eggbucket_android.adapters.VendorAdapter
import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem
import com.eggbucket.eggbucket_android.model.Order
import com.eggbucket.eggbucket_android.model.OrderCreate
import com.eggbucket.eggbucket_android.model.VendorItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val isUrgentCheckBox = findViewById<CheckBox>(R.id.IsUrgent);
        val amountText = findViewById<EditText>(R.id.amount);
        val traysText = findViewById<EditText>(R.id.trays);
        val createOrderBtn = findViewById<Button>(R.id.createOrderBtn);


        lateinit var recyclerView: RecyclerView
        lateinit var adapter: DeliveryPartnerAdapter
        lateinit var vendorAdapter : VendorAdapter
        val deliveryPartnerList = mutableListOf<DeliveryPartnersItem>()
        val vendorList = mutableListOf<VendorItem>()
        var OutletIdInp : String;
        var vendorIdInp : String ="";
        var customerIdInp : String;
        var deliveryPartnerIdInp : String ="";
        var trays : String
        var amount : String
        var isUrgent : Boolean
        //have to chancge when authentication is implemented
        OutletIdInp = "66b3c7226ab3f6c1af298593";
        customerIdInp = "66b3c8aa6ab3f6c1af2985a0";



        recyclerView = findViewById(R.id.vendorRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        vendorAdapter = VendorAdapter(vendorList){ selectedId ,selectedName->
//            Log.d("SelectedID", "Selected Delivery Partner ID: $selectedId");
            SearchVendorText.setText("$selectedName");
            vendorIdInp = "$selectedId";
        }
        recyclerView.adapter = vendorAdapter



        recyclerView = findViewById(R.id.deliveryPartnerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DeliveryPartnerAdapter(deliveryPartnerList){ selectedId ,selectedName->
            // Handle the selected delivery partner's ID
//            Log.d("SelectedID", "Selected Delivery Partner ID: $selectedId");
            assignDeliveryPartnerBtn.setText("$selectedName");
            deliveryPartnerIdInp = "$selectedId";
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
        fun fetchVendorDetails() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val vendors = apiService.getAllVendors()
                    withContext(Dispatchers.Main) {
                        vendorList.clear()
                        vendorList.addAll(vendors)
                        Log.d("checkResponse", vendorList.toString());
                        vendorAdapter.notifyDataSetChanged();
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        fun fetchDeliveryPartnerDetails() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val deliveryPartners = apiService.getAllDeliveryPartners()
                    withContext(Dispatchers.Main) {
                        deliveryPartnerList.clear()
                        deliveryPartnerList.addAll(deliveryPartners)
                        Log.d("checkResponse", deliveryPartnerList.toString());
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        fun getCurrentTimestamp(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            return dateFormat.format(Date())
        }
        fun createOrder() {
            val currentTimestamp = getCurrentTimestamp()
            amount = amountText.text.toString();
            trays = traysText.text.toString();
            isUrgent = isUrgentCheckBox.isChecked;


            val order = OrderCreate(
                outletId = OutletIdInp,
                customerId = customerIdInp,
                vendorId = vendorIdInp,
                deliveryId =deliveryPartnerIdInp,
                numTrays = trays,
                amount = amount,
                isUrgent = isUrgent,
                createdAt = currentTimestamp,
                updatedAt = currentTimestamp,
                status="pending",
            )

            apiService.createOrder(order).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show();
                        val intent = Intent(this@Create_Order_Screen, Order_Placed_Screen::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("checkResponse", response.message());
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show();
                }
            })
        }
        fetchDeliveryPartnerDetails();
        fetchVendorDetails();
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
        createOrderBtn.setOnClickListener {
            createOrder();
        }
    }

}