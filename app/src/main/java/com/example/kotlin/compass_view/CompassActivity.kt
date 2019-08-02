package com.example.kotlin.compass_view

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin.R

class CompassActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorMagnetometer: Sensor
    private lateinit var sensorAccelerometer: Sensor
    private lateinit var compassDetector: CompassDetector
    private lateinit var compassView: CompassView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        compassView = findViewById(R.id.compassView)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        compassDetector = CompassDetector(object : OnCompassListener {
            override fun sensorCompassChanged(radians: Float) {
                compassView.update(radians)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(compassDetector, sensorMagnetometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(compassDetector, sensorAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }


    public override fun onPause() {
        sensorManager.unregisterListener(compassDetector)
        super.onPause()
    }
}
