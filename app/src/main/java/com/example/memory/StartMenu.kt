package com.example.memory

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.memory.MainActivity.StaticFunctions.borderDrawable
import kotlinx.android.synthetic.main.activity_start_menu.*

class StartMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_menu)
        val playButton: Button = findViewById(R.id.playButton)
        playButton.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
        playButton.background = borderDrawable(10, Color.parseColor("#05024c"))
        settingButton.background = borderDrawable(10, Color.parseColor("#001144"))
        val image: ImageView = findViewById(R.id.startImage)
        val clkRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        image.startAnimation(clkRotate)
    }
}