package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.PendingOrder

class PendingOrderAdapter(
    private val context: Context,
    private val pendingOrderList: List<PendingOrder>
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    // ViewHolder class to hold references to the views in each item layout
    inner class PendingOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdTextView: TextView = itemView.findViewById(R.id.order_id)
        val customerIdTextView: TextView = itemView.findViewById(R.id.customer_id)
        val customerIdNumberTextView: TextView = itemView.findViewById(R.id.customer_idNumber)
        val numberOfTraysTextView: TextView = itemView.findViewById(R.id.trays_no)
        val createdAtTextView: TextView = itemView.findViewById(R.id.created_at_text)
        val deliveredAtTextView: TextView = itemView.findViewById(R.id.deliverd_at_text)
        val orderAmountTextView: TextView = itemView.findViewById(R.id.order_money)
        val orderStatusTextView: TextView = itemView.findViewById(R.id.txt_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        // Inflate the item layout and return the ViewHolder
        val view = LayoutInflater.from(context).inflate(R.layout.pending_card_items, parent, false)
        return PendingOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        // Get the current order item
        val orderItem = pendingOrderList[position]

        // Bind the data to the views
        holder.orderIdTextView.text = "Order Id : ${orderItem.orderId}"
        holder.customerIdTextView.text = orderItem.customerId
        holder.customerIdNumberTextView.text = orderItem.customerIdNumber
        holder.numberOfTraysTextView.text = orderItem.numberOfTrays.toString()
        holder.createdAtTextView.text = "Created At: ${orderItem.createdAt}"
        holder.deliveredAtTextView.text = "Delivered At: ${orderItem.deliveredAt}"
        holder.orderAmountTextView.text = "$${orderItem.orderAmount}"
        holder.orderStatusTextView.text = orderItem.orderStatus

        // Set background or text color based on order status
        when (orderItem.orderStatus) {
            "Completed" -> {
                holder.orderStatusTextView.setBackgroundResource(R.drawable.completed_back)
                holder.orderStatusTextView.setTextColor(context.resources.getColor(android.R.color.white))
            }
            "Pending" -> {
                holder.orderStatusTextView.setBackgroundResource(R.drawable.pending_back)
                holder.orderStatusTextView.setTextColor(context.resources.getColor(android.R.color.black))
            }
            "Shipped" -> {
                holder.orderStatusTextView.setBackgroundResource(R.drawable.pendingback)
                holder.orderStatusTextView.setTextColor(context.resources.getColor(android.R.color.holo_blue_dark))
            }
            // Add more statuses as needed
        }
    }

    override fun getItemCount(): Int {
        // Return the size of the order list
        return pendingOrderList.size
    }
}
