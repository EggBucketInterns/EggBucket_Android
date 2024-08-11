package com.eggbucket.eggbucket_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem

class DeliveryPartnerAdapter(private val deliveryPartners: List<DeliveryPartnersItem>,
                             private val onAssignButtonClick: (String,String) -> Unit
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
        val currentItem = deliveryPartners[position]
        holder.firstnameTextView.text = currentItem.firstName

        // Load the image into ImageView
        Glide.with(holder.itemView.context)
            .load(currentItem.img) // Use a placeholder or error image if needed
            .into(holder.profileImageView)


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
            onAssignButtonClick(currentItem._id,currentItem.firstName);
        }

    }

    override fun getItemCount() = deliveryPartners.size
}
