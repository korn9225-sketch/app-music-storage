package com.example.helloworldandmyfirstapp

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class YouTubeAlbum(
    val browseId: String,
    val title: String,
    val artist: String,
    val year: String,
    val artworkUrl: String
)

object YouTubeMusicEngine {
    private const val TAG = "YouTubeMusicEngine"
    private const val INNERTUBE_CLIENT_NAME = "WEB_REMIX"
    private const val INNERTUBE_CLIENT_VERSION = "1.20240318.01.00"

    suspend fun searchMusicAlbums(context: Context, query: String): List<YouTubeAlbum> {
        return withContext(Dispatchers.IO) {
            val albums = mutableListOf<YouTubeAlbum>()
            try {
                val url = URL("https://music.youtube.com/youtubei/v1/search?alt=json")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")

                val cookies = YouTubeMusicAuthManager.getCookies(context)
                if (cookies.isNotBlank()) {
                    conn.setRequestProperty("Cookie", cookies)
                    val sapisidHash = YouTubeMusicAuthManager.getSapisidHash(cookies)
                    if (sapisidHash.isNotBlank()) {
                        conn.setRequestProperty("Authorization", "SAPISIDHASH $sapisidHash")
                    }
                }

                conn.connectTimeout = 8000
                conn.readTimeout = 8000
                conn.doOutput = true

                val payload = JSONObject().apply {
                    put("context", JSONObject().apply {
                        put("client", JSONObject().apply {
                            put("clientName", INNERTUBE_CLIENT_NAME)
                            put("clientVersion", INNERTUBE_CLIENT_VERSION)
                        })
                    })
                    put("query", "$query album")
                }

                conn.outputStream.use { it.write(payload.toString().toByteArray(Charsets.UTF_8)) }

                if (conn.responseCode == 200) {
                    val responseStr = conn.inputStream.bufferedReader().readText()

                    val albumBrowseRegex = Regex(""""browseId":"(MPREb_[a-zA-Z0-9_-]+)"""")
                    val titleRegex = Regex(""""title":\{"runs":\[\{"text":"([^"]+)"\}""")

                    val browseMatches = albumBrowseRegex.findAll(responseStr).map { it.groupValues[1] }.distinct().toList()
                    val titleMatches = titleRegex.findAll(responseStr).map { it.groupValues[1] }.toList()

                    for (i in browseMatches.indices) {
                        val browseId = browseMatches[i]
                        val title = titleMatches.getOrElse(i) { "$query Album" }

                        albums.add(
                            YouTubeAlbum(
                                browseId = browseId,
                                title = title,
                                artist = query,
                                year = "Album",
                                artworkUrl = "https://img.youtube.com/vi/default/hqdefault.jpg"
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Search albums error", e)
            }

            albums.distinctBy { it.browseId }
        }
    }

    suspend fun fetchAlbumTracks(
        context: Context,
        albumBrowseId: String,
        albumTitle: String,
        artistName: String,
        fallbackArtworkUrl: String
    ): List<Song> {
        return withContext(Dispatchers.IO) {
            val tracks = mutableListOf<Song>()
            try {
                val url = URL("https://music.youtube.com/youtubei/v1/browse?alt=json")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")

                val cookies = YouTubeMusicAuthManager.getCookies(context)
                if (cookies.isNotBlank()) {
                    conn.setRequestProperty("Cookie", cookies)
                    val sapisidHash = YouTubeMusicAuthManager.getSapisidHash(cookies)
                    if (sapisidHash.isNotBlank()) {
                        conn.setRequestProperty("Authorization", "SAPISIDHASH $sapisidHash")
                    }
                }

                conn.connectTimeout = 8000
                conn.readTimeout = 8000
                conn.doOutput = true

                val payload = JSONObject().apply {
                    put("context", JSONObject().apply {
                        put("client", JSONObject().apply {
                            put("clientName", INNERTUBE_CLIENT_NAME)
                            put("clientVersion", INNERTUBE_CLIENT_VERSION)
                        })
                    })
                    put("browseId", albumBrowseId)
                }

                conn.outputStream.use { it.write(payload.toString().toByteArray(Charsets.UTF_8)) }

                if (conn.responseCode == 200) {
                    val responseStr = conn.inputStream.bufferedReader().readText()
                    val videoIdRegex = Regex(""""videoId":"([a-zA-Z0-9_-]{11})"""")
                    val titleRegex = Regex(""""title":\{"runs":\[\{"text":"([^"]+)"\}""")

                    val videoMatches = videoIdRegex.findAll(responseStr).map { it.groupValues[1] }.distinct().toList()
                    val titleMatches = titleRegex.findAll(responseStr).map { it.groupValues[1] }.toList()

                    for (i in videoMatches.indices) {
                        val videoId = videoMatches[i]
                        val title = titleMatches.getOrElse(i + 1) { "Track ${i + 1}" }
                        val thumbUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
                        val streamUrl = "https://www.youtube.com/watch?v=$videoId"

                        tracks.add(
                            Song(
                                title = title,
                                artist = artistName,
                                album = albumTitle,
                                url = streamUrl,
                                artworkUrl = thumbUrl,
                                isVideo = false
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Fetch album tracks error", e)
            }
            tracks.distinctBy { it.url }
        }
    }

    suspend fun searchMusic(context: Context, query: String): List<Song> {
        return withContext(Dispatchers.IO) {
            val results = mutableListOf<Song>()
            try {
                val url = URL("https://music.youtube.com/youtubei/v1/search?alt=json")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")

                val cookies = YouTubeMusicAuthManager.getCookies(context)
                if (cookies.isNotBlank()) {
                    conn.setRequestProperty("Cookie", cookies)
                    val sapisidHash = YouTubeMusicAuthManager.getSapisidHash(cookies)
                    if (sapisidHash.isNotBlank()) {
                        conn.setRequestProperty("Authorization", "SAPISIDHASH $sapisidHash")
                    }
                }

                conn.connectTimeout = 8000
                conn.readTimeout = 8000
                conn.doOutput = true

                val payload = JSONObject().apply {
                    put("context", JSONObject().apply {
                        put("client", JSONObject().apply {
                            put("clientName", INNERTUBE_CLIENT_NAME)
                            put("clientVersion", INNERTUBE_CLIENT_VERSION)
                        })
                    })
                    put("query", query)
                }

                conn.outputStream.use { it.write(payload.toString().toByteArray(Charsets.UTF_8)) }

                if (conn.responseCode == 200) {
                    val responseStr = conn.inputStream.bufferedReader().readText()
                    parseInnertubeItems(responseStr, query, results)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Innertube search error, trying fallback", e)
            }

            if (results.isEmpty()) {
                try {
                    val encodedQuery = URLEncoder.encode("$query music", "UTF-8")
                    val url = URL("https://www.youtube.com/results?search_query=$encodedQuery")
                    val conn = url.openConnection() as HttpURLConnection
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    conn.connectTimeout = 8000
                    conn.readTimeout = 8000

                    if (conn.responseCode == 200) {
                        val html = conn.inputStream.bufferedReader().readText()
                        val videoIdRegex = Regex(""""videoId":"([a-zA-Z0-9_-]{11})"""")
                        val matches = videoIdRegex.findAll(html).map { it.groupValues[1] }.distinct().take(12)

                        for (videoId in matches) {
                            val thumbUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
                            results.add(
                                Song(
                                    title = "$query Track",
                                    artist = query,
                                    album = "YouTube Music",
                                    url = "https://www.youtube.com/watch?v=$videoId",
                                    artworkUrl = thumbUrl,
                                    isVideo = false
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Fallback search error", e)
                }
            }

            results.distinctBy { it.url }
        }
    }

    suspend fun fetchUserPlaylists(context: Context): List<Song> {
        return withContext(Dispatchers.IO) {
            val userSongs = mutableListOf<Song>()
            val cookies = YouTubeMusicAuthManager.getCookies(context)
            if (cookies.isBlank()) return@withContext emptyList()

            try {
                val url = URL("https://music.youtube.com/youtubei/v1/browse?alt=json")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                conn.setRequestProperty("Cookie", cookies)

                val sapisidHash = YouTubeMusicAuthManager.getSapisidHash(cookies)
                if (sapisidHash.isNotBlank()) {
                    conn.setRequestProperty("Authorization", "SAPISIDHASH $sapisidHash")
                }

                conn.doOutput = true
                val payload = JSONObject().apply {
                    put("context", JSONObject().apply {
                        put("client", JSONObject().apply {
                            put("clientName", INNERTUBE_CLIENT_NAME)
                            put("clientVersion", INNERTUBE_CLIENT_VERSION)
                        })
                    })
                    put("browseId", "FEmusic_liked")
                }

                conn.outputStream.use { it.write(payload.toString().toByteArray(Charsets.UTF_8)) }

                if (conn.responseCode == 200) {
                    val responseStr = conn.inputStream.bufferedReader().readText()
                    parseInnertubeItems(responseStr, "My Liked Song", userSongs)
                }
            } catch (e: Exception) {
                Log.e(TAG, "User playlists fetch error", e)
            }
            userSongs.distinctBy { it.url }
        }
    }

    suspend fun resolvePlayableAudioUrl(songUrl: String): String {
        return withContext(Dispatchers.IO) {
            if (!songUrl.contains("youtube.com") && !songUrl.contains("youtu.be")) {
                return@withContext songUrl
            }

            val videoId = when {
                songUrl.contains("v=") -> songUrl.substringAfter("v=").substringBefore("&")
                songUrl.contains("youtu.be/") -> songUrl.substringAfter("youtu.be/").substringBefore("?")
                else -> ""
            }

            if (videoId.length != 11) return@withContext songUrl

            // 1. Try Innertube Player API
            try {
                val url = URL("https://www.youtube.com/youtubei/v1/player?alt=json")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Android 14; Mobile; rv:124.0) Gecko/124.0 Firefox/124.0")
                conn.connectTimeout = 8000
                conn.readTimeout = 8000
                conn.doOutput = true

                val payload = JSONObject().apply {
                    put("context", JSONObject().apply {
                        put("client", JSONObject().apply {
                            put("clientName", "ANDROID")
                            put("clientVersion", "19.09.37")
                            put("androidSdkVersion", 34)
                        })
                    })
                    put("videoId", videoId)
                }

                conn.outputStream.use { it.write(payload.toString().toByteArray(Charsets.UTF_8)) }

                if (conn.responseCode == 200) {
                    val responseStr = conn.inputStream.bufferedReader().readText()
                    val json = JSONObject(responseStr)
                    val streamingData = json.optJSONObject("streamingData")
                    if (streamingData != null) {
                        val adaptiveFormats = streamingData.optJSONArray("adaptiveFormats")
                        if (adaptiveFormats != null) {
                            for (i in 0 until adaptiveFormats.length()) {
                                val fmt = adaptiveFormats.getJSONObject(i)
                                val mime = fmt.optString("mimeType", "")
                                val audioUrl = fmt.optString("url", "")
                                if (mime.startsWith("audio/") && audioUrl.isNotBlank()) {
                                    return@withContext audioUrl
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Innertube player extraction error", e)
            }

            // 2. Try Piped API fallback mirrors
            val pipedMirrors = listOf(
                "https://pipedapi.kavin.rocks/streams/$videoId",
                "https://api.piped.video/streams/$videoId",
                "https://pipedapi.privacy.com.de/streams/$videoId"
            )

            for (mirror in pipedMirrors) {
                try {
                    val conn = URL(mirror).openConnection() as HttpURLConnection
                    conn.requestMethod = "GET"
                    conn.connectTimeout = 6000
                    conn.readTimeout = 6000
                    if (conn.responseCode == 200) {
                        val jsonStr = conn.inputStream.bufferedReader().readText()
                        val json = JSONObject(jsonStr)
                        val audioStreams = json.optJSONArray("audioStreams")
                        if (audioStreams != null && audioStreams.length() > 0) {
                            val streamUrl = audioStreams.getJSONObject(0).optString("url", "")
                            if (streamUrl.isNotBlank()) {
                                return@withContext streamUrl
                            }
                        }
                    }
                } catch (_: Exception) {}
            }

            songUrl
        }
    }

    private fun parseInnertubeItems(jsonStr: String, defaultArtist: String, outList: MutableList<Song>) {
        try {
            val videoIdRegex = Regex(""""videoId":"([a-zA-Z0-9_-]{11})"""")
            val titleRegex = Regex(""""title":\{"runs":\[\{"text":"([^"]+)"\}""")

            val videoMatches = videoIdRegex.findAll(jsonStr).map { it.groupValues[1] }.distinct().toList()
            val titleMatches = titleRegex.findAll(jsonStr).map { it.groupValues[1] }.toList()

            for (i in videoMatches.indices) {
                val videoId = videoMatches[i]
                val title = titleMatches.getOrElse(i) { "YouTube Track" }
                val thumbUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
                val streamUrl = "https://www.youtube.com/watch?v=$videoId"

                outList.add(
                    Song(
                        title = title,
                        artist = defaultArtist,
                        album = "YouTube Music",
                        url = streamUrl,
                        artworkUrl = thumbUrl,
                        isVideo = false
                    )
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "parseInnertubeItems error", e)
        }
    }
}
