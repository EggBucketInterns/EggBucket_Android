package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.Order
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem

class AllOrderAdapter(
    private val context: Context,
    private var orderList: ArrayList<GetAllOrdersItem>
) : RecyclerView.Adapter<AllOrderAdapter.OrderViewHolder>() {

    // ViewHolder class to hold references to the views
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noOfTrays: TextView = itemView.findViewById(R.id.no_of_trays)
        val status :TextView = itemView.findViewById(R.id.order_status)
        val amount :TextView = itemView.findViewById(R.id.order_location)

    }
    fun updateData(newData: ArrayList<GetAllOrdersItem>) {
        orderList = newData
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        // Inflate the layout for each item
        val view = LayoutInflater.from(context).inflate(R.layout.item_outletorder, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Get the current order item
        val order = orderList[position]
        holder.noOfTrays.text = order.numTrays;
        holder.status.text = order.status;
        holder.amount.text = order.amount;
        when (order.status) {
            "completed" -> {
              holder.status.setBackgroundResource(R.drawable.completed_back)
            }
            "pending" -> {
                holder.status.setBackgroundResource(R.drawable.pending_back)
            }
            "intransit" ->{
                holder.status.setBackgroundResource(R.drawable.blue_bg)
            }
            "canceled" ->{
                holder.status.setBackgroundResource(R.drawable.cancelled_bg)
            }
            "delivered"->{
                holder.status.setBackgroundResource(R.drawable.button)
            }
            // Add more statuses as needed
        }
//        // Bind the data to the views
//        holder.orderIdTextView.text = "Order Id: #${order._id}"
//        holder.customerImageView.setImageResource(R.drawable.user_profile_image)
//        //  holder.customerIdTextView.text = order.customerId.customerId
//        holder.numberOfTraysTextView.text = order.numTrays
//        holder.createdAtTextView.text = order.createdAt
//        holder.deliveredAtTextView.text = order.updatedAt
//        holder.orderAmountTextView.text = "$ ${order.amount}"
//        holder.orderStatusTextView.text=order.status

        // Set the order status background and text

        // Set background or text color based on order status


    }

    override fun getItemCount(): Int {
        // Return the size of the order list
        return orderList.size
    }
    fun getCompletedOrdersCount(): Int = orderList.count { it.status == "completed" }
    fun getPendingOrdersCount(): Int = orderList.count { it.status == "pending" }
    fun getInTransitOrdersCount(): Int = orderList.count { it.status == "intransit" }
    fun getCancelledOrdersCount(): Int = orderList.count { it.status == "cancelled" }


}
