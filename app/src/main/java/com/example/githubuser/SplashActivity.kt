package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splash = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    val toMainActivity = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(toMainActivity)
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        splash.start()
    }
}