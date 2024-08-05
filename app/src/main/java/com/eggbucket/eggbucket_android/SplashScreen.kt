package com.eggbucket.eggbucket_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.blogspot.atifsoftwares.animatoolib.Animatoo

class SplashScreen : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000 // 3 seconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            // Start the MainActivity
            startActivity(Intent(this, LoginScreenActivity::class.java))
            // Close the SplashActivity
            finish()
            Animatoo.animateShrink(this)
        }, SPLASH_DELAY)
    }
}