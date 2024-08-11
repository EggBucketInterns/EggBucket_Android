package com.eggbucket.eggbucket_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.DeliveryPartnersItem
import com.eggbucket.eggbucket_android.model.VendorItem

class VendorAdapter(private val vendors: List<VendorItem>,
                             private val onItemClick: (String,String) -> Unit
) :
    RecyclerView.Adapter<VendorAdapter.VendorViewHolder>() {

    private var selectedPosition:Int?=null;

    class VendorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val VendorName: TextView = itemView.findViewById(R.id.vendorName)
        val vendorCard: LinearLayout = itemView.findViewById(R.id.vendorCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.vendor_item, parent, false)
        return VendorViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val currentItem = vendors[position]
        holder.VendorName.text = currentItem.vendorName

        // Load the image into ImageView
//        Glide.with(holder.itemView.context)
//            .load(currentItem.img) // Use a placeholder or error image if needed
//            .into(holder.profileImageView)


        if (position == selectedPosition) {
            holder.vendorCard.setBackgroundResource(R.drawable.order_detail_bg);
        } else {
            holder.vendorCard.setBackgroundResource(R.drawable.white_bg);
        }

        // Set the click listener for the assign button
        holder.vendorCard.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position

            // Notify the adapter to refresh UI
            notifyItemChanged(previousPosition ?: -1)
            notifyItemChanged(position)

            // Pass the selected delivery partner's ID back to the activity/fragment
            onItemClick(currentItem._id,currentItem.vendorName);
        }

    }

    override fun getItemCount() = vendors.size
}
