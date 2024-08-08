package com.eggbucket.eggbucket_android

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.model.RecentOrdersData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var  createOrder:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)




        bottomNavigationView = findViewById(R.id.bottomNavigation)

        val rootView = findViewById<View>(android.R.id.content)

        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            if (rootView.isKeyboardVisible()) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }



        createOrder=findViewById<ImageView>(R.id.create_order)
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

    fun View.isKeyboardVisible(): Boolean {
        val rect = Rect()
        this.getWindowVisibleDisplayFrame(rect)
        val screenHeight = this.rootView.height
        val keypadHeight = screenHeight - rect.bottom
        return keypadHeight > screenHeight * 0.15 // 15% threshold
    }
}