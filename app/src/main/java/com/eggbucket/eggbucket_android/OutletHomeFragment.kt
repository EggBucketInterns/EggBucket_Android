package com.eggbucket.eggbucket_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.ResentOrdersAdapter
import com.eggbucket.eggbucket_android.databinding.FragmentOutletHomeBinding
import com.eggbucket.eggbucket_android.model.RecentOrdersData

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
    lateinit var binding:FragmentOutletHomeBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOutletHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sample data
        val orderList = listOf(
            RecentOrdersData(R.drawable.ic_order,"12 Tray Eggs", "Order location", "COMPLETED"),
            RecentOrdersData(R.drawable.ic_order,"Milk Pack", "Another location", "PENDING"),
            RecentOrdersData(R.drawable.ic_order,"Bread Loaf", "Some location", "COMPLETED"),
            RecentOrdersData(R.drawable.ic_order,"Bread Loaf", "Some location", "PENDING"),
            RecentOrdersData(R.drawable.ic_order,"Bread Loaf", "Some location", "COMPLETED"),
            RecentOrdersData(R.drawable.ic_order,"Bread Loaf", "Some location", "PENDING"),
            RecentOrdersData(R.drawable.ic_order,"Bread Loaf", "Some location", "COMPLETED"),
            RecentOrdersData(R.drawable.ic_order,"Bread Loaf", "Some location", "COMPLETED")
            // Add more items as needed
        )

        // Find the RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Set the adapter
        recyclerView.adapter = ResentOrdersAdapter(requireContext(), orderList)
    }
}