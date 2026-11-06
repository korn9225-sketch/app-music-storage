@file:OptIn(UnstableApi::class)

package com.example.helloworldandmyfirstapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null
    private var widgetReceiver: BroadcastReceiver? = null

    companion object {
        @Volatile var activePlayer: Player? = null
        const val GITHUB_TOKEN = ""
    }

    override fun onCreate() {
        super.onCreate()

        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
            .setUserAgent("ZuneMusicPlayer/1.0")
            .setDefaultRequestProperties(
                mapOf("Authorization" to "Bearer $GITHUB_TOKEN")
            )

        val mediaSourceFactory = DefaultMediaSourceFactory(this)
            .setDataSourceFactory(httpDataSourceFactory)

        val player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                true
            )
            .build()
        activePlayer = player

        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateWidget()
            }
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                updateWidget()
            }
        })

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = android.app.PendingIntent.getActivity(
            this,
            0,
            intent,
            android.app.PendingIntent.FLAG_IMMUTABLE or android.app.PendingIntent.FLAG_UPDATE_CURRENT
        )

        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(pendingIntent)
            .build()
        registerWidgetReceiver()
    }

    private fun registerWidgetReceiver() {
        if (widgetReceiver != null) return
        val filter = IntentFilter().apply {
            addAction(MusicWidgetProvider.ACTION_WIDGET_PREV)
            addAction(MusicWidgetProvider.ACTION_WIDGET_PLAY_PAUSE)
            addAction(MusicWidgetProvider.ACTION_WIDGET_NEXT)
        }
        widgetReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val player = mediaSession?.player ?: activePlayer ?: return
                when (intent?.action) {
                    MusicWidgetProvider.ACTION_WIDGET_PREV -> {
                        player.seekToPreviousMediaItem()
                    }
                    MusicWidgetProvider.ACTION_WIDGET_PLAY_PAUSE -> {
                        if (player.isPlaying) player.pause() else player.play()
                    }
                    MusicWidgetProvider.ACTION_WIDGET_NEXT -> {
                        player.seekToNextMediaItem()
                    }
                }
                updateWidget()
            }
        }
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(widgetReceiver, filter, RECEIVER_EXPORTED)
        } else {
            ContextCompat.registerReceiver(this, widgetReceiver, filter, ContextCompat.RECEIVER_EXPORTED)
        }
    }

    private fun updateWidget() {
        val player = mediaSession?.player ?: return
        val currentItem = player.currentMediaItem
        val title = currentItem?.mediaMetadata?.title?.toString() ?: "Now Playing"
        val artist = currentItem?.mediaMetadata?.artist?.toString() ?: "Zune Player"
        MusicWidgetProvider.updateAllWidgets(this, title, artist, player.isPlaying)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player
        if (player != null) {
            if (!player.playWhenReady || player.mediaItemCount == 0) {
                stopSelf()
            }
        }
    }

    override fun onDestroy() {
        activePlayer = null
        widgetReceiver?.let {
            try { unregisterReceiver(it) } catch (_: Exception) {}
        }
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}
