package com.example.trackapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class MainActivity : AppCompatActivity() {

    inner class SplashScreen(millisInFuture: Long, countDownInterval: Long = 1000): CountDownTimer(millisInFuture, countDownInterval) {

        var millisUntilFinished: Long = 0

        override fun onTick(millisUntilFinished: Long) {
            this.millisUntilFinished = millisUntilFinished
        }

        override fun onFinish() {
            changeActivity()
        }
    }

    lateinit var mySplashScreen: SplashScreen
    var stopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            mySplashScreen = SplashScreen(2000)
            mySplashScreen.start()
        }
    }

    private fun changeActivity() {
        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        mySplashScreen.cancel()
        stopped = true
        super.onStop()
    }

    override fun onStart() {
        if (stopped) {
            mySplashScreen = SplashScreen(mySplashScreen.millisUntilFinished)
            mySplashScreen.start()
        }
        super.onStart()
    }
}
