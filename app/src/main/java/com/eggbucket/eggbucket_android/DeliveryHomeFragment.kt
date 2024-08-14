package com.eggbucket.eggbucket_android

import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.LiveOrderAdapter
import com.eggbucket.eggbucket_android.adapters.OrderViewModel
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeliveryHomeFragment : Fragment() {
    private val orderViewModel: OrderViewModel by activityViewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var liveOrderAdapter: LiveOrderAdapter
    lateinit var liveOrderdataList:ArrayList<GetAllOrdersItem>
    lateinit var  amount_col:TextView
    lateinit var  pendingOrderCount:TextView
    lateinit var completed_order:TextView
    lateinit var dataList:ArrayList<GetAllOrdersItem>
    lateinit var adapter: OrdersAdapter
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
         amount_col=view.findViewById(R.id.amount_collected)
         pendingOrderCount=view.findViewById(R.id.pending_order_count)
        completed_order=view.findViewById(R.id.txt_completed)
        recyclerView=view.findViewById(R.id.live_order_RV)

        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            liveOrderdataList = RetrofitInstance.api.getOrdersByDeliveryId(getUserId().toString())
            withContext(Dispatchers.Main) {
                liveOrderAdapter=LiveOrderAdapter(liveOrderdataList)
                recyclerView.adapter=liveOrderAdapter

            }
        }



        CoroutineScope(Dispatchers.IO).launch {
             dataList = RetrofitInstance.api.getAllOrders()
            withContext(Dispatchers.Main) {
                adapter = OrdersAdapter(requireContext(),dataList)
                pendingOrderCount.text=adapter.getPendingOrdersCount().toString()

                completed_order.text=adapter.getCompletedOrdersCount().toString()


            }
        }


        // Initialize the ViewModel
        //orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)


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
       // val confirmOrder=view.findViewById<Button>(R.id.confirm_order)
       // val pendingOd=view.findViewById<CardView>(R.id.pendingOd)
        /*pendingOd.setOnClickListener {
            startActivity(Intent(requireContext(),PendingOrdersActivity::class.java))
        }*/
        /*val amount_collected=view.findViewById<TextView>(R.id.amount_collected)
        amount_collected.text=message.toString()*/

        /*confirmOrder.setOnClickListener {
            startActivity(Intent(requireContext(),Order_Details_Screen::class.java))
        }*/


    }

    override fun onResume() {
        super.onResume()
        orderViewModel.amount.observe(viewLifecycleOwner, { amount ->
            amount_col.text="$${amount}"
        })

       /* orderViewModel.pendingItems.observe(viewLifecycleOwner,{pending ->
            pendingOrderCount.text=pending
        })*/

    }
    private fun getUserId():String?{
        val sharedPref = activity?.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPref?.getString("USER_ID",null)
    }

}