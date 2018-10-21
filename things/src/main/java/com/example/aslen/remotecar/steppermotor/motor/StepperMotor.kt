package com.example.aslen.remotecar.steppermotor.motor

import com.example.aslen.remotecar.steppermotor.Direction
import com.example.aslen.remotecar.steppermotor.driver.StepperMotorDriver
import com.example.aslen.remotecar.steppermotor.listener.RotationListener
import com.example.aslen.remotecar.steppermotor.listener.StepsListener
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class StepperMotor : AutoCloseable {
    protected open lateinit var executor: ExecutorService
    protected open lateinit var stepperMotorDriver: StepperMotorDriver

    init {
        executor = Executors.newSingleThreadExecutor()
    }

    override fun close() {
        abortAllRotations()
        stepperMotorDriver.close()
    }

    fun abortAllRotations() {
        executor.shutdownNow()

    }

    fun rotate(degrees: Double, direction: Direction, resolutionId: Int, rpm: Double) {
        rotate(degrees, direction, resolutionId, rpm, null)
    }

    fun rotate(degrees: Double, direction: Direction, resolutionId: Int, rpm: Double, rotationListener: RotationListener? = null) {
        if (degrees < 0) {
            throw IllegalArgumentException("degrees less than 0: {$degrees}")
        }
        if (rpm < 0) {
            throw IllegalArgumentException("rpm less than 0: {$rpm}")
        }

        val stepsToPerform = getStepsFromDegrees(degrees, resolutionId)
        val executionDuration = getExecutionDurationNanos(rpm, resolutionId, stepsToPerform)
        val motorRunner = getMotorRunner(stepsToPerform, direction, resolutionId, executionDuration)
        motorRunner.stepsListener = object : StepsListener {
            override fun onStarted() {
                rotationListener?.onStarted()
            }

            override fun onFinishedSuccessfully() {
                rotationListener?.onFinishedSuccessfully()
            }

            override fun onFinishedWithError(stepsToPerform: Int, performedSteps: Int, exception: Exception) {
                rotationListener?.onFinishedWithError(degrees, getDegreesFromSteps(performedSteps, resolutionId), exception)
            }

        }

        try {
            executor.submit(motorRunner)
        } catch (exception: Exception) {
            executor = Executors.newSingleThreadExecutor()
            executor.submit(motorRunner)
        }
    }

    protected abstract fun getMotorRunner(stepsToPerform: Int, direction: Direction, resolutionId: Int, executionDurationNanos: Long): MotorRunner

    protected abstract fun getStepsFromDegrees(degrees: Double, resolutionId: Int): Int

    protected abstract fun getDegreesFromSteps(steps: Int, resolutionId: Int): Double

    protected abstract fun getStepDurationMillisForRPM(rpm: Double, resolutionId: Int): Double

    protected abstract fun getStepsPerRevolution(resolutionId: Int): Int

    protected abstract fun getExecutionDurationNanos(rpm: Double, resolutionId: Int, steps: Int): Long

    companion object {
        val CIRCLE_DEGREES = 360
        val MINUTE_MILLIS = 60 * 1000
        val NANOS_IN_SECOND = 1000000
    }
}