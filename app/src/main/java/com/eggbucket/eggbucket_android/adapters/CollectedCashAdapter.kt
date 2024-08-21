package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import kotlinx.coroutines.CoroutineScope

class CollectedCashAdapter(
    private val context: Context,
    private val orderList: ArrayList<GetAllOrdersItem>,
    private val coroutineScope: CoroutineScope,
) : RecyclerView.Adapter<CollectedCashAdapter.CollectedOrderViewHolder>() {

    class CollectedOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deliveryPartnerName: TextView = itemView.findViewById(R.id.deliveryPartnerName)
        val amoutn: TextView = itemView.findViewById(R.id.amount);

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectedOrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cash_collected_item, parent, false)
        return CollectedOrderViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CollectedCashAdapter.CollectedOrderViewHolder,
        position: Int
    ) {
        val order = orderList[position]
        holder.deliveryPartnerName.text = order.deliveryId.firstName;
        holder.amoutn.text = order.amount;
    }

    override fun getItemCount(): Int {
       return orderList.size
    }
}