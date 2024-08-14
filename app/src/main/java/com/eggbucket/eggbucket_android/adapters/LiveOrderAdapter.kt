package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter.OrderViewHolder
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem

class LiveOrderAdapter(val context:Context,val dataList:ArrayList<GetAllOrdersItem>):RecyclerView.Adapter<LiveOrderAdapter.LiveOrderViewHolder>() {

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
        holder.amountlive.text=currentItem.amount
        holder.venderName.text=currentItem.deliveryId.firstName

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}