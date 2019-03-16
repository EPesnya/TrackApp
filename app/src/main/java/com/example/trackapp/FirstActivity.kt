package com.example.trackapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class FirstActivity : AppCompatActivity() {

    inner class CountUpTimer(var millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        var millisUntilFinished: Long = 0

        private val oneNames = listOf("", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
        private val tenNames = listOf("десять", "одинадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
        private val decadesNames = listOf("", "", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
        private val hundredsNames = listOf("", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот", "тысяча")

        override fun onTick(millisUntilFinished: Long) {
            this.millisUntilFinished = millisUntilFinished
            val count: Int = ((millisInFuture - millisUntilFinished)/1000).toInt()
            if (count % 100 in 10..19)
                this@FirstActivity.numText.text = "${hundredsNames[count / 100]} ${tenNames[count % 10]}"
            else
                this@FirstActivity.numText.text = "${hundredsNames[count / 100]} ${decadesNames[count / 10 % 10]} ${oneNames[count % 10]}"
        }

        override fun onFinish() {
            this@FirstActivity.changeTimerState()
        }
    }

    var myTimer: CountUpTimer? = null
    var timerOn = false
    lateinit var button: Button
    lateinit var numText: TextView
    var stopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        numText = findViewById(R.id.textView)
        button = findViewById(R.id.button)

        savedInstanceState?.run {
            if (getBoolean("TIMER_STATE")) {
                myTimer = CountUpTimer(getLong("UNTIL_TIME"), 1000)
                myTimer?.millisInFuture = getLong("TIMER_TIME")
                changeTimerState()
                myTimer?.start()
            }
        }

        button.setOnClickListener {
            if (!timerOn) {
                myTimer = CountUpTimer(1000000, 1000)
                myTimer?.start()
            }
            else {
                myTimer?.cancel()
            }
            changeTimerState()
        }
    }

    fun changeTimerState() {
        if (timerOn) {
            numText.text = ""
            button.text = "Start"
            timerOn = false
        }
        else {
            button.text = "Stop"
            timerOn = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        myTimer?.let {
            outState.putLong("UNTIL_TIME", it.millisUntilFinished)
            outState.putLong("TIMER_TIME", it.millisInFuture)
            outState.putBoolean("TIMER_STATE", timerOn)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        stopped = true
        myTimer?.cancel()
        super.onStop()
    }

    override fun onStart() {
        if (stopped && timerOn) {
            myTimer?.let {
                val startTime = it.millisInFuture
                myTimer = CountUpTimer(it.millisUntilFinished, 1000)
                myTimer?.millisInFuture = startTime
                myTimer?.start()
            }
        }
        super.onStart()
    }
}
