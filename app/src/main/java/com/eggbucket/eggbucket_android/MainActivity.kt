package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var  createOrder:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
         createOrder=findViewById<FloatingActionButton>(R.id.create_order)
        createOrder.setOnClickListener {
            gotoCreateOrder()
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val nevController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(nevController)


    }
    fun gotoCreateOrder(){
            startActivity(Intent(this,Create_Order_Screen::class.java))
    }
}