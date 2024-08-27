package com.eggbucket.eggbucket_android

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
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
 * Use the [OutletShopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutletShopFragment : Fragment() {
    lateinit var adapter: OrdersAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var dataList:ArrayList<GetAllOrdersItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outlet_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gotoFilter = view.findViewById<LinearLayout>(R.id.filter_layout)
        gotoFilter.setOnClickListener {

        }
        dataList= arrayListOf()
        // Find the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview1)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set the adapter
        try {
            fetchDataAndBindRecyclerview()
        }
        catch (e : Exception){
            Log.e("ExceptionCheck", e.message.toString() )
        }



    }
    private fun getOutletId():String?{
        val sharedPref = activity?.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPref?.getString("OUTLET_ID",null)
    }
    fun fetchDataAndBindRecyclerview(){
        CoroutineScope(Dispatchers.IO).launch {
            val dataList = RetrofitInstance.api.getOrdersByOutletId(getOutletId().toString())
            val reverseDataList = ArrayList(dataList.reversed())

            withContext(Dispatchers.Main) {
                adapter = OrdersAdapter(requireContext(),reverseDataList)
                recyclerView.adapter = adapter
            }
        }
    }
}