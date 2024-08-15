package com.eggbucket.eggbucket_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.Order_Details_Screen
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import java.text.SimpleDateFormat
import java.util.Locale

class LiveOrderAdapter(
    val context: Context,
    val dataList: ArrayList<GetAllOrdersItem>,

) : RecyclerView.Adapter<LiveOrderAdapter.LiveOrderViewHolder>() {

   /* // Interface for click events
    interface OnItemClickListener {
        fun onButtonClick(position: Int)
        //fun onTextViewClick(position: Int)
    }*/

    class LiveOrderViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val date = itemview.findViewById<TextView>(R.id.live_order_date)
        val trays = itemview.findViewById<TextView>(R.id.live_order_tray)
        val venderName = itemview.findViewById<TextView>(R.id.vender_name_live)
        val amountlive = itemview.findViewById<TextView>(R.id.live_order_amount)
        val confirm = itemview.findViewById<TextView>(R.id.confirm_order)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LiveOrderAdapter.LiveOrderViewHolder {
        // Inflate the layout for each item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.live_order_items, parent, false)
        return LiveOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: LiveOrderAdapter.LiveOrderViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.date.text = currentItem.createdAt
        holder.trays.text = "${currentItem.numTrays} Trays of Eggs"
        holder.amountlive.text = "₹${currentItem.amount}"

        holder.venderName.text = currentItem.customerId?.customerName ?: "Unknown Customer"
        /*holder.confirm.setOnClickListener {
            listener.onButtonClick(position)
        }*/

        /*holder.textView.setOnClickListener {
            listener.onTextViewClick(position)
        }*/

    holder.confirm.setOnClickListener {
        // Create an Intent to navigate to the Order_Details_Screen
        val intent = Intent(context, Order_Details_Screen::class.java)

        // Pass the order ID to the next screen using a Bundle
        intent.putExtra("ORDER_ID", currentItem._id)

        // Start the activity with the Intent
        context.startActivity(intent)
    }
        }



    override fun getItemCount(): Int {
        return dataList.size
    }
}

    private fun formatDate(dateString: String?): String {
        // Assuming dateString is in ISO format, e.g., "2024-08-15T12:34:56Z"
        // You can use SimpleDateFormat to convert it to a more readable format
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }


