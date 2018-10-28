package com.example.step_motor.steppermotor.driver


import com.example.step_motor.steppermotor.Direction
import com.google.android.things.pio.PeripheralManager

abstract class StepperMotorDriver : AutoCloseable {
    open var direction: Direction = Direction.CLOCKWISE

    abstract fun open(peripheralManager: PeripheralManager)
    abstract fun performStep(stepDuration: StepDuration)

    abstract fun stop()
}