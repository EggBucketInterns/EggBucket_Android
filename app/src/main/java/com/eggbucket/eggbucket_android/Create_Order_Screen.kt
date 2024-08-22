package com.eggbucket.eggbucket_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.eggbucket.eggbucket_android.adapters.DeliveryPartnerAdapter
import com.eggbucket.eggbucket_android.adapters.VendorAdapter
import com.eggbucket.eggbucket_android.model.Customer
import com.eggbucket.eggbucket_android.model.DeliveryPartner
import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem
import com.eggbucket.eggbucket_android.model.OrderCreate
import com.eggbucket.eggbucket_android.model.VendorItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
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
//        val assignDeliveryPartnerBtn = findViewById<TextView>(R.id.assignDelivreyPartnerBtn);
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
        var refinedDeliveryPartnerList = mutableListOf<String>()
        var listOfIdOfDeliveryPartner = mutableListOf<String>()
        var customerList = mutableListOf<Customer>()
        val ListOfCustomersID: MutableList<String> = mutableListOf()
        val ListOfCustomersName: MutableList<String> = mutableListOf()
        val ListOfDeliveryPartner : MutableList<String> = mutableListOf()
        val vendorList = mutableListOf<VendorItem>()
        val outletList = mutableListOf<DeliveryPartner>()
        var OutletIdInp: String;
        var vendorIdInp: String = "";
        var customerIdInp: String = "";
        var deliveryPartnerIdInp: String = "";
        var trays: String = ""
        var amount: String = ""
        var isUrgent: Boolean
        var outletIDFinal: String = ""
        val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)
        val spinner: Spinner = findViewById(R.id.customerIdDropDown)
        val spinner2 :Spinner = findViewById(R.id.assignDelivreyPartnerDropDown);
        val selectedCustomerIDView = findViewById<TextView>(R.id.selectedCustomerName)
        val isActive : Boolean = false
        amountText.imeOptions = EditorInfo.IME_ACTION_NEXT
        traysText.imeOptions = EditorInfo.IME_ACTION_DONE
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

        // Create a list of data for the Spinner
        fun setButtonColor() {
            if (deliveryPartnerIdInp != "" && customerIdInp != "" && trays != "" && amount != "") {
                createOrderBtn.setBackgroundColor(resources.getColor(R.color.blue));
            } else {
                createOrderBtn.setBackgroundColor(resources.getColor(R.color.ButtonBlue));
            }
        }

        amountText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called as the text is being changed
                val currentText = s.toString()
                amount = currentText;
                setButtonColor()
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has been changed
            }
        })
        traysText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called as the text is being changed
                val currentText = s.toString()
                trays = currentText;
                setButtonColor()
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has been changed
            }
        })
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

        fun fetchCustomerByID(id: String) {
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
                            Log.d("checkResponse","customer selected "+customerList[0]._id.toString() )
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

        fun getDeliveryPartnerByName(name : String){
            CoroutineScope(Dispatchers.IO).launch {
            val resp = apiService.getDeliveryPartnerByDeliveryPartnerName(name);
                Log.d("checkResponse----->", resp.toString())
                for(dp in resp){
                    Log.d("checkResponse----->", dp._id.toString())
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
                            refinedDeliveryPartnerList.add("Assign Delivery Partner");
                            listOfIdOfDeliveryPartner.add("ID");
                            for ( del in outlet.deliveryPartner ){
                                refinedDeliveryPartnerList.add(del.firstName);
                                listOfIdOfDeliveryPartner.add(del._id);
                            }
                            Log.d("checkResponse----->", refinedDeliveryPartnerList.toString())
//                            adapter = DeliveryPartnerAdapter(outlet.deliveryPartner) { selectedId, selectedName ->
//                                assignDeliveryPartnerBtn.setText("$selectedName");
//                                deliveryPartnerIdInp = "$selectedId";
//                            }
//                            val handler = Handler(Looper.getMainLooper())
//                            handler.postDelayed({
//                                if (deliveryPartnerIdInp != ""){
//                                    showCreateOrderLayout();
//                                }
//                            }, 300)

                        }
                    }
                } catch (e: Exception) {
                    Log.e("checkResponse", "Error: ${e.message}")
                }
            }
        }
        fun fetchDeliveryPartnerDetgetails() {
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
            Log.d("checkResponse----->",order.toString())

            apiService.createOrder(order).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show();
                        val intent =
                            Intent(this@Create_Order_Screen, Order_Placed_Screen::class.java)
                        startActivity(intent)
                        finish()
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
        fun fetchCustomers(outletId: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    customerList = RetrofitInstance.api.getCustomerByOutletId(outletId)
                    withContext(Dispatchers.Main) {
                        Log.d("checkResponse----->", "aa")
                        Log.d("checkResponse----->", customerList.toString())
                    }
                } catch (e: Exception) {
                    Log.e("ExampleActivity", "Error fetching customers", e)
                }
                ListOfCustomersName.add("Select Customer");
                ListOfCustomersID.add("id");
                for (customer in customerList){
                    ListOfCustomersName.add(customer.customerId.toString());
                    ListOfCustomersID.add(customer._id.toString());
                }
                Log.d("checkResponse----->", ListOfCustomersID.toString())
//                val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item,ListOfCustomersID)
//                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                spinner.adapter = adapter1
            }
        }

