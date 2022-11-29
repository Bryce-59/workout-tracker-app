package com.example.android.finalproject.model.notification

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.example.android.finalproject.R
import java.util.*

class RingActivity : AppCompatActivity() {
    @BindView(R.id.activity_ring_dismiss)
    var dismiss: Button? = null

    @BindView(R.id.activity_ring_snooze)
    var snooze: Button? = null

    @BindView(R.id.activity_ring_clock)
    var clock: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)
        ButterKnife.bind(this)
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
        val rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f)
        rotateAnimation.repeatCount = ValueAnimator.INFINITE
        rotateAnimation.duration = 800
        rotateAnimation.start()
    }
}