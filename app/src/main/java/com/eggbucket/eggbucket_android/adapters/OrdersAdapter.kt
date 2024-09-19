package com.eggbucket.eggbucket_android.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.OrderDetailsOutletActivity
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.Customer
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersAdapter(
    private val context: Context,
    private val orderList: ArrayList<GetAllOrdersItem>
) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {
    private var customerList = mutableListOf<Customer>()
    private val listOfCustomersID: MutableList<String> = mutableListOf()
    private val listOfCustomersName: MutableList<String> = mutableListOf()
    private var outletIDFinal: String = "";
    private var refinedDeliveryPartnerList = mutableListOf<String>()
    private var listOfIdOfDeliveryPartner = mutableListOf<String>()
    private var deliveryPartnerIdInp: String? = null
    private var currentDeliverypartner : String?= null

    // ViewHolder class to hold references to the views
   class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val orderIdTextView: TextView = itemView.findViewById(R.id.order_id)
        val customerImageView: ImageView = itemView.findViewById(R.id.user_image)
        val customerIdTextView: TextView = itemView.findViewById(R.id.customer_idNumber)
        val numberOfTraysTextView: TextView = itemView.findViewById(R.id.trays_no)
        val orderAmountTextView: TextView = itemView.findViewById(R.id.order_money)
        val orderStatusTextView: TextView = itemView.findViewById(R.id.txt_completed)
        val orderDetails:LinearLayout=itemView.findViewById(R.id.linear_layout_all_order_item)
        val brown_btn : TextView=itemView.findViewById(R.id.brown_btn)
    }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
       // Inflate the layout for each item
        val view = LayoutInflater.from(context).inflate(R.layout.all_orders_cart_items, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Get the current order item
        val order = orderList[position]
        // Bind the data to the views
        holder.orderIdTextView.text = "Order Id: #${order._id}"
        holder.customerImageView.setImageResource(R.drawable.user_profile_image)
        holder.customerIdTextView.text = order.customerId?.customerId
        holder.numberOfTraysTextView.text = order.numTrays
        holder.orderAmountTextView.text = "₹ ${order.amount}"
        holder.orderStatusTextView.text=order.status
        var outletImage=""
        var customerImage=""
        if (order.status == "pending"){
            holder.brown_btn.text = "Change Delivery Partner"
            getUserId()?.let { fetchOutletByOutletPartnerID(it) };
            currentDeliverypartner = order.deliveryId.firstName;
            holder.orderDetails.setOnClickListener {
                // Inflate the dialog layout
                val dialogView = LayoutInflater.from(context).inflate(R.layout.change_delivery_partner_dailogbox, null)

                // Create the dialog
                val dialog = Dialog(context)
                dialog.setContentView(dialogView)

                // Set up the spinner (dropdown)
                val dropdownSpinner: Spinner = dialog.findViewById(R.id.dropdownSpinner)
//                val options = arrayOf("Option 1 ahsddhasdjashd", "Option 2 asjkldhajsdh", "Option 3 askjdhasjdh")
                Handler().postDelayed(
                    {
                        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, refinedDeliveryPartnerList)
                        dropdownSpinner.adapter = adapter
                    },1000
                )

                    dropdownSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val selectedDP = listOfIdOfDeliveryPartner[position]
                        if (position != 0) {
                            Log.d("checkResponse----->", "helloDP")
                            Log.d("checkResponse----->", selectedDP)
                            deliveryPartnerIdInp = selectedDP
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        Log.d("checkResponse----->", "nothing selected")
                    }
                }

                // Handle the "Done" button click
                val doneButton: Button = dialog.findViewById(R.id.doneButton)
                doneButton.setOnClickListener {
                    deliveryPartnerIdInp?.let { it1 ->
                        updateDeliveryPartner(order._id.toString(),
                            it1
                        )
                    };
                    dialog.dismiss()
                }

                // Show the dialog
                dialog.show()
            }
        }
        else{
            holder.brown_btn.text = "Order Details"
            holder.orderDetails.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        //Navigation
                        // Create an Intent to navigate to the Order_Details_Screen
                        val intent = Intent(context, OrderDetailsOutletActivity::class.java)
                        intent.putExtra("TRAYS",order.numTrays)
                        intent.putExtra("AMOUNT",order.amount)
                        intent.putExtra("DELIVERY_NAME",order.deliveryId.firstName)

                        intent.putExtra("CREATED_AT", order.createdAt)
                        // Pass the order ID to the next screen using a Bundle
                        intent.putExtra("ORDER_ID", order._id)
                        intent.putExtra("OUTLET_ID",order.outletId?._id)
                        intent.putExtra("id",order.customerId?.customerId)
                        intent.putExtra("CUSTOMER_NAME",order.customerId?.customerName)

                        // Start the activity with the Intent
                        context.startActivity(intent)

                    } catch (e: Exception) {
                        // Handle any errors
                        Log.e("MyAdapter", "Failed to transfer", e)
                    }
                }

            }
        }


        // Set the order status background and text

        // Set background or text color based on order status
        when (order.status) {
            "Completed" -> {
                holder.orderStatusTextView.setBackgroundResource(R.drawable.completed_back)
                holder.orderStatusTextView.setTextColor(context.resources.getColor(android.R.color.white))
            }
            "Pending" -> {
                holder.orderStatusTextView.setBackgroundResource(R.drawable.pending_back)
                holder.orderStatusTextView.setTextColor(context.resources.getColor(android.R.color.black))
            }
            // Add more statuses as needed
        }

    }

   override fun getItemCount(): Int {
        // Return the size of the order list
        return orderList.size
    }
    fun getCompletedOrdersCount(): Int = orderList.count { it.status == "completed" }
    fun getPendingOrdersCount(): Int = orderList.count { it.status == "pending" }
    fun getInTransitOrdersCount(): Int = orderList.count { it.status == "intransit" }
    fun getCancelledOrdersCount(): Int = orderList.count { it.status == "cancelled" }

    private fun getUserId(): String? {
        val sharedPreferences = context.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPreferences?.getString("USER_ID", null)
    }
    fun fetchOutletByOutletPartnerID(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getOutletByOutletPartnerID(id)
                withContext(Dispatchers.Main) {
                    val outlets = response.data
                    Log.d("checkResponse", "Fetched outlets: $outlets")
                    for(outlet in outlets){
                        Log.d("checkResponse", outlet.deliveryPartner.toString())
                        outletIDFinal = outlet._id;
                        refinedDeliveryPartnerList.add("Assign Delivery Partner");
                        listOfIdOfDeliveryPartner.add("ID");
                        Log.d("checkResponse----->", "current delivery partner : "+ currentDeliverypartner)
                        for ( del in outlet.deliveryPartner ){
                            if( del.firstName != currentDeliverypartner ){
                            refinedDeliveryPartnerList.add(del.firstName);
                            listOfIdOfDeliveryPartner.add(del._id);}
                        }
                        Log.d("checkResponse----->", refinedDeliveryPartnerList.toString())
                    }
                }
            } catch (e: Exception) {
                Log.e("checkResponse", "Error: ${e.message}")
            }
        }
    }

