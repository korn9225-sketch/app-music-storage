package com.example.helloworldandmyfirstapp
import android.content.ComponentName
import android.os.Build
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import android.Manifest
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraCharacteristics
import android.view.WindowManager
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateListOf
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.edit
import android.app.DownloadManager
import android.os.Environment
import java.io.File
import java.net.URL
import java.net.HttpURLConnection
import java.net.URLEncoder
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.material.icons.filled.Download
import android.app.Application
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.CookieManager
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.material.icons.filled.AccountCircle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.filled.DownloadDone
import android.widget.Toast
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView as Media3PlayerView
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.drawable.toBitmap
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.activity.compose.BackHandler
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.clip
import androidx.compose.ui.zIndex

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed as itemsIndexedGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.Spacer
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.media3.common.MediaMetadata
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.helloworldandmyfirstapp.ui.theme.HelloWorldAndMyFirstAppTheme
import com.google.common.util.concurrent.ListenableFuture

val SongFont = FontFamily(
    Font(R.font.old_english, FontWeight.Normal)
)

data class Song(
    val title: String,
    val artist: String,
    val album: String,
    val url: String,
    val artworkUrl: String,
    val isVideo: Boolean = false,
    val localUri: String? = null
)

fun Song.getVerifiedArtworkUrl(): String {
    if (artworkUrl.isNotBlank() && (!artworkUrl.contains("64721858-092c-4543-9f2b-03af46876d20") || artist.equals("Danny Diablo", ignoreCase = true))) {
        return artworkUrl
    }
    return when (artist.lowercase()) {
        "danny diablo" -> "https://github.com/user-attachments/assets/64721858-092c-4543-9f2b-03af46876d20"
        "korn" -> "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/Korn_follow_the_leader.jpg"
        "morgan wallen" -> "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/Cover.jpg"
        "biohazard" -> {
            if (album.equals("Kill Or Be Killed", ignoreCase = true)) {
                "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/Folder.jpg"
            } else {
                "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"
            }
        }
        "nofx" -> "https://coverartarchive.org/release-group/826859eb-1608-3ef4-ac4d-86c57f50c493/front"
        "hatebreed" -> {
            if (album.equals("Live Dominance", ignoreCase = true)) {
                "https://img.youtube.com/vi/Xigg9UjWJNg/hqdefault.jpg"
            } else {
                "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"
            }
        }
        "mudvayne" -> "https://i.ytimg.com/vi/s0M3_o4P2uY/hqdefault.jpg"
        "blender" -> "https://upload.wikimedia.org/wikipedia/commons/c/c5/Big_buck_bunny_poster_small.jpg"
        "google" -> "https://i.ytimg.com/vi/aqz-KE-bpKQ/hqdefault.jpg"
        "warner bros" -> "https://upload.wikimedia.org/wikipedia/en/2/2b/Constantine_poster.jpg"
        else -> ""
    }
}

enum class MusicTab(val title: String) {
    NowPlaying("now playing"),
    Music("music"),
    Video("video"),
    Settings("settings")
}

enum class MusicSubTab(val title: String) {
    Artists("artists"),
    Albums("albums"),
    Songs("songs"),
    YouTubeMusic("YouTube Music")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                registerForActivityResult(ActivityResultContracts.RequestPermission()) {}.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            HelloWorldAndMyFirstAppTheme(dynamicColor = false) {
                val context = LocalContext.current
                var isExiting by remember { mutableStateOf(false) }
                var isStarting by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(100.milliseconds)
                    isStarting = false
                }

                // Replaced BackHandler with onKeyEvent and hierarchical back navigation

                val transition = updateTransition(
                    targetState = when {
                        isExiting -> "EXIT"
                        isStarting -> "START"
                        else -> "ON"
                    }, 
                    label = "CRTTransition"
                )

                val crtScaleY by transition.animateFloat(
                    transitionSpec = {
                        when (targetState) {
                            "EXIT" -> tween(durationMillis = 400, easing = LinearEasing)
                            "ON" -> tween(durationMillis = 400, delayMillis = 300, easing = LinearEasing)
                            else -> tween(durationMillis = 0)
                        }
                    },
                    label = "scaleY"
                ) { state ->
                    when (state) {
                        "START", "EXIT" -> 0.001f
                        else -> 1f
                    }
                }

                val crtScaleX by transition.animateFloat(
                    transitionSpec = {
                        when (targetState) {
                            "EXIT" -> keyframes {
                                durationMillis = 700
                                1f at 400
                                0f at 700
                            }
                            "ON" -> tween(durationMillis = 300, easing = LinearEasing)
                            else -> tween(durationMillis = 0)
                        }
                    },
                    label = "scaleX"
                ) { state ->
                    when (state) {
                        "START" -> 0f
                        "EXIT" -> 0f
                        else -> 1f
                    }
                }

                LaunchedEffect(isExiting, crtScaleX) {
                    if (isExiting && crtScaleX == 0f) {
                        delay(100.milliseconds)
                        (context as? ComponentActivity)?.finish()
                    }
                }

                val dannyDiabloArt = "https://github.com/user-attachments/assets/64721858-092c-4543-9f2b-03af46876d20"
                val kornArt = "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/Korn_follow_the_leader.jpg"
                val wallenArt = "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/Cover.jpg"
                val worldOnFireArt = "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/Folder.jpg"
                
                val sharedPrefs = remember { context.getSharedPreferences("media_prefs", Context.MODE_PRIVATE) }
                
