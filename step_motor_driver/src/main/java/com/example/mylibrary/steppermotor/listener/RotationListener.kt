package com.example.step_motor.steppermotor.listener

interface RotationListener {
    fun onStarted() {
    }

    fun onFinishedSuccessfully() {
    }

    fun onFinishedWithError(degreesToRotate: Double, rotatedDegrees: Double, exception: Exception) {
    }
}