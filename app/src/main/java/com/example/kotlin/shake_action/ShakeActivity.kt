package com.example.kotlin.shake_action

import android.content.Context
import android.hardware.Sensor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin.R
import android.hardware.SensorManager
import android.view.View
import com.google.android.material.snackbar.Snackbar


class ShakeActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAccelerometer: Sensor
    private lateinit var shakeDetector: ShakeDetector
    private lateinit var mainView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        mainView = findViewById<View>(R.id.mainView)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        shakeDetector = ShakeDetector(object : OnShakeListener {
            override fun onShake() {
                Snackbar.make(mainView, "Android is being shaked", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(shakeDetector, sensorAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }


    public override fun onPause() {
        sensorManager.unregisterListener(shakeDetector)
        super.onPause()
    }
}
