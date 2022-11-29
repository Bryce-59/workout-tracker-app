package com.example.android.finalproject.model.notification

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.android.finalproject.R
import com.example.android.finalproject.model.notification.alarm.AlarmService
import java.util.*

class RingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val dismiss: Button? = findViewById(R.id.activity_ring_dismiss)
        val snooze: Button? = findViewById(R.id.activity_ring_snooze)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)
        dismiss!!.setOnClickListener {
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
        snooze!!.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.MINUTE, 10)
            val alarm = Notification(
                Random().nextInt(Int.MAX_VALUE),
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                System.currentTimeMillis(),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
            )
            alarm.schedule(applicationContext)
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
        animateClock()
    }

    private fun animateClock() {
        val clock: ImageView? = findViewById(R.id.activity_ring_clock)
        val rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f)
        rotateAnimation.repeatCount = ValueAnimator.INFINITE
        rotateAnimation.duration = 800
        rotateAnimation.start()
    }
}