//        try {
//            CoroutineScope(Dispatchers.Main).launch {
//                try {
//                    Log.d("checkResponse----->1", outletIDFinal.toString())
//                    customerList = apiService.getCustomerByOutletId("66c03a48776be7d715f457d5");
//                }
//                catch (e:Exception){
//                    Log.d("checkResponse----->", e.message.toString())
//                }
//
//                Log.d("checkResponse----->", customerList.toString())
//
////            for(customer in customerList){
////                ListOfCustomersID.add(customer._id.toString());
////            }
//            }
//        }
//        catch (e: Exception){
//            Log.e("checkResponse----->", e.message.toString() )
//        }









//        fetchDeliveryPartnerDetails();
        fetchOutletByOutletPartnerID(getUserId().toString());
//        var adapter11 : ArrayAdapter<String>;
//        Handler().postDelayed({
//            Log.d("checkResponse----->",outletIDFinal)
//            fetchCustomers(outletIDFinal);
//            adapter11 = ArrayAdapter(this, android.R.layout.simple_spinner_item,ListOfCustomersID)
//            adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter11
//            }, 1000)

//            spinner.setOnClickListener {
//                Log.d("checkResponse----->", spinner.selectedItem.toString())
//            }

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val selectedCustomer = ListOfCustomersID[position]
//                Log.d("checkResponse----->", "hello");
//                Log.d("checkResponse----->", selectedCustomer);
//                if (position != 0) {
//
//                }
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                Log.d("checkResponse----->", "nothing selected");
//            }
//    }
        Handler().postDelayed({
            fetchCustomers(outletIDFinal);
        },1000)
        Handler().postDelayed({
//            val list = listOf("Select Customer", "Customer 1", "Customer 2", "Customer 3")

            // Create an ArrayAdapter using the string array and a default spinner layout
            val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, ListOfCustomersName)

            // Specify the layout to use when the list of choices appears
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Apply the adapter to the Spinner
            spinner.adapter = adapter2
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCustomer = ListOfCustomersID[position]
                if (position != 0) {
                    Log.d("checkResponse----->", selectedCustomer);
                    customerIdInp = selectedCustomer;
                    setButtonColor()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("checkResponse----->", "nothing selected");
            }
    }
        },2000)

        fun getCusotmerSelectedFromDropDown(){
            Log.d("checkResponse----->", spinner.selectedItem.toString())
        }

        ////for assigning the delivery partner drop down option
        Handler().postDelayed({
//            val list = listOf("Select Customer", "Customer 1", "Customer 2", "Customer 3")

            // Create an ArrayAdapter using the string array and a default spinner layout
            val adapter2DP = ArrayAdapter(this, android.R.layout.simple_spinner_item, refinedDeliveryPartnerList)

            // Specify the layout to use when the list of choices appears
            adapter2DP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Apply the adapter to the Spinner
            spinner2.adapter = adapter2DP
            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedDP = listOfIdOfDeliveryPartner[position]
                    if (position != 0) {
                        Log.d("checkResponse----->", "helloDP");
                        Log.d("checkResponse----->", selectedDP);
                        deliveryPartnerIdInp = selectedDP;
                        setButtonColor()
//                        getDeliveryPartnerByName(selectedDP)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Log.d("checkResponse----->", "nothing selected");
                }
            }
        },1000)


//        Handler().postDelayed({
//            getCusotmerSelectedFromDropDown();
//        },3000)
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
//        assignDeliveryPartnerBtn.setOnClickListener {
//            val id = CustomerId.text;
//            fetchCustomerByID(id.toString());
//            fetchOutletByOutletPartnerID(getUserId().toString());
//            showAssignDeliveryPartnerLayout();
//        }
    }

}