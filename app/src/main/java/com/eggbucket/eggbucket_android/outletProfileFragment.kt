package com.eggbucket.eggbucket_android

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eggbucket.eggbucket_android.model.data.OutletPartnerResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [outletProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class outletProfileFragment : Fragment() {

    private lateinit var firstNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var aadharNumberTextView: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_outlet_profile, container, false)

        firstNameTextView = view.findViewById(R.id.firstNameTextView)
        lastNameTextView = view.findViewById(R.id.lastNameTextView)
        phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView)
        aadharNumberTextView = view.findViewById(R.id.addressTextView)
        profileImageView = view.findViewById(R.id.profileImageView)
        nameTextView = view.findViewById(R.id.nameText)

        val userID = getUserId()
        if (userID != null){
            fetchOutletPartnerData(userID)
        }else{
//            fetchOutletPartnerData("66b8b8fa8678ebf8692ab1c1")
            Toast.makeText(context, "User ID not found", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun getUserId():String?{
        val sharedPref = activity?.getSharedPreferences("EggBucketPrefs",Context.MODE_PRIVATE)
        return sharedPref?.getString("USER_ID",null)
    }

    private fun fetchOutletPartnerData(id: String) {
        RetrofitInstance.api.getOutletPartner(id).enqueue(object : Callback<OutletPartnerResponse> {
            override fun onResponse(call: Call<OutletPartnerResponse>, response: Response<OutletPartnerResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val partner = it.result
                        firstNameTextView.text = partner.firstName
                        lastNameTextView.text = partner.lastName
                        phoneNumberTextView.text = partner.phoneNumber
                        aadharNumberTextView.text = partner.aadharNumber
                        nameTextView.text = partner.firstName
                        Picasso.get().load(partner.img).into(profileImageView)
                        Log.d("OutletProfileFragment", "Data fetched successfully: $partner")
                    }
                } else {
                    Log.e("OutletProfileFragment", "Failed to fetch data: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OutletPartnerResponse>, t: Throwable) {
                Log.e("OutletProfileFragment", "Error: ${t.message}")
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}