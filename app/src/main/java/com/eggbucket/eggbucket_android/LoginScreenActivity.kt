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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)
        val outletePartner=findViewById<CheckBox>(R.id.outlet_pertner)
        val deliveryPartner=findViewById<CheckBox>(R.id.delivery_partner)
        val gotoNext=findViewById<ImageView>(R.id.goto_next)
        val customerCode=findViewById<EditText>(R.id.customer_code)
        val password=findViewById<EditText>(R.id.password)

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
        gotoNext.setOnClickListener {
            if(customerCode.text.toString().isEmpty() || password.text.toString().isEmpty()){
                Toast.makeText(this, "Please fill Customer code and Password first", Toast.LENGTH_SHORT).show()
            }else {
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
    private fun closeKey(){
        val view:View?=this.currentFocus
        if (view!==null){
      val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}