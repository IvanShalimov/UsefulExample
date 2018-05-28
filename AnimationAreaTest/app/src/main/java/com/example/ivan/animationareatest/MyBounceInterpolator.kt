package com.example.ivan.animationareatest

import android.view.animation.Interpolator

class MyBounceInterpolator: Interpolator {
    private var mAmplitude = 1.0
    private var mFrequency = 10.0

    constructor(amplitude: Double, frequency: Double) {
        mAmplitude = amplitude
        mFrequency = frequency
    }

    override fun getInterpolation(input: Float): Float {
        return (-1 * Math.pow(Math.E, -input/ mAmplitude) *
                Math.cos(mFrequency * input) + 1).toFloat()
    }
}