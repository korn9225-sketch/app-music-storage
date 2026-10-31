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
import androidx.activity.compose.rememberLauncherForActivityResult
import android.provider.MediaStore
import android.net.Uri
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.edit
import android.app.DownloadManager
import android.os.Environment
import java.io.File
import androidx.compose.material.icons.filled.Download
import android.app.Application
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.drawable.toBitmap
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.activity.compose.BackHandler
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.graphicsLayer
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
        "hatebreed" -> "https://img.youtube.com/vi/Xigg9UjWJNg/hqdefault.jpg"
        "mudvayne" -> "https://i.ytimg.com/vi/s0M3_o4P2uY/hqdefault.jpg"
        "blender" -> "https://upload.wikimedia.org/wikipedia/commons/c/c5/Big_buck_bunny_poster_small.jpg"
        "google" -> "https://i.ytimg.com/vi/aqz-KE-bpKQ/hqdefault.jpg"
        "warner bros" -> "https://upload.wikimedia.org/wikipedia/en/2/2b/Constantine_poster.jpg"
        else -> "https://github.com/korn9225-sketch/app-music-storage/releases/download/v1.0/Folder.jpg"
    }
}

enum class MusicTab(val title: String) {
    NowPlaying("now playing"),
    Music("music"),
    Video("video"),
    Settings("settings")
}

enum class MusicSubTab(val title: String) {
    Songs("songs"),
    Artists("artists"),
    Albums("albums")
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
                    
