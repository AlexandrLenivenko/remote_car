package com.example.step_motor.steppermotor.driver


import com.example.step_motor.steppermotor.Direction

abstract class StepperMotorDriver : AutoCloseable {
    open var direction: Direction = Direction.CLOCKWISE

    abstract fun open()

    abstract fun performStep(stepDuration: StepDuration)

    abstract fun stop()
}