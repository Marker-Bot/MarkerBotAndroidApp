package com.example.marketbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
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
        val anim: Animation? = AnimationUtils.loadAnimation(this, R.anim.alpha)
        /**
         * Кнопка, которая вызывает метод eraseAll() в DrawingView
         */
        eraseButton.setOnClickListener {
            mCustomView.eraseAll()
            it.startAnimation(anim)
        }
        /**
         * Кнопка выхода
         */
        exitButton.setOnClickListener {
            exitProcess(0)
        }
        /**
         * Кнопка для отправки данных
         */
        sendButton.setOnClickListener {
            it.startAnimation(anim)
            mCustomView.sendToServer()
        }
    }

    /**
     * Метод для создания меню в верхнем правом углу прлиложения
     *
     * @param menu: Menu
     * @return: boolean создалось ли меню
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Метод, который обрабатывает нажатия пользователя в меню
     *
     * @param item: Элемент меню
     * @return: boolean нажатие на элемент меню
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //запускаем Activity с информацией о проекте
            R.id.information -> {
                startActivity(Intent(this,AboutUsActivity::class.java))
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}