                    // Load saved local media
                    val savedUris = sharedPrefs.getStringSet("local_media_uris", emptySet()) ?: emptySet()
                    savedUris.forEach { uriString ->
                        val uri = Uri.parse(uriString)
                        initialList.add(getSongFromUri(context, uri))
                    }
                    initialList
                }

                val viewModel: MusicViewModel = viewModel()

                LaunchedEffect(Unit) {
                    viewModel.initController(context, songs)
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

                val handleBackPress = {
                    when {
                        selectedArtist != null -> selectedArtist = null
                        selectedAlbum != null -> selectedAlbum = null
                        selectedMusicSubTab != MusicSubTab.Songs -> selectedMusicSubTab = MusicSubTab.Songs
                        selectedTab != MusicTab.NowPlaying -> selectedTab = MusicTab.NowPlaying
                        else -> {
                            Toast.makeText(context, "Hold back to exit", Toast.LENGTH_SHORT).show()
                        }
                    }
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

                val songPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.OpenMultipleDocuments()
                ) { uris ->
                    uris.forEach { uri ->
                        try {
                            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        } catch (_: Exception) {
                            // Some providers don't support persistable permissions
                        }

                        val song = getSongFromUri(context, uri)
                        songs.add(song)
                        
                        // Save to persistence
                        val currentUris = sharedPrefs.getStringSet("local_media_uris", emptySet())?.toMutableSet() ?: mutableSetOf()
                        currentUris.add(uri.toString())
                        sharedPrefs.edit { putStringSet("local_media_uris", currentUris) }

                        playerState?.addMediaItem(
                            MediaItem.Builder()
                                .setMediaId(song.url)
                                .setUri(song.url.toUri())
                                .setMediaMetadata(
                                    MediaMetadata.Builder()
                                        .setTitle(song.title)
                                        .setArtist(song.artist)
                                        .build()
                                )
                                .build()
                        )
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
                                PrimaryTabRow(
                                    selectedTabIndex = selectedTab.ordinal,
                                    containerColor = Color.Black,
                                    contentColor = Color(0xFFE5007D),
                                    divider = {}
                                ) {
                                    MusicTab.entries.forEach { tab ->
                                        Tab(
                                            selected = selectedTab == tab,
                                            onClick = { 
                                                if (selectedTab == tab && tab == MusicTab.Music) {
                                                    selectedArtist = null
                                                    selectedAlbum = null
                                                }
                                                selectedTab = tab 
                                            },
                                            text = { 
                                                Text(
                                                    text = tab.title,
                                                    style = MaterialTheme.typography.headlineMedium.copy(
                                                        fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                                                        fontSize = 24.sp,
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
                                if (isHalloweenSeasonActive()) {
                                    DrippingBloodTabOverlay()
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
                                onClick = { selectedTab = MusicTab.NowPlaying },
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
                                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                                            ) {
                                                MusicSubTab.entries.forEach { subTab ->
                                                    Text(
                                                        text = subTab.title,
                                                        modifier = Modifier.clickable { selectedMusicSubTab = subTab },
                                                        style = MaterialTheme.typography.titleLarge.copy(
                                                            fontWeight = if (selectedMusicSubTab == subTab) FontWeight.Bold else FontWeight.Normal,
                                                            fontSize = 24.sp
                                                        ),
                                                        color = if (selectedMusicSubTab == subTab) Color(0xFFE5007D) else Color.White.copy(alpha = 0.4f)
                                                    )
                                                }
                                            }
                                        }

                                        when {
                                            selectedArtist != null -> ArtistDetailView(
                                                artist = selectedArtist!!,
                                                songs = songs,
                                                onBack = { selectedArtist = null },
                                                onSongClick = { song ->
                                                    val index = songs.indexOf(song)
                                                    if (index != -1) {
                                                        playerState?.seekTo(index, 0L)
                                                        playerState?.play()
                                                        selectedTab = MusicTab.NowPlaying
                                                    }
                                                },
                                                onDownloadClick = onDownloadClick
                                            )
                                            selectedAlbum != null -> AlbumDetailView(
                                                album = selectedAlbum!!,
                                                songs = songs,
                                                onBack = { selectedAlbum = null },
                                                onSongClick = { song ->
                                                    val index = songs.indexOf(song)
                                                    if (index != -1) {
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
                                                        playerState?.seekTo(index, 0L)
                                                        playerState?.play()
                                                        selectedTab = MusicTab.NowPlaying
                                                    }, onDownloadClick = onDownloadClick)
                                                    MusicSubTab.Artists -> ArtistList(songs, onArtistClick = { selectedArtist = it })
                                                    MusicSubTab.Albums -> AlbumList(songs, onAlbumClick = { selectedAlbum = it })
                                                }
                                            }
                                        }
                                    }
                                }
                                MusicTab.Video -> VideoList(songs, onDownloadClick = onDownloadClick) { song ->
                                    val index = songs.indexOf(song)
                                    if (index != -1) {
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
                                    onAddLocalMusic = { songPickerLauncher.launch(arrayOf("*/*")) }
                                )
                            }
                        }
                    }
                }
                        // Library content
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
                                            // Sub-tab Navigation
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                                            ) {
                                                MusicSubTab.entries.forEach { subTab ->
                                                    Text(
                                                        text = subTab.title,
                                                        modifier = Modifier.clickable { selectedMusicSubTab = subTab },
                                                        style = MaterialTheme.typography.titleLarge.copy(
                                                            fontWeight = if (selectedMusicSubTab == subTab) FontWeight.Bold else FontWeight.Normal,
                                                            fontSize = 24.sp
                                                        ),
                                                        color = if (selectedMusicSubTab == subTab) Color(0xFFE5007D) else Color.White.copy(alpha = 0.4f)
                                                    )
                                                }
                                            }

                                            when (selectedMusicSubTab) {
                                                MusicSubTab.Songs -> SongList(songs, onSongClick = { index ->
                                                    playerState?.seekTo(index, 0L)
                                                    playerState?.play()
                                                    selectedTab = MusicTab.NowPlaying
                                                }, onDownloadClick = onDownloadClick)
                                                MusicSubTab.Artists -> ArtistList(songs, onArtistClick = { selectedArtist = it })
                                                MusicSubTab.Albums -> AlbumList(songs, onAlbumClick = { selectedAlbum = it })
                                            }
                                        } else if (selectedArtist != null) {
                                            ArtistDetailView(
                                                artist = selectedArtist!!,
                                                songs = songs,
                                                onBack = { selectedArtist = null },
                                                onSongClick = { song ->
                                                    val index = songs.indexOf(song)
                                                    if (index != -1) {
                                                        playerState?.seekTo(index, 0L)
                                                        playerState?.play()
                                                        selectedTab = MusicTab.NowPlaying
                                                    }
                                                },
                                                onDownloadClick = onDownloadClick
                                            )
                                        } else if (selectedAlbum != null) {
                                            AlbumDetailView(
                                                album = selectedAlbum!!,
                                                songs = songs,
                                                onBack = { selectedAlbum = null },
                                                onSongClick = { song ->
                                                    val index = songs.indexOf(song)
                                                    if (index != -1) {
                                                        playerState?.seekTo(index, 0L)
                                                        playerState?.play()
                                                        selectedTab = MusicTab.NowPlaying
                                                    }
                                                },
                                                onDownloadClick = onDownloadClick
                                            )
                                        }
                                    }
                                }
                                MusicTab.Video -> VideoList(songs, onDownloadClick = onDownloadClick) { song ->
                                    val index = songs.indexOf(song)
                                    if (index != -1) {
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
                                    onAddLocalMusic = { songPickerLauncher.launch(arrayOf("*/*")) }
                                )
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

        val spookyTextColor = if (isHalloweenSeasonActive()) Color(0xFFFF3300).copy(alpha = 0.85f) else Color.White

        // 3. Kinetic Typography Layer
        Text(
            text = artist.uppercase(),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = 220.sp,
                letterSpacing = (-14).sp
            ),
            color = spookyTextColor,
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
            color = spookyTextColor,
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

        // Spooky Halloween Floating Particles Layer (Active until Nov 15, 2026 12:00 AM EST)
        SpookyFloatingParticlesOverlay()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                    if (currentSong != null && !currentSong.url.startsWith("content://")) {
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
                                AsyncImage(
                                    model = song?.getVerifiedArtworkUrl() ?: R.drawable.kornimage,
                                    contentDescription = "Album Art",
                                    modifier = Modifier
                                        .size(260.dp)
                                        .border(0.5.dp, Color.White.copy(alpha = 0.1f)),
                                    contentScale = ContentScale.Crop
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
                    // Minimalist Seek Bar
                    Column(modifier = Modifier.padding(horizontal = 48.dp)) {
                        Slider(
                            value = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f,
                            onValueChange = { playerState?.seekTo((it * totalDuration).toLong()) },
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFFE5007D),
                                activeTrackColor = Color(0xFFE5007D),
                                inactiveTrackColor = Color.White.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.height(12.dp)
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
                        horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterHorizontally)
                    ) {
                        IconButton(onClick = { playerState?.seekToPreviousMediaItem() }) {
                            Icon(
                                Icons.Default.SkipPrevious,
                                contentDescription = "Previous",
                                modifier = Modifier.size(40.dp),
                                tint = Color.Blue
                            )
                        }
                        
                        if (isHalloweenPumpkinActive()) {
                            ScaryPumpkinPlayPauseButton(
                                isPlaying = isPlaying,
                                onClick = { if (isPlaying) playerState?.pause() else playerState?.play() }
                            )
                        } else {
                            Surface(
                                onClick = { if (isPlaying) playerState?.pause() else playerState?.play() },
                                shape = androidx.compose.foundation.shape.CircleShape,
                                color = Color(0xFFE5007D),
                                modifier = Modifier.size(80.dp),
                                tonalElevation = 8.dp
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = "Play/Pause",
                                        modifier = Modifier.size(44.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }

                        IconButton(onClick = { playerState?.seekToNextMediaItem() }) {
                            Icon(
                                Icons.Default.SkipNext,
                                contentDescription = "Next",
                                modifier = Modifier.size(40.dp),
                                tint = Color.Red
                            )
                        }
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
}

fun isHalloweenPumpkinActive(): Boolean {
    val cutoffEst = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("America/New_York")).apply {
        set(2026, java.util.Calendar.NOVEMBER, 15, 0, 0, 0)
        set(java.util.Calendar.MILLISECOND, 0)
    }
    return System.currentTimeMillis() < cutoffEst.timeInMillis
}

fun isHalloweenSeasonActive(): Boolean = isHalloweenPumpkinActive()

@Composable
fun SpookyFloatingParticlesOverlay() {
    if (!isHalloweenSeasonActive()) return

    val infiniteTransition = rememberInfiniteTransition(label = "SpookyFloat")
    val floatAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spookyFloat"
    )

    val swayAnim by infiniteTransition.animateFloat(
        initialValue = -25f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "spookySway"
    )

    val bloodPulse by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bloodPulse"
    )

    // Blood-Red & Spooky Purple Ambient Fog Layer
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                androidx.compose.ui.graphics.Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFF0033).copy(alpha = bloodPulse),
                        Color(0xFF8A2BE2).copy(alpha = bloodPulse * 0.6f),
                        Color.Transparent
                    )
                )
            )
    )

    val spookyEntities = listOf("🎃", "👻", "🦇", "🕷️", "💀", "🧛", "🩸", "🕸️", "🎃", "👻", "🦇", "💀")

    Box(modifier = Modifier.fillMaxSize()) {
        spookyEntities.forEachIndexed { idx, emoji ->
            val xOffset = ((idx * 80 + 20) % 360).dp + (swayAnim * (if (idx % 2 == 0) 1f else -1f)).dp
            val yProgress = (floatAnim + (idx * 0.08f)) % 1.0f

            Text(
                text = emoji,
                fontSize = (24 + (idx % 4) * 6).sp,
                modifier = Modifier
                    .offset(x = xOffset, y = (yProgress * 850).dp)
                    .graphicsLayer {
                        alpha = (1.0f - yProgress).coerceIn(0.15f, 0.85f)
                        rotationZ = swayAnim * (if (idx % 2 == 0) 0.5f else -0.5f)
                    }
            )
        }
    }
}

