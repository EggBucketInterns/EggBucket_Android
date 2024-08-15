package com.eggbucket.eggbucket_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.widget.*
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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

class DeliveryHomeFragment : Fragment()  {
    private val orderViewModel: OrderViewModel by activityViewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var liveOrderAdapter: LiveOrderAdapter
    lateinit var liveOrderDataList: ArrayList<GetAllOrdersItem>
    lateinit var amountCollected: TextView
    lateinit var pendingOrderCount: TextView
    lateinit var completedOrder: TextView
    lateinit var dataList: ArrayList<GetAllOrdersItem>
    lateinit var adapter: OrdersAdapter
    lateinit var pendingOrders:LinearLayout
    lateinit var CompletedOrders:LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delivery_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        fetchOrders()
        observeViewModel()

        pendingOrders.setOnClickListener {
            startActivity(Intent(requireContext(),PendingDeliveryOrders::class.java))
        }
        CompletedOrders.setOnClickListener {
            startActivity(Intent(requireContext(),CompletedDeliveryOrders::class.java))
        }
    }

    private fun initViews(view: View) {
        amountCollected = view.findViewById(R.id.amount_collected)
        pendingOrderCount = view.findViewById(R.id.pending_order_count)
        completedOrder = view.findViewById(R.id.txt_completed)
        recyclerView = view.findViewById(R.id.live_order_RV)
        pendingOrders=view.findViewById(R.id.pending_layout)
        CompletedOrders=view.findViewById(R.id.completed_layout)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fetchOrders() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val userId = getUserId()
                if (userId != null) {
                    Log.d("DeliveryHomeFragment", "Fetching all orders")

                    // Fetch all orders
                    dataList = withContext(Dispatchers.IO) {
                        RetrofitInstance.api.getAllOrders()
                    }
                    Log.d("DeliveryHomeFragment", "All orders response: $dataList")

                    // Filter live orders based on user ID
                    liveOrderDataList = dataList.filter { it.deliveryId._id == userId } as ArrayList<GetAllOrdersItem>
                    Log.d("DeliveryHomeFragment", "Filtered live orders: $liveOrderDataList")

                    // Set up the live order adapter
                    liveOrderAdapter = LiveOrderAdapter(requireContext(), liveOrderDataList)
                    recyclerView.adapter = liveOrderAdapter
                    Log.d("DeliveryHomeFragment", "Live order adapter set with ${liveOrderDataList.size} items")

                    // Set up the orders adapter
                    adapter = OrdersAdapter(requireContext(), dataList)
                    updateOrderStats()
                } else {
                    Log.d("DeliveryHomeFragment", "User ID is null")
                }
            } catch (e: Exception) {
                Log.e("DeliveryHomeFragment", "Error fetching orders", e)
            }
        }
    }

    private fun updateOrderStats() {
        val pendingOrdersCount = adapter.getPendingOrdersCount()
        val completedOrdersCount = adapter.getCompletedOrdersCount()

        Log.d("DeliveryHomeFragment", "Pending orders count: $pendingOrdersCount")
        Log.d("DeliveryHomeFragment", "Completed orders count: $completedOrdersCount")

        pendingOrderCount.text = pendingOrdersCount.toString()
        completedOrder.text = completedOrdersCount.toString()
    }

    private fun observeViewModel() {
        orderViewModel.amount.observe(viewLifecycleOwner) { amount ->
            Log.d("DeliveryHomeFragment", "Amount collected: ₹${amount}")
            amountCollected.text = "₹${amount}"
        }
    }

    private fun getUserId(): String? {
        val sharedPref = activity?.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref?.getString("USER_ID", null)
        Log.d("DeliveryHomeFragment", "Retrieved user ID: $userId")
        return userId
    }

    /*override fun onButtonClick(position: Int) {
       startActivity(Intent(requireContext(),Order_Details_Screen::class.java))
    }
*/
    /*override fun onTextViewClick(position: Int) {
        TODO("Not yet implemented")
    }*/
}