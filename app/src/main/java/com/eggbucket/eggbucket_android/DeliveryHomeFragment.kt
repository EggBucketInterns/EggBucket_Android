package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.widget.*
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class DeliveryHomeFragment : Fragment() {
   // private var message: String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delivery_home, container, false)
        val amount_col=view.findViewById<TextView>(R.id.amount_collected)
        val pendingOrderCount=view.findViewById<TextView>(R.id.pending_order_count)
        val  message = arguments?.getString("amount")
        amount_col.setText("$${message}")

        val  pendingItem = arguments?.getString("Items")
        pendingOrderCount.setText(pendingItem)

        // Use the message as needed
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val confirmOrder=view.findViewById<Button>(R.id.confirm_order)
        val pendingOd=view.findViewById<CardView>(R.id.pendingOd)
        pendingOd.setOnClickListener {
            startActivity(Intent(requireContext(),PendingOrdersActivity::class.java))
        }
        /*val amount_collected=view.findViewById<TextView>(R.id.amount_collected)
        amount_collected.text=message.toString()*/

        confirmOrder.setOnClickListener {
            startActivity(Intent(requireContext(),Order_Details_Screen::class.java))
        }


    }

}