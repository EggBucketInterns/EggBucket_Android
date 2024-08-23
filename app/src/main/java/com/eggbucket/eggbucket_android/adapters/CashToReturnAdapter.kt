package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.data.DeliveryPartnerrr
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CashToReturnAdapter(
    private val context: Context,
    private val orderList: ArrayList<DeliveryPartnerrr>,
) : RecyclerView.Adapter<CashToReturnAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val outletName: TextView = itemView.findViewById(R.id.outletName)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val image: ImageView = itemView.findViewById(R.id.imagev1)
    }



    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.returned_cash_card_items, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Get the current order item
        val order = orderList[position]
        val outletName = order.payments[position]

        fetchOutletDetails(outletName.oId) { outletPartnerName,outletImage  ->
            holder.outletName.text = (outletPartnerName ?: "Unknown Outlet").toString()
            Glide.with(context)
                .load(outletImage)
                .placeholder(R.drawable.logo)
                .into(holder.image)
        }

        holder.amount.text ="₹ ${outletName.returnAmt}"

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    private fun fetchOutletDetails(outletId: String, onResult: (String?, Any?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val outletResponse = RetrofitInstance.apiService.getOutletByOutletId(outletId)
                if (outletResponse.status == "success") {
                    val outlet = outletResponse.data.firstOrNull()
                    val outletPartnerName = outlet?.outletArea
                    val outletImage = outlet?.img
                    withContext(Dispatchers.Main) {
                        onResult(outletPartnerName, outletImage)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResult(null, null)
                    }
                }
            } catch (e: Exception) {
                Log.e("FetchOutletError", "Error fetching outlet details: ${e.message}")
                withContext(Dispatchers.Main) {
                    onResult(null, null)
                }
            }
        }
    }
}