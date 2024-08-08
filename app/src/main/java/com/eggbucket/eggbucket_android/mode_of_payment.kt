package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eggbucket.eggbucket_android.R

class mode_of_payment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mode_of_payment)

        val amountR=findViewById<EditText>(R.id.amount_rs)
        val deli_completed = findViewById<Button>(R.id.deli_completed)

        deli_completed.setOnClickListener {
            // Get the value from EditText
            val amount = amountR.text.toString()

            // Create an Intent to start SecondActivity
            val intent = Intent(this, delivery_dashboard::class.java)

            // Put the EditText value into the Intent
            intent.putExtra("EXTRA_AMOUNT", amount)

            // Start SecondActivity
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}