package com.example.trackapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class MainActivity : AppCompatActivity() {

    lateinit var splashScreen: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            splashScreen = object : CountDownTimer(2000, 1000) {

                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    changeActivity()
                }
            }.start()
    }

    private fun changeActivity() {
        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        splashScreen.cancel()
        super.onStop()
    }

    override fun onStart() {
        splashScreen.start()
        super.onStart()
    }
}
