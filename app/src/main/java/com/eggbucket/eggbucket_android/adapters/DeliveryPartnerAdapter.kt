package com.eggbucket.eggbucket_android.adapters

import DeliveryPartner
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.Create_Order_Screen
import com.eggbucket.eggbucket_android.MainActivity
import com.eggbucket.eggbucket_android.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeliveryPartnerAdapter(
                             private val deliveryPartners: List<DeliveryPartner>,
                             private val onAssignButtonClick: (String, String) -> Unit,
                            ) :
    RecyclerView.Adapter<DeliveryPartnerAdapter.DeliveryPartnerViewHolder>() {

        private var selectedPosition:Int?=null;

    class DeliveryPartnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstnameTextView: TextView = itemView.findViewById(R.id.deliveryPartnerName)
        val profileImageView: ImageView = itemView.findViewById(R.id.profilePhoto)
        val assignBtn: TextView = itemView.findViewById(R.id.assignBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryPartnerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.delivery_partner_item, parent, false)
        return DeliveryPartnerViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: DeliveryPartnerViewHolder, position: Int) {
        val currentItem = deliveryPartners[holder.adapterPosition]
        Log.d("ChcekResponse", currentItem.toString())
        holder.firstnameTextView.text = currentItem.firstName

        // Load the image into ImageView
//        Glide.with(holder.itemView.context)
//            .load(currentItem.img) // Use a placeholder or error image if needed
//            .into(holder.profileImageView)


        if (position == selectedPosition) {
            holder.assignBtn.setText("Assigned");
            holder.assignBtn.setBackgroundResource(R.drawable.button_shape);
        } else {
            holder.assignBtn.setText("Assign");
            holder.assignBtn.setBackgroundResource(R.drawable.bg_green);
        }

        // Set the click listener for the assign button
        holder.assignBtn.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position

            // Notify the adapter to refresh UI
            notifyItemChanged(previousPosition ?: -1)
            notifyItemChanged(position)

            // Pass the selected delivery partner's ID back to the activity/fragment
            currentItem._id?.let { it1 -> currentItem.firstName?.let { it2 ->
                onAssignButtonClick(it1,
                    it2
                )
            } };
        }

    }

    override fun getItemCount() = deliveryPartners.size
}
