package com.example.notes.worker
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.provider.MediaStore.Audio.Media
import java.io.File
import java.io.FileOutputStream

interface recorder {

    fun start(outputFile : File)
    fun stop()
}