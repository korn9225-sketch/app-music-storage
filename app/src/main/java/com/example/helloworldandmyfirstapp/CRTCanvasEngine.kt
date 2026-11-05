package com.example.helloworldandmyfirstapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout

class CRTCanvasEngine(context: Context) : FrameLayout(context) {

    private val crtBeam: View

    init {
        setBackgroundColor(Color.TRANSPARENT)

        crtBeam = View(context).apply {
            setBackgroundColor(CRTSettings.selectedStyle.beamColor.hashCode())
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                Gravity.CENTER
            )
        }
        addView(crtBeam)
    }

    fun startAnimation(isScreenOn: Boolean, onEnd: () -> Unit) {
        crtBeam.setBackgroundColor(CRTSettings.selectedStyle.beamColor.hashCode())
        val totalTime = CRTSettings.durationMs.toLong()

        if (!isScreenOn) {
            crtBeam.scaleX = 1.0f
            crtBeam.scaleY = 1.0f
            crtBeam.alpha = 1.0f

            val scaleY = ObjectAnimator.ofFloat(crtBeam, View.SCALE_Y, 1.0f, 0.004f).apply {
                duration = (totalTime * 0.50).toLong()
                interpolator = DecelerateInterpolator(1.8f)
            }

            val scaleX = ObjectAnimator.ofFloat(crtBeam, View.SCALE_X, 1.0f, 0.004f).apply {
                duration = (totalTime * 0.35).toLong()
                interpolator = AccelerateInterpolator(1.5f)
            }

            val fadeOut = ObjectAnimator.ofFloat(crtBeam, View.ALPHA, 1.0f, 0.0f).apply {
                duration = (totalTime * 0.15).toLong()
            }

            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(scaleY, scaleX, fadeOut)
            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onEnd()
                }
            })
            animatorSet.start()
        } else {
            crtBeam.scaleX = 0.004f
            crtBeam.scaleY = 0.004f
            crtBeam.alpha = 1.0f

            val scaleX = ObjectAnimator.ofFloat(crtBeam, View.SCALE_X, 0.004f, 1.0f).apply {
                duration = (totalTime * 0.40).toLong()
                interpolator = DecelerateInterpolator(1.5f)
            }

            val scaleY = ObjectAnimator.ofFloat(crtBeam, View.SCALE_Y, 0.004f, 1.0f).apply {
                duration = (totalTime * 0.60).toLong()
                interpolator = DecelerateInterpolator(1.5f)
            }

            val fadeBeam = ObjectAnimator.ofFloat(crtBeam, View.ALPHA, 1.0f, 0.0f).apply {
                duration = (totalTime * 0.60).toLong()
            }

            val animatorSet = AnimatorSet()
            animatorSet.play(scaleX)
            animatorSet.play(scaleY).after(scaleX)
            animatorSet.play(fadeBeam).with(scaleY)
            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onEnd()
                }
            })
            animatorSet.start()
        }
    }
}