@Composable
fun DrippingBloodTabOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "BloodDrip")
    val dripProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "dripProgress"
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    ) {
        val w = size.width
        val bloodColor = Color(0xFFCC0000)
        val darkBloodColor = Color(0xFF880E4F)

        val bloodPath = androidx.compose.ui.graphics.Path().apply {
            moveTo(0f, 0f)
            lineTo(w, 0f)
            lineTo(w, 6f)

            val dripPoints = listOf(
                0.05f to 12f, 0.12f to 22f, 0.18f to 8f, 0.25f to 18f,
                0.32f to 10f, 0.40f to 24f, 0.48f to 12f, 0.55f to 20f,
                0.62f to 9f, 0.70f to 22f, 0.78f to 11f, 0.85f to 19f,
                0.92f to 8f, 0.98f to 15f
            )

            dripPoints.forEach { (xRatio, dripLen) ->
                val x = w * xRatio
                lineTo(x - 4f, 6f)
                quadraticTo(x, dripLen + 4f, x + 4f, 6f)
            }
            lineTo(w, 0f)
            close()
        }

        drawPath(bloodPath, color = bloodColor)

        val dropRatios = listOf(0.12f, 0.40f, 0.70f, 0.85f)
        dropRatios.forEachIndexed { idx, ratio ->
            val dropX = w * ratio
            val startY = 18f
            val maxDrip = 35f
            val dropProgress = (dripProgress + (idx * 0.25f)) % 1.0f
            val currentY = startY + (dropProgress * maxDrip)
            val dropAlpha = (1.0f - dropProgress).coerceIn(0f, 1f)

            drawCircle(
                color = darkBloodColor.copy(alpha = dropAlpha),
                radius = 3.5f * dropAlpha,
                center = Offset(dropX, currentY)
            )
        }
    }
}

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
fun ScaryPumpkinPlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(84.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Green Stem
            val stemPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.46f, h * 0.18f)
                lineTo(w * 0.50f, h * 0.05f)
                lineTo(w * 0.58f, h * 0.07f)
                lineTo(w * 0.54f, h * 0.20f)
                close()
            }
            drawPath(stemPath, color = Color(0xFF2E7D32))

            // Orange Pumpkin Body
            val pumpkinColor = Color(0xFFFF6F00)
            val shadowColor = Color(0xFFE65100)

            drawOval(
                color = shadowColor,
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.08f, h * 0.18f),
                size = androidx.compose.ui.geometry.Size(w * 0.84f, h * 0.72f)
            )
            drawOval(
                color = pumpkinColor,
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.22f, h * 0.16f),
                size = androidx.compose.ui.geometry.Size(w * 0.56f, h * 0.76f)
            )

            // Scary Eyes
            val eyeColor = if (isPlaying) Color(0xFFFFFF00) else Color(0xFF111111)
            val eyePathLeft = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.28f, h * 0.36f)
                lineTo(w * 0.40f, h * 0.36f)
                lineTo(w * 0.34f, h * 0.48f)
                close()
            }
            drawPath(eyePathLeft, color = eyeColor)

            val eyePathRight = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.60f, h * 0.36f)
                lineTo(w * 0.72f, h * 0.36f)
                lineTo(w * 0.66f, h * 0.48f)
                close()
            }
            drawPath(eyePathRight, color = eyeColor)

            // Scary Jagged Mouth
            val mouthPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.26f, h * 0.62f)
                lineTo(w * 0.36f, h * 0.72f)
                lineTo(w * 0.44f, h * 0.64f)
                lineTo(w * 0.50f, h * 0.74f)
                lineTo(w * 0.56f, h * 0.64f)
                lineTo(w * 0.64f, h * 0.72f)
                lineTo(w * 0.74f, h * 0.62f)
                lineTo(w * 0.66f, h * 0.82f)
                lineTo(w * 0.50f, h * 0.86f)
                lineTo(w * 0.34f, h * 0.82f)
                close()
            }
            drawPath(mouthPath, color = eyeColor)
        }

        // Overlay Play/Pause Icon
        Icon(
            if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = "Play/Pause",
            modifier = Modifier.size(36.dp),
            tint = if (isPlaying) Color.Black else Color.White
        )
    }
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
    onAddLocalMusic: () -> Unit
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

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onAddLocalMusic,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Icon(Icons.Default.VideoLibrary, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Local Media", color = Color.Black)
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
                text = "v1.0 ZuneBeta09012026",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
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
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexedGrid(songs) { index, song ->
            val fileName = song.url.substringAfterLast("/")
            val isDownloaded = remember(song.url) { 
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
            }
            
            ListItem(
                headlineContent = { Text(song.title) },
                supportingContent = { Text("${song.artist} • ${song.album}") },
                leadingContent = {
                    AsyncImage(
                        model = song.getVerifiedArtworkUrl(),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                },
                trailingContent = {
                    if (!song.url.startsWith("content://")) {
                        IconButton(onClick = { onDownloadClick(song) }) {
                            Icon(
                                imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                contentDescription = "Download",
                                tint = if (isDownloaded) MaterialTheme.colorScheme.primary else LocalContentColor.current
                            )
                        }
                    }
                },
                modifier = Modifier.clickable { onSongClick(index) }
            )
        }
    }
}

