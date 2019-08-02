package com.example.kotlin.compass_view

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log


class CompassDetector(private val onCompassListener: OnCompassListener) : SensorEventListener {

    private var accelerometerReadings: FloatArray? = null
    private var geomagneticReadings: FloatArray? = null
    private var rotationInRadians: Double = 0.toDouble()

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.w(this::class.java.simpleName, "Accuracy has changed")
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {

        if (sensorEvent?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            accelerometerReadings = FloatArray(3)
            System.arraycopy(sensorEvent.values, 0, accelerometerReadings!!, 0, 3)

        } else if (sensorEvent?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {

            geomagneticReadings = FloatArray(3)
            System.arraycopy(sensorEvent.values, 0, geomagneticReadings!!, 0, 3)

        }

        if (accelerometerReadings != null && geomagneticReadings != null) {

            val rotationMatrix = FloatArray(9)

            val success =
                SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReadings, geomagneticReadings)

            if (success) {
                val orientationMatrix = FloatArray(3)

                SensorManager.getOrientation(rotationMatrix, orientationMatrix)
                val rotationInRadians = orientationMatrix[0]

                this.rotationInRadians = rotationInRadians.toDouble()
                onCompassListener.sensorCompassChanged(this.rotationInRadians.toFloat())
            }
        }
    }
}