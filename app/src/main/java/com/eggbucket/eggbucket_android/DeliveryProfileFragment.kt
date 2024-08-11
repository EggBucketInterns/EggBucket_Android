package com.eggbucket.eggbucket_android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.eggbucket.eggbucket_android.model.data.DeliveryPartner
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [DeliveryProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeliveryProfileFragment : Fragment() {

    private lateinit var firstNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var driverLicenceNumberTextView: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_delivery_profile, container, false)

        firstNameTextView = view.findViewById(R.id.deliveryFirstNameTextView)
        lastNameTextView = view.findViewById(R.id.deliveryLastNameTextView)
        phoneNumberTextView = view.findViewById(R.id.deliveryPhoneNumberTextView)
        profileImageView = view.findViewById(R.id.deliveryProfileImageView)
        nameTextView = view.findViewById(R.id.deliveryNameText)
        driverLicenceNumberTextView = view.findViewById(R.id.deliveryVehicleNumberTextView)

        fetchDeliveryPartnerData("66b8b94f8678ebf8692ab1c7")

        return view
    }

    private fun fetchDeliveryPartnerData(id: String) {
        RetrofitInstance.api.getDeliveryPartner(id)
            .enqueue(object : Callback<DeliveryPartner> {
                override fun onResponse(
                    call: Call<DeliveryPartner>,
                    response: Response<DeliveryPartner>
                ) {
                    if (response.isSuccessful) {
                        val partner = response.body()
                        if (partner != null) {
                            firstNameTextView.text = partner.firstName
                            lastNameTextView.text = partner.lastName
                            phoneNumberTextView.text = partner.phoneNumber
                            driverLicenceNumberTextView.text = partner.driverLicenceNumber
                            nameTextView.text = partner.firstName
                            Picasso.get().load(partner.img).into(profileImageView)
                            Log.d("DeliveryProfileFragment", "Data fetched successfully: $partner")
                        } else {
                            Log.e("DeliveryProfileFragment", "Response body is null")
                            Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("DeliveryProfileFragment", "Failed to fetch data: ${response.errorBody()?.string()}")
                        Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DeliveryPartner>, t: Throwable) {
                    Log.e("DeliveryProfileFragment", "Error: ${t.message}")
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}