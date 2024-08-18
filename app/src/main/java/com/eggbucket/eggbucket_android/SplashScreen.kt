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
    private val SPLASH_DELAY: Long = 3000
    var userId : String? = null;// 3 seconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
//        fun getUserId():String?{
//            val sharedPref = getSharedPreferences("EggBucketPrefs", Context.MODE_PRIVATE)
//            return sharedPref?.getString("USER_ID",null)
//        }
//        fun isSessionActive(userId: String?): Boolean {
//            return !userId.isNullOrEmpty()
//        }
//        userId  = getUserId().toString();
//        Log.d("checkResponse----->", "userID "+getUserId().toString())
//        if(!isSessionActive(userId)) {
//            Log.d("checkResponse----->", "equal to null "+getUserId().toString())
                startActivity(Intent(this, LoginScreenActivity::class.java))
                // Close the SplashActivity
                finish()
                Animatoo.animateShrink(this)
//        }
//        else{
//            Log.d("checkResponse----->","not equal to null "+ getUserId().toString())
//            startActivity(Intent(this, MainActivity::class.java))
//            // Close the SplashActivity
//            finish()
//
//        }
    }
}