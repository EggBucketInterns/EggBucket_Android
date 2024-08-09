package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.widget.*
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.eggbucket.eggbucket_android.adapters.OrderViewModel

class DeliveryHomeFragment : Fragment() {
    private val orderViewModel: OrderViewModel by activityViewModels()
   // private var message: String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delivery_home, container, false)


        // Use the message as needed
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val status=false
        val amount_col=view.findViewById<TextView>(R.id.amount_collected)
        val pendingOrderCount=view.findViewById<TextView>(R.id.pending_order_count)

        // Initialize the ViewModel
        //orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        orderViewModel.amount.observe(viewLifecycleOwner, { amount ->
            amount_col.text=amount

        })

        orderViewModel.pendingItems.observe(viewLifecycleOwner,{pending ->
            pendingOrderCount.text=pending
        })
        /*// Get arguments
        val message = arguments?.getString("amount") ?: ""
        val pendingItem = arguments?.getString("Items") ?: ""
// Observe ViewModel
        orderViewModel.amount.observe(viewLifecycleOwner, {message ->
            amount_col.setText("$$message")
        })

        orderViewModel.pendingItems.observe(viewLifecycleOwner, { pendingItem ->
            pendingOrderCount.setText(pendingItem)
        })



        // Update ViewModel
        orderViewModel.setAmount(message)
        orderViewModel.setPendingItems(pendingItem)

*/
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