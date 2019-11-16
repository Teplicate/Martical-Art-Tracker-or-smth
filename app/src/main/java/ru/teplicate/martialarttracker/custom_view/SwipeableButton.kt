package ru.teplicate.martialarttracker.custom_view

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import java.lang.Math.abs

class SwipeableButton : Button {

    private val customButton = "SwipeableButton"
    private var startPoint: Float = 0F
    private var prevX: Float = 0F
    private var beyondEdge: Boolean = false
    private var startAlpha: Int = 0
    private var leftDistance = 0F
    private var rightDistance = 0F
    private lateinit var onSwipeListener: OnSwipeListener

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        Log.i(customButton, "x -> $x")
        Log.i(customButton, "width -> $width")
        Log.i(customButton, "xWidth -> ${(x + width)}")
        Log.i(customButton, "y -> $y")
        Log.i(customButton, "yHeight -> ${(y + height)}")
        Log.i(customButton, "xmRaw -> ${event?.rawX}")
        Log.i(customButton, "xEv -> ${event?.x}")
        Log.i(
            customButton,
            "xPrec -> ${event?.xPrecision} xCalc -> ${event!!.xPrecision * event.x}"
        )

        return event.let {
            when (event.action) {
                //wtf is this?
                MotionEvent.ACTION_BUTTON_PRESS -> {
                    Log.i(customButton, "pressed")
                    true
                }

                //Pressing button
                MotionEvent.ACTION_DOWN -> {
                    presetTrackers(event.x)
                    startPoint = event.x
                    startAlpha = background.alpha
                    prevX = startPoint

                    Log.i(customButton, "down")
                    true
                }
                //releasing button
                MotionEvent.ACTION_UP -> {
                    if (beyondEdge)
                        onSwipeListener.onSwipe(view = this)

                    Log.i(customButton, "up")
                    resetBackgroundState()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    beyondEdge = event.x <= 0 || event.x >= this.width
                    changeBackground(event.x)
                    true
                }
                else -> false
            }
        }
    }


    private fun presetTrackers(mX: Float) {
        startPoint = mX
        startAlpha = background.alpha
        prevX = startPoint
        leftDistance = if (this.x == 0F) {
            startPoint + startPoint * 0.2F
        } else {
            abs(startPoint) + abs(startPoint) * 0.2F
        }
        rightDistance =
            abs(startPoint - (this.width)) + abs(startPoint - (this.width)) * 0.2F
        Log.i(customButton, "this is rightDist - > $rightDistance")
        Log.i(customButton, "this is leftDist - > $leftDistance")
    }

    private fun resetBackgroundState() {
        background.alpha = startAlpha
        beyondEdge = false
        background.invalidateSelf()
    }

    private fun changeBackground(mx: Float) {
        val rightDirection = mx > prevX
        val shiftProportion = if (!rightDirection) {
            Log.i(customButton, "Left Direction")

            if (beyondEdge) {
                Log.i(customButton, "beyond edge")
                0.2F
            } else calcCurrentShiftProportion(mx, leftDistance)

        } else {
            Log.i(customButton, "Right Direction")
            if (beyondEdge) {
                Log.i(customButton, "beyond edge")
                0.2F
            } else calcCurrentShiftProportion(mx, rightDistance)
        }

        Log.i(customButton, "Shift proportion is $shiftProportion")

        prevX = mx
        background.alpha = (startAlpha * shiftProportion).toInt()
        background.invalidateSelf()
        Log.i(customButton, "-------------------------------------------------")
    }

    private fun calcCurrentShiftProportion(mx: Float, distance: Float): Float {
        Log.i(customButton, "mx - $mx, x - $x")
        return 1F - abs(mx - startPoint) / distance
    }

//    override fun performClick(): Boolean {
//        return super.performClick()
//    }

    fun setupOnSwipeListener(l: OnSwipeListener): View {
        this.onSwipeListener = l

        return this
    }
}

interface OnSwipeListener {
    fun onSwipe(view: View)
}