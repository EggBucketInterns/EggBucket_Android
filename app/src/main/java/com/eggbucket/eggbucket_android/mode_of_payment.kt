package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.adapters.OrderViewModel

class mode_of_payment : AppCompatActivity() {
    private lateinit var orderViewModel: OrderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mode_of_payment)
       val discountAmount=findViewById<EditText>(R.id.discount_amount)

        val amountR=findViewById<EditText>(R.id.amount_rs)
        val deli_completed = findViewById<Button>(R.id.deli_completed)
       // orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)


        deli_completed.setOnClickListener {
            // Get the value from EditText
            val discount=discountAmount.text.toString().toInt()
            val amount = amountR.text.toString().toInt()
            var finalAmount=(amount - discount).toString()
           // orderViewModel.setAmount(finalAmount)

            // Create an Intent to start SecondActivity
            val intent = Intent(this, delivery_dashboard::class.java)
        intent.putExtra("amount",finalAmount)

            // Start SecondActivity
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}