@Composable
fun VideoList(songs: List<Song>, onDownloadClick: (Song) -> Unit, onVideoClick: (Song) -> Unit) {
    val context = LocalContext.current
    val videos = remember(songs) { songs.filter { it.isVideo } }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(videos) { video ->
            val fileName = video.url.substringAfterLast("/")
            val isDownloaded = remember(video.url) { 
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
            }

            ListItem(
                headlineContent = { Text(video.title) },
                supportingContent = { Text(video.artist) },
                leadingContent = {
                    AsyncImage(
                        model = video.getVerifiedArtworkUrl(),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp).clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop
                    )
                },
                trailingContent = {
                    if (!video.url.startsWith("content://")) {
                        IconButton(onClick = { onDownloadClick(video) }) {
                            Icon(
                                imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                contentDescription = "Download",
                                tint = if (isDownloaded) MaterialTheme.colorScheme.primary else LocalContentColor.current
                            )
                        }
                    }
                },
                modifier = Modifier.clickable { onVideoClick(video) }
            )
        }
    }
}

@Composable
fun ArtistList(songs: List<Song>, onArtistClick: (String) -> Unit) {
    val artists = remember(songs) { songs.map { it.artist }.distinct().sorted() }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(artists) { artist ->
            ListItem(
                headlineContent = { Text(artist) },
                leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.clickable { onArtistClick(artist) }
            )
        }
    }
}

