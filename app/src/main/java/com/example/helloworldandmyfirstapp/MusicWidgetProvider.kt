package com.example.helloworldandmyfirstapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class MusicWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_WIDGET_PREV = "com.example.helloworldandmyfirstapp.WIDGET_PREV"
        const val ACTION_WIDGET_PLAY_PAUSE = "com.example.helloworldandmyfirstapp.WIDGET_PLAY_PAUSE"
        const val ACTION_WIDGET_NEXT = "com.example.helloworldandmyfirstapp.WIDGET_NEXT"

        var currentTitle: String = "Now Playing"
        var currentArtist: String = "Tap to open Zune Player"
        var isPlaying: Boolean = false

        fun updateAllWidgets(context: Context, title: String? = null, artist: String? = null, playing: Boolean? = null) {
            if (title != null) currentTitle = title
            if (artist != null) currentArtist = artist
            if (playing != null) isPlaying = playing

            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, MusicWidgetProvider::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName) ?: return

            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_music_player)

            views.setTextViewText(R.id.widget_title, currentTitle)
            views.setTextViewText(R.id.widget_artist, currentArtist)

            val playIcon = if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
            views.setImageViewResource(R.id.widget_play_pause, playIcon)

            val mainIntent = Intent(context, MainActivity::class.java)
            val mainPendingIntent = PendingIntent.getActivity(
                context, 0, mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_container, mainPendingIntent)

            val prevIntent = Intent(context, MusicWidgetProvider::class.java).apply { action = ACTION_WIDGET_PREV }
            val prevPending = PendingIntent.getBroadcast(
                context, 1, prevIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_prev, prevPending)

            val playPauseIntent = Intent(context, MusicWidgetProvider::class.java).apply { action = ACTION_WIDGET_PLAY_PAUSE }
            val playPausePending = PendingIntent.getBroadcast(
                context, 2, playPauseIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_play_pause, playPausePending)

            val nextIntent = Intent(context, MusicWidgetProvider::class.java).apply { action = ACTION_WIDGET_NEXT }
            val nextPending = PendingIntent.getBroadcast(
                context, 3, nextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_next, nextPending)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when (intent.action) {
            ACTION_WIDGET_PREV -> {
                val broadcastIntent = Intent("com.example.helloworldandmyfirstapp.PREV_SONG")
                context.sendBroadcast(broadcastIntent)
            }
            ACTION_WIDGET_PLAY_PAUSE -> {
                val broadcastIntent = Intent("com.example.helloworldandmyfirstapp.TOGGLE_PLAY")
                context.sendBroadcast(broadcastIntent)
            }
            ACTION_WIDGET_NEXT -> {
                val broadcastIntent = Intent("com.example.helloworldandmyfirstapp.NEXT_SONG")
                context.sendBroadcast(broadcastIntent)
            }
        }
    }
}
