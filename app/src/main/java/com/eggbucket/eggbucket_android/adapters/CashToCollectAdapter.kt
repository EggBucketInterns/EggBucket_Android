package com.eggbucket.eggbucket_android.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.MainActivity
import com.eggbucket.eggbucket_android.OutletHomeFragment
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import com.eggbucket.eggbucket_android.network.UpdateReturnAmountRequest1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CashToCollectAdapter(
    private val context: Context,
    private val orderList: ArrayList<GetAllOrdersItem>,

    private val coroutineScope: CoroutineScope,
) : RecyclerView.Adapter<CashToCollectAdapter.OrderViewHolder>() {

    interface OnCompleteClickListener {
        fun onCompleteClick()
    }


    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deliveryPartnerName: TextView = itemView.findViewById(R.id.deliveryPartnerName)
        val amoutn: TextView = itemView.findViewById(R.id.amount);
        val CompletedBtn: Button = itemView.findViewById(R.id.completedBtn);
        fun bind(data: GetAllOrdersItem, onCompleteClickListener: OnCompleteClickListener) {
            CompletedBtn.setOnClickListener {
                onCompleteClickListener.onCompleteClick()
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
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
                    decreaseReturnAmount(order._id,order.amount)
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

    private fun decreaseReturnAmount(orderId: String,amount:String) {
        val requestBody = UpdateReturnAmountRequest1(orderId, amount)

        Log.d("ReturnAmountDec", "Request Body: OrderId = $orderId, Amount = $amount")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.decreaseReturnAmount(requestBody)
                if (response.isSuccessful) {
                    Log.d("ReturnAmountDec", "Successfully decreased return amount.")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Return amount decreased successfully", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ReturnAmountDec", "Failed to decrease return amount: ${response.code()}, Error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("ReturnAmountDec", "Error decreasing return amount", e)
                e.printStackTrace()
            }
        }
    }

    private fun updateOrder(orderId: String) {
        val statusUpdate = mapOf("status" to "completed")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    RetrofitInstance.apiService.updateStatusCompleted(orderId, statusUpdate);
                val fragment = OutletHomeFragment()


                // Handle success, e.g., update UI or process the response
                println("Order updated successfully: $response")
            } catch (e: Exception) {
                // Handle failure
                println("Failed to update order: ${e.message}")
            }
        }
    }

}
