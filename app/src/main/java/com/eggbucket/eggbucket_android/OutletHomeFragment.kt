package com.eggbucket.eggbucket_android

import RecentOrdersAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.AllOrderAdapter
import com.eggbucket.eggbucket_android.adapters.DeliveryPartnerAdapter
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
//import com.eggbucket.eggbucket_android.adapters.ResentOrdersAdapter
import com.eggbucket.eggbucket_android.databinding.FragmentOutletHomeBinding
import com.eggbucket.eggbucket_android.model.AllOrders
import com.eggbucket.eggbucket_android.model.RecentOrdersData
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutletHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutletHomeFragment : Fragment() {
    lateinit var adapter: AllOrderAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var dataList:ArrayList<GetAllOrdersItem>
    lateinit var allOrders:ArrayList<GetAllOrdersItem>
    private var _binding: FragmentOutletHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderAdapter: RecentOrdersAdapter
    var totalOrders = 0
    var pendingOrders = 0
    var completedOrders = 0
    var totalPendingCash = 0

    private fun processOrders(orders: List<AllOrders>) {

        orderAdapter = RecentOrdersAdapter(orders)
        binding.recyclerView.adapter = orderAdapter

        var totalOrders = 0
        var pendingOrders = 0
        var completedOrders = 0
        var totalPendingCash = 0

        for (order in orders) {
            totalOrders++

            when (order.status) {
//                "pending" -> {
//                    pendingOrders++
//                    totalPendingCash += Integer.parseInt(order.amount);
//                }
//                "completed" -> completedOrders++
//                "delivered" -> totalPendingCash += Integer.parseInt(order.amount);
//                "intransit" -> totalPendingCash += Integer.parseInt(order.amount);
            }
        }
        // Now you have the counts and total pending cash.
        // You can update the UI or handle these values as needed.
        // For example, updating TextViews:
//        view?.findViewById<TextView>(R.id.totalOrdersTextView)?.text = "Total Orders: $totalOrders"
//        view?.findViewById<TextView>(R.id.pendingOrdersTextView)?.text = "Pending Orders: $pendingOrders"
//        view?.findViewById<TextView>(R.id.completedOrdersTextView)?.text = "Completed Orders: $completedOrders"
//        view?.findViewById<TextView>(R.id.pendingCashTextView)?.text = "Total Pending Cash: $totalPendingCash"
        binding.totalOrders.text = totalOrders.toString();
        binding.completedOrders.text = completedOrders.toString();
        binding.pendingOrders.text = pendingOrders.toString();
        binding.pendingCash.text = totalPendingCash.toString();
    }



//  private suspend fun fetchOrders() {
//        apiService.getAllOrders().enqueue(object : Callback<List<AllOrders>> {
//            override fun onResponse(call: Call<List<AllOrders>>, response: Response<List<AllOrders>>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val orders = response.body()!!
//                    Log.d("checkResponse", response.body().toString())
//                    processOrders(orders)
//                } else {
//                    Log.d("checkResponse", response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<List<AllOrders>>, t: Throwable) {
//                Log.d("checkResponse", "Failed"+t.message)
//            }
//        })
//    }
    private fun setupRecyclerView() {
        orderAdapter = RecentOrdersAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
    }
    private fun getUserId():String?{
        val sharedPref = activity?.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPref?.getString("USER_ID",null)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOutletHomeBinding.inflate(inflater, container, false)
        // Return the root view

        return binding.root
    }
    override fun onResume() {
        try {
            fetchDataAndBindRecyclerview()
        }
        catch (e : Exception){
            Log.e("ExceptionError", e.message.toString() )
        }

        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            Log.d("aaaaaaaaaaaaaa", getUserId().toString())
            setupRecyclerView()
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
        }
        catch (e : Exception){
            Log.e("ExceptionError", e.message.toString() )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference when the view is destroyed
        _binding = null
    }
    fun fetchDataAndBindRecyclerview(){

        try {
            CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getOutletByOutletPartnerID(getUserId().toString())
            withContext(Dispatchers.Main) {
                val outlets = response.data
                // Handle the list of outlets as needed
                Log.d("checkResponse", "Fetched outlets: $outlets")
                for(outlet in outlets){
                    Log.d("checkResponse", outlet.deliveryPartner.toString())
                    try {
                        binding.outletName.text= outlet.outletArea ;
                    }
                    catch (e : Exception){
                        e.message.toString()
                    }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("checkResponse", "Error: ${e.message}")
        }



        CoroutineScope(Dispatchers.IO).launch {
            dataList = RetrofitInstance.api.getOrdersByOutletId(getUserId().toString());

            withContext(Dispatchers.Main) {
                adapter = AllOrderAdapter(requireContext(),dataList)
                recyclerView.adapter = adapter
            }
            var totalOrders = 0
            var pendingOrders = 0
            var completedOrders = 0
            var totalPendingCash = 0
            for (order in dataList) {
                totalOrders++

                when (order.status) {
                    "pending" -> {
                        pendingOrders++
                    }
                    "completed" -> completedOrders++
                    "delivered" -> totalPendingCash += Integer.parseInt(order.amount);

                }
            }
            binding.totalOrders.text = totalOrders.toString();
            binding.completedOrders.text = completedOrders.toString();
            binding.pendingOrders.text = pendingOrders.toString();
            binding.pendingCash.text = totalPendingCash.toString();
        }
    }
}