package com.example.aslen.remotecar.steppermotor.driver


import com.example.aslen.remotecar.steppermotor.Direction

abstract class StepperMotorDriver : AutoCloseable {
    open var direction: Direction = Direction.CLOCKWISE

    abstract fun open()

    abstract fun performStep(stepDuration: StepDuration)
}