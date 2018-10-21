package com.example.step_motor.steppermotor.awaiter

interface Awaiter {
    fun await(millis: Long, nanos: Int)
}