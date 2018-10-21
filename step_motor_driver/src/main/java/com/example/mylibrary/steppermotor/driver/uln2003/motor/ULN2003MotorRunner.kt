package com.example.aslen.remotecar.steppermotor.driver.uln2003.motor

import com.example.aslen.remotecar.steppermotor.driver.uln2003.driver.ULN2003
import com.example.aslen.remotecar.steppermotor.driver.uln2003.driver.ULN2003Resolution
import com.example.step_motor.steppermotor.Direction
import com.example.step_motor.steppermotor.motor.MotorRunner

class ULN2003MotorRunner(val uln2003: ULN2003,
                         steps: Int,
                         direction: Direction,
                         val resolution: ULN2003Resolution,
                         executionDurationNanos: Long) : MotorRunner(uln2003, steps, direction, executionDurationNanos) {

    override fun applyResolution() {
        uln2003.resolution = resolution
    }
}