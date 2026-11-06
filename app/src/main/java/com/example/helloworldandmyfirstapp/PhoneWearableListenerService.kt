package com.example.helloworldandmyfirstapp

import android.content.ComponentName
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.google.common.util.concurrent.ListenableFuture

class PhoneWearableListenerService : WearableListenerService() {

    private var controllerFuture: ListenableFuture<MediaController>? = null

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        if (messageEvent.path == PhoneWearSyncManager.PATH_REMOTE_CONTROL) {
            val command = String(messageEvent.data, Charsets.UTF_8)
            Log.d("PhoneWearListener", "Received command from Watch: $command")
            handleRemoteCommand(command)
        }
    }

    private fun handleRemoteCommand(command: String) {
        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()

        controllerFuture?.addListener({
            val controller = controllerFuture?.get() ?: return@addListener
            when {
                command == "TOGGLE_PLAY" -> {
                    if (controller.isPlaying) {
                        controller.pause()
                    } else {
                        controller.play()
                    }
                }
                command == "PREV" -> {
                    controller.seekToPreviousMediaItem()
                }
                command == "NEXT" -> {
                    controller.seekToNextMediaItem()
                }
                command.startsWith("SEEK:") -> {
                    val posStr = command.removePrefix("SEEK:")
                    val targetPos = posStr.toLongOrNull()
                    if (targetPos != null) {
                        controller.seekTo(targetPos)
                    }
                }
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        controllerFuture?.let { MediaController.releaseFuture(it) }
    }
}
