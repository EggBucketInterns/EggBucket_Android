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
import com.eggbucket.eggbucket_android.model.Outlet
import com.eggbucket.eggbucket_android.model.StatusUpdate
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.data.DeliveryPartnerrr
import com.eggbucket.eggbucket_android.model.login.LoginRequest
import com.eggbucket.eggbucket_android.model.login.LoginResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CashToReturnAdapter(
    private val context: Context,
    private val orderList: ArrayList<DeliveryPartnerrr>,
) : RecyclerView.Adapter<CashToReturnAdapter.OrderViewHolder>() {
    var outletPartnerName = ""

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val outletName: TextView = itemView.findViewById(R.id.outletName)
        val amount: TextView = itemView.findViewById(R.id.amount)
    }



    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.returned_cash_card_items, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Get the current order item
        val order = orderList[position]
        val outletName = order.payments[position]

        fetchOutletDetails(outletName.oId) { outletPartnerName ->
            holder.outletName.text = (outletPartnerName ?: "Unknown Outlet").toString()
        }

//        holder.outletName.text = outletPartnerName

        holder.amount.text ="₹ ${outletName.returnAmt.toString()}"

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    private fun fetchOutletDetails(outletId: String, onResult: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val outletResponse = RetrofitInstance.apiService.getOutletByOutletId(outletId)
                if (outletResponse.status == "success") {
                    val outlet = outletResponse.data.firstOrNull()
                    val outletPartnerName = outlet?.outletPartner?.firstName
                    withContext(Dispatchers.Main) {
                        onResult(outletPartnerName)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResult(null)
                    }
                }
            } catch (e: Exception) {
                Log.e("FetchOutletError", "Error fetching outlet details: ${e.message}")
                withContext(Dispatchers.Main) {
                    onResult(null)
                }
            }
        }
    }
}