package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.OrderDetailsOutletActivity
import com.eggbucket.eggbucket_android.Order_Details_Screen
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.Order
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrdersAdapter(
    private val context: Context,
   private val orderList: ArrayList<GetAllOrdersItem>
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
        val orderDetails:LinearLayout=itemView.findViewById(R.id.linear_layout_all_order_item)

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
        holder.orderIdTextView.text = "Order Id: #${order._id}"
      holder.customerImageView.setImageResource(R.drawable.user_profile_image)
      //  holder.customerIdTextView.text = order.customerId.customerId
        holder.numberOfTraysTextView.text = order.numTrays
        holder.createdAtTextView.text = order.createdAt
        holder.deliveredAtTextView.text = order.updatedAt
        holder.orderAmountTextView.text = "$ ${order.amount}"
        holder.orderStatusTextView.text=order.status

        holder.orderDetails.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    //Navigation
                    // Create an Intent to navigate to the Order_Details_Screen
                    val intent = Intent(context, OrderDetailsOutletActivity::class.java)
                    intent.putExtra("TRAYS",order.numTrays)
                    intent.putExtra("AMOUNT",order.amount)
                    intent.putExtra("NAME",order.deliveryId.firstName)
                    intent.putExtra("CREATED_AT", order.createdAt)
                    // Pass the order ID to the next screen using a Bundle
                    intent.putExtra("ORDER_ID", order._id)

                    // Start the activity with the Intent
                    context.startActivity(intent)

                } catch (e: Exception) {
                    // Handle any errors
                    Log.e("MyAdapter", "Failed to transfer", e)
                }
            }

        }

        // Set the order status background and text

        // Set background or text color based on order status
        when (order.status) {
            "Completed" -> {
                holder.orderStatusTextView.setBackgroundResource(R.drawable.completed_back)
                holder.orderStatusTextView.setTextColor(context.resources.getColor(android.R.color.white))
            }
            "Pending" -> {

                holder.orderStatusTextView.setBackgroundResource(R.drawable.pending_back)
                holder.orderStatusTextView.setTextColor(context.resources.getColor(android.R.color.black))
            }
            // Add more statuses as needed
        }

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
