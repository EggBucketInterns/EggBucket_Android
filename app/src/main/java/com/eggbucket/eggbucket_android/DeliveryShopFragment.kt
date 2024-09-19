package com.eggbucket.eggbucket_android

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.OrderAdapter_2
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeliveryShopFragment : Fragment() {
    lateinit var adapter: OrderAdapter_2
    lateinit var recyclerView: RecyclerView
    lateinit var dataList:ArrayList<GetAllOrdersItem>
    lateinit var refreshBtn : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          dataList= arrayListOf()
        // Find the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview123)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set the adapter
        fetchDataAndBindRecyclerview()
        refreshBtn =view.findViewById(R.id.refreshBtn);
        val rotationAnimator = ObjectAnimator.ofFloat(refreshBtn, "rotation", 0f, 360f)
        rotationAnimator.duration = 1000 // 1/2 second for the full rotation
        rotationAnimator.start()
        refreshBtn.setOnClickListener{
            fragmentManager?.beginTransaction()?.detach(this)?.commit();
            fragmentManager?.beginTransaction()?.attach(this)?.commit()
        }


    }
    fun fetchDataAndBindRecyclerview(){
        CoroutineScope(Dispatchers.IO).launch {
            val dataList = RetrofitInstance.api.getOrdersByDeliveryId(getUserId().toString())
            val reversedList = ArrayList(dataList.reversed())

            withContext(Dispatchers.Main) {
/*<<<<<<< HEAD:app/src/main/java/com/eggbucket/eggbucket_android/DeliveryStoreFragment.kt

                adapter = OrderAdapter_2(requireContext(),parentFragmentManager,dataList)
=======*/
                adapter = OrderAdapter_2(requireContext(),parentFragmentManager,reversedList)

                recyclerView.adapter = adapter

            }
        }
    }

    private fun getUserId():String?{
        val sharedPref = activity?.getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
        return sharedPref?.getString("USER_ID",null)
    }
}