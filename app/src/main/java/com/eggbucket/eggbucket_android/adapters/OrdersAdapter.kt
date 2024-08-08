package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.Orders

class OrdersAdapter(
    private val context: Context,
    private val orderList: List<Orders>
) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    // ViewHolder class to hold references to the views
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdTextView: TextView = itemView.findViewById(R.id.order_id)
        val customerImageView: ImageView = itemView.findViewById(R.id.user_image)
        val customerIdTextView: TextView = itemView.findViewById(R.id.customer_idNumber)
        val numberOfTraysTextView: TextView = itemView.findViewById(R.id.trays_no)
        val createdAtTextView: TextView = itemView.findViewById(R.id.created_at_text)
        val deliveredAtTextView: TextView = itemView.findViewById(R.id.deliverd_at_text)
        val orderAmountTextView: TextView = itemView.findViewById(R.id.order_money)
        val orderStatusTextView: TextView = itemView.findViewById(R.id.txt_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        // Inflate the layout for each item
        val view = LayoutInflater.from(context).inflate(R.layout.all_orders_cart_items, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Get the current order item
        val order = orderList[position]

        // Bind the data to the views
        holder.orderIdTextView.text = "Order Id: #${order.orderId}"
        holder.customerImageView.setImageResource(order.customerImageResId)
        holder.customerIdTextView.text = order.customerId
        holder.numberOfTraysTextView.text = order.numberOfTrays.toString()
        holder.createdAtTextView.text = order.createdAt
        holder.deliveredAtTextView.text = order.deliveredAt
        holder.orderAmountTextView.text = "$ ${order.orderAmount}"

        // Set the order status background and text
        holder.orderStatusTextView.text = order.orderStatus
        holder.orderStatusTextView.setBackgroundResource(
            when (order.orderStatus) {
                "Completed" -> R.drawable.completed_back
                "Pending" -> R.drawable.pending_back // Example for other statuses
                //"Cancelled" -> R.drawable.cancelled_back // Example for other statuses
                else -> R.drawable.completed_back // Fallback drawable
            }
        )
    }

    override fun getItemCount(): Int {
        // Return the size of the order list
        return orderList.size
    }
}