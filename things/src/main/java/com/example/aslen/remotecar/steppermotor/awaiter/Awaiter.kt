package com.example.aslen.remotecar.steppermotor.awaiter

interface Awaiter {
    fun await(millis: Long, nanos: Int)
}