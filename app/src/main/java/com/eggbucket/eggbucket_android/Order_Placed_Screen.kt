package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Order_Placed_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_order_placed_screen)
            val backBtn = findViewById<Button>(R.id.backBtn);

        backBtn.setOnClickListener {
            val intent = Intent(this@Order_Placed_Screen, MainActivity::class.java)
            startActivity(intent);
        }
        }
    }
