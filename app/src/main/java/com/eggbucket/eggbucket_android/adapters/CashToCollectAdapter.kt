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
import com.eggbucket.eggbucket_android.MainActivity
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.Order
import com.eggbucket.eggbucket_android.model.StatusUpdate
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.login.LoginRequest
import com.eggbucket.eggbucket_android.model.login.LoginResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CashToCollectAdapter(
    private val context: Context,
    private val orderList: ArrayList<GetAllOrdersItem>,
    private val coroutineScope: CoroutineScope
) : RecyclerView.Adapter<CashToCollectAdapter.OrderViewHolder>() {

    // ViewHolder class to hold references to the views
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deliveryPartnerName: TextView = itemView.findViewById(R.id.deliveryPartnerName)
        val amoutn: TextView = itemView.findViewById(R.id.amount);
        val CompletedBtn: Button =  itemView.findViewById(R.id.completedBtn);
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
                    val statusUpdate1 = StatusUpdate(status = "delivered")
                    val gson = Gson()
                    val strJsonObject = gson.toJson(statusUpdate1)
                    Log.d("MyAdapter", strJsonObject)
//                    val response = apiService.updateOrderStatus(order._id,strJsonObject )
                    updateStatusToDelivered(order._id, StatusUpdate(status = "delivered"));
                    Log.d("MyAdapter", "updated")
                } catch (e: Exception) {
                    // Handle any errors
                    Log.e("MyAdapter", "Failed to update order", e)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    private suspend fun updateStatusToDelivered(userid : String, loginRequest: StatusUpdate) {
        RetrofitInstance.api.updateOrderStatus(userid, loginRequest)
            .enqueue(object : Callback<Order?> {
                override fun onResponse(call: Call<Order?>, response: Response<Order?>) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("MyAdapter", "Updated")
                    } else {
                        Log.d("MyAdapter", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<Order?>, t: Throwable) {
                    Log.e("MyAdapter", t.message.toString() )
                }
            })

    }

}
