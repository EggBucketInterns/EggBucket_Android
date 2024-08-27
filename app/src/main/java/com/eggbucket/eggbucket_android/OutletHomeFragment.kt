package com.eggbucket.eggbucket_android

import RecentOrdersAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.AllOrderAdapter
//import com.eggbucket.eggbucket_android.adapters.ResentOrdersAdapter
import com.eggbucket.eggbucket_android.databinding.FragmentOutletHomeBinding
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OutletHomeFragment : Fragment() {
    lateinit var adapter: AllOrderAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var dataList: ArrayList<GetAllOrdersItem>
    lateinit var allOrders: ArrayList<GetAllOrdersItem>
    private var _binding: FragmentOutletHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderAdapter: RecentOrdersAdapter
    private var outletId: String = ""
    lateinit var profileImage:ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOutletHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage=view.findViewById(R.id.avatar)
        setupRecyclerView()
        try {
            binding.totalOrderBtn.setOnClickListener {

                val intent = Intent(requireContext(), AllOrdersActiviry::class.java).apply {
                    putExtra("name", "Total Orders");
                }
                startActivity(intent)
            }
            binding.completedOrderBtn.setOnClickListener {

                val intent = Intent(requireContext(), CompletedOrders::class.java)
                startActivity(intent)
            }
            binding.pendingOrdersBtn.setOnClickListener {

                val intent = Intent(requireContext(), PendingOrders::class.java)
                startActivity(intent)
            }
            binding.pendingCashBtn.setOnClickListener {

                val intent = Intent(requireContext(), CashCollectedActivity::class.java)
                startActivity(intent)

            }

            Log.d("getting user id ", getUserId().toString())
            // fetchOrders()
            dataList = arrayListOf()
            // Find the RecyclerView
            recyclerView = view.findViewById(R.id.recyclerView)

            // Set layout manager
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // Set the adapter
            try {
                fetchDataAndBindRecyclerview()
            } catch (e: Exception) {
                Log.e("ExceptionError", e.message.toString())
            }
        } catch (e: Exception) {
            Log.e("ExceptionError", e.message.toString())
        }
    }

    override fun onResume() {
        try {
            fetchDataAndBindRecyclerview()
        } catch (e: Exception) {
            Log.e("ExceptionError", e.message.toString())
        }

        super.onResume()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference when the view is destroyed
        _binding = null
    }


    private fun setupRecyclerView() {
        orderAdapter = RecentOrdersAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
    }

    private fun getUserId(): String? {
        val sharedPref = activity?.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPref?.getString("USER_ID", null)
    }


    private fun saveOutletIdToPreferences(outletId: String) {
        val sharedPref =
            activity?.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("OUTLET_ID", outletId)
            apply()
        }
    }

    private fun fetchDataAndBindRecyclerview() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userId = getUserId() ?: return@launch
                val response = RetrofitInstance.apiService.getOutletByOutletPartnerID(userId)

                withContext(Dispatchers.Main) {
                    response.data.let { outlets ->
                        for (outlet in outlets) {
                            binding.outletName.text = outlet.outletArea
                            outletId = outlet._id
                            Picasso.get().load(outlet.img).into(profileImage)
                            Log.d("OutletId", "outletId $outletId")
                            saveOutletIdToPreferences(outletId)
                            Log.d("OutletId Saved", "saved $outletId")
                        }

                        if (outletId.isNotEmpty()) {
                            fetchOrdersByOutletId(outletId)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("fetchData", "Error: ${e.message}")
            }
        }
    }

    private suspend fun fetchOrdersByOutletId(outletId: String) {
        try {
            dataList = RetrofitInstance.api.getOrdersByOutletId(outletId)

            withContext(Dispatchers.Main) {
                adapter = AllOrderAdapter(requireContext(), dataList)
                binding.recyclerView.adapter = adapter

                var totalOrders = 0
                var pendingOrders = 0
                var completedOrders = 0
                var totalPendingCash = 0

                for (order in dataList) {
                    totalOrders++

                    when (order.status) {
                        "pending" -> pendingOrders++
                        "completed" -> completedOrders++
                        "delivered" -> totalPendingCash += Integer.parseInt(order.amount)
                    }
                }

                binding.totalOrders.text = totalOrders.toString()
                binding.completedOrders.text = completedOrders.toString()
                binding.pendingOrders.text = pendingOrders.toString()
                binding.pendingCash.text = totalPendingCash.toString()
            }
        } catch (e: Exception) {
            Log.e("fetchOrders", "Error: ${e.message}")
        }
    }
}