@Composable
fun ArtistDetailView(
    artist: String,
    songs: List<Song>,
    onBack: () -> Unit,
    onSongClick: (Song) -> Unit,
    onDownloadClick: (Song) -> Unit
) {
    val context = LocalContext.current
    val artistSongs = remember(artist, songs) { songs.filter { it.artist == artist } }
    val albums = remember(artistSongs) { artistSongs.map { it.album }.distinct() }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp).clickable { onBack() }
        ) {
            Icon(Icons.Default.SkipPrevious, contentDescription = "Back")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Back to Artists", style = MaterialTheme.typography.titleMedium)
        }
        
        Text(
            text = artist,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            item {
                Text("Albums", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
            }
            items(albums) { album ->
                ListItem(
                    headlineContent = { Text(album) },
                    leadingContent = { Icon(Icons.Default.Album, contentDescription = null) }
                )
            }
            item {
                Text("Songs", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
            }
            items(artistSongs) { song ->
                val fileName = song.url.substringAfterLast("/")
                val isDownloaded = remember(song.url) { 
                    File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
                }
                ListItem(
                    headlineContent = { Text(song.title) },
                    supportingContent = { Text(song.album) },
                    leadingContent = {
                        AsyncImage(
                            model = song.getVerifiedArtworkUrl(),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp).clip(MaterialTheme.shapes.small),
                            contentScale = ContentScale.Crop
                        )
                    },
                    trailingContent = {
                        if (!song.url.startsWith("content://")) {
                            IconButton(onClick = { onDownloadClick(song) }) {
                                Icon(
                                    imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                    contentDescription = "Download"
                                )
                            }
                        }
                    },
                    modifier = Modifier.clickable { onSongClick(song) }
                )
            }
        }
    }
}

