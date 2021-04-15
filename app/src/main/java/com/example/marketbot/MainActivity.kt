package com.example.marketbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eraseButton:Button = findViewById(R.id.eraseButton)
        val exitButton:Button = findViewById(R.id.exitButton)
        val sendButton:Button = findViewById(R.id.sendButton)
        val mCustomView:DrawingView = findViewById(R.id.simpleDrawingView1)
        val anim: Animation? = AnimationUtils.loadAnimation(this, R.anim.alpha);
        eraseButton.setOnClickListener {
            mCustomView.eraseAll()
            it.startAnimation(anim)
        }

        exitButton.setOnClickListener {
            exitProcess(0)
        }

        sendButton.setOnClickListener {
            it.startAnimation(anim)
            Handler().postDelayed({
                Toast.makeText(this,"Робот не найден",Toast.LENGTH_SHORT).show()
            }, 2000)
        }
    }
}