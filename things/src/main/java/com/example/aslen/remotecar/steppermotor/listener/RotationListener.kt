package com.example.aslen.remotecar.steppermotor.listener

interface RotationListener {
    fun onStarted() {
    }

    fun onFinishedSuccessfully() {
    }

    fun onFinishedWithError(degreesToRotate: Double, rotatedDegrees: Double, exception: Exception) {
    }
}