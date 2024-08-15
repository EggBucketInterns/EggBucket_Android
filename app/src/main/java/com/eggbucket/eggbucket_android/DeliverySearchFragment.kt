package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeliverySearchFragment : Fragment() {
    private lateinit var adapter: OrdersAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<GetAllOrdersItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val completed=view.findViewById<TextView>(R.id.filter_completed)
        val cancelled=view.findViewById<TextView>(R.id.filter_cancelled)
        val intransit=view.findViewById<TextView>(R.id.filter_intransit)
        val pending=view.findViewById<TextView>(R.id.filter_pending)
        val filter=view.findViewById<TextView>(R.id.apply_filter)
        val reset=view.findViewById<TextView>(R.id.filter_reset)

        completed.setOnClickListener {
            completed.setBackgroundResource(R.drawable.apply_filter_back)
            cancelled.setBackgroundResource(R.drawable.no_background)
            intransit.setBackgroundResource(R.drawable.no_background)
            pending.setBackgroundResource(R.drawable.no_background)
        }
        cancelled.setOnClickListener {
            completed.setBackgroundResource(R.drawable.no_background)
            cancelled.setBackgroundResource(R.drawable.apply_filter_back)
            intransit.setBackgroundResource(R.drawable.no_background)
            pending.setBackgroundResource(R.drawable.no_background)
        }
        intransit.setOnClickListener {
            completed.setBackgroundResource(R.drawable.no_background)
            cancelled.setBackgroundResource(R.drawable.no_background)
            intransit.setBackgroundResource(R.drawable.apply_filter_back)
            pending.setBackgroundResource(R.drawable.no_background)
        }
        pending.setOnClickListener {
            completed.setBackgroundResource(R.drawable.no_background)
            cancelled.setBackgroundResource(R.drawable.no_background)
            intransit.setBackgroundResource(R.drawable.no_background)
            pending.setBackgroundResource(R.drawable.apply_filter_back)
        }

        filter.setOnClickListener {
            when {
                completed.background.constantState == resources.getDrawable(R.drawable.apply_filter_back).constantState -> {
                    // Action for 'completed' being selected
                    Toast.makeText(context, "Completed is selected", Toast.LENGTH_SHORT).show()
                    // Navigate to another activity or perform any action you want
                    val intent = Intent(activity, CompletedDeliveryOrders::class.java)
                    startActivity(intent)
                }

                cancelled.background.constantState == resources.getDrawable(R.drawable.apply_filter_back).constantState -> {
                    // Action for 'cancelled' being selected
                    Toast.makeText(context, "Cancelled is selected", Toast.LENGTH_SHORT).show()
                    // Navigate to another activity or perform any action you want
                    val intent = Intent(activity, CancelledDeliveryActivity::class.java)
                    startActivity(intent)
                }

                intransit.background.constantState == resources.getDrawable(R.drawable.apply_filter_back).constantState -> {
                    // Action for 'intransit' being selected
                    Toast.makeText(context, "In Transit is selected", Toast.LENGTH_SHORT).show()
                    // Navigate to another activity or perform any action you want
                    val intent = Intent(activity, IntransitDeliveryActivity::class.java)
                    startActivity(intent)
                }

                pending.background.constantState == resources.getDrawable(R.drawable.apply_filter_back).constantState -> {
                    // Action for 'pending' being selected
                    Toast.makeText(context, "Pending is selected", Toast.LENGTH_SHORT).show()
                    // Navigate to another activity or perform any action you want
                    val intent = Intent(activity, PendingDeliveryOrders::class.java)
                    startActivity(intent)
                }

                else -> {
                    // Default action if no button is selected
                    Toast.makeText(context, "No filter selected", Toast.LENGTH_SHORT).show()
                }
            }
        }


       /* filter.setOnClickListener {
            when(R.drawable.apply_filter_back){

            }


            CoroutineScope(Dispatchers.IO).launch {
                dataList = RetrofitInstance.api.getAllOrders()
                withContext(Dispatchers.Main) {
                    adapter = OrdersAdapter(requireContext(),dataList)
                    pending.text="Pending  ${adapter.getPendingOrdersCount().toString()} "
                    completed.text="Completed  ${adapter.getCompletedOrdersCount().toString()} "
                    intransit.text="In-Transit  ${adapter.getInTransitOrdersCount().toString()} "
                    cancelled.text="Cancelled  ${adapter.getCancelledOrdersCount().toString()} "
                }
            }
        }
        reset.setOnClickListener {
            pending.text="Pending"
            completed.text="Completed"
            intransit.text="In-Transit"
            cancelled.text="Cancelled"
        }*/

    }
}