package com.eggbucket.eggbucket_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.eggbucket.eggbucket_android.model.loginmodel.LoginRequest
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginScreenActivity : AppCompatActivity() {
    lateinit var submit:LinearLayout

    // Define a CoroutineScope tied to the Activity's lifecycle
    private val activityScope = CoroutineScope(Dispatchers.Main + Job())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)
        val outletePartner = findViewById<CheckBox>(R.id.outlet_pertner)
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
        outletePartner.setOnCheckedChangeListener { _, isChecked ->
            closeKey()
            if (isChecked) {
                deliveryPartner.isChecked = false
            }
        }

        deliveryPartner.setOnCheckedChangeListener { _, isChecked ->
            closeKey()
            if (isChecked) {
                outletePartner.isChecked = false
            }
        }

        // Set a click listener for the button to perform an action
        submit.setOnClickListener {
            if (customerCode.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Toast.makeText(this, "Please fill Customer code and Password first", Toast.LENGTH_SHORT).show()
            } else {
                if (outletePartner.isChecked) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else if (deliveryPartner.isChecked) {
                    startActivity(Intent(this, delivery_dashboard::class.java))
                } else {
                    Toast.makeText(this, "Please select a Anyone", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    /* fun show(v:View){
         closeKey()
     }*/
    private fun closeKey() {
        val view: View? = this.currentFocus
        if (view !== null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

   /* private fun authenticateUser(phone: String, password: String) {
        // Show a loading indicator if necessary

        activityScope.launch {
            try {
                val loginRequest = LoginRequest(phone = phone, pass = password)

                // Choose the appropriate endpoint based on your use case
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.outletPartnerLogin(loginRequest)
                    // Or for delivery partner login:
                    // RetrofitClient.apiService.deliveryPartnerLogin(loginRequest)
                }

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {
                        Toast.makeText(this@LoginScreenActivity, "Login successful: ${loginResponse.message}", Toast.LENGTH_LONG).show()
                        // Navigate to the next screen or perform other actions
                        startActivity(Intent(this@LoginScreenActivity,MainActivity::class.java))
                    } else {
                        Toast.makeText(this@LoginScreenActivity, "Login failed: ${loginResponse?.message ?: "Unknown error"}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@LoginScreenActivity, "Server Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginScreenActivity, "Network Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            } finally {
                // Hide the loading indicator if necessary
            }
        }
    }*/

}