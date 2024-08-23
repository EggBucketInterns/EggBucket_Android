package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.blogspot.atifsoftwares.animatoolib.Animatoo

class Order_Placed_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_order_placed_screen)
            val backBtn = findViewById<Button>(R.id.backBtn);
        Handler().postDelayed({
            // Start the MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            // Close the SplashActivity
            finish()
            Animatoo.animateFade(this)
        }, 1000)

        backBtn.setOnClickListener {
            val intent = Intent(this@Order_Placed_Screen, MainActivity::class.java)
            startActivity(intent);
            finish()
        }
        }
    }
