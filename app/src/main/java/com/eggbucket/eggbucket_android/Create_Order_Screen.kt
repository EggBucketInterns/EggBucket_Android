package com.eggbucket.eggbucket_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.DeliveryPartnerAdapter
import com.eggbucket.eggbucket_android.adapters.VendorAdapter
import com.eggbucket.eggbucket_android.model.Customer
import com.eggbucket.eggbucket_android.model.DeliveryPartner
import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem
import com.eggbucket.eggbucket_android.model.OrderCreate
import com.eggbucket.eggbucket_android.model.VendorItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Create_Order_Screen : AppCompatActivity() {
    public val assignedDeliveryPartners = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_create_order_screen)
        val CreateOrderLayout = findViewById<LinearLayout>(R.id.createOrderLayout);
        val SearchVendorLayout = findViewById<LinearLayout>(R.id.selectVendorLayout);
        val AssignDeliveryPartnerLayout =
            findViewById<LinearLayout>(R.id.assignDeliveryPartnerLayout);
        val assignDeliveryPartnerBtn = findViewById<TextView>(R.id.assignDelivreyPartnerBtn);
        val selectVendorBtn = findViewById<LinearLayout>(R.id.search_vendor_btn);
        val closeDeliveryPartnerBtn = findViewById<ImageView>(R.id.closeLayout2);
        val closeSelectVendorBtn = findViewById<ImageView>(R.id.closeLayout1);
        val SearchVendorText = findViewById<TextView>(R.id.search_vendor_text);
        val isUrgentCheckBox = findViewById<CheckBox>(R.id.IsUrgent);
        val amountText = findViewById<EditText>(R.id.amount);
        val traysText = findViewById<EditText>(R.id.trays);
        val createOrderBtn = findViewById<Button>(R.id.createOrderBtn);
        val outletIdTextView = findViewById<EditText>(R.id.OutleId);
        val CustomerId = findViewById<EditText>(R.id.customerID)
        lateinit var recyclerView: RecyclerView
        lateinit var adapter: DeliveryPartnerAdapter
        lateinit var vendorAdapter: VendorAdapter
        val deliveryPartnerList = mutableListOf<DeliveryPartnersItem>()
        var refinedDeliveryPartnerList = mutableListOf<DeliveryPartner>()
        var customerList = mutableListOf<Customer>()
        val vendorList = mutableListOf<VendorItem>()
        val outletList = mutableListOf<DeliveryPartner>()
        var OutletIdInp: String;
        var vendorIdInp: String = "";
        var customerIdInp: String = "";
        var deliveryPartnerIdInp: String = "";
        var trays: String
        var amount: String
        var isUrgent: Boolean
        var outletIDFinal: String = ""
        val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
