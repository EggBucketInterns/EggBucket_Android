package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.delivery_dashboard
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem

class CashToCollectAdapter(
    private val context: Context,
    private val orderList: ArrayList<GetAllOrdersItem>
) : RecyclerView.Adapter<CashToCollectAdapter.OrderViewHolder>() {

    // ViewHolder class to hold references to the views
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deliveryPartnerName: TextView = itemView.findViewById(R.id.deliveryPartnerName)
        val amoutn: TextView = itemView.findViewById(R.id.amount);
        val CompletedBtn: ImageView =  itemView.findViewById(R.id.completedBtn);
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
    }

    override fun getItemCount(): Int {
        return orderList.size
    }


}