//    private fun changeDeliverypartner(orderId: String, deliveryPartnerId:String) {
//        val requestBody = UpdateDeliveryPartner(orderId, deliveryPartnerId)
//
//        RetrofitInstance.apiService.updateDeliveryPartner(requestBody).enqueue(object :
//            Callback<GetAllOrdersItem> {
//            override fun onResponse(
//                call: Call<GetAllOrdersItem>,
//                response: Response<GetAllOrdersItem>
//            ) {
//                if (response.isSuccessful) {
//                    Toast.makeText(context, "Delivery Partner Changed successfully", Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.e("checkResponse----->", "Failed to update delivery Partner"+ response.message() )
//                }
//            }
//
//            override fun onFailure(call: Call<GetAllOrdersItem>, t: Throwable) {
//                Log.e("checkResponse----->", "Failed to update delivery Partner"+ t.message )
//            }
//        })
//    }
private fun updateDeliveryPartner(orderId: String, deliveryPartnerID: String) {
    // Ensure orderId is correctly formatted
    if (orderId.isBlank()) {
        Log.e("OrderAmount", "Order ID is missing or blank!")
        return
    }

    // Create the request body as a map with just the amount
    val requestBody = mapOf("deliveryId" to deliveryPartnerID)
    Log.d("checkResponse---->", requestBody.toString())
    RetrofitInstance.apiService.updateDeliveryPartner(orderId, requestBody).enqueue(object :
        Callback<GetAllOrdersItem> {
        override fun onResponse(
            call: Call<GetAllOrdersItem>,
            response: Response<GetAllOrdersItem>
        ) {
            if (response.isSuccessful) {
                Log.d("checkResponse----->", "Successfully updated Delivery Partner.")
                Log.d("checkResponse----->", "Response: ${response.body()}")
            } else {
                Log.e("checkResponse----->", "Failed to update Delivery Partner: ${response.code()} - ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<GetAllOrdersItem>, t: Throwable) {
            Log.e("checkResponse----->", "Error updating Delivery partner", t)
        }
    })
}
}
