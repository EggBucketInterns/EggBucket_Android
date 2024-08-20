package com.eggbucket.eggbucket_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
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

        fun getUserId(): String? {
            val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
            return sharedPref?.getString("USER_ID", null)
        }
        fun getUserType():String?{
            val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
            return sharedPref?.getString("USER_TYPE", null)
        }
        val userid = getUserId();
        val userType = getUserType();
        Log.d("checkResponse----->", userid.toString() + userType)
        if(userid != null){
            if (userType == "1"){
                Handler().postDelayed({
                    // Start the MainActivity
                    startActivity(Intent(this, delivery_dashboard::class.java))
                    // Close the SplashActivity
                    finish()
                    Animatoo.animateShrink(this)
                }, SPLASH_DELAY)
            }
            else if (userType == "0"){
                Handler().postDelayed({
                    // Start the MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    // Close the SplashActivity
                    finish()
                    Animatoo.animateShrink(this)
                }, SPLASH_DELAY)
            }
            else{
                Handler().postDelayed({
                    // Start the MainActivity
                    startActivity(Intent(this, LoginScreenActivity::class.java))
                    // Close the SplashActivity
                    finish()
                    Animatoo.animateShrink(this)
                }, SPLASH_DELAY)
            }
        }
        else{
            Handler().postDelayed({
                // Start the MainActivity
                startActivity(Intent(this, LoginScreenActivity::class.java))
                // Close the SplashActivity
                finish()
                Animatoo.animateShrink(this)
            }, SPLASH_DELAY)        }
    }
}