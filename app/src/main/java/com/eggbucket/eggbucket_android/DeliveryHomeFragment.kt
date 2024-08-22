package com.eggbucket.eggbucket_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.CashToReturn
import com.eggbucket.eggbucket_android.adapters.LiveOrderAdapter
import com.eggbucket.eggbucket_android.adapters.OrderViewModel
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.data.DeliveryPartnerrr
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryHomeFragment : Fragment() {
    private val orderViewModel: OrderViewModel by activityViewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var liveOrderAdapter: LiveOrderAdapter
    lateinit var liveOrderDataList: ArrayList<GetAllOrdersItem>
    lateinit var amountCollected: TextView
    lateinit var pendingOrderCount: TextView
    lateinit var completedOrderCard: CardView
    lateinit var pendingOrderCard: CardView
    lateinit var completedOrder: TextView
    lateinit var dataList: ArrayList<GetAllOrdersItem>
    lateinit var adapter: OrdersAdapter
    lateinit var cashCollectedBtn : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delivery_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        fetchDeliveryPartnerData()
        fetchOrders()
        //observeViewModel()

        cashCollectedBtn.setOnClickListener {
            startActivity(
                Intent(context,
                    CashToReturn::class.java
                )
            )
        }

        completedOrderCard.setOnClickListener {
            startActivity(Intent(requireContext(), CompletedDeliveryOrders::class.java))
        }
        pendingOrderCard.setOnClickListener {
            startActivity(Intent(requireContext(), PendingDeliveryOrders::class.java))
        }
    }

    private fun initViews(view: View) {
        completedOrderCard = view.findViewById(R.id.completed_order_delivey)
        pendingOrderCard = view.findViewById(R.id.pending_order_delivery)
        amountCollected = view.findViewById(R.id.amount_collected)
        pendingOrderCount = view.findViewById(R.id.pending_order_count)
        completedOrder = view.findViewById(R.id.txt_completed)
        recyclerView = view.findViewById(R.id.live_order_RV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cashCollectedBtn = view.findViewById(R.id.cashCollectedBtn)
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
                    liveOrderDataList =
                        dataList.filter { it.deliveryId._id == userId && it.status == "pending" } as ArrayList<GetAllOrdersItem>
                    Log.d("DeliveryHomeFragment", "Filtered live orders: $liveOrderDataList")
                    Log.d("DeliveryHomeFragment", "User Id: $userId")

                    // Calculate the number of completed and pending orders
                    val completedOrders =
                        dataList.filter { it.deliveryId._id == userId && it.status == "delivered" }
                    val pendingOrders = liveOrderDataList.size

                    val totalAmountCollected = completedOrders.sumOf { it.amount.toDouble() }

                    pendingOrderCount.text = pendingOrders.toString()
                    completedOrder.text = completedOrders.size.toString()

                    // Set up the live order adapter
                    liveOrderAdapter = LiveOrderAdapter(requireContext(),parentFragmentManager, liveOrderDataList)
                    recyclerView.adapter = liveOrderAdapter
                    Log.d(
                        "DeliveryHomeFragment",
                        "Live order adapter set with ${liveOrderDataList.size} items"
                    )

                    // Set up the orders adapter
                    adapter = OrdersAdapter(requireContext(), dataList)
//                    updateOrderStats()
                } else {
                    Log.d("DeliveryHomeFragment", "User ID is null")
                }
            } catch (e: Exception) {
                Log.e("DeliveryHomeFragment", "Error fetching orders", e)
            }
        }
    }

    private fun fetchDeliveryPartnerData() {
        val userId = getUserId()
        if (userId != null) {
            RetrofitInstance.api.getDeliveryPartner(userId).enqueue(object :
                Callback<DeliveryPartnerrr> {
                override fun onResponse(
                    call: Call<DeliveryPartnerrr>,
                    response: Response<DeliveryPartnerrr>
                ) {
                    if (response.isSuccessful) {
                        val partner = response.body()
                        if (partner != null) {
                            // Calculate total amount collected
                            val totalAmountCollected = partner.payments.sumOf { it.returnAmt.toDouble() }
                            amountCollected.text = "₹${totalAmountCollected}"
                            Log.d(
                                "DeliveryHomeFragment",
                                "Amount collected: ₹${totalAmountCollected}"
                            )
                        } else {
                            Log.e("DeliveryHomeFragment", "Response body is null")
                        }
                    } else {
                        Log.e(
                            "DeliveryHomeFragment",
                            "Failed to fetch delivery partner data: ${
                                response.errorBody()?.string()
                            }"
                        )
                    }
                }

                override fun onFailure(call: Call<DeliveryPartnerrr>, t: Throwable) {
                    Log.e(
                        "DeliveryHomeFragment",
                        "Error fetching delivery partner data: ${t.message}"
                    )
                }
            })
        } else {
            Log.d("DeliveryHomeFragment", "User ID is null")
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

    /*private fun observeViewModel() {
        orderViewModel.amount.observe(viewLifecycleOwner) { amount ->
            Log.d("DeliveryHomeFragment", "Amount collected: ₹${amount}")
            amountCollected.text = "₹${amount}"
        }
    }*/

    private fun getUserId(): String? {
        val sharedPref = activity?.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref?.getString("USER_ID", null)
        Log.d("DeliveryHomeFragment", "Retrieved user ID: $userId")
        return userId
    }

    fun onButtonClick(position: Int) {
        startActivity(Intent(requireContext(), Order_Details_Screen::class.java))
    }

    /*override fun onTextViewClick(position: Int) {
        TODO("Not yet implemented")
    }*/
}