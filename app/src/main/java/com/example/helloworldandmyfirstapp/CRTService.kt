package com.example.helloworldandmyfirstapp

import android.accessibilityservice.AccessibilityService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.KeyEvent
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent

class CRTService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private var screenReceiver: BroadcastReceiver? = null
    private var isLocking = false

    companion object {
        @Volatile var instance: CRTService? = null
        @Volatile var isServiceRunning = false

        fun lockScreenWithCRT() {
            instance?.lockWithAnimation()
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        isServiceRunning = true
        registerScreenReceiver()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_POWER && event.action == KeyEvent.ACTION_DOWN) {
            if (CRTSettings.isPowerButtonInterceptEnabled && CRTSettings.isScreenOffEnabled) {
                if (!isLocking) {
                    isLocking = true
                    lockWithAnimation()
                }
                return true
            }
        }
        return super.onKeyEvent(event)
    }

    private fun registerScreenReceiver() {
        if (screenReceiver != null) return
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_USER_PRESENT)
        }
        screenReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_SCREEN_OFF -> {
                        if (CRTSettings.isScreenOffEnabled && !isLocking) {
                            handler.post { playOverlayAnimation(isScreenOn = false) }
                        }
                    }
                    Intent.ACTION_SCREEN_ON -> {
                        if (CRTSettings.isScreenOnEnabled) {
                            handler.post { playOverlayAnimation(isScreenOn = true) }
                        }
                    }
                    Intent.ACTION_USER_PRESENT -> isLocking = false
                }
            }
        }
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(screenReceiver, filter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(screenReceiver, filter)
        }
    }

    fun lockWithAnimation() {
        if (!CRTSettings.isScreenOffEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
            }
            return
        }
        playOverlayAnimation(isScreenOn = false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
            }
            handler.postDelayed({ isLocking = false }, 1000)
        }
    }

    fun playOverlayAnimation(isScreenOn: Boolean, onAnimationComplete: (() -> Unit)? = null) {
        try {
            val windowManager = getSystemService(WINDOW_SERVICE) as? WindowManager ?: return

            val crtEngineView = CRTCanvasEngine(this)

            @Suppress("DEPRECATION")
            var flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

            if (isScreenOn) {
                flags = flags or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            } else {
                flags = flags or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            }

            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                flags,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.CENTER
            }

            var isCleanedUp = false
            fun cleanupOverlay() {
                if (!isCleanedUp) {
                    isCleanedUp = true
                    try {
                        windowManager.removeViewImmediate(crtEngineView)
                    } catch (_: Exception) {}
                    onAnimationComplete?.invoke()
                }
            }

            handler.postDelayed({ cleanupOverlay() }, (CRTSettings.durationMs + 300).toLong())

            windowManager.addView(crtEngineView, params)

            crtEngineView.startAnimation(isScreenOn = isScreenOn) {
                cleanupOverlay()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            onAnimationComplete?.invoke()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        isServiceRunning = false
        screenReceiver?.let {
            try { unregisterReceiver(it) } catch (_: Exception) {}
        }
    }
}
