package com.eggbucket.eggbucket_android

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class delivery_dashboard : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delivery_dashboard)


        val amount = intent.getStringExtra("EXTRA_AMOUNT")
        val fragment=DeliveryHomeFragment()
        val bundle=Bundle()
        bundle.putString("amount",amount)
        fragment.arguments=bundle
        supportFragmentManager.beginTransaction().add(R.id.fragment2,fragment).commit()

        val pendigItems = intent.getStringExtra("pendingItem")
        val fragment2=DeliveryHomeFragment()
        val bundle2=Bundle()
        bundle2.putString("Items",pendigItems)
        fragment2.arguments=bundle2
        supportFragmentManager.beginTransaction().add(R.id.fragment2,fragment2).commit()

        bottomNavigationView = findViewById(R.id.bottomNavigation2)

        val rootView = findViewById<View>(android.R.id.content)

        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            if (rootView.isKeyboardVisible()) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation2)
        val nevController = findNavController(R.id.fragment2)
        bottomNavigationView.setupWithNavController(nevController)

    }
    fun View.isKeyboardVisible(): Boolean {
        val rect = Rect()
        this.getWindowVisibleDisplayFrame(rect)
        val screenHeight = this.rootView.height
        val keypadHeight = screenHeight - rect.bottom
        return keypadHeight > screenHeight * 0.15 // 15% threshold
    }
}