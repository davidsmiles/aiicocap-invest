package com.app.aiicapinvest


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileReader


class Welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_welcome)

        splash()
    }

    private fun splash(){
        val handler = Handler()
        handler.postDelayed({
            pop()
        }, 2000)
    }

    /**
     *  Decide if User has logged in before and take them to home page
     *  if(file.exists) returns true if User has launched the App before and if true,
     *  Then move Users straight to Login page without showing them the onboarding screens.
     */
    private fun pop(){
        val file = File("${cacheDir.path}/logged_in.txt")
        if(file.exists()) {
            val fw = FileReader(file)
            when (fw.readText()) {
                "true" -> {
                    Intent(this@Welcome, Home::class.java).apply {
                        startActivity(this)
                        finish()
                    }
                }
                else -> {
                    Intent(this@Welcome, Login::class.java).apply {
                        startActivity(this)
                        finish()
                    }
                }
            }
        }
        else {
            Intent(this@Welcome, Onboard::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

}
