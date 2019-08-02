package com.example.kotlin.shake_action

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log


class ShakeDetector(private val shakeListener: OnShakeListener) : SensorEventListener {

    companion object {
        private const val TIME_BETWEEN_SHAKES = 500
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7f
    }

    private var shakeTimeStamp: Long = 0

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.w(this::class.java.simpleName, "Accuracy has changed")
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val x = sensorEvent?.values?.get(0)
        val y = sensorEvent?.values?.get(1)
        val z = sensorEvent?.values?.get(2)

        if (x == null || y == null || z == null) {
            return
        }

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            if (shakeTimeStamp + TIME_BETWEEN_SHAKES > now) {
                return
            }

            shakeTimeStamp = now
            shakeListener.onShake()
        }
    }
}