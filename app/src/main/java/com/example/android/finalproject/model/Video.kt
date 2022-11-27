package com.example.android.finalproject.model

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import android.util.Log
import com.example.android.finalproject.NewWorkoutActivity
import com.example.android.finalproject.R


class video : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val youTubePlayerView = YouTubePlayerView(this)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                    Log.d("video link!", intent?.getStringExtra(NewWorkoutActivity.EXTRA_REPLY).toString())
                    var videoId : String? = intent?.getStringExtra(NewWorkoutActivity.EXTRA_REPLY)
                    if (videoId != null) {
                        videoId = videoId.split("=")[1]
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
            }
        })
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_video)
        var layout = findViewById<LinearLayout>(R.id.videoLayout)
        layout.addView(youTubePlayerView)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}