// Retrieve the stored USER_ID
        val userId = sharedPref.getString("USER_ID", null)
        //outletIdTextView.setText(userId);
        //have to chancge when authentication is implemented
        OutletIdInp = userId.toString();
        recyclerView = findViewById(R.id.vendorRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        vendorAdapter = VendorAdapter(vendorList) { selectedId, selectedName ->
//            Log.d("SelectedID", "Selected Delivery Partner ID: $selectedId");
            SearchVendorText.setText("$selectedName");
            vendorIdInp = "$selectedId";
        }
        recyclerView.adapter = vendorAdapter



        recyclerView = findViewById(R.id.deliveryPartnerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = DeliveryPartnerAdapter(refinedDeliveryPartnerList) { selectedId, selectedName ->
//            assignDeliveryPartnerBtn.setText("$selectedName");
//            deliveryPartnerIdInp = "$selectedId";
//        }
//        recyclerView.adapter = adapter

        fun showCreateOrderLayout() {
            CreateOrderLayout.visibility = View.VISIBLE;
            SearchVendorLayout.visibility = View.GONE;
            AssignDeliveryPartnerLayout.visibility = View.GONE
        }

        fun showAssignDeliveryPartnerLayout() {
            CreateOrderLayout.visibility = View.GONE;
            SearchVendorLayout.visibility = View.GONE;
            AssignDeliveryPartnerLayout.visibility = View.VISIBLE;
        }

        fun showSelectVendorLayout() {
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

        fun fetchCustomerByOutlet(id: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val customer = apiService.getCustomerByID(id);
                    withContext(Dispatchers.Main) {
                        customerList.clear()
                        customerList.addAll(customer)
                        Log.d("checkResponse", customerList.toString());
                        adapter.notifyDataSetChanged()
                        for (data in customerList) {
                            Log.d("checkResponse", data._id.toString() + data.customerName)
                        }
                        if (customerList != null) {
                            customerIdInp = customerList[0]._id.toString()
                            Log.d("checkResponse",customerList[0]._id.toString() )
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Invalid Customer Id",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

//        fun fetchOutletByOutletPartnerID(id: String) {
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val outlets = apiService.getOutletByOutletPartnerID(id);
//                    withContext(Dispatchers.Main) {
//                        outletList.clear()
//                        outletList.addAll(outlets)
//                        Log.d("checkResponse11", "outlet" + outletList.toString());
//                        adapter.notifyDataSetChanged()
//                    }
//                } catch (e: Exception) {
//                    Log.e("checkResponse11", e.message.toString() )
//                }
//            }
//        }

        fun fetchOutletByOutletPartnerID(id: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getOutletByOutletPartnerID(id)
                    withContext(Dispatchers.Main) {
                        val outlets = response.data
                        // Handle the list of outlets as needed
                        Log.d("checkResponse", "Fetched outlets: $outlets")

                        for(outlet in outlets){
                            Log.d("checkResponse", outlet.deliveryPartner.toString())
                            outletIDFinal = outlet._id;
                            outletIdTextView.setText(outlet.outletArea + " EggBucket");
//                            outlet.deliveryPartner?.let {
//                                refinedDeliveryPartnerList.addAll(it) // Directly add the List<DeliveryPartner>
//                            }
//                            for ( del in outlet.deliveryPartner ){
//                                refinedDeliveryPartnerList.add(del);
//                            }
                            adapter = DeliveryPartnerAdapter(outlet.deliveryPartner) { selectedId, selectedName ->
                                assignDeliveryPartnerBtn.setText("$selectedName");
                                deliveryPartnerIdInp = "$selectedId";
                            }
                            val handler = Handler(Looper.getMainLooper())
                            handler.postDelayed({
                                if (deliveryPartnerIdInp != ""){
                                    showCreateOrderLayout();
                                }
                            }, 300)


                            recyclerView.adapter = adapter
                        }
                    }
                } catch (e: Exception) {
                    Log.e("checkResponse", "Error: ${e.message}")
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
                outletId = outletIDFinal,
                customerId = customerIdInp,
                deliveryId = deliveryPartnerIdInp,
                numTrays = trays,
                amount = amount,
                isUrgent = isUrgent,
                createdAt = currentTimestamp,
                updatedAt = currentTimestamp,
                status = "pending",
            )

            apiService.createOrder(order).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show();
                        val intent =
                            Intent(this@Create_Order_Screen, Order_Placed_Screen::class.java)
                        startActivity(intent)
                    } else {
                        Log.d("checkResponse", order.toString())
                        Toast.makeText(applicationContext, "Invalid Customer ID", Toast.LENGTH_SHORT).show();
                        Log.d("checkResponse1", response.message());
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show();
                }
            })
        }

        fun getUserId(): String? {
            val sharedPrefer = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
            return sharedPrefer?.getString("USER_ID", null)
        }
//        fetchDeliveryPartnerDetails();
        fetchOutletByOutletPartnerID(getUserId().toString());

//        assignDeliveryPartnerBtn.setOnClickListener {
//            fetchOutletByOutletPartnerID(getUserId().toString())
//            showAssignDeliveryPartnerLayout();
//        }
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
        assignDeliveryPartnerBtn.setOnClickListener {
            val id = CustomerId.text;
            fetchCustomerByOutlet(id.toString());
            fetchOutletByOutletPartnerID(getUserId().toString());
            showAssignDeliveryPartnerLayout();
        }
    }

}