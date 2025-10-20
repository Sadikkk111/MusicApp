package com.example.musicapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isPlaying = false
    private lateinit var playPauseButton: ImageButton
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var currentTime: TextView
    private lateinit var remainingTime: TextView
    private lateinit var progressBar: SeekBar
    private lateinit var volumeBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        playPauseButton = findViewById(R.id.playPauseButton)
        songTitle = findViewById(R.id.songTitle)
        artistName = findViewById(R.id.artistName)
        currentTime = findViewById(R.id.currentTime)
        remainingTime = findViewById(R.id.remainingTime)
        progressBar = findViewById(R.id.progressBar)
        volumeBar = findViewById(R.id.volumeBar)

        // Configurar valores iniciales
        progressBar.progress = 25
        volumeBar.progress = 70
        updateTimeDisplays()
    }

    private fun setupListeners() {
        playPauseButton.setOnClickListener {
            togglePlayPause()
        }

        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    updateTimeDisplays()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        volumeBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualizar volumen
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Otros listeners para los botones...
        findViewById<ImageButton>(R.id.prevButton).setOnClickListener {
            // Ir a canción anterior
        }

        findViewById<ImageButton>(R.id.nextButton).setOnClickListener {
            // Ir a siguiente canción
        }

        findViewById<ImageButton>(R.id.shareButton).setOnClickListener {
            // Compartir canción
        }

        findViewById<ImageButton>(R.id.lyricsButton).setOnClickListener {
            // Ver letra
        }

        findViewById<ImageButton>(R.id.queueButton).setOnClickListener {
            // Ver cola de reproducción
        }
    }

    private fun togglePlayPause() {
        isPlaying = !isPlaying
        if (isPlaying) {
            playPauseButton.setImageResource(R.drawable.ic_pause)  // ← Cambiado
            // Simular reproducción
        } else {
            playPauseButton.setImageResource(R.drawable.ic_play)   // ← Cambiado
            // Simular pausa
        }
    }

    private fun updateTimeDisplays() {
        val totalTime = 240 // 4 minutos en segundos
        val currentProgress = progressBar.progress
        val currentSeconds = (totalTime * currentProgress) / 100
        val remainingSeconds = totalTime - currentSeconds

        currentTime.text = formatTime(currentSeconds)
        remainingTime.text = "-${formatTime(remainingSeconds)}"
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%d:%02d", minutes, secs)
    }
}