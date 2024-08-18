package com.eggbucket.eggbucket_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eggbucket.eggbucket_android.model.login.LoginRequest
import com.eggbucket.eggbucket_android.model.login.LoginResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.eggbucket.eggbucket_android.network.RetrofitInstance.apiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginScreenActivity : AppCompatActivity() {
    lateinit var submit:LinearLayout

    // Define a CoroutineScope tied to the Activity's lifecycle
   // private val activityScope = CoroutineScope(Dispatchers.Main + Job())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        val outletPartner = findViewById<CheckBox>(R.id.outlet_pertner)
        val deliveryPartner = findViewById<CheckBox>(R.id.delivery_partner)
        val customerCode = findViewById<EditText>(R.id.customer_code)
        val password = findViewById<EditText>(R.id.password)
        submit=findViewById(R.id.submit)

        // Set click listener
       /* submit.setOnClickListener {
            val phone = customerCode.text.toString().trim()
            val password = password.text.toString().trim()

            if (phone.isNotEmpty() && password.isNotEmpty()) {
                authenticateUser(phone, password)
            } else {
                Toast.makeText(this, "Please enter both phone and password.", Toast.LENGTH_SHORT).show()
            }
        }*/

        // Set listeners to ensure only one checkbox is checked at a time
        outletPartner.setOnCheckedChangeListener { _, isChecked ->
            closeKey()
            if (isChecked) {
                deliveryPartner.isChecked = false
            }
        }

        deliveryPartner.setOnCheckedChangeListener { _, isChecked ->
            closeKey()
            if (isChecked) {
                outletPartner.isChecked = false
            }
        }

        // Set a click listener for the button to perform login
        submit.setOnClickListener {
            if (customerCode.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Toast.makeText(this, "Please fill Customer code and Password first", Toast.LENGTH_SHORT).show()
            } else {
                val loginRequest = LoginRequest(
                    phone = customerCode.text.toString(),
                    pass = password.text.toString()
                )

                if (outletPartner.isChecked) {
                    //startActivity(Intent(this,MainActivity::class.java))
                    loginAsOutletPartner(loginRequest)
                } else if (deliveryPartner.isChecked) {
                    //startActivity(Intent(this,delivery_dashboard::class.java))
                    loginAsDeliveryPartner(loginRequest)
                } else {
                    Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun loginAsOutletPartner(loginRequest: LoginRequest) {
        RetrofitInstance.api.outletPartnerLogin(loginRequest)
            .enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val userId = response.body()?.user?._id
                        saveUserId(userId)
                        // Navigate to OutletPartner dashboard
                        startActivity(Intent(this@LoginScreenActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@LoginScreenActivity,
                            "Please check phone no or password for Outlet Partner",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    Toast.makeText(
                        this@LoginScreenActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

    private fun loginAsDeliveryPartner(loginRequest: LoginRequest) {
        RetrofitInstance.api.deliveryPartnerLogin(loginRequest)
            .enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val userId = response.body()?.user?._id                        // Store the user ID in shared preferences
                        saveUserId(userId)
                        // Navigate to DeliveryPartner dashboard
                        startActivity(
                            Intent(
                                this@LoginScreenActivity,
                                delivery_dashboard::class.java
                            )
                        )
                    } else {
                        Toast.makeText(
                            this@LoginScreenActivity,
                            "Please check phone no or password for Delivery Partner",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    Toast.makeText(
                        this@LoginScreenActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun saveUserId(userId: String?) {
        var idOutlet : String = "";
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getOutletByOutletPartnerID(userId.toString())
            withContext(Dispatchers.Main) {
                val outlets = response.data
                Log.d("checkResponse", "Fetched outlets: $outlets")
                for(outlet in outlets){
                    Log.d("checkResponse", outlet.deliveryPartner.toString())
                    idOutlet = outlet._id.toString();
                }
            }
        }
        if (userId != null) {
            val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("USER_ID", userId)
                Log.d("qwerty", idOutlet)
                apply()
            }
            Toast.makeText(this, "Saved User ID", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to save User ID", Toast.LENGTH_SHORT).show()
        }
    }

     fun show(v:View){
         closeKey()
     }
    private fun closeKey() {
        val view: View? = this.currentFocus
        if (view !== null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}