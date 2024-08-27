package com.eggbucket.eggbucket_android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class EggBucketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}