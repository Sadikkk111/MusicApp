package com.example.musicappjetpackcompose

import android.os.Bundle
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicAppTheme {
                MusicPlayerScreen()
            }
        }
    }
}

@Composable
fun MusicPlayerScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(25f) }
    var volume by remember { mutableFloatStateOf(70f) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A0033),
                        Color(0xFF4A0072)
                    )
                )
            )
    ) {
        if (isLandscape) {
            LandscapeLayout(
                isPlaying = isPlaying,
                progress = progress,
                volume = volume,
                onPlayPauseToggle = { isPlaying = !isPlaying },
                onProgressChange = { progress = it },
                onVolumeChange = { volume = it }
            )
        } else {
            PortraitLayout(
                isPlaying = isPlaying,
                progress = progress,
                volume = volume,
                onPlayPauseToggle = { isPlaying = !isPlaying },
                onProgressChange = { progress = it },
                onVolumeChange = { volume = it }
            )
        }
    }
}

@Composable
fun PortraitLayout(
    isPlaying: Boolean,
    progress: Float,
    volume: Float,
    onPlayPauseToggle: () -> Unit,
    onProgressChange: (Float) -> Unit,
    onVolumeChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlbumArtSection()

        Spacer(modifier = Modifier.height(20.dp))

        ControlsSection(
            isPlaying = isPlaying,
            progress = progress,
            volume = volume,
            onPlayPauseToggle = onPlayPauseToggle,
            onProgressChange = onProgressChange,
            onVolumeChange = onVolumeChange
        )
    }
}

@Composable
fun LandscapeLayout(
    isPlaying: Boolean,
    progress: Float,
    volume: Float,
    onPlayPauseToggle: () -> Unit,
    onProgressChange: (Float) -> Unit,
    onVolumeChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(
                        color = Color(0xFF6200EE),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.album_cover),
                    contentDescription = "Album Cover",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "BOHEMIAN RHAPSODY",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Queen",
                    color = Color(0xFFE0B0FF),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.width(32.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0x40000000),
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProgressSection(
                        progress = progress,
                        onProgressChange = onProgressChange
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    PlaybackControlsSection(
                        isPlaying = isPlaying,
                        onPlayPauseToggle = onPlayPauseToggle
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    VolumeSection(
                        volume = volume,
                        onVolumeChange = onVolumeChange
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AdditionalControlsSection()
                }
            }
        }
    }
}

@Composable
fun AlbumArtSection() {
    Box(
        modifier = Modifier
            .size(280.dp)
            .background(
                color = Color(0xFF6200EE),
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.album_cover),
            contentDescription = "Album Cover",
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ControlsSection(
    isPlaying: Boolean,
    progress: Float,
    volume: Float,
    onPlayPauseToggle: () -> Unit,
    onProgressChange: (Float) -> Unit,
    onVolumeChange: (Float) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0x40000000),
                shape = MaterialTheme.shapes.large
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SongInfoSection()

            Spacer(modifier = Modifier.height(16.dp))

            ProgressSection(
                progress = progress,
                onProgressChange = onProgressChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            PlaybackControlsSection(
                isPlaying = isPlaying,
                onPlayPauseToggle = onPlayPauseToggle
            )

            Spacer(modifier = Modifier.height(24.dp))

            VolumeSection(
                volume = volume,
                onVolumeChange = onVolumeChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            AdditionalControlsSection()
        }
    }
}

@Composable
fun SongInfoSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "BOHEMIAN RHAPSODY",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Queen",
            color = Color(0xFFE0B0FF),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProgressSection(
    progress: Float,
    onProgressChange: (Float) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            value = progress,
            onValueChange = onProgressChange,
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color(0xFFBB86FC),
                inactiveTrackColor = Color(0xFFBB86FC).copy(alpha = 0.3f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "1:00",
                color = Color(0xFFE0B0FF),
                fontSize = 12.sp
            )

            Text(
                text = "-3:00",
                color = Color(0xFFE0B0FF),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun PlaybackControlsSection(
    isPlaying: Boolean,
    onPlayPauseToggle: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /* Previous song */ },
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.previous),
                contentDescription = "Previous",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        IconButton(
            onClick = onPlayPauseToggle,
            modifier = Modifier
                .size(72.dp)
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.extraLarge
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (isPlaying)
                        R.drawable.pause
                    else
                        R.drawable.play
                ),
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = Color(0xFF6200EE),
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        IconButton(
            onClick = { /* Next song */ },
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.next),
                contentDescription = "Next",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun VolumeSection(
    volume: Float,
    onVolumeChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.volume),
            contentDescription = "Volume",
            tint = Color(0xFFBB86FC),
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Slider(
            value = volume,
            onValueChange = onVolumeChange,
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color(0xFFBB86FC),
                inactiveTrackColor = Color(0xFFBB86FC).copy(alpha = 0.3f)
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AdditionalControlsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { /* Share */ },
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = Color(0x30BB86FC),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.share),
                contentDescription = "Share",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        IconButton(
            onClick = { /* View lyrics */ },
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = Color(0x30BB86FC),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.lyrics),
                contentDescription = "Lyrics",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        IconButton(
            onClick = { /* View queue */ },
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = Color(0x30BB86FC),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.queue),
                contentDescription = "Queue",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun MusicAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MusicPlayerPreview() {
    MusicAppTheme {
        MusicPlayerScreen()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=1280dp,height=720dp,dpi=480"
)
@Composable
fun MusicPlayerPreviewLandscape() {
    MusicAppTheme {
        MusicPlayerScreen()
    }
}