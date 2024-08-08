package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.RecentOrdersData

class ResentOrdersAdapter(
    private val context: Context,
    private val orderList: List<RecentOrdersData>
) : RecyclerView.Adapter<ResentOrdersAdapter.OrderViewHolder>() {

    // ViewHolder class to hold references to the views in each item layout
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_outlet)
        val orderNameTextView: TextView = itemView.findViewById(R.id.order_name_1)
        val orderLocationTextView: TextView = itemView.findViewById(R.id.order_location_1)
        val orderStatusTextView: TextView = itemView.findViewById(R.id.order_status_1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        // Inflate the item layout and return the ViewHolder
        val view = LayoutInflater.from(context).inflate(R.layout.item_outletorder, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Get the current order item
        val orderItem = orderList[position]

        // Bind the data to the views
        holder.imageView.setImageResource(R.drawable.ic_order) // Update with actual image if needed
        holder.orderNameTextView.text = orderItem.orderName
        holder.orderLocationTextView.text = orderItem.orderLocation
        holder.orderStatusTextView.text = orderItem.orderStatus

        // Set background based on order status
        when (orderItem.orderStatus) {
            "COMPLETED" -> holder.orderStatusTextView.setBackgroundResource(R.drawable.rounded_corner_green)
            // Add other status conditions if needed
            else -> holder.orderStatusTextView.setBackgroundResource(R.drawable.pendingback) // Fallback drawable
        }
    }

    override fun getItemCount(): Int {
        // Return the size of the order list
        return orderList.size
    }
}