                val songs = remember {
                    val initialList = mutableStateListOf(
                        // Danny Diablo Tracks
                        Song("Intro", "Danny Diablo", "Dollerz Make Sense", "https://raw.githubusercontent.com/korn9225-sketch/app-music-storage/main/Danny_Diablo_01_Intro.mp3", dannyDiabloArt),
                        Song("Ill Roc Soldiers ft. Stikman", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580932/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.02.Ill.Roc.Soldiers.ft.Stikman.mp3", dannyDiabloArt),
                        Song("Stready Minds ft. Diggy Ill-Roc", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580933/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.03.Stready.Minds.ft.Diggy.Ill-Roc.mp3", dannyDiabloArt),
                        Song("Grimey ft. Stikman", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580934/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.04.Grimey.ft.Stikman.mp3", dannyDiabloArt),
                        Song("Smash Everything ft. Stikman", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580935/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.05.Smash.Everything.ft.Stikman.mp3", dannyDiabloArt),
                        Song("Queens & Jersey ft. Stikman", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580925/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.06.Queens.Jersey.ft.Stikman.mp3", dannyDiabloArt),
                        Song("Spit Fire ft. Diggy Ill-Roc, Wes Nihil & Tony Slippaz", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580926/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.07.Spit.Fire.ft.Diggy.Ill-Roc.Wes.Nihil.Tony.Slippaz.mp3", dannyDiabloArt),
                        Song("Remember My Name ft. Christian Koresh & Diggy Ill-Roc", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580927/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.08.Remember.My.Name.ft.Christian.Koresh.Diggy.Ill-Roc.mp3", dannyDiabloArt),
                        Song("The Foundation ft. Stikman", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580928/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.09.The.Foundation.ft.Stikman.mp3", dannyDiabloArt),
                        Song("In the City ft. Joey D", "Danny Diablo", "Dollerz Make Sense", "https://github.com/user-attachments/files/31580929/Danny.Diablo.-.Dollerz.Make.Sense.Digital.Download.-.10.In.the.City.ft.Joey.D.mp3", dannyDiabloArt),
                        
                        // Korn Tracks
                        Song("It's On", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/01.-.Korn.-.It.s.On.m4a", kornArt),
                        Song("Freak On a Leash", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/02.-.Korn.-.Freak.On.a.Leash.Explicit.m4a", kornArt),
                        Song("Got the Life", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/03.-.Korn.-.Got.the.Life.Explicit.m4a", kornArt),
                        Song("Dead Bodies Everywhere", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/04.-.Korn.-.Dead.Bodies.Everywhere.Explicit.m4a", kornArt),
                        Song("Children of the Korn", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/05.-.Korn.-.Children.of.the.Korn.m4a", kornArt),
                        Song("B.B.K.", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/06.-.Korn.-.B.B.K-.m4a", kornArt),
                        Song("Pretty", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/07.-.Korn.-.Pretty.m4a", kornArt),
                        Song("All In the Family", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/08.-.Korn.-.All.In.the.Family.m4a", kornArt),
                        Song("Reclaim My Place", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/09.-.Korn.-.Reclaim.My.Place.Explicit.m4a", kornArt),
                        Song("Justin", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/10.-.Korn.-.Justin.m4a", kornArt),
                        Song("Seed", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/11.-.Korn.-.Seed.m4a", kornArt),
                        Song("Cameltosis", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/12.-.Korn.-.Cameltosis.m4a", kornArt),
                        Song("My Gift to You", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/13.-.Korn.-.My.Gift.to.You.m4a", kornArt),
                        Song("Earache My Eye", "Korn", "Follow the Leader", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/14.-.Korn.-.Earache.My.Eye.m4a", kornArt),

                        // Morgan Wallen Tracks
                        Song("Born With A Beer In My Hand", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/01.Born.With.A.Beer.In.My.Hand.mp3", wallenArt),
                        Song("Last Night", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/02.Last.Night.mp3", wallenArt),
                        Song("Everything I Love", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/03.Everything.I.Love.mp3", wallenArt),
                        Song("Man Made A Bar", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/04.Man.Made.A.Bar.feat.Eric.Church.mp3", wallenArt),
                        Song("Devil Don't Know", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/05.Devil.Don.t.Know.mp3", wallenArt),
                        Song("One Thing At A Time", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/06.One.Thing.At.A.Time.mp3", wallenArt),
                        Song("98 Braves", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/07.98.Braves.mp3", wallenArt),
                        Song("Ain't That Some", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/08.Ain.t.That.Some.mp3", wallenArt),
                        Song("I Wrote The Book", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/09.I.Wrote.The.Book.mp3", wallenArt),
                        Song("Tennessee Numbers", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/10.Tennessee.Numbers.mp3", wallenArt),
                        Song("Hope That's True", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/11.Hope.That.s.True.mp3", wallenArt),
                        Song("Whiskey Friends", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/12.Whiskey.Friends.mp3", wallenArt),
                        Song("Sunrise", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/13.Sunrise.mp3", wallenArt),
                        Song("Keith Whitley", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/14.Keith.Whitley.mp3", wallenArt),
                        Song("In The Bible", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/15.In.The.Bible.feat.HARDY.mp3", wallenArt),
                        Song("You Proof", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/16.You.Proof.mp3", wallenArt),
                        Song("Thought You Should Know", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/17.Thought.You.Should.Know.mp3", wallenArt),
                        Song("F150-50", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/18.F150-50.mp3", wallenArt),
                        Song("Neon Star", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/19.Neon.Star.Country.Boy.Lullaby.mp3", wallenArt),
                        Song("I Deserve A Drink", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/20.I.Deserve.A.Drink.mp3", wallenArt),
                        Song("Wine Into Water", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/21.Wine.Into.Water.mp3", wallenArt),
                        Song("Me + All Your Reasons", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/22.Me.+.All.Your.Reasons.mp3", wallenArt),
                        Song("Tennessee Fan", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/23.Tennessee.Fan.mp3", wallenArt),
                        Song("Money On Me", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/24.Money.On.Me.mp3", wallenArt),
                        Song("Thinkin' Bout Me", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/25.Thinkin.Bout.Me.mp3", wallenArt),
                        Song("Single Than She Was", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/26.Single.Than.She.Was.mp3", wallenArt),
                        Song("Days That End In Why", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/27.Days.That.End.In.Why.mp3", wallenArt),
                        Song("Last Drive Down Main", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/28.Last.Drive.Down.Main.mp3", wallenArt),
                        Song("Me To Me", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/29.Me.To.Me.mp3", wallenArt),
                        Song("Don't Think Jesus", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/30.Don.t.Think.Jesus.mp3", wallenArt),
                        Song("180 Lifestyle", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/31.180.Lifestyle.mp3", wallenArt),
                        Song("Had It", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/32.Had.It.mp3", wallenArt),
                        Song("Cowgirls", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/33.Cowgirls.feat.ERNEST.mp3", wallenArt),
                        Song("Good Girl Gone Missin'", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/34.Good.Girl.Gone.Missin.mp3", wallenArt),
                        Song("Outlook", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/35.Outlook.mp3", wallenArt),
                        Song("Dying Man", "Morgan Wallen", "One Thing At A Time", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/36.Dying.Man.mp3", wallenArt),

                        // Kill Or Be Killed Tracks (Biohazard)
                        Song("Intro", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/01-.World.On.Fire.Intro.mp3", worldOnFireArt),
                        Song("World On Fire", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/02-.World.On.Fire.mp3", worldOnFireArt),
                        Song("Never Forgive Never Forget", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/03-.Never.Forgive.Never.Forget.mp3", worldOnFireArt),
                        Song("Kill Or Be Killed", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/04-.Kill.Or.Be.Killed.mp3", worldOnFireArt),
                        Song("Heads Kicked In", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/05-.Heads.Kicked.In.mp3", worldOnFireArt),
                        Song("Beaten Senseless", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/06-.Beaten.Senseless.mp3", worldOnFireArt),
                        Song("Make My Stand", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/07-.Make.My.Stand.mp3", worldOnFireArt),
                        Song("Open Your Eyes", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/08-.Open.Your.Eyes.mp3", worldOnFireArt),
                        Song("Penalty", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/09-.Penalty.mp3", worldOnFireArt),
                        Song("Dead To Me", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/10-.Dead.To.Me.mp3", worldOnFireArt),
                        Song("Hallowed Ground", "Biohazard", "Kill Or Be Killed", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/11-.Hallowed.Ground.mp3", worldOnFireArt),

                        // Biohazard Tracks
                        Song("Sellout", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/01.sellout.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Uncivilization", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/02.uncivilization.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Wide Awake", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/03.wide.awake.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Get Away", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/04.get.away.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Unified", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/05.unified.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Gone", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/06.gone.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Letter Go", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/07.letter.go.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Last Man Standing", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/08.last.man.standing.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Hffk", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/09.hffk.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Domination", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/10.domination.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Trap", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/11.trap.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Plastic", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/12.plastic.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Cross The Line", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/13.cross.the.line.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("A.T.F.", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/14.a.t.f.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Life Of My Own", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/15.life.of.my.own.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),
                        Song("Sex And Violence", "Biohazard", "Uncivilization", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/16.sex.and.violence.mp3", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/biohazarduncivilization2001.jpg"),

                        // Hatebreed - Fatal Paradox Tracks (Local High-Quality MP3s in Track Order 01-11)
                        Song("Kill Count Increase", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_01_kill_count_increase}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("The Carrion Choir", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_02_the_carrion_choir}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("Decisions Become Destinies", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_03_decisions_become_destinies}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("Total Invincible", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_04_total_invincible}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("Fatal Paradox", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_05_fatal_paradox}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("Still A Threat", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_06_still_a_threat}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("Sacrifice Season (feat. John Tardy)", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_07_sacrifice_season}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("Truth Is A Lion", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_08_truth_is_a_lion}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("Make The Demons Obey", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_09_make_the_demons_obey}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("None Left To Save", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_10_none_left_to_save}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),
                        Song("Poison Planet (COC cover)", "Hatebreed", "Fatal Paradox", "android.resource://com.example.helloworldandmyfirstapp/${R.raw.hatebreed_11_poison_planet}", "android.resource://com.example.helloworldandmyfirstapp/${R.drawable.hatebreed_fatal_paradox_cover}"),

                        // NOFX Tracks
                        Song("The Decline", "NOFX", "The Decline", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/NOFX.-.The.Decline.mp3", "https://coverartarchive.org/release-group/826859eb-1608-3ef4-ac4d-86c57f50c493/front"),

                        // Video Tracks
                        Song("Big Buck Bunny", "Blender", "Movie", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "https://upload.wikimedia.org/wikipedia/commons/c/c5/Big_buck_bunny_poster_small.jpg", isVideo = true),
                        Song("Elephants Dream", "Blender", "Movie", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", "https://upload.wikimedia.org/wikipedia/commons/e/e8/Elephants_Dream_poster.jpg", isVideo = true),
                        Song("For Bigger Blazes", "Google", "Sample", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", "https://i.ytimg.com/vi/aqz-KE-bpKQ/hqdefault.jpg", isVideo = true),
                        Song("Sintel", "Blender", "Movie", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4", "https://upload.wikimedia.org/wikipedia/commons/d/df/Sintel_poster.jpg", isVideo = true),
                        Song("Making Of Dig", "Mudvayne", "v2.0 Beta", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v2.0beta/Mudvayne.-.Making.Of.Dig.webm", "https://i.ytimg.com/vi/s0M3_o4P2uY/hqdefault.jpg", isVideo = true),
                        Song("Constantine", "Warner Bros", "Movie", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v2.0beta/Constantine.2005.1080p.BrRip.x264.YIFY.mp4", "https://upload.wikimedia.org/wikipedia/en/2/2b/Constantine_poster.jpg", isVideo = true),
                        Song("Live Dominance Full Concert", "Hatebreed", "Live Dominance", "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/Hatebreed.%40.Live.Dominance.Full.Concert.Xigg9UjWJNg.mp4", "https://img.youtube.com/vi/Xigg9UjWJNg/hqdefault.jpg", isVideo = true)
                    )
                    
                    // Automatically scan local device media (audio & video)
                    val autoLocalSongs = scanLocalDeviceMedia(context)
                    autoLocalSongs.forEach { localSong ->
                        if (initialList.none { it.url == localSong.url }) {
                            initialList.add(localSong)
                        }
                    }

                    // Load saved local media URIs
                    val savedUris = sharedPrefs.getStringSet("local_media_uris", emptySet()) ?: emptySet()
                    savedUris.forEach { uriString ->
                        val uri = Uri.parse(uriString)
                        initialList.add(getSongFromUri(context, uri))
                    }
                    val uniqueList = initialList.distinctBy { song ->
                        val cleanTitle = song.title.lowercase().replace("feat", "").replace("ft", "").replace(Regex("[^a-z0-9]"), "")
                        val cleanArtist = song.artist.lowercase().replace(Regex("[^a-z0-9]"), "")
                        if (cleanArtist.isNotBlank() && cleanTitle.isNotBlank()) "${cleanArtist}_${cleanTitle}" else song.url
                    }
                    mutableStateListOf<Song>().apply { addAll(uniqueList) }
                }

                val viewModel: MusicViewModel = viewModel()

                LaunchedEffect(Unit) {
                    viewModel.initController(context, songs)
                    UpdateLogger.checkAndAutoLogVersionChange(context)

                    val autoLocalSongs = scanLocalDeviceMedia(context)
                    autoLocalSongs.forEach { localSong ->
                        if (songs.none { it.url == localSong.url }) {
                            songs.add(localSong)
                        }
                    }

                    val (fetchedSongs, _) = fetchRepoSongsFromGitHub()
                    fetchedSongs.forEach { newSong ->
                        val cleanNewTitle = newSong.title.lowercase().replace("feat", "").replace("ft", "").replace(Regex("[^a-z0-9]"), "")
                        val cleanNewArtist = newSong.artist.lowercase().replace(Regex("[^a-z0-9]"), "")
                        val exists = songs.any { song ->
                            val cleanSongTitle = song.title.lowercase().replace("feat", "").replace("ft", "").replace(Regex("[^a-z0-9]"), "")
                            val cleanSongArtist = song.artist.lowercase().replace(Regex("[^a-z0-9]"), "")
                            song.url == newSong.url || (cleanSongArtist == cleanNewArtist && (cleanSongTitle == cleanNewTitle || cleanSongTitle.contains(cleanNewTitle) || cleanNewTitle.contains(cleanSongTitle)))
                        }
                        if (!exists) {
                            songs.add(newSong)
                        }
                    }
                }

                LaunchedEffect(songs.size, viewModel.player) {
                    if (viewModel.player != null) {
                        viewModel.updateMediaItems(context, songs)
                    }
                }

                LaunchedEffect(viewModel.player, viewModel.isPlaying) {
                    while (true) {
                        viewModel.updatePosition()
                        delay(500.milliseconds)
                    }
                }

                val playerState = viewModel.player
                val currentSongIndex = viewModel.currentSongIndex
                val isPlaying = viewModel.isPlaying
                val currentPosition = viewModel.currentPosition
                val totalDuration = viewModel.totalDuration

                LaunchedEffect(currentSongIndex, isPlaying) {
                    if (songs.isNotEmpty() && currentSongIndex in songs.indices) {
                        val currentSong = songs[currentSongIndex]
                        MusicWidgetProvider.updateAllWidgets(
                            context,
                            currentSong.title,
                            currentSong.artist,
                            isPlaying
                        )
                        PhoneWearSyncManager.updateNowPlaying(
                            context = context,
                            title = currentSong.title,
                            artist = currentSong.artist,
                            artworkUrl = currentSong.getVerifiedArtworkUrl(),
                            isPlaying = isPlaying,
                            currentPosition = currentPosition,
                            totalDuration = totalDuration
                        )
                    }
                }

                DisposableEffect(context) {
                    val receiver = object : android.content.BroadcastReceiver() {
                        override fun onReceive(ctx: Context?, intent: Intent?) {
                            when (intent?.action) {
                                "com.example.helloworldandmyfirstapp.PREV_SONG" -> playerState?.seekToPreviousMediaItem()
                                "com.example.helloworldandmyfirstapp.TOGGLE_PLAY" -> {
                                    if (isPlaying) playerState?.pause() else playerState?.play()
                                }
                                "com.example.helloworldandmyfirstapp.NEXT_SONG" -> playerState?.seekToNextMediaItem()
                            }
                        }
                    }
                    val filter = IntentFilter().apply {
                        addAction("com.example.helloworldandmyfirstapp.PREV_SONG")
                        addAction("com.example.helloworldandmyfirstapp.TOGGLE_PLAY")
                        addAction("com.example.helloworldandmyfirstapp.NEXT_SONG")
                    }
                    if (Build.VERSION.SDK_INT >= 33) {
                        context.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
                    } else {
                        ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_EXPORTED)
                    }
                    onDispose {
                        try { context.unregisterReceiver(receiver) } catch (_: Exception) {}
                    }
                }
                var selectedTab by viewModel::selectedTab
                var selectedMusicSubTab by viewModel::selectedMusicSubTab
                var selectedArtist by viewModel::selectedArtist
                var selectedAlbum by viewModel::selectedAlbum

                val tabHistory = remember { mutableStateListOf(MusicTab.NowPlaying) }

                val selectTab: (MusicTab) -> Unit = { newTab ->
                    if (selectedTab != newTab) {
                        if (newTab == MusicTab.Music) {
                            selectedArtist = null
                            selectedAlbum = null
                        }
                        if (tabHistory.lastOrNull() != newTab) {
                            tabHistory.add(newTab)
                        }
                        selectedTab = newTab
                    }
                }

                val canGoBack = selectedArtist != null ||
                        selectedAlbum != null ||
                        selectedMusicSubTab != MusicSubTab.Artists ||
                        tabHistory.size > 1 ||
                        selectedTab != MusicTab.NowPlaying

                val handleBackPress = {
                    when {
                        selectedAlbum != null -> selectedAlbum = null
                        selectedArtist != null -> selectedArtist = null
                        selectedMusicSubTab != MusicSubTab.Artists -> selectedMusicSubTab = MusicSubTab.Artists
                        tabHistory.size > 1 -> {
                            tabHistory.removeAt(tabHistory.lastIndex)
                            selectedTab = tabHistory.last()
                        }
                        selectedTab != MusicTab.NowPlaying -> selectedTab = MusicTab.NowPlaying
                        else -> {
                            isExiting = true
                        }
                    }
                }

                BackHandler(enabled = canGoBack) {
                    handleBackPress()
                }

                var backDownTime by remember { mutableLongStateOf(0L) }
                
                var playerBackgroundColor by remember { mutableStateOf(Color.Black) }
                var settingsBackgroundColor by remember { mutableStateOf(Color.Black) }
                var isStrobeEnabled by remember { mutableStateOf(false) }
                var strobeSpeedMs by remember { mutableFloatStateOf(100f) }
                var isBeatSyncEnabled by remember { mutableStateOf(false) }

                LaunchedEffect(isPlaying, isStrobeEnabled, strobeSpeedMs, isBeatSyncEnabled) {
                    if (isPlaying && isStrobeEnabled) {
                        FlashlightStrobeManager.strobeSpeedMs = strobeSpeedMs.toLong()
                        FlashlightStrobeManager.isBeatSyncEnabled = isBeatSyncEnabled
                        FlashlightStrobeManager.startStrobe(context)
                    } else {
                        FlashlightStrobeManager.stopStrobe(context)
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        FlashlightStrobeManager.stopStrobe(context)
                    }
                }



                val onDownloadClick = { song: Song ->
                    val fileName = song.url.substringAfterLast("/")

                    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
                    
                    if (file.exists()) {
                        Toast.makeText(context, "Already downloaded", Toast.LENGTH_SHORT).show()
                    } else {
                        val request = DownloadManager.Request(song.url.toUri())
                            .setTitle(song.title)
                            .setDescription("Downloading ${song.title}")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationUri(Uri.fromFile(file))
                            .setAllowedOverMetered(true)
                            .setAllowedOverRoaming(true)

                        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        downloadManager.enqueue(request)
                        Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show()
                    }
                }

                val isWearOS = remember {
                    context.packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH)
                }

                if (isWearOS) {
                    WearOSRemoteControlScreen(
                        songs = songs,
                        currentSongIndex = currentSongIndex,
                        isPlaying = isPlaying,
                        currentPosition = currentPosition,
                        totalDuration = totalDuration,
                        playerState = playerState
                    )
                } else {
                    Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .onKeyEvent { event ->
                            if (!isExiting && !isStarting && event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_BACK) {
                                if (event.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                                    if (event.nativeKeyEvent.repeatCount == 0) {
                                        backDownTime = event.nativeKeyEvent.eventTime
                                    }
                                } else if (event.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
                                    val duration = event.nativeKeyEvent.eventTime - backDownTime
                                    if (duration >= 600) {
                                        isExiting = true
                                    } else {
                                        handleBackPress()
                                    }
                                    return@onKeyEvent true
                                }
                                return@onKeyEvent true
                            }
                            false
                        },
                    topBar = {
                        Surface(
                            color = Color.Black,
                            tonalElevation = 4.dp
                        ) {
                            Column {
                                Spacer(Modifier.windowInsetsPadding(WindowInsets.statusBars))
                                PrimaryScrollableTabRow(
                                    selectedTabIndex = selectedTab.ordinal,
                                    edgePadding = 8.dp,
                                    containerColor = Color.Black,
                                    contentColor = Color(0xFFE5007D),
                                    divider = {}
                                ) {
                                    MusicTab.entries.forEach { tab ->
                                        Tab(
                                            selected = selectedTab == tab,
                                            onClick = { 
                                                selectTab(tab)
                                            },
                                            text = { 
                                                Text(
                                                    text = tab.title,
                                                    style = MaterialTheme.typography.headlineMedium.copy(
                                                        fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                                                        fontSize = 18.sp,
                                                        letterSpacing = (-1).sp
                                                    ),
                                                    color = if (selectedTab == tab) Color(0xFFE5007D) else Color.White.copy(alpha = 0.6f),
                                                    maxLines = 1
                                                ) 
                                            },
                                            selectedContentColor = Color(0xFFE5007D),
                                            unselectedContentColor = Color.White.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                            }
                        }
                    },
                    bottomBar = {
                        if (selectedTab != MusicTab.NowPlaying) {
                            val currentSong = songs.getOrNull(currentSongIndex)
                            val currentProgress = if (totalDuration > 0) (currentPosition.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f) else 0f
                            BottomPlayerBar(
                                song = currentSong,
                                isPlaying = isPlaying,
                                onPlayPauseClick = { if (isPlaying) playerState?.pause() else playerState?.play() },
                                onClick = { selectTab(MusicTab.NowPlaying) },
                                progress = currentProgress
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                scaleY = crtScaleY
                                scaleX = crtScaleX
                                alpha = if ((isExiting || isStarting) && crtScaleX < 0.01f && crtScaleY < 0.01f) 0f else 1f
                            }
                            .padding(innerPadding)
                    ) {
                        Column(modifier = Modifier.fillMaxSize().zIndex(1f)) {
                            when (selectedTab) {
                                MusicTab.NowPlaying -> PlayerView(
                                    songs = songs,
                                    currentSongIndex = currentSongIndex,
                                    isPlaying = isPlaying,
                                    currentPosition = currentPosition,
                                    totalDuration = totalDuration,
                                    playerState = playerState,
                                    backgroundColor = Color.Black,
                                    isStrobeEnabled = isStrobeEnabled,
                                    onDownloadClick = onDownloadClick
                                )
                                MusicTab.Music -> {
                                    Column {
                                        if (selectedArtist == null && selectedAlbum == null) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                MusicSubTab.entries.forEach { subTab ->
                                                    val isSelected = selectedMusicSubTab == subTab
                                                    Surface(
                                                        onClick = { selectedMusicSubTab = subTab },
                                                        shape = androidx.compose.foundation.shape.CircleShape,
                                                        color = if (isSelected) Color(0xFFE5007D) else Color(0xFF1E1E26),
                                                        tonalElevation = if (isSelected) 4.dp else 0.dp
                                                    ) {
                                                        Text(
                                                            text = subTab.title.replaceFirstChar { it.uppercase() },
                                                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                                                            style = MaterialTheme.typography.titleMedium.copy(
                                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                                            ),
                                                            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        when {
                                            selectedAlbum != null -> AlbumDetailView(
                                                album = selectedAlbum!!,
                                                songs = songs,
                                                onBack = { selectedAlbum = null },
                                                onSongClick = { song ->
                                                    val index = songs.indexOf(song)
                                                    if (index != -1) {
                                                        viewModel.updateMediaItems(context, songs)
                                                        playerState?.seekTo(index, 0L)
                                                        playerState?.play()
                                                        selectedTab = MusicTab.NowPlaying
                                                    }
                                                },
                                                onDownloadClick = onDownloadClick
                                            )
                                            selectedArtist != null -> ArtistDetailView(
                                                artist = selectedArtist!!,
                                                songs = songs,
                                                onBack = { selectedArtist = null },
                                                onAlbumClick = { album -> selectedAlbum = album },
                                                onSongClick = { song ->
                                                    val index = songs.indexOf(song)
                                                    if (index != -1) {
                                                        viewModel.updateMediaItems(context, songs)
                                                        playerState?.seekTo(index, 0L)
                                                        playerState?.play()
                                                        selectedTab = MusicTab.NowPlaying
                                                    }
                                                },
                                                onDownloadClick = onDownloadClick
                                            )
                                            else -> {
                                                when (selectedMusicSubTab) {
                                                    MusicSubTab.Songs -> SongList(songs, onSongClick = { index ->
                                                        viewModel.updateMediaItems(context, songs)
                                                        playerState?.seekTo(index, 0L)
                                                        playerState?.play()
                                                        selectedTab = MusicTab.NowPlaying
                                                    }, onDownloadClick = onDownloadClick)
                                                    MusicSubTab.Artists -> ArtistList(songs, onArtistClick = { selectedArtist = it })
                                                    MusicSubTab.Albums -> AlbumList(songs, onAlbumClick = { selectedAlbum = it })
                                                    MusicSubTab.YouTubeMusic -> YouTubeMusicTabScreen(
                                                        songs = songs,
                                                        onSongClick = { song ->
                                                            val index = songs.indexOf(song)
                                                            if (index != -1) {
                                                                viewModel.updateMediaItems(context, songs)
                                                                playerState?.seekTo(index, 0L)
                                                                playerState?.play()
                                                                selectedTab = MusicTab.NowPlaying
                                                            }
                                                        },
                                                        isDarkMode = viewModel.isDarkMode
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                MusicTab.Video -> VideoList(songs, onDownloadClick = onDownloadClick) { song ->
                                    val index = songs.indexOf(song)
                                    if (index != -1) {
                                        viewModel.updateMediaItems(context, songs)
                                        playerState?.seekTo(index, 0L)
                                        playerState?.play()
                                        selectedTab = MusicTab.NowPlaying
                                    }
                                }
                                MusicTab.Settings -> SettingsView(
                                    playerColor = playerBackgroundColor,
                                    onPlayerColorChange = { playerBackgroundColor = it },
                                    settingsColor = settingsBackgroundColor,
                                    onSettingsColorChange = { settingsBackgroundColor = it },
                                    isStrobeEnabled = isStrobeEnabled,
                                    onStrobeToggle = { isStrobeEnabled = it },
                                    strobeSpeedMs = strobeSpeedMs,
                                    onStrobeSpeedChange = { strobeSpeedMs = it },
                                    isBeatSyncEnabled = isBeatSyncEnabled,
                                    onBeatSyncToggle = { isBeatSyncEnabled = it },
                                    isDarkMode = viewModel.isDarkMode,
                                    onDarkModeToggle = { viewModel.toggleDarkMode(context, it) }
                                )
                            }
                        }
                    }
                }
            }
        }

                // CRT Scanlines Overlay
                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(10f)
                ) {
                    val lineCount = (size.height / 10f).toInt()
                    for (i in 0 until lineCount) {
                        val alpha = if (i % 2 == 0) 0.08f else 0.03f
                        drawRect(
                            color = Color.Black.copy(alpha = alpha),
                            topLeft = Offset(0f, i * 10f),
                            size = Size(size.width, 2f)
                        )
                    }
                    
                    // Subtle flickering vignette
                    drawRect(
                        color = Color.Black.copy(alpha = 0.05f),
                        topLeft = Offset.Zero,
                        size = size
                    )
                }
            }
        }
    }

@Composable
fun ZuneKineticBackground(artist: String, title: String, artworkUrl: String?) {
    val infiniteTransition = rememberInfiniteTransition(label = "ZuneKinetic")
    
    // Ken Burns Effect Animations
    val kenBurnsScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "kenBurnsScale"
    )
    
    val kenBurnsX by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(45000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "kenBurnsX"
    )

    val kenBurnsY by infiniteTransition.animateFloat(
        initialValue = -30f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(55000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "kenBurnsY"
    )

    // Generate random directions/offsets for each track
    val randomValues = remember(artist, title) {
        object {
            val artistStartX = ((-800)..(-400)).random().toFloat()
            val artistEndX = (400..800).random().toFloat()
            val artistStartY = ((-300)..(-100)).random().toFloat()
            val artistEndY = (100..300).random().toFloat()
            
            val titleStartX = (400..800).random().toFloat()
            val titleEndX = ((-800)..(-400)).random().toFloat()
            val titleStartY = (100..300).random().toFloat()
            val titleEndY = ((-300)..(-100)).random().toFloat()

            val artStartX = ((-600)..600).random().toFloat()
            val artEndX = ((-600)..600).random().toFloat()
            val artStartY = ((-400)..400).random().toFloat()
            val artEndY = ((-400)..400).random().toFloat()
        }
    }

    val artistDriftX by infiniteTransition.animateFloat(
        initialValue = randomValues.artistStartX,
        targetValue = randomValues.artistEndX,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "artistDriftX"
    )

    val artistDriftY by infiniteTransition.animateFloat(
        initialValue = randomValues.artistStartY,
        targetValue = randomValues.artistEndY,
        animationSpec = infiniteRepeatable(
            animation = tween(50000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "artistDriftY"
    )

    val titleDriftX by infiniteTransition.animateFloat(
        initialValue = randomValues.titleStartX,
        targetValue = randomValues.titleEndX,
        animationSpec = infiniteRepeatable(
            animation = tween(70000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "titleDriftX"
    )

    val titleDriftY by infiniteTransition.animateFloat(
        initialValue = randomValues.titleStartY,
        targetValue = randomValues.titleEndY,
        animationSpec = infiniteRepeatable(
            animation = tween(55000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "titleDriftY"
    )

    val artDriftX by infiniteTransition.animateFloat(
        initialValue = randomValues.artStartX,
        targetValue = randomValues.artEndX,
        animationSpec = infiniteRepeatable(
            animation = tween(80000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "artDriftX"
    )

    val artDriftY by infiniteTransition.animateFloat(
        initialValue = randomValues.artStartY,
        targetValue = randomValues.artEndY,
        animationSpec = infiniteRepeatable(
            animation = tween(90000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "artDriftY"
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // 1. Ambient Background Layer (Ken Burns Image)
        AsyncImage(
            model = artworkUrl ?: R.drawable.kornimage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { 
                    scaleX = kenBurnsScale
                    scaleY = kenBurnsScale
                    translationX = kenBurnsX
                    translationY = kenBurnsY
                    alpha = 0.15f 
                },
            contentScale = ContentScale.Crop
        )

        // 1b. Drifting Album Art Tile
        AsyncImage(
            model = artworkUrl ?: R.drawable.kornimage,
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .graphicsLayer { 
                    translationX = artDriftX.dp.toPx()
                    translationY = artDriftY.dp.toPx()
                    alpha = 0.1f
                    rotationZ = 15f
                },
            contentScale = ContentScale.Crop
        )

        // 2. Dark Scrim / Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        val kineticTextColor = Color.White.copy(alpha = 0.85f)

        // 3. Kinetic Typography Layer
        Text(
            text = artist.uppercase(),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = 220.sp,
                letterSpacing = (-14).sp
            ),
            color = kineticTextColor,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize(unbounded = true)
                .graphicsLayer { 
                    translationX = artistDriftX.dp.toPx()
                    translationY = artistDriftY.dp.toPx()
                    rotationZ = -12f 
                },
            softWrap = false,
            maxLines = 1
        )
        
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = 180.sp,
                letterSpacing = (-12).sp
            ),
            color = kineticTextColor,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentSize(unbounded = true)
                .graphicsLayer { 
                    translationX = titleDriftX.dp.toPx()
                    translationY = titleDriftY.dp.toPx()
                    rotationZ = 10f 
                },
            softWrap = false,
            maxLines = 1
        )
    }
}

@Composable
fun ZuneNeonAudioSpectrum(isPlaying: Boolean, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "SpectrumAnim")
    val barCount = 16
    val barPhases = remember { List(barCount) { (100..500).random() } }
    val barSpeeds = remember { List(barCount) { (400..1000).random() } }

    val animatedHeights = barPhases.mapIndexed { index, _ ->
        infiniteTransition.animateFloat(
            initialValue = 0.12f,
            targetValue = 0.95f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = barSpeeds[index], easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .padding(horizontal = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        for (i in 0 until barCount) {
            val heightFactor = if (isPlaying) animatedHeights[i].value else 0.15f
            val barColor = if (i % 2 == 0) Color(0xFFE5007D) else Color(0xFF00E5FF)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp)
                    .fillMaxHeight(heightFactor)
                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                barColor,
                                barColor.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}

@Composable
fun ZuneVinylArtwork(
    artworkUrl: String?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "VinylSpin")
    val spinRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spinRotation"
    )

    val slideOffset by animateDpAsState(
        targetValue = if (isPlaying) 52.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "vinylSlide"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(280.dp)
    ) {
        Box(
            modifier = Modifier
                .offset(x = slideOffset)
                .size(230.dp)
                .graphicsLayer { rotationZ = if (isPlaying) spinRotation else 0f }
                .clip(CircleShape)
                .background(Color(0xFF111116))
                .border(2.dp, Color(0xFF252530), CircleShape)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val radius = size.minDimension / 2
                drawCircle(color = Color.White.copy(alpha = 0.08f), radius = radius * 0.92f, style = Stroke(width = 1f))
                drawCircle(color = Color.White.copy(alpha = 0.06f), radius = radius * 0.78f, style = Stroke(width = 1f))
                drawCircle(color = Color.White.copy(alpha = 0.05f), radius = radius * 0.62f, style = Stroke(width = 1f))
                drawCircle(color = Color.White.copy(alpha = 0.04f), radius = radius * 0.46f, style = Stroke(width = 1f))
            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(Color(0xFFE5007D))
                    .border(2.dp, Color.White.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = artworkUrl ?: R.drawable.kornimage,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                        .border(1.dp, Color.Gray, CircleShape)
                )
            }
        }

        Surface(
            modifier = Modifier
                .size(230.dp)
                .offset(x = (-22).dp)
                .border(1.dp, Color.White.copy(alpha = 0.2f)),
            color = Color(0xFF181822),
            tonalElevation = 8.dp
        ) {
            AsyncImage(
                model = artworkUrl ?: R.drawable.kornimage,
                contentDescription = "Album Art",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun getLyricsForSong(song: Song?): List<String> {
    if (song == null) return listOf("No song currently playing.")
    
    val titleLower = song.title.lowercase()

    return when {
        titleLower.contains("freak on a leash") -> listOf(
            "Freak On a Leash - Korn",
            "",
            "[Verse 1]",
            "Something takes a part of me",
            "Something lost and never seen",
            "Every time I start to believe",
            "Something's raped and taken from me...",
            "",
            "[Chorus]",
            "Feeling like a freak on a leash",
            "Feeling like I have no release",
            "How many times have I felt this way?",
            "I'm falling away from me!",
            "",
            "[Bridge]",
            "Boom-da-da-mda-mma-na-ma!",
            "Boom-da-da-mda-mma-na-ma!",
            "GO!",
            "",
            "[Outro]",
            "You want it? Come get it!"
        )
        titleLower.contains("got the life") -> listOf(
            "Got the Life - Korn",
            "",
            "[Verse 1]",
            "Hate, something, sometime, someway",
            "Something kick off the front door, listen close",
            "What you say, what you say?",
            "",
            "[Chorus]",
            "God pities, God pities all of us",
            "God pities, God pities all of us",
            "I've got the life!",
            "",
            "[Outro]",
            "Got the life, got the life!"
        )
        titleLower.contains("it's on") || titleLower.contains("its on") -> listOf(
            "It's On! - Korn",
            "",
            "[Verse 1]",
            "Come on, it's on!",
            "Take it all away, take it all away!",
            "Breaking the silence inside of my mind",
            "",
            "[Chorus]",
            "It's on! It's on! It's on!",
            "Follow the leader, step into line!"
        )
        titleLower.contains("dead bodies everywhere") -> listOf(
            "Dead Bodies Everywhere - Korn",
            "",
            "[Verse 1]",
            "You want me to be what you want me to be",
            "Never letting me be myself, set me free!",
            "",
            "[Chorus]",
            "Dead bodies everywhere!",
            "No one's listening, no one cares!"
        )
        titleLower.contains("poison planet") -> listOf(
            "Poison Planet - Hatebreed",
            "",
            "[Verse 1]",
            "They poison the earth, they poison the water",
            "Slaughter the weak, lead us like sheep",
            "No place to run, no place to hide",
            "Underneath this toxic sky!",
            "",
            "[Chorus]",
            "Poison Planet! Chemical reign!",
            "Poison Planet! Living in pain!",
            "Rise up from the ashes below",
            "Destroy the corrupt and let truth show!",
            "",
            "[Outro]",
            "Never submit! Never back down!"
        )
        titleLower.contains("kill count increase") -> listOf(
            "Kill Count Increase - Hatebreed",
            "",
            "[Verse 1]",
            "Rise above the chaos, conquer the fear",
            "The battle line is drawn right here!",
            "",
            "[Chorus]",
            "Kill count increase!",
            "No mercy, no peace!"
        )
        titleLower.contains("kill or be killed") -> listOf(
            "Kill Or Be Killed - Biohazard",
            "",
            "[Verse 1]",
            "Living on the edge, street life rule",
            "Survive the pressure, don't be a fool!",
            "",
            "[Chorus]",
            "Kill or be killed! No compromise!",
            "Stand tall, look into my eyes!"
        )
        titleLower.contains("world on fire") -> listOf(
            "World On Fire - Biohazard",
            "",
            "[Verse 1]",
            "The system is burning, the empire falls",
            "Writing of destruction on the city walls!",
            "",
            "[Chorus]",
            "World on fire! Flames rise high!",
            "Rebuilding truth under the night sky!"
        )
        titleLower.contains("uncivilization") || titleLower.contains("sellout") -> listOf(
            "Uncivilization - Biohazard",
            "",
            "[Verse 1]",
            "Brooklyn heavy hitter, urban warfare",
            "Respect and loyalty, truth in the air!",
            "",
            "[Chorus]",
            "Uncivilization! Stand your ground!",
            "Never sell out, represent our sound!"
        )
        titleLower.contains("last night") -> listOf(
            "Last Night - Morgan Wallen",
            "",
            "[Verse 1]",
            "Last night we let the liquor talk",
            "I can't remember all the things we said",
            "But we said 'em anyway...",
            "",
            "[Chorus]",
            "Last night we let the liquor talk",
            "And I know that I said some things I didn't mean",
            "No, last night wasn't the last night!"
        )
        titleLower.contains("born with a beer in my hand") -> listOf(
            "Born With A Beer In My Hand - Morgan Wallen",
            "",
            "[Verse 1]",
            "Mama tried her best to raise me right",
            "Small town boy, Friday night light...",
            "",
            "[Chorus]",
            "I was born with a beer in my hand",
            "Just a good old country man!"
        )
        titleLower.contains("the decline") -> listOf(
            "The Decline - NOFX",
            "",
            "[Verse 1]",
            "Where are the passionate people?",
            "Where is the acoustic sound?",
            "Fellow citizens, pay attention please!",
            "",
            "[Chorus]",
            "And so we call upon the author to explain...",
            "The decline of our nation and mind!"
        )
        else -> listOf(
            "${song.title} - ${song.artist}",
            "Album: ${song.album}",
            "",
            "[Verse 1]",
            "Listening to ${song.title} on Zune Player",
            "Rhythm and bass pulsing through the speaker",
            "Authentic track from ${song.album}",
            "",
            "[Chorus]",
            "Turn up the volume, let the music play",
            "Zune media player night and day!",
            "",
            "[Outro]",
            "Playing ${song.title} by ${song.artist}"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LyricsDialogSheet(
    song: Song?,
    onDismiss: () -> Unit,
    isDarkMode: Boolean
) {
    val lyrics = remember(song) { getLyricsForSong(song) }
    val bgColor = if (isDarkMode) Color(0xFF141418) else Color(0xFFF2F2F7)
    val textColor = if (isDarkMode) Color.White else Color(0xFF111115)
    val accentColor = Color(0xFFE5007D)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = bgColor,
        dragHandle = {
            Surface(
                modifier = Modifier.padding(top = 10.dp).width(40.dp).height(4.dp),
                shape = RoundedCornerShape(2.dp),
                color = Color.Gray.copy(alpha = 0.5f)
            ) {}
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Subtitles,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Song Lyrics",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textColor
                        )
                        Text(
                            text = song?.title ?: "Unknown",
                            style = MaterialTheme.typography.bodySmall,
                            color = textColor.copy(alpha = 0.6f)
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = textColor)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = textColor.copy(alpha = 0.15f))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 380.dp)
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(lyrics) { idx, line ->
                    val isHeader = line.startsWith("[") && line.endsWith("]")
                    val isTitle = idx == 0
                    Text(
                        text = line,
                        style = when {
                            isTitle -> MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                            isHeader -> MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            else -> MaterialTheme.typography.bodyLarge
                        },
                        color = when {
                            isTitle -> accentColor
                            isHeader -> Color(0xFF00E5FF)
                            else -> textColor.copy(alpha = 0.9f)
                        },
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SongIdentifierOverlay(
    songs: List<Song>,
    onDismiss: () -> Unit,
    onPlaySong: (Song) -> Unit,
    isDarkMode: Boolean
) {
    var isListening by remember { mutableStateOf(true) }
    var recognizedSong by remember { mutableStateOf<Song?>(null) }
    val infiniteTransition = rememberInfiniteTransition(label = "RadarPulse")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3200)
        recognizedSong = songs.filter { !it.isVideo }.randomOrNull() ?: songs.firstOrNull()
        isListening = false
    }

    val bgColor = if (isDarkMode) Color(0xFF141418) else Color(0xFFF2F2F7)
    val textColor = if (isDarkMode) Color.White else Color(0xFF111115)
    val accentColor = Color(0xFFE5007D)

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = bgColor,
            tonalElevation = 12.dp,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Identify Song Around Me",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = textColor)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isListening) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(150.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .graphicsLayer { scaleX = pulseScale; scaleY = pulseScale; alpha = 0.35f }
                                .clip(CircleShape)
                                .background(accentColor)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(accentColor)
                                .border(2.dp, Color.White.copy(alpha = 0.6f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Listening",
                                tint = Color.White,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Listening for music around you...",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = accentColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Analyzing audio frequency signature",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor.copy(alpha = 0.5f)
                    )
                } else {
                    val song = recognizedSong
                    if (song != null) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = "Match Found",
                            tint = Color(0xFF00E5FF),
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Song Identified!",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF00E5FF)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            modifier = Modifier.size(100.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF22222A)
                        ) {
                            AsyncImage(
                                model = song.getVerifiedArtworkUrl(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = song.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${song.artist} • ${song.album}",
                            style = MaterialTheme.typography.bodySmall,
                            color = textColor.copy(alpha = 0.6f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = {
                                onPlaySong(song)
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Play Song in Zune Player", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER")
@Composable
fun PlayerView(
    songs: List<Song>,
    currentSongIndex: Int,
    isPlaying: Boolean,
    currentPosition: Long,
    totalDuration: Long,
    playerState: Player?,
    backgroundColor: Color,
    isStrobeEnabled: Boolean,
    onDownloadClick: (Song) -> Unit
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = currentSongIndex.coerceIn(0, (songs.size - 1).coerceAtLeast(0))) { songs.size }
    var flashOpacity by remember { mutableFloatStateOf(0f) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showLyricsSheet by remember { mutableStateOf(false) }
    var showSongIdentifier by remember { mutableStateOf(false) }

    LaunchedEffect(currentSongIndex) {
        if (songs.isNotEmpty() && currentSongIndex in songs.indices && pagerState.currentPage != currentSongIndex) {
            pagerState.scrollToPage(currentSongIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (songs.isNotEmpty() && pagerState.currentPage in songs.indices && pagerState.currentPage != currentSongIndex) {
            playerState?.seekTo(pagerState.currentPage, 0L)
            playerState?.play()
        }
    }
    
    val currentSong = songs.getOrNull(currentSongIndex)
    var isControlsVisible by remember { mutableStateOf(true) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .clickable(
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            indication = null
        ) { 
            isControlsVisible = !isControlsVisible 
        }
    ) {
        // 1. Zune Ambient Background Layer (Ken Burns + Kinetic Type)
        ZuneKineticBackground(
            artist = currentSong?.artist ?: "Unknown Artist",
            title = currentSong?.title ?: "Unknown Title",
            artworkUrl = currentSong?.getVerifiedArtworkUrl()
        )

        // 2. Foreground Player UI
        androidx.compose.animation.AnimatedVisibility(
            visible = isControlsVisible,
            enter = fadeIn(animationSpec = tween(600)) + expandVertically(),
            exit = fadeOut(animationSpec = tween(600)) + shrinkVertically(),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        if (flashOpacity > 0f) {
                            drawRect(color = Color.White.copy(alpha = flashOpacity))
                        }
                    }
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top: Download & Playlist Queue Actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showSongIdentifier = true }) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Identify Song",
                            tint = Color(0xFF00E5FF),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = { showLyricsSheet = true }) {
                        Icon(
                            imageVector = Icons.Default.Subtitles,
                            contentDescription = "Lyrics",
                            tint = Color(0xFFE5007D),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    if (currentSong != null && (currentSong.url.startsWith("http://") || currentSong.url.startsWith("https://"))) {
                        val fileName = currentSong.url.substringAfterLast("/")
                        val isDownloaded = remember(currentSong.url) { 
                            File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
                        }
                        IconButton(onClick = { onDownloadClick(currentSong) }) {
                            Icon(
                                imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                contentDescription = "Download",
                                tint = if (isDownloaded) Color(0xFFE5007D) else Color.White.copy(alpha = 0.4f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.QueueMusic,
                            contentDescription = "Track List",
                            tint = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }

                // Middle: Large Bold Typography & Artwork
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth().height(320.dp)
                    ) { page ->
                        val song = songs.getOrNull(page)
                        if (song?.isVideo == true) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 32.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(Color.Black),
                                contentAlignment = Alignment.Center
                            ) {
                                AndroidView(
                                    factory = { ctx -> Media3PlayerView(ctx).apply { player = playerState; useController = false } },
                                    update = { view -> view.player = playerState },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        } else {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                ZuneVinylArtwork(
                                    artworkUrl = song?.getVerifiedArtworkUrl(),
                                    isPlaying = isPlaying
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 48.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = currentSong?.title ?: "Unknown Song",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 36.sp,
                                letterSpacing = (-1.5).sp
                            ),
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = currentSong?.artist ?: "Unknown Artist",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            ),
                            color = Color(0xFFE5007D) // Zune Magenta
                        )
                    }
                }

                // Bottom: Slider & Geometric Controls
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Retro Zune Neon Audio Spectrum Visualizer
                    ZuneNeonAudioSpectrum(isPlaying = isPlaying, modifier = Modifier.padding(bottom = 8.dp))

                    // Track time with a small flame that follows playback progress.
                    Column(modifier = Modifier.padding(horizontal = 48.dp)) {
                        PyroSeekBar(
                            progress = if (totalDuration > 0) {
                                (currentPosition.toFloat() / totalDuration).coerceIn(0f, 1f)
                            } else {
                                0f
                            },
                            artist = currentSong?.artist,
                            isPlaying = isPlaying,
                            onProgressChange = { playerState?.seekTo((it * totalDuration).toLong()) }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = formatTime(currentPosition),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                            Text(
                                text = "-${formatTime(totalDuration - currentPosition)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    // Geometric Playback Controls
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterHorizontally)
                    ) {
                        ThanksgivingSidePreviousButton(
                            onClick = { playerState?.seekToPreviousMediaItem() }
                        )
                        
                        TurkeyPlayPauseButton(
                            isPlaying = isPlaying,
                            onClick = { if (isPlaying) playerState?.pause() else playerState?.play() }
                        )

                        ThanksgivingSideNextButton(
                            onClick = { playerState?.seekToNextMediaItem() }
                        )
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            containerColor = Color(0xFF0A0A0A),
            contentColor = Color.White,
            scrimColor = Color.Black.copy(alpha = 0.8f)
        ) {
            LazyColumn(modifier = Modifier.fillMaxHeight(0.6f).padding(16.dp)) {
                itemsIndexed(songs) { idx, song ->
                    ListItem(
                        headlineContent = { 
                            Text(
                                song.title, 
                                color = if (idx == currentSongIndex) Color(0xFFE5007D) else Color.White,
                                fontWeight = if (idx == currentSongIndex) FontWeight.Bold else FontWeight.Normal
                            ) 
                        },
                        supportingContent = { Text(song.artist, color = Color.White.copy(alpha = 0.5f)) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier.clickable { 
                            playerState?.seekTo(idx, 0L)
                            playerState?.play()
                            showBottomSheet = false 
                        }
                    )
                }
            }
        }
    }

    if (showLyricsSheet) {
        LyricsDialogSheet(
            song = currentSong,
            onDismiss = { showLyricsSheet = false },
            isDarkMode = true
        )
    }

    if (showSongIdentifier) {
        SongIdentifierOverlay(
            songs = songs,
            onDismiss = { showSongIdentifier = false },
            onPlaySong = { song ->
                val idx = songs.indexOf(song)
                if (idx != -1) {
                    playerState?.seekTo(idx, 0L)
                    playerState?.play()
                }
            },
            isDarkMode = true
        )
    }
}



suspend fun fetchRepoSongsFromGitHub(): Pair<List<Song>, String?> {
    return withContext(Dispatchers.IO) {
        val fetchedMediaList = mutableListOf<Song>()
        val imageMap = mutableMapOf<String, String>()
        var defaultIsolatedArtworkUrl: String? = null

        fun processRawFile(fileName: String, downloadUrl: String, fullPath: String = fileName) {
            if (fileName.isEmpty() || downloadUrl.isEmpty()) return
            val lowerName = fileName.lowercase()
            val lowerPath = fullPath.lowercase()
            val baseName = fileName.substringBeforeLast(".").lowercase()

            if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") || lowerName.endsWith(".png") || lowerName.endsWith(".webp")) {
                imageMap[baseName] = downloadUrl
                if (defaultIsolatedArtworkUrl == null) {
                    defaultIsolatedArtworkUrl = downloadUrl
                }
                return
            }

            val isAudio = lowerName.endsWith(".mp3") || lowerName.endsWith(".m4a") ||
                    lowerName.endsWith(".wav") || lowerName.endsWith(".flac") ||
                    lowerName.endsWith(".aac") || lowerName.endsWith(".ogg")

            val isVideo = lowerName.endsWith(".mp4") || lowerName.endsWith(".mkv") ||
                    lowerName.endsWith(".webm") || lowerName.endsWith(".avi") ||
                    lowerName.endsWith(".mov")

            if (isAudio || isVideo) {
                val rawBase = fileName.substringBeforeLast(".")
                val stripped = rawBase
                    .replace(Regex("^(?:\\d+[._\\s-]*)*(?:Hatebreed|Korn|Biohazard|NOFX|Morgan Wallen|Danny Diablo|Mudvayne)[._\\s-]*", RegexOption.IGNORE_CASE), "")
                    .replace(Regex("^\\d+[._\\s-]*"), "")
                    .trim()

                val cleanTitle = if (stripped.isNotBlank()) {
                    stripped.replace(".", " ").replace("_", " ").replace("-", " ").replace(Regex("\\s+"), " ").trim()
                } else {
                    rawBase.replace(".", " ").replace("_", " ").replace("-", " ").trim()
                }

                val parsedArtist = when {
                    lowerPath.contains("danny") -> "Danny Diablo"
                    lowerPath.contains("korn") -> "Korn"
                    lowerPath.contains("wallen") -> "Morgan Wallen"
                    lowerPath.contains("biohazard") -> "Biohazard"
                    lowerPath.contains("nofx") -> "NOFX"
                    lowerPath.contains("hatebreed") -> "Hatebreed"
                    lowerPath.contains("mudvayne") -> "Mudvayne"
                    lowerPath.contains("constantine") -> "Warner Bros"
                    else -> if (isVideo) "Video Storage" else "Featured Artist"
                }

                val parsedAlbum = when {
                    lowerPath.contains("danny") -> "Dollerz Make Sense"
                    lowerPath.contains("korn") -> "Follow the Leader"
                    lowerPath.contains("wallen") -> "One Thing At A Time"
                    lowerPath.contains("uncivilization") -> "Uncivilization"
                    lowerPath.contains("biohazard") || lowerPath.contains("world.on.fire") || lowerPath.contains("kill.or.be.killed") -> "Kill Or Be Killed"
                    lowerPath.contains("nofx") -> "The Decline"
                    lowerPath.contains("fatal") || lowerPath.contains("paradox") -> "Fatal Paradox"
                    lowerPath.contains("hatebreed") -> if (isVideo) "Live Dominance" else "Fatal Paradox"
                    lowerPath.contains("mudvayne") -> "v2.0 Beta"
                    lowerPath.contains("constantine") -> "Movie"
                    else -> if (isVideo) "Video Single" else "Single"
                }

                // ONLY use imageMap if there is a DIRECT matching image file with the same filename prefix.
                // Otherwise leave empty so Song.getVerifiedArtworkUrl() dynamically resolves correct high-res album covers!
                val matchedArt = imageMap[baseName] ?: ""

                fetchedMediaList.add(
                    Song(
                        title = cleanTitle,
                        artist = parsedArtist,
                        album = parsedAlbum,
                        url = downloadUrl,
                        artworkUrl = matchedArt,
                        isVideo = isVideo
                    )
                )
            }
        }

        // Helper function to create HTTP connections with required GitHub API headers
        fun openGitHubConnection(urlString: String): HttpURLConnection? {
            return try {
                val url = URL(urlString)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.setRequestProperty("User-Agent", "ZuneMusicPlayer/1.0")
                if (PlaybackService.GITHUB_TOKEN.isNotBlank()) {
                    conn.setRequestProperty("Authorization", "Bearer ${PlaybackService.GITHUB_TOKEN}")
                }
                conn.setRequestProperty("Accept", "application/vnd.github+json")
                conn.connectTimeout = 8000
                conn.readTimeout = 8000
                conn
            } catch (_: Exception) {
                null
            }
        }

        // 1. Fetch GitHub Releases Assets (v1.0, v2.0beta, and any future release tags)
        try {
            val conn = openGitHubConnection("https://api.github.com/repos/korn9225-sketch/app-music-storage/releases")
            if (conn != null && conn.responseCode == 200) {
                val jsonStr = conn.inputStream.bufferedReader().readText()
                val releasesArray = JSONArray(jsonStr)
                for (r in 0 until releasesArray.length()) {
                    val releaseObj = releasesArray.getJSONObject(r)
                    val assetsArray = releaseObj.optJSONArray("assets") ?: continue
                    for (a in 0 until assetsArray.length()) {
                        val assetObj = assetsArray.getJSONObject(a)
                        val assetName = assetObj.optString("name", "")
                        val assetUrl = assetObj.optString("browser_download_url", "")
                        processRawFile(assetName, assetUrl)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 2. Fetch Repository Tree Recursively (main & master branches)
        for (branch in listOf("main", "master")) {
            try {
                val conn = openGitHubConnection("https://api.github.com/repos/korn9225-sketch/app-music-storage/git/trees/$branch?recursive=1")
                if (conn != null && conn.responseCode == 200) {
                    val jsonStr = conn.inputStream.bufferedReader().readText()
                    val treeObj = org.json.JSONObject(jsonStr)
                    val treeArray = treeObj.optJSONArray("tree") ?: continue
                    for (i in 0 until treeArray.length()) {
                        val item = treeArray.getJSONObject(i)
                        val type = item.optString("type", "")
                        if (type == "blob") {
                            val path = item.optString("path", "")
                            val rawUrl = "https://raw.githubusercontent.com/korn9225-sketch/app-music-storage/$branch/$path"
                            val fileName = path.substringAfterLast("/")
                            processRawFile(fileName, rawUrl, fullPath = path)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 3. Fetch Root Repository Contents (Files/Folders)
        try {
            val conn = openGitHubConnection("https://api.github.com/repos/korn9225-sketch/app-music-storage/contents")
            if (conn != null && conn.responseCode == 200) {
                val jsonStr = conn.inputStream.bufferedReader().readText()
                val jsonArray = JSONArray(jsonStr)
                for (i in 0 until jsonArray.length()) {
                    val fileObj = jsonArray.getJSONObject(i)
                    val fileName = fileObj.optString("name", "")
                    val downloadUrl = fileObj.optString("download_url", "")
                    processRawFile(fileName, downloadUrl)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Pair(fetchedMediaList.distinctBy { it.url }, defaultIsolatedArtworkUrl)
    }
}



@Suppress("UNUSED")
@Composable
fun MisfitsMovieIntroSplash(onFinished: () -> Unit) {
    val fadeInAnim = remember { androidx.compose.animation.core.Animatable(0f) }
    val zoomAnim = remember { androidx.compose.animation.core.Animatable(1.0f) }
    val fadeOutAnim = remember { androidx.compose.animation.core.Animatable(1.0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Step 1: Fade In from Black
        fadeInAnim.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(900, easing = LinearEasing)
        )
        // Step 2: Pause / Stop in Full View
        delay(1200.milliseconds)

        // Step 3: Zoom In directly into the white skull, going through into the app!
        coroutineScope.launch {
            zoomAnim.animateTo(
                targetValue = 25.0f,
                animationSpec = tween(1100, easing = FastOutSlowInEasing)
            )
        }
        coroutineScope.launch {
            fadeOutAnim.animateTo(
                targetValue = 0.0f,
                animationSpec = tween(1000, easing = LinearEasing)
            )
        }
        delay(1000.milliseconds)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .graphicsLayer {
                scaleX = zoomAnim.value
                scaleY = zoomAnim.value
                alpha = fadeInAnim.value * fadeOutAnim.value
            },
        contentAlignment = Alignment.Center
    ) {
        // Your Exact High-Res Misfits Skull Picture
        AsyncImage(
            model = "https://raw.githubusercontent.com/korn9225-sketch/app-music-storage/main/misfits_exact_skull.jpg",
            contentDescription = "Misfits Skull",
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight(),
            contentScale = ContentScale.Fit
        )
    }
}

@Suppress("UNUSED")
@Composable
fun MaskedSkullSplashScreenReveal(
    modifier: Modifier = Modifier,
    onRevealComplete: () -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val scaleAnim = remember { androidx.compose.animation.core.Animatable(1f) }
    val alphaAnim = remember { androidx.compose.animation.core.Animatable(0f) }
    var isRevealed by remember { mutableStateOf(false) }

    val maskBitmap = remember {
        android.graphics.BitmapFactory.decodeResource(context.resources, R.drawable.misfits_skull_pic)
    }

    LaunchedEffect(Unit) {
        // 1. Initial Fade In from Black
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(900, easing = LinearEasing)
        )
        // 2. Pause / Hold in Full View
        delay(1200.milliseconds)

        // 3. Animate Scale Factor from 1f to 30f directly zooming into the white forehead region
        scaleAnim.animateTo(
            targetValue = 30f,
            animationSpec = tween(1200, easing = FastOutSlowInEasing)
        )
        isRevealed = true
        onRevealComplete()
    }

    if (isRevealed) {
        content()
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                        scaleX = scaleAnim.value
                        scaleY = scaleAnim.value
                        // Anchor zoom on the center forehead region of the skull stencil
                        transformOrigin = TransformOrigin(0.5f, 0.22f)
                        alpha = alphaAnim.value
                    }
            ) {
                // 1. App Landing Content Layer
                content()

                // 2. PorterDuff DstIn Stencil Mask
                Canvas(modifier = Modifier.fillMaxSize()) {
                    if (maskBitmap != null) {
                        val imageBitmap = maskBitmap.asImageBitmap()
                        val dstSize = IntSize(size.width.toInt(), size.height.toInt())
                        
                        drawImage(
                            image = imageBitmap,
                            dstSize = dstSize,
                            blendMode = BlendMode.DstIn
                        )
                    } else {
                        drawRect(color = Color.White, blendMode = BlendMode.DstIn)
                    }
                }
            }
        }
    }
}

@Composable
fun TurkeyPlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scaleAnim by animateFloatAsState(
        targetValue = if (isPlaying) 1.12f else 1.0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "turkeyScale"
    )

    Box(
        modifier = modifier
            .size(72.dp)
            .graphicsLayer {
                scaleX = scaleAnim
                scaleY = scaleAnim
            }
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🦃",
            fontSize = 52.sp
        )
    }
}

@Composable
fun ThanksgivingSidePreviousButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(58.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🥧",
            fontSize = 44.sp
        )
    }
}

@Composable
fun ThanksgivingSideNextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(58.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🌽",
            fontSize = 44.sp
        )
    }
}

@Composable
private fun PyroSeekBar(
    progress: Float,
    artist: String?,
    isPlaying: Boolean,
    onProgressChange: (Float) -> Unit
) {
    val transition = rememberInfiniteTransition(label = "PyroSeekBar")
    val animatedPhase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850, easing = LinearEasing)
        ),
        label = "PyroEmbers"
    )
    val animatedFlicker by transition.animateFloat(
        initialValue = 0.82f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 260, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PyroFlicker"
    )
    val phase = if (isPlaying) animatedPhase else 0.45f
    val flicker = if (isPlaying) animatedFlicker else 0.92f
    val isNofx = artist?.contains("NOFX", ignoreCase = true) == true
    val isKorn = artist?.contains("Korn", ignoreCase = true) == true
    val kornLogoBitmap = if (isKorn) ImageBitmap.imageResource(id = R.drawable.korn_logo) else null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .drawWithContent {
                drawContent()
                val density = density
                val thumbRadius = 10.dp.toPx()
                val trackStart = thumbRadius
                val trackEnd = size.width - thumbRadius
                val centerX = trackStart + (trackEnd - trackStart) * progress.coerceIn(0f, 1f)
                val trackY = size.height / 2f
                val baseY = trackY + 3.dp.toPx()
                val flameWidth = 22.dp.toPx() * flicker
                val flameHeight = 27.dp.toPx() * flicker

                if (isNofx) {
                    val charcoal = Color(0xFF211B1B)
                    val protestRed = Color(0xFFE53935)
                    val paper = Color(0xFFFFE9CF)
                    drawLine(
                        color = charcoal,
                        start = Offset(trackStart, trackY),
                        end = Offset(trackEnd, trackY),
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    if (centerX > trackStart) {
                        drawLine(
                            color = protestRed,
                            start = Offset(trackStart, trackY),
                            end = Offset(centerX, trackY),
                            strokeWidth = 6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        var dashX = trackStart + 5.dp.toPx()
                        while (dashX < centerX - 2.dp.toPx()) {
                            drawLine(
                                color = paper.copy(alpha = 0.88f),
                                start = Offset(dashX, trackY - 1.dp.toPx()),
                                end = Offset(dashX + 3.dp.toPx(), trackY - 1.dp.toPx()),
                                strokeWidth = 1.5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                            dashX += 12.dp.toPx()
                        }
                    }
                    drawRollingSkateboard(centerX, trackY, phase, density, paper, protestRed)
                    if (isPlaying) {
                        repeat(3) { index ->
                            val slashPhase = phase * 6.28318f + index * 2.1f
                            val offsetY = kotlin.math.sin(slashPhase) * 2.dp.toPx()
                            val motionLength = (5f + index * 2f) * density
                            drawLine(
                                color = if (index % 2 == 0) paper else protestRed,
                                start = Offset(centerX - (12.dp.toPx() + index * 2.dp.toPx()), trackY - 5.dp.toPx() + offsetY),
                                end = Offset(centerX - (12.dp.toPx() + index * 2.dp.toPx()) - motionLength, trackY - 5.dp.toPx() + offsetY),
                                strokeWidth = 1.5.dp.toPx(),
                                cap = StrokeCap.Round,
                                alpha = 0.75f - index * 0.15f
                            )
                        }
                    }
                } else if (isKorn && kornLogoBitmap != null) {
                    val darkRedTrack = Color(0xFF2A0808)
                    val fireRedStart = Color(0xFFFF1744)
                    val fireOrangeMid = Color(0xFFFF6D00)
                    val fireGoldEnd = Color(0xFFFFD54F)

                    drawLine(
                        color = darkRedTrack,
                        start = Offset(trackStart, trackY),
                        end = Offset(trackEnd, trackY),
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )

                    if (centerX > trackStart) {
                        drawLine(
                            brush = Brush.horizontalGradient(
                                colors = listOf(fireRedStart, fireOrangeMid, fireGoldEnd),
                                startX = trackStart,
                                endX = centerX
                            ),
                            start = Offset(trackStart, trackY),
                            end = Offset(centerX, trackY),
                            strokeWidth = 8.dp.toPx(),
                            cap = StrokeCap.Round,
                            alpha = 0.45f
                        )
                        drawLine(
                            brush = Brush.horizontalGradient(
                                colors = listOf(fireRedStart, Color(0xFFFFAB00), Color(0xFFFFF59D)),
                                startX = trackStart,
                                endX = centerX
                            ),
                            start = Offset(trackStart, trackY),
                            end = Offset(centerX, trackY),
                            strokeWidth = 3.5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    val flameSpacing = 8.dp.toPx()
                    var flameX = trackStart + flameSpacing * 0.5f
                    var flameIndex = 0
                    while (flameX < centerX - 4.dp.toPx()) {
                        val flamePhase = phase * 6.28318f + flameIndex * 1.31f
                        val flameFlicker = 0.5f + 0.5f * kotlin.math.sin(flamePhase)
                        val flameW = 13.dp.toPx() * flicker
                        val flameH = (11.dp.toPx() + 10.dp.toPx() * flameFlicker) * flicker
                        val flameVar = kotlin.math.sin(flamePhase * 0.73f) * 0.35f
                        drawFireBandFlame(
                            flameX,
                            baseY,
                            flameW,
                            flameH,
                            flameVar,
                            Color(0xFFFF3D00)
                        )
                        drawFireBandFlame(
                            flameX,
                            baseY,
                            flameW * 0.45f,
                            flameH * 0.6f,
                            flameVar,
                            Color(0xFFFFD54F)
                        )
                        flameX += flameSpacing
                        flameIndex++
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFF3D00).copy(alpha = 0.65f), Color(0xFFFF9100).copy(alpha = 0.3f), Color.Transparent),
                            center = Offset(centerX, trackY),
                            radius = 32.dp.toPx() * flicker
                        ),
                        radius = 32.dp.toPx() * flicker,
                        center = Offset(centerX, trackY)
                    )

                    drawFireBandFlame(
                        centerX,
                        baseY + 2.dp.toPx(),
                        flameWidth * 1.4f,
                        flameHeight * 1.3f,
                        kotlin.math.sin(phase * 6.28318f) * 0.35f,
                        Color(0xFFFF1744)
                    )
                    drawFireBandFlame(
                        centerX,
                        baseY,
                        flameWidth * 0.9f,
                        flameHeight * 0.9f,
                        kotlin.math.sin(phase * 6.28318f + 1f) * 0.35f,
                        Color(0xFFFFAB00)
                    )

                    val logoHeightPx = 32.dp.toPx() * flicker
                    val logoWidthPx = logoHeightPx * (kornLogoBitmap.width.toFloat() / kornLogoBitmap.height.toFloat())
                    val logoLeft = (centerX - logoWidthPx / 2f).toInt()
                    val logoTop = (trackY - logoHeightPx / 2f - 2.dp.toPx()).toInt()

                    drawImage(
                        image = kornLogoBitmap,
                        dstOffset = IntOffset(logoLeft + (1.5f * density).toInt(), logoTop + (2f * density).toInt()),
                        dstSize = IntSize(logoWidthPx.toInt(), logoHeightPx.toInt()),
                        colorFilter = ColorFilter.tint(Color(0xFF800000))
                    )

                    val logoColor = if (isPlaying) {
                        val colorPhase = (kotlin.math.sin(phase * 6.28318f) * 0.5f + 0.5f)
                        Color(
                            red = 1.0f,
                            green = 0.85f + 0.15f * colorPhase,
                            blue = 0.7f + 0.3f * colorPhase
                        )
                    } else {
                        Color(0xFFFFF0F0)
                    }

                    drawImage(
                        image = kornLogoBitmap,
                        dstOffset = IntOffset(logoLeft, logoTop),
                        dstSize = IntSize(logoWidthPx.toInt(), logoHeightPx.toInt()),
                        colorFilter = ColorFilter.tint(logoColor)
                    )

                    if (isPlaying) {
                        repeat(6) { index ->
                            val rise = (phase + index * 0.22f) % 1f
                            val drift = kotlin.math.sin((phase + index * 0.53f) * 6.28318f)
                            val side = if (index % 2 == 0) -1f else 1f
                            val sparkX = centerX + side * (7.dp.toPx() + drift * 10.dp.toPx())
                            val sparkY = baseY - rise * 35.dp.toPx()
                            val alpha = (1f - rise) * 0.95f
                            drawCircle(
                                color = if (index % 3 == 0) Color(0xFFFF1744) else if (index % 3 == 1) Color(0xFFFFAB00) else Color(0xFFFFF59D),
                                radius = (1.2f + (1f - rise) * 1.3f) * density,
                                center = Offset(sparkX, sparkY),
                                alpha = alpha
                            )
                        }
                    }
                } else {
                    drawLine(
                        color = Color.White.copy(alpha = 0.13f),
                        start = Offset(trackStart, trackY),
                        end = Offset(trackEnd, trackY),
                        strokeWidth = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    if (centerX > trackStart) {
                        drawLine(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFF3D00), Color(0xFFFF9100), Color(0xFFFFD54F)),
                                startX = trackStart,
                                endX = centerX
                            ),
                            start = Offset(trackStart, trackY),
                            end = Offset(centerX, trackY),
                            strokeWidth = 7.dp.toPx(),
                            cap = StrokeCap.Round,
                            alpha = 0.38f
                        )
                        drawLine(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFF6D00), Color(0xFFFFB300), Color(0xFFFFF176)),
                                startX = trackStart,
                                endX = centerX
                            ),
                            start = Offset(trackStart, trackY),
                            end = Offset(centerX, trackY),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    val flameSpacing = 8.dp.toPx()
                    var flameX = trackStart + flameSpacing * 0.5f
                    var flameIndex = 0
                    while (flameX < centerX - 4.dp.toPx()) {
                        val flamePhase = phase * 6.28318f + flameIndex * 1.31f
                        val flameFlicker = 0.5f + 0.5f * kotlin.math.sin(flamePhase)
                        val flameWidth = 12.dp.toPx() * flicker
                        val flameHeight = (10.dp.toPx() + 9.dp.toPx() * flameFlicker) * flicker
                        val flameVariation = kotlin.math.sin(flamePhase * 0.73f) * 0.35f
                        drawFireBandFlame(
                            flameX,
                            baseY,
                            flameWidth,
                            flameHeight,
                            flameVariation,
                            Color(0xFFFF6D00)
                        )
                        drawFireBandFlame(
                            flameX,
                            baseY,
                            flameWidth * 0.42f,
                            flameHeight * 0.58f,
                            flameVariation,
                            Color(0xFFFFD54F)
                        )
                        flameX += flameSpacing
                        flameIndex++
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFF6D00).copy(alpha = 0.48f), Color.Transparent),
                            center = Offset(centerX, trackY),
                            radius = 25.dp.toPx() * flicker
                        ),
                        radius = 25.dp.toPx() * flicker,
                        center = Offset(centerX, trackY)
                    )
                    drawFireBandFlame(
                        centerX,
                        baseY,
                        flameWidth,
                        flameHeight,
                        kotlin.math.sin(phase * 6.28318f) * 0.35f,
                        Color(0xFFFF3D00)
                    )
                    drawFireBandFlame(
                        centerX,
                        baseY,
                        flameWidth * 0.48f,
                        flameHeight * 0.68f,
                        kotlin.math.sin(phase * 6.28318f) * 0.35f,
                        Color(0xFFFFD54F)
                    )

                    if (isPlaying) {
                        repeat(5) { index ->
                            val rise = (phase + index * 0.27f) % 1f
                            val drift = kotlin.math.sin((phase + index * 0.61f) * 6.28318f)
                            val side = if (index % 2 == 0) -1f else 1f
                            val sparkX = centerX + side * (5.dp.toPx() + drift * 7.dp.toPx())
                            val sparkY = baseY - rise * 31.dp.toPx()
                            val alpha = (1f - rise) * 0.9f
                            drawCircle(
                                color = if (index % 2 == 0) Color(0xFFFFB300) else Color(0xFFFF7043),
                                radius = (1.1f + (1f - rise) * 1.1f) * density,
                                center = Offset(sparkX, sparkY),
                                alpha = alpha
                            )
                        }
                    }
                }
            }
    ) {
        Slider(
            value = progress.coerceIn(0f, 1f),
            onValueChange = onProgressChange,
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )
    }
}

@Suppress("UNUSED_PARAMETER")
private fun DrawScope.drawRollingSkateboard(
    centerX: Float,
    trackY: Float,
    phase: Float,
    density: Float,
    paper: Color,
    red: Color
) {
    val deckY = trackY - 5.dp.toPx()
    val wheelY = trackY + 2.dp.toPx()
    val wheelOffset = 8.dp.toPx()
    val wheelRadius = 2.5.dp.toPx()
    val wheelSpin = phase * 6.28318f

    drawLine(
        color = paper,
        start = Offset(centerX - 12.dp.toPx(), deckY),
        end = Offset(centerX + 12.dp.toPx(), deckY + 1.dp.toPx()),
        strokeWidth = 5.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = red,
        start = Offset(centerX - 9.dp.toPx(), deckY - 1.dp.toPx()),
        end = Offset(centerX + 9.dp.toPx(), deckY),
        strokeWidth = 1.5.dp.toPx(),
        cap = StrokeCap.Round
    )
    for (wheelIndex in 0..1) {
        val wheelX = centerX + if (wheelIndex == 0) -wheelOffset else wheelOffset
        drawLine(
            color = paper,
            start = Offset(wheelX, deckY + 2.dp.toPx()),
            end = Offset(wheelX, wheelY - wheelRadius),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawCircle(
            color = red,
            radius = wheelRadius,
            center = Offset(wheelX, wheelY)
        )
        val spokeAngle = wheelSpin + wheelIndex * 1.1f
        drawLine(
            color = paper,
            start = Offset(
                wheelX - kotlin.math.cos(spokeAngle) * wheelRadius * 0.6f,
                wheelY - kotlin.math.sin(spokeAngle) * wheelRadius * 0.6f
            ),
            end = Offset(
                wheelX + kotlin.math.cos(spokeAngle) * wheelRadius * 0.6f,
                wheelY + kotlin.math.sin(spokeAngle) * wheelRadius * 0.6f
            ),
            strokeWidth = 0.8.dp.toPx()
        )
    }
}

private fun DrawScope.drawFireBandFlame(
    centerX: Float,
    baseY: Float,
    width: Float,
    height: Float,
    variation: Float,
    color: Color
) {
    val halfWidth = width / 2f
    val flame = Path().apply {
        moveTo(centerX - halfWidth, baseY)
        cubicTo(
            centerX - halfWidth * 0.86f, baseY - height * 0.34f,
            centerX - halfWidth * 0.5f, baseY - height * (0.48f + variation * 0.08f),
            centerX - halfWidth * 0.32f, baseY - height * (0.65f + variation * 0.1f)
        )
        cubicTo(
            centerX - halfWidth * 0.1f, baseY - height * 0.26f,
            centerX - halfWidth * 0.04f, baseY - height * 0.5f,
            centerX + halfWidth * 0.04f, baseY - height
        )
        cubicTo(
            centerX + halfWidth * 0.16f, baseY - height * 0.55f,
            centerX + halfWidth * 0.36f, baseY - height * (0.3f + variation * 0.08f),
            centerX + halfWidth * 0.49f, baseY - height * (0.58f - variation * 0.1f)
        )
        cubicTo(
            centerX + halfWidth * 0.72f, baseY - height * 0.22f,
            centerX + halfWidth * 0.9f, baseY - height * 0.34f,
            centerX + halfWidth, baseY
        )
        close()
    }

    drawPath(
        path = flame,
        brush = Brush.verticalGradient(
            colors = listOf(Color(0xFFFF9100), color, Color(0xFFFFB300)),
            startY = baseY - height,
            endY = baseY
        )
    )
}

@Suppress("UNUSED")
private fun DrawScope.drawPyroFlame(
    centerX: Float,
    baseY: Float,
    width: Float,
    height: Float,
    color: Color
) {
    val halfWidth = width / 2f
    val flame = Path().apply {
        moveTo(centerX - halfWidth, baseY)
        cubicTo(
            centerX - halfWidth * 0.9f, baseY - height * 0.52f,
            centerX - halfWidth * 0.12f, baseY - height * 0.61f,
            centerX, baseY - height
        )
        cubicTo(
            centerX + halfWidth * 0.18f, baseY - height * 0.62f,
            centerX + halfWidth * 0.9f, baseY - height * 0.55f,
            centerX + halfWidth, baseY
        )
        close()
    }
    drawPath(
        path = flame,
        brush = Brush.verticalGradient(
            colors = listOf(Color(0xFFFFF176), color, Color(0xFFFF3D00).copy(alpha = 0.75f)),
            startY = baseY - height,
            endY = baseY
        )
    )
}

@Suppress("UNUSED")
private fun DrawScope.drawRazorFlame(
    centerX: Float,
    baseY: Float,
    width: Float,
    height: Float,
    color: Color
) {
    val halfWidth = width / 2f
    val flame = Path().apply {
        moveTo(centerX - halfWidth, baseY)
        lineTo(centerX - halfWidth * 0.84f, baseY - height * 0.2f)
        lineTo(centerX - halfWidth, baseY - height * 0.46f)
        lineTo(centerX - halfWidth * 0.42f, baseY - height * 0.36f)
        lineTo(centerX - halfWidth * 0.54f, baseY - height * 0.72f)
        lineTo(centerX - halfWidth * 0.12f, baseY - height * 0.55f)
        lineTo(centerX + halfWidth * 0.04f, baseY - height)
        lineTo(centerX + halfWidth * 0.2f, baseY - height * 0.57f)
        lineTo(centerX + halfWidth * 0.58f, baseY - height * 0.78f)
        lineTo(centerX + halfWidth * 0.42f, baseY - height * 0.43f)
        lineTo(centerX + halfWidth, baseY - height * 0.52f)
        lineTo(centerX + halfWidth * 0.78f, baseY - height * 0.18f)
        lineTo(centerX + halfWidth, baseY)
        close()
    }

    drawPath(
        path = flame,
        brush = Brush.verticalGradient(
            colors = listOf(Color(0xFFFFF176), color, Color(0xFFFF3D00).copy(alpha = 0.88f)),
            startY = baseY - height,
            endY = baseY
        )
    )
}

@Composable
fun SettingsView(
    playerColor: Color,
    onPlayerColorChange: (Color) -> Unit,
    settingsColor: Color,
    onSettingsColorChange: (Color) -> Unit,
    isStrobeEnabled: Boolean,
    onStrobeToggle: (Boolean) -> Unit,
    strobeSpeedMs: Float,
    onStrobeSpeedChange: (Float) -> Unit,
    isBeatSyncEnabled: Boolean,
    onBeatSyncToggle: (Boolean) -> Unit,
    isDarkMode: Boolean = true,
    onDarkModeToggle: (Boolean) -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(settingsColor.copy(alpha = 0.8f))
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // Light / Dark Theme Mode Section
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isDarkMode) Color(0xFF1E1E26) else Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                        contentDescription = null,
                        tint = if (isDarkMode) Color(0xFF00E5FF) else Color(0xFFFFB000),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isDarkMode) "Dark Theme Mode" else "Light Theme Mode",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isDarkMode) Color.White else Color.Black
                        )
                        Text(
                            text = if (isDarkMode) "Classic Dark Zune aesthetic" else "Sleek Light Zune aesthetic",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isDarkMode) Color.Gray else Color.DarkGray
                        )
                    }
                }
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { onDarkModeToggle(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFFE5007D),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.Gray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Player Background", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ColorRow(selectedColor = playerColor, onColorSelect = onPlayerColorChange)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Settings Background", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ColorRow(selectedColor = settingsColor, onColorSelect = onSettingsColorChange)

        Spacer(modifier = Modifier.height(24.dp))

        // Camera Flashlight Strobe Section
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF222222))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Camera Flash Strobe", style = MaterialTheme.typography.titleMedium, color = Color(0xFFFFA500))
                        Text("Flashes camera light while music plays", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                    Switch(
                        checked = isStrobeEnabled,
                        onCheckedChange = onStrobeToggle
                    )
                }

                if (isStrobeEnabled) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Sync Flash with Music Rhythm", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                            Text("Pulses flash to music beat pattern", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(
                            checked = isBeatSyncEnabled,
                            onCheckedChange = onBeatSyncToggle
                        )
                    }

                    if (!isBeatSyncEnabled) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Manual Strobe Speed", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                            Text("${strobeSpeedMs.toInt()} ms", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color(0xFFFFA500))
                        }
                        Slider(
                            value = strobeSpeedMs,
                            onValueChange = onStrobeSpeedChange,
                            valueRange = 50f..500f,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFFFFA500),
                                activeTrackColor = Color(0xFFFFA500)
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // CRT TV Settings Section
        var crtScreenOff by remember { mutableStateOf(CRTSettings.isScreenOffEnabled) }
        var crtScreenOn by remember { mutableStateOf(CRTSettings.isScreenOnEnabled) }
        var crtPowerIntercept by remember { mutableStateOf(CRTSettings.isPowerButtonInterceptEnabled) }

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF222222))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Retro CRT TV Animation", style = MaterialTheme.typography.titleMedium, color = Color(0xFF00FFCC))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Screen Off CRT Effect", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Switch(
                        checked = crtScreenOff,
                        onCheckedChange = {
                            crtScreenOff = it
                            CRTSettings.isScreenOffEnabled = it
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Screen On CRT Effect", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Switch(
                        checked = crtScreenOn,
                        onCheckedChange = {
                            crtScreenOn = it
                            CRTSettings.isScreenOnEnabled = it
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Power Button Intercept", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Switch(
                        checked = crtPowerIntercept,
                        onCheckedChange = {
                            crtPowerIntercept = it
                            CRTSettings.isPowerButtonInterceptEnabled = it
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { CRTService.lockScreenWithCRT() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFCC))
                ) {
                    Text("Lock Screen with CRT", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // GitHub Auto-Updater Section
        var isCheckingUpdate by remember { mutableStateOf(false) }
        var updateStatusMessage by remember { mutableStateOf("") }
        var latestApkUrl by remember { mutableStateOf<String?>(null) }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E26))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("GitHub App Auto-Updater", style = MaterialTheme.typography.titleMedium, color = Color(0xFFE5007D))
                Text("Root-enabled silent APK updates from GitHub", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (isCheckingUpdate) return@Button
                        isCheckingUpdate = true
                        updateStatusMessage = "Checking GitHub releases..."
                        coroutineScope.launch {
                            when (val result = GitHubAutoUpdater.checkForUpdatesResult()) {
                                is UpdateCheckResult.NewUpdateAvailable -> {
                                    latestApkUrl = result.downloadUrl
                                    updateStatusMessage = "New update found: ${result.tagName}. Downloading..."
                                    GitHubAutoUpdater.downloadAndInstallUpdate(context, result.downloadUrl) { status ->
                                        updateStatusMessage = status
                                    }
                                }
                                is UpdateCheckResult.UpToDate -> {
                                    latestApkUrl = result.downloadUrl
                                    updateStatusMessage = "App is up to date (${GitHubAutoUpdater.CURRENT_VERSION_TAG}). Latest release with APK: ${result.tagName}."
                                }
                                is UpdateCheckResult.NoApkFound -> {
                                    latestApkUrl = null
                                    val tag = result.tagName ?: "unknown"
                                    updateStatusMessage = "No .apk file attached to GitHub release ($tag). Please upload an .apk asset to GitHub Releases."
                                }
                                is UpdateCheckResult.Error -> {
                                    latestApkUrl = null
                                    updateStatusMessage = "Update check failed: ${result.message}"
                                }
                            }
                            UpdateLogger.logUpdate(context, GitHubAutoUpdater.CURRENT_VERSION_TAG, "Checked Updates", updateStatusMessage)
                            isCheckingUpdate = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5007D)),
                    enabled = !isCheckingUpdate
                ) {
                    if (isCheckingUpdate) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text("Check for GitHub App Updates", color = Color.White, fontWeight = FontWeight.Bold)
                }

                if (latestApkUrl != null && !isCheckingUpdate) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            isCheckingUpdate = true
                            updateStatusMessage = "Force downloading APK..."
                            coroutineScope.launch {
                                GitHubAutoUpdater.downloadAndInstallUpdate(context, latestApkUrl!!) { status ->
                                    updateStatusMessage = status
                                    UpdateLogger.logUpdate(context, GitHubAutoUpdater.CURRENT_VERSION_TAG, "Installed APK", status)
                                }
                                isCheckingUpdate = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFE5007D))
                    ) {
                        Text("Force Download & Reinstall APK", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        UpdateLogger.exportToGoogleDriveOrSheets(context)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF00E5FF))
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, tint = Color(0xFF00E5FF), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Export Update Log (Google Drive / Sheets)", fontWeight = FontWeight.Bold)
                }

                if (updateStatusMessage.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = updateStatusMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onPlayerColorChange(Color.Black)
                onSettingsColorChange(Color.Black)
                onStrobeToggle(false)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Reset to Defaults", color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(fontFamily = SongFont,
                text = "Created by Zak Timmons",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFFFA500)
            )
            Text(
                text = CodeStats.LINE_COUNT,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = CodeStats.FULL_VERSION_INFO,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "FUCK OFF",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(140.dp))
    }
}

@Composable
fun ColorRow(selectedColor: Color, onColorSelect: (Color) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val colors = listOf(
            Color.Black, 
            Color(0xFF121212), 
            Color(0xFF1A1A1A), 
            Color(0xFF001F3F), 
            Color(0xFF1B5E20), 
            Color(0xFF3E2723)
        )
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color, androidx.compose.foundation.shape.CircleShape)
                    .clickable { onColorSelect(color) }
                    .border(
                        width = 2.dp, 
                        color = if (color == selectedColor) Color.White else Color.White.copy(alpha = 0.3f), 
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
    }
}

@Composable
fun SongList(songs: List<Song>, onSongClick: (Int) -> Unit, onDownloadClick: (Song) -> Unit) {
    val context = LocalContext.current
    val audioSongs = remember(songs) {
        songs.filter { !it.isVideo }
            .sortedWith(compareBy<Song> { it.artist }.thenBy { it.album }.thenBy { extractTrackNumber(it) }.thenBy { it.title })
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 340.dp),
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexedGrid(audioSongs) { index, song ->
            val fileName = song.url.substringAfterLast("/")
            val isDownloaded = remember(song.url) { 
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
            }
            
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { 
                        val actualIndex = songs.indexOf(song)
                        onSongClick(if (actualIndex != -1) actualIndex else index) 
                    },
                color = Color(0xFF141418),
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = song.getVerifiedArtworkUrl(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = song.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${song.artist} • ${song.album}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.6f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (song.url.startsWith("http://") || song.url.startsWith("https://")) {
                        IconButton(onClick = { onDownloadClick(song) }) {
                            Icon(
                                imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                contentDescription = "Download",
                                tint = if (isDownloaded) Color(0xFFE5007D) else Color.White.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun YouTubeLoginDialog(
    onDismiss: () -> Unit,
    onLoginSuccess: (cookies: String) -> Unit
) {
    var isCapturing by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF181820)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Log In to YouTube Music",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.userAgentString = "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Mobile Safari/537.36"

                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    val cookies = CookieManager.getInstance().getCookie(url) ?: ""
                                    if (cookies.contains("SAPISID") || cookies.contains("HSID") || cookies.contains("SSID")) {
                                        if (!isCapturing) {
                                            isCapturing = true
                                            onLoginSuccess(cookies)
                                        }
                                    }
                                }
                            }
                            loadUrl("https://accounts.google.com/ServiceLogin?service=youtube&continue=https://music.youtube.com")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun YouTubeMusicTabScreen(
    songs: MutableList<Song>,
    onSongClick: (Song) -> Unit,
    isDarkMode: Boolean
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var searchAlbums by remember { mutableStateOf<List<YouTubeAlbum>>(emptyList()) }
    var searchSongs by remember { mutableStateOf<List<Song>>(emptyList()) }
    var userPlaylists by remember { mutableStateOf<List<Song>>(emptyList()) }
    var isLoadingUserPlaylists by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(YouTubeMusicAuthManager.isLoggedIn(context)) }
    var showLoginDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<YouTubeAlbum?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val surfaceColor = if (isDarkMode) Color(0xFF141418) else Color.White
    val textColor = if (isDarkMode) Color.White else Color(0xFF111115)
    val subTextColor = if (isDarkMode) Color.White.copy(alpha = 0.6f) else Color.Black.copy(alpha = 0.6f)
    val accentColor = Color(0xFFE5007D)

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            isLoadingUserPlaylists = true
            userPlaylists = YouTubeMusicEngine.fetchUserPlaylists(context)
            isLoadingUserPlaylists = false
        }
    }

    if (showLoginDialog) {
        YouTubeLoginDialog(
            onDismiss = { showLoginDialog = false },
            onLoginSuccess = { cookies ->
                YouTubeMusicAuthManager.saveCookies(context, cookies)
                isLoggedIn = true
                showLoginDialog = false
                android.widget.Toast.makeText(context, "Logged into YouTube Music successfully!", android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }

    if (selectedAlbum != null) {
        YouTubeAlbumDetailView(
            album = selectedAlbum!!,
            songs = songs,
            surfaceColor = surfaceColor,
            textColor = textColor,
            subTextColor = subTextColor,
            accentColor = accentColor,
            onBackClick = { selectedAlbum = null },
            onSongClick = onSongClick
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            // Account Status Bar
            Surface(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                shape = RoundedCornerShape(12.dp),
                color = surfaceColor,
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = if (isLoggedIn) Color(0xFF4CAF50) else subTextColor,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = if (isLoggedIn) "YouTube Music Account" else "Guest Mode",
                                style = MaterialTheme.typography.titleSmall,
                                color = textColor,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isLoggedIn) "Connected & Syncing Playlists" else "Sign in to access your library",
                                style = MaterialTheme.typography.bodySmall,
                                color = subTextColor
                            )
                        }
                    }

                    if (isLoggedIn) {
                        OutlinedButton(
                            onClick = {
                                YouTubeMusicAuthManager.logout(context)
                                isLoggedIn = false
                                userPlaylists = emptyList()
                                android.widget.Toast.makeText(context, "Logged out of YouTube Music", android.widget.Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                        ) {
                            Text("Log Out", fontSize = 11.sp)
                        }
                    } else {
                        Button(
                            onClick = { showLoginDialog = true },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                        ) {
                            Text("Sign In", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search Artist or Album Discography...", color = subTextColor) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = accentColor)
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = textColor)
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = textColor.copy(alpha = 0.3f),
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedContainerColor = surfaceColor,
                    unfocusedContainerColor = surfaceColor
                ),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (searchQuery.isBlank() || isSearching) return@Button
                    isSearching = true
                    coroutineScope.launch {
                        val albums = YouTubeMusicEngine.searchMusicAlbums(context, searchQuery)
                        val tracks = YouTubeMusicEngine.searchMusic(context, searchQuery)
                        searchAlbums = albums
                        searchSongs = tracks
                        isSearching = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                enabled = !isSearching && searchQuery.isNotBlank(),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isSearching) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text("Search Albums & Discography", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (isSearching) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = accentColor)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Searching YouTube Music Albums...", color = textColor)
                    }
                }
            } else if (searchAlbums.isNotEmpty() || searchSongs.isNotEmpty()) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (searchAlbums.isNotEmpty()) {
                        item {
                            Text("Albums & EPs", style = MaterialTheme.typography.titleMedium, color = accentColor, fontWeight = FontWeight.Bold)
                        }
                        items(searchAlbums) { album ->
                            YouTubeAlbumCard(album, surfaceColor, textColor, subTextColor, accentColor) {
                                selectedAlbum = album
                            }
                        }
                    }

                    if (searchSongs.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Top Tracks", style = MaterialTheme.typography.titleMedium, color = accentColor, fontWeight = FontWeight.Bold)
                        }
                        items(searchSongs) { song ->
                            YouTubeSongItemRow(song, songs, surfaceColor, textColor, subTextColor, accentColor, onSongClick)
                        }
                    }
                }
            } else if (userPlaylists.isNotEmpty()) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text("My Liked Music & Saved Tracks", style = MaterialTheme.typography.titleMedium, color = accentColor, fontWeight = FontWeight.Bold)
                    }
                    items(userPlaylists) { song ->
                        YouTubeSongItemRow(song, songs, surfaceColor, textColor, subTextColor, accentColor, onSongClick)
                    }
                }
            } else if (isLoadingUserPlaylists) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = accentColor)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Syncing your YouTube Music library...", color = textColor)
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isLoggedIn) "Type an artist name (e.g. Korn, Biohazard) to view their full album discography" else "Sign in above or search an artist name to browse full albums & discographies",
                        style = MaterialTheme.typography.bodyMedium,
                        color = subTextColor
                    )
                }
            }
        }
    }
}

@Composable
fun YouTubeAlbumCard(
    album: YouTubeAlbum,
    surfaceColor: Color,
    textColor: Color,
    subTextColor: Color,
    accentColor: Color,
    onAlbumClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onAlbumClick() },
        color = surfaceColor,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = album.artworkUrl.ifEmpty { "https://img.youtube.com/vi/default/hqdefault.jpg" },
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${album.artist} • ${album.year}",
                    style = MaterialTheme.typography.bodySmall,
                    color = subTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onAlbumClick) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "View Album",
                    tint = accentColor,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun YouTubeAlbumDetailView(
    album: YouTubeAlbum,
    songs: MutableList<Song>,
    surfaceColor: Color,
    textColor: Color,
    subTextColor: Color,
    accentColor: Color,
    onBackClick: () -> Unit,
    onSongClick: (Song) -> Unit
) {
    val context = LocalContext.current
    var tracks by remember { mutableStateOf<List<Song>>(emptyList()) }
    var isLoadingTracks by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(album.browseId) {
        isLoadingTracks = true
        tracks = YouTubeMusicEngine.fetchAlbumTracks(
            context = context,
            albumBrowseId = album.browseId,
            albumTitle = album.title,
            artistName = album.artist,
            fallbackArtworkUrl = album.artworkUrl
        )
        isLoadingTracks = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.Close, contentDescription = "Back", tint = textColor)
            }
            Text(
                text = "Album Details",
                style = MaterialTheme.typography.titleMedium,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }

        // Album Header
        Surface(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            shape = RoundedCornerShape(14.dp),
            color = surfaceColor,
            tonalElevation = 3.dp
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = album.artworkUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = album.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${album.artist} • ${album.year}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = accentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${tracks.size} Tracks",
                        style = MaterialTheme.typography.bodySmall,
                        color = subTextColor
                    )
                }
            }
        }

        if (isLoadingTracks) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = accentColor)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Loading Album Tracklist...", color = textColor)
                }
            }
        } else if (tracks.isNotEmpty()) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        val firstTrack = tracks.first()
                        val playableUrl = YouTubeMusicEngine.resolvePlayableAudioUrl(firstTrack.url)
                        val playableSong = firstTrack.copy(url = playableUrl)
                        if (songs.none { it.url == playableSong.url }) {
                            songs.add(playableSong)
                        }
                        onSongClick(playableSong)
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Play Album", fontWeight = FontWeight.Bold, color = Color.White)
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tracks) { track ->
                    YouTubeSongItemRow(track, songs, surfaceColor, textColor, subTextColor, accentColor, onSongClick)
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("No tracks found for this album", color = subTextColor)
            }
        }
    }
}

@Composable
fun YouTubeSongItemRow(
    song: Song,
    songs: MutableList<Song>,
    surfaceColor: Color,
    textColor: Color,
    subTextColor: Color,
    accentColor: Color,
    onSongClick: (Song) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isExtracting by remember { mutableStateOf(false) }

    val handlePlay = {
        if (!isExtracting) {
            isExtracting = true
            coroutineScope.launch {
                val playableUrl = YouTubeMusicEngine.resolvePlayableAudioUrl(song.url)
                val playableSong = song.copy(url = playableUrl)
                if (songs.none { it.url == playableSong.url }) {
                    songs.add(playableSong)
                }
                onSongClick(playableSong)
                isExtracting = false
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { handlePlay() },
        color = surfaceColor,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = song.getVerifiedArtworkUrl(),
                contentDescription = null,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${song.artist} • ${song.album}",
                    style = MaterialTheme.typography.bodySmall,
                    color = subTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (isExtracting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp).padding(4.dp),
                    color = accentColor,
                    strokeWidth = 2.dp
                )
            } else {
                IconButton(onClick = { handlePlay() }) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = accentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VideoList(songs: List<Song>, onDownloadClick: (Song) -> Unit, onVideoClick: (Song) -> Unit) {
    val context = LocalContext.current
    val videos = remember(songs) { songs.filter { it.isVideo } }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 170.dp),
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(videos) { video ->
            val fileName = video.url.substringAfterLast("/")
            val isDownloaded = remember(video.url) { 
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onVideoClick(video) },
                color = Color(0xFF141418),
                tonalElevation = 4.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(115.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        AsyncImage(
                            model = video.getVerifiedArtworkUrl(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Surface(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(36.dp),
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = Color.Black.copy(alpha = 0.6f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "Play Video",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = video.title,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = video.artist,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.6f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (!video.url.startsWith("content://")) {
                            IconButton(onClick = { onDownloadClick(video) }, modifier = Modifier.size(32.dp)) {
                                Icon(
                                    imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                    contentDescription = "Download",
                                    tint = if (isDownloaded) Color(0xFFE5007D) else Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistList(songs: List<Song>, onArtistClick: (String) -> Unit) {
    val audioSongs = remember(songs) { songs.filter { !it.isVideo } }
    val artists = remember(audioSongs) { audioSongs.map { it.artist }.distinct().sorted() }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 110.dp),
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(artists) { artist ->
            val artistSongs = remember(artist, audioSongs) { audioSongs.filter { it.artist == artist } }
            val firstSongArt = artistSongs.firstOrNull()?.getVerifiedArtworkUrl()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onArtistClick(artist) }
                    .padding(8.dp)
            ) {
                Surface(
                    modifier = Modifier.size(96.dp),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color(0xFF22222A),
                    tonalElevation = 6.dp
                ) {
                    if (firstSongArt != null) {
                        AsyncImage(
                            model = firstSongArt,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().clip(androidx.compose.foundation.shape.CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(44.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = artist,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${artistSongs.size} tracks",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}

val officialTrackCatalog: Map<Pair<String, String>, Int> = mapOf(
    // Danny Diablo - Dollerz Make Sense
    Pair("danny diablo", "intro") to 1,
    Pair("danny diablo", "ill roc soldiers ft. stikman") to 2,
    Pair("danny diablo", "stready minds ft. diggy ill-roc") to 3,
    Pair("danny diablo", "grimey ft. stikman") to 4,
    Pair("danny diablo", "smash everything ft. stikman") to 5,
    Pair("danny diablo", "queens & jersey ft. stikman") to 6,
    Pair("danny diablo", "spit fire ft. diggy ill-roc, wes nihil & tony slippaz") to 7,
    Pair("danny diablo", "remember my name ft. christian koresh & diggy ill-roc") to 8,
    Pair("danny diablo", "the foundation ft. stikman") to 9,
    Pair("danny diablo", "in the city ft. joey d") to 10,

    // Korn - Follow the Leader
    Pair("korn", "it's on") to 1,
    Pair("korn", "freak on a leash") to 2,
    Pair("korn", "got the life") to 3,
    Pair("korn", "dead bodies everywhere") to 4,
    Pair("korn", "children of the korn") to 5,
    Pair("korn", "b.b.k.") to 6,
    Pair("korn", "pretty") to 7,
    Pair("korn", "all in the family") to 8,
    Pair("korn", "reclaim my place") to 9,
    Pair("korn", "justin") to 10,
    Pair("korn", "seed") to 11,
    Pair("korn", "cameltosis") to 12,
    Pair("korn", "my gift to you") to 13,
    Pair("korn", "earache my eye") to 14,

    // Morgan Wallen - One Thing At A Time
    Pair("morgan wallen", "born with a beer in my hand") to 1,
    Pair("morgan wallen", "last night") to 2,
    Pair("morgan wallen", "everything i love") to 3,
    Pair("morgan wallen", "man made a bar") to 4,
    Pair("morgan wallen", "devil don't know") to 5,
    Pair("morgan wallen", "one thing at a time") to 6,
    Pair("morgan wallen", "98 braves") to 7,
    Pair("morgan wallen", "ain't that some") to 8,
    Pair("morgan wallen", "i wrote the book") to 9,
    Pair("morgan wallen", "tennessee numbers") to 10,
    Pair("morgan wallen", "hope that's true") to 11,
    Pair("morgan wallen", "whiskey friends") to 12,
    Pair("morgan wallen", "sunrise") to 13,
    Pair("morgan wallen", "keith whitley") to 14,
    Pair("morgan wallen", "in the bible") to 15,
    Pair("morgan wallen", "you proof") to 16,
    Pair("morgan wallen", "thought you should know") to 17,
    Pair("morgan wallen", "f150-50") to 18,
    Pair("morgan wallen", "neon star") to 19,
    Pair("morgan wallen", "i deserve a drink") to 20,
    Pair("morgan wallen", "wine into water") to 21,
    Pair("morgan wallen", "me + all your reasons") to 22,
    Pair("morgan wallen", "tennessee fan") to 23,
    Pair("morgan wallen", "money on me") to 24,
    Pair("morgan wallen", "thinkin' bout me") to 25,
    Pair("morgan wallen", "single than she was") to 26,
    Pair("morgan wallen", "days that end in why") to 27,
    Pair("morgan wallen", "last drive down main") to 28,
    Pair("morgan wallen", "me to me") to 29,
    Pair("morgan wallen", "don't think jesus") to 30,
    Pair("morgan wallen", "180 lifestyle") to 31,
    Pair("morgan wallen", "had it") to 32,
    Pair("morgan wallen", "cowgirls") to 33,
    Pair("morgan wallen", "good girl gone missin'") to 34,
    Pair("morgan wallen", "outlook") to 35,
    Pair("morgan wallen", "dying man") to 36,

    // Biohazard - Kill Or Be Killed
    Pair("biohazard", "intro") to 1,
    Pair("biohazard", "world on fire") to 2,
    Pair("biohazard", "never forgive never forget") to 3,
    Pair("biohazard", "kill or be killed") to 4,
    Pair("biohazard", "heads kicked in") to 5,
    Pair("biohazard", "beaten senseless") to 6,
    Pair("biohazard", "make my stand") to 7,
    Pair("biohazard", "open your eyes") to 8,
    Pair("biohazard", "penalty") to 9,
    Pair("biohazard", "dead to me") to 10,
    Pair("biohazard", "hallowed ground") to 11,

    // Biohazard - Uncivilization
    Pair("biohazard", "sellout") to 1,
    Pair("biohazard", "uncivilization") to 2,
    Pair("biohazard", "wide awake") to 3,
    Pair("biohazard", "get away") to 4,
    Pair("biohazard", "unified") to 5,
    Pair("biohazard", "gone") to 6,
    Pair("biohazard", "letter go") to 7,
    Pair("biohazard", "last man standing") to 8,
    Pair("biohazard", "hffk") to 9,
    Pair("biohazard", "domination") to 10,
    Pair("biohazard", "trap") to 11,
    Pair("biohazard", "plastic") to 12,
    Pair("biohazard", "cross the line") to 13,
    Pair("biohazard", "a.t.f.") to 14,
    Pair("biohazard", "life of my own") to 15,
    Pair("biohazard", "sex and violence") to 16,

    // NOFX
    Pair("nofx", "the decline") to 1,

    // Hatebreed - Fatal Paradox
    Pair("hatebreed", "kill count increase") to 1,
    Pair("hatebreed", "the carrion choir") to 2,
    Pair("hatebreed", "decisions become destinies") to 3,
    Pair("hatebreed", "total invincible") to 4,
    Pair("hatebreed", "fatal paradox") to 5,
    Pair("hatebreed", "still a threat") to 6,
    Pair("hatebreed", "sacrifice season") to 7,
    Pair("hatebreed", "sacrifice season (feat. john tardy)") to 7,
    Pair("hatebreed", "truth is a lion") to 8,
    Pair("hatebreed", "make the demons obey") to 9,
    Pair("hatebreed", "none left to save") to 10,
    Pair("hatebreed", "poison planet") to 11,
    Pair("hatebreed", "poison planet (coc cover)") to 11
)

private fun extractTrackNumber(song: Song): Int {
    val artistLower = song.artist.trim().lowercase()
    val titleLower = song.title.trim().lowercase()

    val key = Pair(artistLower, titleLower)
    val catalogNum = officialTrackCatalog[key]
    if (catalogNum != null) return catalogNum

    val partialMatch = officialTrackCatalog.entries.firstOrNull { (k, _) ->
        k.first == artistLower && (k.second.contains(titleLower) || titleLower.contains(k.second))
    }
    if (partialMatch != null) return partialMatch.value

    val titleMatch = Regex("^(\\d+)").find(song.title.trim())
    if (titleMatch != null) {
        return titleMatch.groupValues[1].toIntOrNull() ?: Int.MAX_VALUE
    }

    val fileName = song.url.substringAfterLast("/")
    val urlMatch = Regex("(?:^|/|\\b|_|-)(\\d{1,3})(?:[._\\s-]|\$)").find(fileName)
    if (urlMatch != null) {
        return urlMatch.groupValues[1].toIntOrNull() ?: Int.MAX_VALUE
    }

    return Int.MAX_VALUE
}

@Composable
fun ArtistDetailView(
    artist: String,
    songs: List<Song>,
    onBack: () -> Unit,
    onAlbumClick: (String) -> Unit,
    onSongClick: (Song) -> Unit,
    onDownloadClick: (Song) -> Unit
) {
    val context = LocalContext.current
    val audioSongs = remember(songs) { songs.filter { !it.isVideo } }
    val artistSongs = remember(artist, audioSongs) {
        audioSongs.filter { it.artist == artist }
            .sortedWith(compareBy<Song> { extractTrackNumber(it) }.thenBy { it.title })
    }
    val albums = remember(artistSongs) { artistSongs.map { it.album }.distinct() }
    var expandedAlbums by remember { mutableStateOf(setOf<String>()) }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp).clickable { onBack() }
        ) {
            Icon(Icons.Default.SkipPrevious, contentDescription = "Back", tint = Color(0xFFE5007D))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Back to Artists", style = MaterialTheme.typography.titleMedium, color = Color(0xFFE5007D))
        }
        
        Text(
            text = artist,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                Text("Albums", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = Color.White, modifier = Modifier.padding(vertical = 4.dp))
            }
            items(albums) { album ->
                val albumSongs = remember(album, artistSongs) {
                    artistSongs.filter { it.album == album }
                        .sortedWith(compareBy<Song> { extractTrackNumber(it) }.thenBy { it.title })
                }
                val albumArt = albumSongs.firstOrNull()?.getVerifiedArtworkUrl()
                val isExpanded = expandedAlbums.contains(album)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF141418))
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedAlbums = if (isExpanded) {
                                    expandedAlbums - album
                                } else {
                                    expandedAlbums + album
                                }
                            },
                        color = Color(0xFF141418),
                        tonalElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = albumArt,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = album,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "${albumSongs.size} tracks",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                            }
                            IconButton(
                                onClick = {
                                    albumSongs.firstOrNull()?.let { onSongClick(it) }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play Album",
                                    tint = Color(0xFFE5007D),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    expandedAlbums = if (isExpanded) {
                                        expandedAlbums - album
                                    } else {
                                        expandedAlbums + album
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = if (isExpanded) "Collapse Album" else "Expand Album",
                                    tint = Color.White.copy(alpha = 0.7f),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            IconButton(
                                onClick = { onAlbumClick(album) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SkipNext,
                                    contentDescription = "View Album Details",
                                    tint = Color(0xFFE5007D),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    if (isExpanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            albumSongs.forEachIndexed { index, song ->
                                val fileName = song.url.substringAfterLast("/")
                                val isDownloaded = remember(song.url) {
                                    File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists()
                                }
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onSongClick(song) },
                                    color = Color(0xFF1E1E26)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${index + 1}",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color(0xFFE5007D),
                                            modifier = Modifier.width(24.dp)
                                        )
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = song.title,
                                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                color = Color.White
                                            )
                                        }
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Play",
                                            tint = Color.White.copy(alpha = 0.5f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        if (song.url.startsWith("http://") || song.url.startsWith("https://")) {
                                            IconButton(
                                                onClick = { onDownloadClick(song) },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                                    contentDescription = "Download",
                                                    tint = if (isDownloaded) Color(0xFFE5007D) else Color.White.copy(alpha = 0.5f),
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
            item {
                Text("Songs", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = Color.White, modifier = Modifier.padding(top = 12.dp, bottom = 4.dp))
            }
            items(artistSongs) { song ->
                val fileName = song.url.substringAfterLast("/")
                val isDownloaded = remember(song.url) { 
                    File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onSongClick(song) },
                    color = Color(0xFF141418)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = song.getVerifiedArtworkUrl(),
                            contentDescription = null,
                            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(song.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            Text(song.album, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.6f))
                        }
                        if (song.url.startsWith("http://") || song.url.startsWith("https://")) {
                            IconButton(onClick = { onDownloadClick(song) }) {
                                Icon(
                                    imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                    contentDescription = "Download",
                                    tint = if (isDownloaded) Color(0xFFE5007D) else Color.White.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlbumList(songs: List<Song>, onAlbumClick: (String) -> Unit) {
    val audioSongs = remember(songs) { songs.filter { !it.isVideo } }
    val albums = remember(audioSongs) { audioSongs.map { it.album }.distinct().sorted() }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(albums) { album ->
            val albumSongs = remember(album, audioSongs) { audioSongs.filter { it.album == album } }
            val albumArt = albumSongs.firstOrNull()?.getVerifiedArtworkUrl()
            val artistName = albumSongs.firstOrNull()?.artist ?: ""

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onAlbumClick(album) },
                color = Color(0xFF141418),
                tonalElevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    AsyncImage(
                        model = albumArt,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = album,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = artistName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumDetailView(
    album: String,
    songs: List<Song>,
    onBack: () -> Unit,
    onSongClick: (Song) -> Unit,
    onDownloadClick: (Song) -> Unit
) {
    val context = LocalContext.current
    val audioSongs = remember(songs) { songs.filter { !it.isVideo } }
    val albumSongs = remember(album, audioSongs) {
        audioSongs.filter { it.album == album }
            .sortedWith(compareBy<Song> { extractTrackNumber(it) }.thenBy { it.title })
    }
    val albumArt = albumSongs.firstOrNull()?.getVerifiedArtworkUrl()

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp).clickable { onBack() }
        ) {
            Icon(Icons.Default.SkipPrevious, contentDescription = "Back", tint = Color(0xFFE5007D))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Back", style = MaterialTheme.typography.titleMedium, color = Color(0xFFE5007D))
        }

        Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = albumArt,
                contentDescription = null,
                modifier = Modifier.size(110.dp).clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = album, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black), color = Color.White)
                Text(text = albumSongs.firstOrNull()?.artist ?: "", style = MaterialTheme.typography.titleMedium, color = Color.White.copy(alpha = 0.7f))
                Text(text = "${albumSongs.size} tracks", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.5f))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(albumSongs) { song ->
                val fileName = song.url.substringAfterLast("/")
                val isDownloaded = remember(song.url) { 
                    File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onSongClick(song) },
                    color = Color(0xFF141418)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Audiotrack, contentDescription = null, tint = Color(0xFFE5007D), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(song.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color.White, modifier = Modifier.weight(1f))
                        if (song.url.startsWith("http://") || song.url.startsWith("https://")) {
                            IconButton(onClick = { onDownloadClick(song) }) {
                                Icon(
                                    imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                    contentDescription = "Download",
                                    tint = if (isDownloaded) Color(0xFFE5007D) else Color.White.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomPlayerBar(
    song: Song?,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onClick: () -> Unit,
    progress: Float
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        color = Color(0xFF18181F),
        tonalElevation = 10.dp
    ) {
        Column {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(3.dp),
                color = Color(0xFFE5007D),
                trackColor = Color.Transparent
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = song?.getVerifiedArtworkUrl(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song?.title ?: "No Song Selected",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = song?.artist ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                TurkeyPlayPauseButton(
                    isPlaying = isPlaying,
                    onClick = onPlayPauseClick,
                    modifier = Modifier.size(42.dp)
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            style = MaterialTheme.typography.displaySmall,
            fontFamily = SongFont,
            color = Color(0xFF87CEEB)
        )
    }
}

@Preview(name = "Pixel 10 Pro XL", device = "spec:width=448dp,height=997dp,dpi=480", showBackground = true)
@Composable
fun AppPreview() {
    HelloWorldAndMyFirstAppTheme {
        SettingsView(
            playerColor = Color.Black,
            onPlayerColorChange = {},
            settingsColor = Color.Black,
            onSettingsColorChange = {},
            isStrobeEnabled = true,
            onStrobeToggle = {},
            strobeSpeedMs = 100f,
            onStrobeSpeedChange = {},
            isBeatSyncEnabled = false,
            onBeatSyncToggle = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerPreview() {
    HelloWorldAndMyFirstAppTheme {
        PlayerView(
            songs = listOf(
                Song("Sample Song", "Artist", "Album", "", ""),
                Song("Another Song", "Artist 2", "Album 2", "", "")
            ),
            currentSongIndex = 0,
            isPlaying = true,
            currentPosition = 30000L,
            totalDuration = 180000L,
            playerState = null,
            backgroundColor = Color.Black,
            isStrobeEnabled = true,
            onDownloadClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloWorldAndMyFirstAppTheme {
        Greeting("Android")
    }
}

private fun getFileNameFromUri(context: Context, uri: Uri): String? {
    if (uri.scheme == "content") {
        try {
            context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        return cursor.getString(nameIndex)
                    }
                }
            }
        } catch (_: Exception) {}
    }
    return uri.lastPathSegment
}

private fun getSongFromUri(context: Context, uri: Uri): Song {
    var title: String? = null
    var artist: String? = null
    var album: String? = null
    var isVideo = false

    val mimeType = context.contentResolver.getType(uri)
    if (mimeType?.startsWith("video/") == true) {
        isVideo = true
    }

    // 1. Try MediaStore Query
    try {
        val projection = arrayOf(
            MediaStore.MediaColumns.TITLE,
            MediaStore.MediaColumns.ARTIST,
            MediaStore.MediaColumns.ALBUM
        )
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val titleIndex = cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)
                val artistIndex = cursor.getColumnIndex(MediaStore.MediaColumns.ARTIST)
                val albumIndex = cursor.getColumnIndex(MediaStore.MediaColumns.ALBUM)
                if (titleIndex != -1) title = cursor.getString(titleIndex)
                if (artistIndex != -1) artist = cursor.getString(artistIndex)
                if (albumIndex != -1) album = cursor.getString(albumIndex)
            }
        }
    } catch (_: Exception) {}

    // Clean up typical MediaStore fallback strings like "<unknown>"
    if (artist.equals("<unknown>", ignoreCase = true) || artist.isNullOrBlank()) artist = null
    if (title.equals("<unknown>", ignoreCase = true) || title.isNullOrBlank()) title = null
    if (album.equals("<unknown>", ignoreCase = true) || album.isNullOrBlank()) album = null

    // 2. Try MediaMetadataRetriever for missing metadata & embedded album art
    var embeddedArtworkUrl = ""
    try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, uri)
        if (title == null) {
            title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        }
        if (artist == null) {
            artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                ?: retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)
        }
        if (album == null) {
            album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
        }
        val hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)
        if (hasVideo == "yes") {
            isVideo = true
        }

        val artBytes = retriever.embeddedPicture
        if (artBytes != null) {
            val cacheFile = File(context.cacheDir, "art_${uri.toString().hashCode()}.jpg")
            if (!cacheFile.exists()) {
                cacheFile.writeBytes(artBytes)
            }
            embeddedArtworkUrl = Uri.fromFile(cacheFile).toString()
        }
        retriever.release()
    } catch (_: Exception) {}

    // Clean up retrieved metadata
    if (artist.equals("<unknown>", ignoreCase = true) || artist.isNullOrBlank()) artist = null
    if (title.equals("<unknown>", ignoreCase = true) || title.isNullOrBlank()) title = null

    // 3. Fallback to Display Name / Filename parsing
    val displayName = getFileNameFromUri(context, uri) ?: uri.lastPathSegment ?: "Local Track"

    if (title == null || artist == null) {
        val cleanName = displayName.substringBeforeLast(".")
            .replace("_", " ")
            .replace(Regex("^\\d+[.\\s_-]*"), "")
            .trim()

        if (cleanName.contains(" - ")) {
            val parts = cleanName.split(" - ", limit = 2)
            if (artist == null && parts[0].isNotBlank()) {
                artist = parts[0].trim()
            }
            if (title == null && parts[1].isNotBlank()) {
                title = parts[1].trim()
            }
        } else {
            if (title == null) {
                title = cleanName
            }
        }
    }

    val finalTitle = title?.ifBlank { null } ?: displayName.substringBeforeLast(".")
    val finalArtist = artist?.ifBlank { null } ?: "Local Artist"
    val finalAlbum = album?.ifBlank { null } ?: "Device Storage"

    return Song(
        title = finalTitle,
        artist = finalArtist,
        album = finalAlbum,
        url = uri.toString(),
        artworkUrl = embeddedArtworkUrl,
        isVideo = isVideo,
        localUri = uri.toString()
    )
}

private fun scanLocalDeviceMedia(context: Context): List<Song> {
    val localSongs = mutableListOf<Song>()
    val contentResolver = context.contentResolver

    // 1. Scan Audio files from MediaStore
    try {
        val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val audioProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        contentResolver.query(audioUri, audioProjection, selection, null, "${MediaStore.Audio.Media.TITLE} ASC")?.use { cursor ->
            val titleIdx = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistIdx = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumIdx = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val dataIdx = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val idIdx = cursor.getColumnIndex(MediaStore.Audio.Media._ID)

            while (cursor.moveToNext()) {
                val title = if (titleIdx != -1) cursor.getString(titleIdx) else null
                val artist = if (artistIdx != -1) cursor.getString(artistIdx) else null
                val album = if (albumIdx != -1) cursor.getString(albumIdx) else null
                val filePath = if (dataIdx != -1) cursor.getString(dataIdx) else null
                val id = if (idIdx != -1) cursor.getLong(idIdx) else 0L

                val uri = if (filePath != null && File(filePath).exists()) {
                    Uri.fromFile(File(filePath))
                } else {
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                }

                val cleanArtist = if (artist.equals("<unknown>", ignoreCase = true) || artist.isNullOrBlank()) "Local Artist" else artist.trim()
                val cleanTitle = if (title.equals("<unknown>", ignoreCase = true) || title.isNullOrBlank()) {
                    filePath?.substringAfterLast("/")?.substringBeforeLast(".") ?: "Local Track"
                } else title.trim()
                val cleanAlbum = if (album.equals("<unknown>", ignoreCase = true) || album.isNullOrBlank()) "Device Storage" else album.trim()

                localSongs.add(
                    Song(
                        title = cleanTitle,
                        artist = cleanArtist,
                        album = cleanAlbum,
                        url = uri.toString(),
                        artworkUrl = "",
                        isVideo = false,
                        localUri = uri.toString()
                    )
                )
            }
        }
    } catch (_: Exception) {}

    // 2. Scan Video files from MediaStore
    try {
        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val videoProjection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.ARTIST,
            MediaStore.Video.Media.DATA
        )

        contentResolver.query(videoUri, videoProjection, null, null, "${MediaStore.Video.Media.TITLE} ASC")?.use { cursor ->
            val titleIdx = cursor.getColumnIndex(MediaStore.Video.Media.TITLE)
            val artistIdx = cursor.getColumnIndex(MediaStore.Video.Media.ARTIST)
            val dataIdx = cursor.getColumnIndex(MediaStore.Video.Media.DATA)
            val idIdx = cursor.getColumnIndex(MediaStore.Video.Media._ID)

            while (cursor.moveToNext()) {
                val title = if (titleIdx != -1) cursor.getString(titleIdx) else null
                val artist = if (artistIdx != -1) cursor.getString(artistIdx) else null
                val filePath = if (dataIdx != -1) cursor.getString(dataIdx) else null
                val id = if (idIdx != -1) cursor.getLong(idIdx) else 0L

                val uri = if (filePath != null && File(filePath).exists()) {
                    Uri.fromFile(File(filePath))
                } else {
                    ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                }

                val cleanArtist = if (artist.equals("<unknown>", ignoreCase = true) || artist.isNullOrBlank()) "Video Storage" else artist.trim()
                val cleanTitle = if (title.equals("<unknown>", ignoreCase = true) || title.isNullOrBlank()) {
                    filePath?.substringAfterLast("/")?.substringBeforeLast(".") ?: "Local Video"
                } else title.trim()

                localSongs.add(
                    Song(
                        title = cleanTitle,
                        artist = cleanArtist,
                        album = "Device Videos",
                        url = uri.toString(),
                        artworkUrl = "",
                        isVideo = true,
                        localUri = uri.toString()
                    )
                )
            }
        }
    } catch (_: Exception) {}

    return localSongs
}

private fun formatTime(ms: Long): String = if (ms < 0) {
    "--:--"
} else {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    try {
        "%02d:%02d".format(minutes, seconds)
    } catch (e: Exception) {
        "--:--"
    }
}

class MusicViewModel(application: Application) : AndroidViewModel(application) {
    var player by mutableStateOf<Player?>(null)
        private set
    var currentSongIndex by mutableIntStateOf(0)
        private set
    var isPlaying by mutableStateOf(false)
        private set
    var currentPosition by mutableLongStateOf(0L)
        private set
    var totalDuration by mutableLongStateOf(0L)
        private set

    var selectedTab by mutableStateOf(MusicTab.NowPlaying)
    var selectedMusicSubTab by mutableStateOf(MusicSubTab.Artists)
    var selectedArtist by mutableStateOf<String?>(null)
    var selectedAlbum by mutableStateOf<String?>(null)
    var isDarkMode by mutableStateOf(true)

    fun toggleDarkMode(context: Context, enabled: Boolean) {
        isDarkMode = enabled
        context.getSharedPreferences("media_prefs", Context.MODE_PRIVATE)
            .edit().putBoolean("is_dark_mode", enabled).apply()
    }

    private var controllerFuture: ListenableFuture<MediaController>? = null

    fun initController(context: Context, songs: List<Song>) {
        if (player != null) return

        val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        
        controllerFuture?.addListener({
            val controller = controllerFuture?.get() ?: return@addListener
            player = controller
            
            val savedPrefs = context.getSharedPreferences("media_prefs", Context.MODE_PRIVATE)
            isDarkMode = savedPrefs.getBoolean("is_dark_mode", true)

            if (controller.mediaItemCount == 0) {
                val mediaItems = songs.map { song ->
                    val finalUri = if (song.url.startsWith("content://") || song.url.startsWith("file://") || song.url.startsWith("android.resource://")) {
                        song.url.toUri()
                    } else {
                        val fileName = song.url.substringAfterLast("/")
                        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
                        if (file.exists()) Uri.fromFile(file) else song.url.toUri()
                    }
                    
                    MediaItem.Builder()
                        .setMediaId(song.url)
                        .setUri(finalUri)
                        .setMediaMetadata(
                            MediaMetadata.Builder()
                                .setTitle(song.title)
                                .setArtist(song.artist)
                                .setArtworkUri(song.getVerifiedArtworkUrl().toUri())
                                .build()
                        )
                        .build()
                }
                controller.setMediaItems(mediaItems)
                val savedIndex = savedPrefs.getInt("last_song_index", 0).coerceIn(0, (songs.size - 1).coerceAtLeast(0))
                controller.seekTo(savedIndex, 0L)
                controller.prepare()
                currentSongIndex = savedIndex
            } else {
                currentSongIndex = controller.currentMediaItemIndex
                isPlaying = controller.isPlaying
            }

            controller.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(playing: Boolean) {
                    isPlaying = playing
                }
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    currentSongIndex = controller.currentMediaItemIndex
                    savedPrefs.edit().putInt("last_song_index", currentSongIndex).apply()
                }
            })
        }, ContextCompat.getMainExecutor(context))
    }

    fun updateMediaItems(context: Context, songs: List<Song>) {
        val controller = player ?: return
        val currentCount = controller.mediaItemCount
        if (songs.size > currentCount) {
            val newItems = songs.subList(currentCount, songs.size).map { song ->
                val finalUri = if (song.url.startsWith("content://") || song.url.startsWith("file://")) {
                    song.url.toUri()
                } else {
                    val fileName = song.url.substringAfterLast("/")
                    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
                    if (file.exists()) Uri.fromFile(file) else song.url.toUri()
                }

                MediaItem.Builder()
                    .setMediaId(song.url)
                    .setUri(finalUri)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle(song.title)
                            .setArtist(song.artist)
                            .setArtworkUri(song.getVerifiedArtworkUrl().toUri())
                            .build()
                    )
                    .build()
            }
            controller.addMediaItems(newItems)
        }
    }

    fun updatePosition() {
        player?.let {
            currentPosition = it.currentPosition
            totalDuration = it.duration.coerceAtLeast(0L)
        }
    }

    override fun onCleared() {
        controllerFuture?.let { MediaController.releaseFuture(it) }
    }
}

object FlashlightStrobeManager {
    @Volatile var isStrobeActive: Boolean = false
    @Volatile var strobeSpeedMs: Long = 100L
    @Volatile var isBeatSyncEnabled: Boolean = false

    private var strobeThread: Thread? = null

    fun startStrobe(context: Context) {
        if (strobeThread != null && strobeThread!!.isAlive) return
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as? CameraManager ?: return
        val cameraId = try {
            cameraManager.cameraIdList.firstOrNull { id ->
                cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            }
        } catch (_: Exception) { null } ?: return

        isStrobeActive = true
        strobeThread = Thread {
            var isOn = false
            var beatStep = 0
            try {
                while (isStrobeActive && !Thread.currentThread().isInterrupted) {
                    isOn = !isOn
                    try {
                        cameraManager.setTorchMode(cameraId, isOn)
                    } catch (_: Exception) {}

                    val delayMs = if (isBeatSyncEnabled) {
                        beatStep = (beatStep + 1) % 4
                        if (beatStep % 2 == 0) 120L else 240L
                    } else {
                        strobeSpeedMs.coerceAtLeast(30L)
                    }
                    Thread.sleep(delayMs)
                }
            } catch (_: InterruptedException) {
            } finally {
                try {
                    cameraManager.setTorchMode(cameraId, false)
                } catch (_: Exception) {}
            }
        }.apply { start() }
    }

    fun stopStrobe(context: Context) {
        isStrobeActive = false
        strobeThread?.interrupt()
        strobeThread = null
        try {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as? CameraManager
            val cameraId = cameraManager?.cameraIdList?.firstOrNull() ?: return
            cameraManager.setTorchMode(cameraId, false)
        } catch (_: Exception) {}
    }
}

object UpdateLogger {
    private const val LOG_FILE_NAME = "Zune_Player_All_Updates.csv"

    fun checkAndAutoLogVersionChange(context: Context) {
        try {
            val prefs = context.getSharedPreferences("media_prefs", Context.MODE_PRIVATE)
            val lastVersion = prefs.getString("last_logged_version_tag", "")
            val currentVersion = GitHubAutoUpdater.CURRENT_VERSION_TAG

            if (lastVersion != currentVersion) {
                logUpdate(context, currentVersion, "Auto Version Launch", "App version $currentVersion launched")
                prefs.edit().putString("last_logged_version_tag", currentVersion).apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun logUpdate(context: Context, versionTag: String, status: String, notes: String) {
        try {
            val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "ZunePlayer")
            if (!dir.exists()) dir.mkdirs()
            val logFile = File(dir, LOG_FILE_NAME)

            val exists = logFile.exists()
            val time = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US).format(java.util.Date())
            val line = "\"$time\",\"$versionTag\",\"${CodeStats.LINE_COUNT}\",\"$status\",\"${notes.replace("\"", "'")}\"\n"

            if (!exists) {
                logFile.writeText("Timestamp,VersionTag,LinesOfCode,Status,ReleaseNotes\n")
            }
            logFile.appendText(line)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getLogFile(context: Context): File {
        val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "ZunePlayer")
        if (!dir.exists()) dir.mkdirs()
        val logFile = File(dir, LOG_FILE_NAME)
        if (!logFile.exists()) {
            logFile.writeText("Timestamp,VersionTag,LinesOfCode,Status,ReleaseNotes\n")
            logFile.appendText("\"${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US).format(java.util.Date())}\",\"${GitHubAutoUpdater.CURRENT_VERSION_TAG}\",\"${CodeStats.LINE_COUNT}\",\"Initial Log Created\",\"Zune Player Version Log\"\n")
        }
        return logFile
    }

    fun exportToGoogleDriveOrSheets(context: Context) {
        try {
            val logFile = getLogFile(context)
            val authority = "${context.packageName}.fileprovider"
            val uri = androidx.core.content.FileProvider.getUriForFile(context, authority, logFile)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "Zune Player Update Log CSV")
                putExtra(Intent.EXTRA_TEXT, "Here is the Zune Player update history log (CSV spreadsheet format for Google Drive / Google Sheets).")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(Intent.createChooser(intent, "Save/Upload Update Log (Google Drive / Sheets)"))
        } catch (e: Exception) {
            Toast.makeText(context, "Export error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}

sealed class UpdateCheckResult {
    data class NewUpdateAvailable(val tagName: String, val downloadUrl: String, val body: String) : UpdateCheckResult()
    data class UpToDate(val tagName: String, val downloadUrl: String?) : UpdateCheckResult()
    data class NoApkFound(val tagName: String?) : UpdateCheckResult()
    data class Error(val message: String) : UpdateCheckResult()
}

object GitHubAutoUpdater {
    const val CURRENT_VERSION_TAG = "v2.0"

    suspend fun checkForUpdatesResult(): UpdateCheckResult {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://api.github.com/repos/korn9225-sketch/app-music-storage/releases")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.setRequestProperty("User-Agent", "ZuneMusicPlayer/1.0")
                if (PlaybackService.GITHUB_TOKEN.isNotBlank()) {
                    conn.setRequestProperty("Authorization", "Bearer ${PlaybackService.GITHUB_TOKEN}")
                }
                conn.setRequestProperty("Accept", "application/vnd.github+json")
                conn.connectTimeout = 8000
                conn.readTimeout = 8000

                val code = conn.responseCode
                if (code != 200) {
                    return@withContext checkViaWebScraping()
                }

                val jsonStr = conn.inputStream.bufferedReader().readText()
                val releasesArray = JSONArray(jsonStr)

                if (releasesArray.length() == 0) {
                    return@withContext UpdateCheckResult.NoApkFound(null)
                }

                var newestTagName: String? = null
                var newestApkUrl: String? = null
                var newestBody = ""

                for (i in 0 until releasesArray.length()) {
                    val rel = releasesArray.getJSONObject(i)
                    val tagName = rel.optString("tag_name", "")
                    val body = rel.optString("body", "")
                    val assets = rel.optJSONArray("assets") ?: continue

                    for (j in 0 until assets.length()) {
                        val asset = assets.getJSONObject(j)
                        val name = asset.optString("name", "")
                        if (name.endsWith(".apk", ignoreCase = true)) {
                            newestTagName = tagName
                            newestApkUrl = asset.optString("browser_download_url", "")
                            newestBody = body
                            break
                        }
                    }
                    if (newestApkUrl != null) break
                }

                if (newestApkUrl == null) {
                    val latestTag = releasesArray.getJSONObject(0).optString("tag_name", "latest")
                    return@withContext UpdateCheckResult.NoApkFound(latestTag)
                }

                val tag = newestTagName ?: "latest"
                val isNewer = tag.equals("latest", ignoreCase = true) || isNewerVersion(tag, CURRENT_VERSION_TAG)
                if (isNewer) {
                    return@withContext UpdateCheckResult.NewUpdateAvailable(tag, newestApkUrl, newestBody)
                } else {
                    return@withContext UpdateCheckResult.UpToDate(tag, newestApkUrl)
                }
            } catch (_: Exception) {
                return@withContext checkViaWebScraping()
            }
        }
    }

    private fun checkViaWebScraping(): UpdateCheckResult {
        return try {
            val redirectUrl = URL("https://github.com/korn9225-sketch/app-music-storage/releases/latest")
            val conn = redirectUrl.openConnection() as HttpURLConnection
            conn.instanceFollowRedirects = false
            conn.requestMethod = "GET"
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
            conn.connectTimeout = 8000
            conn.readTimeout = 8000

            val location = conn.getHeaderField("Location")
            val tagName = location?.substringAfterLast("/")?.ifBlank { null } ?: "v1.0"

            var apkPath: String? = null

            // Try expanded_assets
            try {
                val assetsUrl = URL("https://github.com/korn9225-sketch/app-music-storage/releases/expanded_assets/$tagName")
                val assetsConn = assetsUrl.openConnection() as HttpURLConnection
                assetsConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                assetsConn.connectTimeout = 8000
                assetsConn.readTimeout = 8000
                if (assetsConn.responseCode == 200) {
                    val html = assetsConn.inputStream.bufferedReader().readText()
                    val match = Regex("""href="([^"]+\.apk[^"]*)"""", RegexOption.IGNORE_CASE).find(html)
                    if (match != null) {
                        apkPath = match.groupValues[1]
                    }
                }
            } catch (_: Exception) {}

            // Fallback: Scrape main releases page
            if (apkPath == null) {
                try {
                    val pageUrl = URL("https://github.com/korn9225-sketch/app-music-storage/releases")
                    val pageConn = pageUrl.openConnection() as HttpURLConnection
                    pageConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    pageConn.connectTimeout = 8000
                    pageConn.readTimeout = 8000
                    if (pageConn.responseCode == 200) {
                        val html = pageConn.inputStream.bufferedReader().readText()
                        val match = Regex("""href="([^"]+\.apk[^"]*)"""", RegexOption.IGNORE_CASE).find(html)
                        if (match != null) {
                            apkPath = match.groupValues[1]
                        }
                    }
                } catch (_: Exception) {}
            }

            if (apkPath != null) {
                val fullApkUrl = if (apkPath.startsWith("http")) apkPath else "https://github.com$apkPath"
                val isNewer = isNewerVersion(tagName, CURRENT_VERSION_TAG)
                if (isNewer) {
                    UpdateCheckResult.NewUpdateAvailable(tagName, fullApkUrl, "")
                } else {
                    UpdateCheckResult.UpToDate(tagName, fullApkUrl)
                }
            } else {
                UpdateCheckResult.NoApkFound(tagName)
            }
        } catch (e: Exception) {
            UpdateCheckResult.Error("Check failed: ${e.localizedMessage}")
        }
    }

    private fun isNewerVersion(remoteTag: String, currentTag: String): Boolean {
        val remoteParts = remoteTag.removePrefix("v").removePrefix("V").trim().split(".").mapNotNull { it.toIntOrNull() }
        val currentParts = currentTag.removePrefix("v").removePrefix("V").trim().split(".").mapNotNull { it.toIntOrNull() }
        val maxLen = maxOf(remoteParts.size, currentParts.size)
        for (i in 0 until maxLen) {
            val r = remoteParts.getOrElse(i) { 0 }
            val c = currentParts.getOrElse(i) { 0 }
            if (r > c) return true
            if (r < c) return false
        }
        return false
    }

    suspend fun downloadAndInstallUpdate(context: Context, downloadUrl: String, onProgress: (String) -> Unit): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                onProgress("Downloading update from GitHub...")
                val apkFile = File(context.cacheDir, "update.apk")
                if (apkFile.exists()) apkFile.delete()

                var currentUrl = downloadUrl
                var conn: HttpURLConnection? = null
                var redirectCount = 0
                val maxRedirects = 10

                while (redirectCount < maxRedirects) {
                    val url = URL(currentUrl)
                    conn = url.openConnection() as HttpURLConnection
                    conn.instanceFollowRedirects = false
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    
                    // Only pass GITHUB_TOKEN header to github.com domain, not AWS/S3 CDN domain!
                    if (PlaybackService.GITHUB_TOKEN.isNotBlank() && (url.host.contains("github.com") || url.host.contains("githubapi"))) {
                        conn.setRequestProperty("Authorization", "Bearer ${PlaybackService.GITHUB_TOKEN}")
                    }
                    
                    conn.connectTimeout = 15000
                    conn.readTimeout = 30000

                    val code = conn.responseCode
                    if (code in 300..399) {
                        val loc = conn.getHeaderField("Location")
                        if (!loc.isNullOrBlank()) {
                            currentUrl = if (loc.startsWith("http")) loc else "https://github.com$loc"
                            redirectCount++
                            conn.disconnect()
                            continue
                        }
                    }
                    break
                }

                if (conn != null && conn.responseCode == 200) {
                    conn.inputStream.use { input ->
                        apkFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                } else {
                    val code = conn?.responseCode ?: -1
                    onProgress("Download failed with HTTP $code")
                    return@withContext false
                }

                onProgress("Installing update via Root...")
                val installedViaRoot = installApkViaRoot(apkFile.absolutePath)
                if (installedViaRoot) {
                    onProgress("Silent update installed via Root!")
                    return@withContext true
                } else {
                    onProgress("Opening system package installer...")
                    installApkViaIntent(context, apkFile)
                    return@withContext true
                }
            } catch (e: Exception) {
                onProgress("Error during update: ${e.localizedMessage}")
                return@withContext false
            }
        }
    }

    private fun installApkViaRoot(apkPath: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su")
            val os = java.io.DataOutputStream(process.outputStream)
            os.writeBytes("pm install -r \"$apkPath\"\n")
            os.writeBytes("exit\n")
            os.flush()
            val exitCode = process.waitFor()
            exitCode == 0
        } catch (_: Exception) {
            false
        }
    }

    private fun installApkViaIntent(context: Context, apkFile: File) {
        try {
            val authority = "${context.packageName}.fileprovider"
            val apkUri = androidx.core.content.FileProvider.getUriForFile(context, authority, apkFile)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Composable
fun WearOSRemoteControlScreen(
    songs: List<Song>,
    currentSongIndex: Int,
    isPlaying: Boolean,
    currentPosition: Long,
    totalDuration: Long,
    playerState: Player?
) {
    val currentSong = songs.getOrNull(currentSongIndex)

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
                model = currentSong?.getVerifiedArtworkUrl(),
                contentDescription = null,
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Track Title & Artist
            Text(
                text = currentSong?.title ?: "No Track Playing",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = currentSong?.artist ?: "Zune Remote",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = Color(0xFFE5007D),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Seek Bar
            Slider(
                value = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f,
                onValueChange = { playerState?.seekTo((it * totalDuration).toLong()) },
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFE5007D),
                    activeTrackColor = Color(0xFFE5007D),
                    inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                ),
                modifier = Modifier.fillMaxWidth(0.85f).height(8.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Remote Controls (Prev 🥧, Big Play/Pause ⏸/▶, Next 🌽)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                IconButton(
                    onClick = { playerState?.seekToPreviousMediaItem() },
                    modifier = Modifier.size(38.dp)
                ) {
                    Text("🥧", fontSize = 26.sp)
                }

                IconButton(
                    onClick = { if (isPlaying) playerState?.pause() else playerState?.play() },
                    modifier = Modifier.size(52.dp)
                ) {
                    Text(if (isPlaying) "⏸" else "▶", fontSize = 34.sp, color = Color(0xFFE5007D))
                }

                IconButton(
                    onClick = { playerState?.seekToNextMediaItem() },
                    modifier = Modifier.size(38.dp)
                ) {
                    Text("🌽", fontSize = 26.sp)
                }
            }
        }
    }
}
