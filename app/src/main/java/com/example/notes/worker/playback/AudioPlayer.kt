package com.example.notes.worker.playback

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()

}