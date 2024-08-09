package com.eggbucket.eggbucket_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.model.Orders

class DeliveryStoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sample data
        val orderList = listOf(
            Orders(
                orderId = "#3577",
                customerId = "Customer ID",
                customerImageResId=R.drawable.user_profile_image,
                numberOfTrays = 12,
                createdAt = "2023-08-01",
                deliveredAt = "2023-08-03",
                orderAmount = 567.88,
                orderStatus = "Completed"
            ),
            Orders(
                orderId = "#3578",
                customerId = "Customer ID",
                customerImageResId=R.drawable.user_profile_image,
                numberOfTrays = 10,
                createdAt = "2023-08-02",
                deliveredAt = "2023-08-04",
                orderAmount = 345.67,
                orderStatus = "Pending"
            ),
            Orders(
                orderId = "#3579",
                customerId = "Customer ID",
                customerImageResId=R.drawable.user_profile_image,
                numberOfTrays = 8,
                createdAt = "2023-08-03",
                deliveredAt = "2023-08-05",
                orderAmount = 765.45,
                orderStatus = "Completed"
            ),
            Orders(
                orderId = "#3580",
                customerId = "Customer ID",
                customerImageResId=R.drawable.user_profile_image,
                numberOfTrays = 15,
                createdAt = "2023-08-04",
                deliveredAt = "2023-08-06",
                orderAmount = 890.12,
                orderStatus = "Shipped"
            )
            // Add more dummy items if needed
        )

        // Find the RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview123)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set the adapter
        recyclerView.adapter = OrdersAdapter(requireContext(), orderList)

    }


}