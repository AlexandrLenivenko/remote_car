package com.example.aslen.remotecar.steppermotor.driver

data class StepDuration(val millis: Long = 1, val nanos: Int = 0) {
    init {
        if (millis < 0) {
            throw IllegalArgumentException("millis less than 0: {$millis}")
        }
        if (nanos < 0) {
            throw IllegalArgumentException("nanos less than 0: {$nanos}")
        }
    }
}