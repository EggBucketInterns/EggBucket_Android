package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.CompletedOrders
import com.eggbucket.eggbucket_android.MainActivity
import com.eggbucket.eggbucket_android.OutletHomeFragment
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.databinding.FragmentOutletHomeBinding
import com.eggbucket.eggbucket_android.model.Order
import com.eggbucket.eggbucket_android.model.StatusUpdate
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.login.LoginRequest
import com.eggbucket.eggbucket_android.model.login.LoginResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CashToCollectAdapter(
    private val context: Context,
    private val orderList: ArrayList<GetAllOrdersItem>,
    private val coroutineScope: CoroutineScope,
) : RecyclerView.Adapter<CashToCollectAdapter.OrderViewHolder>() {
    interface OnCompleteClickListener {
        fun onCompleteClick()
    }

    // ViewHolder class to hold references to the views
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deliveryPartnerName: TextView = itemView.findViewById(R.id.deliveryPartnerName)
        val amoutn: TextView = itemView.findViewById(R.id.amount);
        val CompletedBtn: Button = itemView.findViewById(R.id.completedBtn);
        fun bind(data: GetAllOrdersItem,onCompleteClickListener: OnCompleteClickListener) {
            CompletedBtn.setOnClickListener {
                onCompleteClickListener.onCompleteClick()
            }
        }

    }



    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cash_collected_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Get the current order item
        val order = orderList[position]
        holder.deliveryPartnerName.text = order.deliveryId.firstName;
        holder.amoutn.text = order.amount;
        holder.CompletedBtn.setOnClickListener {
            coroutineScope.launch {
                try {
                        updateOrder(order._id)
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                } catch (e: Exception) {

                    Log.e("MyAdapter", "Failed to update order", e)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
    fun updateOrder(orderId: String) {
        val statusUpdate = mapOf("status" to "completed")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.apiService.updateStatusCompleted(orderId,statusUpdate);
                val fragment = OutletHomeFragment()


                // Handle success, e.g., update UI or process the response
                println("Order updated successfully: $response")
            } catch (e: Exception) {
                // Handle failure
                println("Failed to update order: ${e.message}")
            }
        }
    }
//    private suspend fun updateStatusToDelivered(userid : String, loginRequest: StatusUpdate) {
//        RetrofitInstance.api.updateOrderStatus(userid, loginRequest)
//            .enqueue(object : Callback<Order?> {
//                override fun onResponse(call: Call<Order?>, response: Response<Order?>) {
//                    if (response.isSuccessful && response.body() != null) {
//                        Log.d("MyAdapter", "Updated")
//                    } else {
//                        Log.d("MyAdapter", response.body().toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<Order?>, t: Throwable) {
//                    Log.e("MyAdapter", t.message.toString() )
//                }
//            })
//
//    }



}
