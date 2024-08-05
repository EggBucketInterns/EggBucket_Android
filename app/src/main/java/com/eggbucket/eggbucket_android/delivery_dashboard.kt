package com.eggbucket.eggbucket_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class delivery_dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delivery_dashboard)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation2)
        val nevController = findNavController(R.id.fragment2)
        bottomNavigationView.setupWithNavController(nevController)

    }
}