package com.example.step_motor.steppermotor.listener

interface StepsListener {
    fun onStarted() {
    }

    fun onFinishedSuccessfully() {
    }

    fun onFinishedWithError(stepsToPerform: Int, performedSteps: Int, exception: Exception) {
    }
}