package com.example.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kotlin.compass_view.CompassActivity
import com.example.kotlin.shake_action.ShakeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun shakeAction(view: View) {
        val intent = Intent(applicationContext, ShakeActivity::class.java)
        startActivity(intent)
    }

    fun showCompass(view: View) : Float {
        val intent = Intent(applicationContext, CompassActivity::class.java)
        startActivity(intent)
        return 0f
    }
}
