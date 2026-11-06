package com.example.helloworldandmyfirstapp.wear

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable

class WearActivity : ComponentActivity(), DataClient.OnDataChangedListener {

    private var songTitle by mutableStateOf("No Track Playing")
    private var artistName by mutableStateOf("Zune Remote")
    private var artworkUrl by mutableStateOf("")
    private var isPlaying by mutableStateOf(false)
    private var currentPosition by mutableLongStateOf(0L)
    private var totalDuration by mutableLongStateOf(0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearOSRemoteControlScreen(
                title = songTitle,
                artist = artistName,
                artworkUrl = artworkUrl,
                isPlaying = isPlaying,
                currentPosition = currentPosition,
                totalDuration = totalDuration,
                onSendCommand = { command -> sendCommandToPhone(command) }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        Wearable.getDataClient(this).addListener(this)
    }

    override fun onPause() {
        super.onPause()
        Wearable.getDataClient(this).removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/now_playing") {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                songTitle = dataMap.getString("title", "No Track Playing")
                artistName = dataMap.getString("artist", "Zune Remote")
                artworkUrl = dataMap.getString("artworkUrl", "")
                isPlaying = dataMap.getBoolean("isPlaying", false)
                currentPosition = dataMap.getLong("currentPosition", 0L)
                totalDuration = dataMap.getLong("totalDuration", 0L)
            }
        }
    }

    private fun sendCommandToPhone(command: String) {
        val bytes = command.toByteArray(Charsets.UTF_8)
        Wearable.getNodeClient(this).connectedNodes.addOnSuccessListener { nodes ->
            for (node in nodes) {
                Wearable.getMessageClient(this)
                    .sendMessage(node.id, "/remote_control", bytes)
                    .addOnSuccessListener { Log.d("WearActivity", "Sent $command to phone node ${node.displayName}") }
                    .addOnFailureListener { e -> Log.e("WearActivity", "Failed to send $command to phone", e) }
            }
        }
    }
}

@Composable
fun WearOSRemoteControlScreen(
    title: String,
    artist: String,
    artworkUrl: String,
    isPlaying: Boolean,
    currentPosition: Long,
    totalDuration: Long,
    onSendCommand: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Circular Artwork
            AsyncImage(
                model = artworkUrl.ifEmpty { null },
                contentDescription = null,
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Track Title & Artist
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = artist,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = Color(0xFFE5007D),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Seek Bar
            Slider(
                value = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f,
                onValueChange = { onSendCommand("SEEK:${(it * totalDuration).toLong()}") },
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFE5007D),
                    activeTrackColor = Color(0xFFE5007D),
                    inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                ),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(8.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Remote Controls (Prev 🥧, Big Play/Pause ⏸/▶, Next 🌽)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                IconButton(
                    onClick = { onSendCommand("PREV") },
                    modifier = Modifier.size(38.dp)
                ) {
                    Text("🥧", fontSize = 26.sp)
                }

                IconButton(
                    onClick = { onSendCommand("TOGGLE_PLAY") },
                    modifier = Modifier.size(52.dp)
                ) {
                    Text(if (isPlaying) "⏸" else "▶", fontSize = 34.sp, color = Color(0xFFE5007D))
                }

                IconButton(
                    onClick = { onSendCommand("NEXT") },
                    modifier = Modifier.size(38.dp)
                ) {
                    Text("🌽", fontSize = 26.sp)
                }
            }
        }
    }
}