@Composable
fun AlbumList(songs: List<Song>, onAlbumClick: (String) -> Unit) {
    val albums = remember(songs) { songs.map { it.album }.distinct().sorted() }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(albums) { album ->
            ListItem(
                headlineContent = { Text(album) },
                leadingContent = { Icon(Icons.Default.Album, contentDescription = null) },
                modifier = Modifier.clickable { onAlbumClick(album) }
            )
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
    val albumSongs = remember(album, songs) { songs.filter { it.album == album } }
    val albumArt = albumSongs.firstOrNull()?.artworkUrl

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp).clickable { onBack() }
        ) {
            Icon(Icons.Default.SkipPrevious, contentDescription = "Back")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Back to Albums", style = MaterialTheme.typography.titleMedium)
        }

        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = albumArt,
                contentDescription = null,
                modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = album, style = MaterialTheme.typography.headlineMedium)
                Text(text = albumSongs.firstOrNull()?.artist ?: "", style = MaterialTheme.typography.titleMedium)
            }
        }

        LazyColumn {
            items(albumSongs) { song ->
                val fileName = song.url.substringAfterLast("/")
                val isDownloaded = remember(song.url) { 
                    File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName).exists() 
                }
                ListItem(
                    headlineContent = { Text(song.title) },
                    leadingContent = { Icon(Icons.Default.Audiotrack, contentDescription = null) },
                    trailingContent = {
                        if (!song.url.startsWith("content://")) {
                            IconButton(onClick = { onDownloadClick(song) }) {
                                Icon(
                                    imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                    contentDescription = "Download"
                                )
                            }
                        }
                    },
                    modifier = Modifier.clickable { onSongClick(song) }
                )
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
            .height(64.dp)
            .clickable(onClick = onClick),
        color = Color.DarkGray.copy(alpha = 0.95f),
        tonalElevation = 8.dp
    ) {
        Column {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(2.dp),
                color = Color(0xFFFFA500),
                trackColor = Color.Transparent
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = song?.artworkUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song?.title ?: "No Song",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = song?.artist ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(onClick = onPlayPauseClick) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White
                    )
                }
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
            onBeatSyncToggle = {},
            onAddLocalMusic = {}
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

private fun getSongFromUri(context: Context, uri: Uri): Song {
    var title = "Unknown Title"
    var artist = "Unknown Artist"
    var isVideo = false
    
    val mimeType = context.contentResolver.getType(uri)
    if (mimeType?.startsWith("video/") == true) {
        isVideo = true
    }

    val projection = arrayOf(
        MediaStore.MediaColumns.TITLE,
        MediaStore.MediaColumns.ARTIST
    )
    try {
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val titleIndex = cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)
                val artistIndex = cursor.getColumnIndex(MediaStore.MediaColumns.ARTIST)
                if (titleIndex != -1) title = cursor.getString(titleIndex)
                if (artistIndex != -1) artist = cursor.getString(artistIndex)
            }
        }
    } catch (e: Exception) {
        title = uri.lastPathSegment ?: "Local Media"
    }
    return Song(title, artist, "Device", uri.toString(), "", isVideo = isVideo)
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
    var selectedMusicSubTab by mutableStateOf(MusicSubTab.Songs)
    var selectedArtist by mutableStateOf<String?>(null)
    var selectedAlbum by mutableStateOf<String?>(null)

    private var controllerFuture: ListenableFuture<MediaController>? = null

    fun initController(context: Context, songs: List<Song>) {
        if (player != null) return

        val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        
        controllerFuture?.addListener({
            val controller = controllerFuture?.get() ?: return@addListener
            player = controller
            
            val savedPrefs = context.getSharedPreferences("media_prefs", Context.MODE_PRIVATE)

            if (controller.mediaItemCount == 0) {
                val mediaItems = songs.map { song ->
                    val fileName = song.url.substringAfterLast("/")
                    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
                    val finalUri = if (file.exists()) Uri.fromFile(file) else song.url.toUri()
                    
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
