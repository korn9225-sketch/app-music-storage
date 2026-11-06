package com.example.helloworldandmyfirstapp

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

object PhoneWearSyncManager {
    private const val TAG = "PhoneWearSyncManager"
    const val PATH_NOW_PLAYING = "/now_playing"
    const val PATH_REMOTE_CONTROL = "/remote_control"

    fun updateNowPlaying(
        context: Context,
        title: String,
        artist: String,
        artworkUrl: String,
        isPlaying: Boolean,
        currentPosition: Long,
        totalDuration: Long
    ) {
        try {
            val request = PutDataMapRequest.create(PATH_NOW_PLAYING).apply {
                dataMap.putString("title", title)
                dataMap.putString("artist", artist)
                dataMap.putString("artworkUrl", artworkUrl)
                dataMap.putBoolean("isPlaying", isPlaying)
                dataMap.putLong("currentPosition", currentPosition)
                dataMap.putLong("totalDuration", totalDuration)
                dataMap.putLong("timestamp", System.currentTimeMillis())
            }

            val putDataReq = request.asPutDataRequest().setUrgent()
            Wearable.getDataClient(context).putDataItem(putDataReq)
                .addOnSuccessListener { Log.d(TAG, "Successfully synced now playing state to Wear OS") }
                .addOnFailureListener { e -> Log.e(TAG, "Failed to sync state to Wear OS", e) }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating Wear OS state", e)
        }
    }
}
