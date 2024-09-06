package com.eggbucket.eggbucket_android.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.DeliveryHomeFragment
import com.eggbucket.eggbucket_android.Order_Details_Screen
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.Order
import com.eggbucket.eggbucket_android.model.StatusUpdate
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.data.OrderDetailsResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class LiveOrderAdapter(

    val context:Context,
    private val fragmentManager: FragmentManager,
    val dataList:ArrayList<GetAllOrdersItem>
):RecyclerView.Adapter<LiveOrderAdapter.LiveOrderViewHolder>() {

    class LiveOrderViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        val date=itemview.findViewById<TextView>(R.id.live_order_date)
        val trays=itemview.findViewById<TextView>(R.id.live_order_tray)
        val venderName=itemview.findViewById<TextView>(R.id.vender_name_live)
        val amountlive=itemview.findViewById<TextView>(R.id.live_order_amount)
        val confirm=itemview.findViewById<TextView>(R.id.confirm_order)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LiveOrderAdapter.LiveOrderViewHolder {
        // Inflate the layout for each item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.live_order_items, parent, false)
        return LiveOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: LiveOrderAdapter.LiveOrderViewHolder, position: Int) {
       val currentItem=dataList[position]
        holder.date.text=currentItem.createdAt
        holder.trays.text=currentItem.numTrays
        holder.amountlive.text="₹ ${currentItem.amount}"
        holder.venderName.text = currentItem.customerId?.customerName ?: "Unknown Customer"
        holder.confirm.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
//                    val statusUpdate1 = StatusUpdate(status = "intransit")
//                    val gson = Gson()
//                    val strJsonObject = gson.toJson(statusUpdate1)
//                    Log.d("MyAdapter", strJsonObject)
////                    val response = apiService.updateOrderStatus(order._id,strJsonObject )
////                    updateStatusToIntransit(currentItem._id, StatusUpdate(status = "delivered"));
//                    Log.d("MyAdapter", "updated")

                    updateOrder(currentItem._id)


                    //Navigation
                    // Create an Intent to navigate to the Order_Details_Screen
                    val intent = Intent(context, Order_Details_Screen::class.java)

                    intent.putExtra("TRAYS",currentItem.numTrays)
                    intent.putExtra("AMOUNT",currentItem.amount)
                    intent.putExtra("NAME",currentItem.deliveryId.firstName)
                    intent.putExtra("CREATED_AT", currentItem.createdAt)
                    // Pass the order ID to the next screen using a Bundle
                    intent.putExtra("CUSTOMER_NAME",currentItem.customerId?.customerName)
                    intent.putExtra("id",currentItem.customerId?.customerId)
                    intent.putExtra("OUTLET_ID", currentItem.outletId?._id)


                    // Pass the order ID to the next screen using a Bundle
                    intent.putExtra("ORDER_ID", currentItem._id)

                    // Start the activity with the Intent
                    withContext(Dispatchers.Main) {
                        context.startActivity(intent)

                        // Remove the fragment from the back stack
                       // fragmentManager.popBackStack()
                        // Finish the hosted activity
                        //(context as? Activity)?.finish()
                    }


                } catch (e: Exception) {
                    // Handle any errors
                    Log.e("MyAdapter", "Failed to update order", e)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun formatDate(dateString: String?): String {
        // Assuming dateString is in ISO format, e.g., "2024-08-15T12:34:56Z"
        // You can use SimpleDateFormat to convert it to a more readable format
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }

//    private suspend fun updateStatusToIntransit(userid : String, loginRequest: StatusUpdate) {
//        RetrofitInstance.api.updateOrderStatuss(userid, loginRequest)
//            .enqueue(object : Callback<OrderDetailsResponse?> {
//                override fun onResponse(call: Call<OrderDetailsResponse?>, response: Response<OrderDetailsResponse?>) {
//                    if (response.isSuccessful && response.body() != null) {
//                        Log.d("MyAdapter", "Updated")
//                    } else {
//                        Log.d("MyAdapter", response.body().toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<OrderDetailsResponse?>, t: Throwable) {
//                    Log.e("MyAdapter", t.message.toString() )
//                }
//            })
//
//    }

    fun updateOrder(orderId: String) {
        val statusUpdate = mapOf("status" to "intransit")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.apiService.updateOrderStatus(orderId,statusUpdate);
                val fragment = DeliveryHomeFragment()
                // Handle success, e.g., update UI or process the response
                println("Order updated successfully: $response")
            } catch (e: Exception) {
                // Handle failure
                println("Failed to update order: ${e.message}")
            }
        }
    }

}