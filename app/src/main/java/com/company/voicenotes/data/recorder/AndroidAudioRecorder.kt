package com.company.voicenotes.data.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.company.voicenotes.R
import com.company.voicenotes.di.ApplicationScope
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ApplicationScope
class AndroidAudioRecorder @Inject constructor(
    private val context: Context,
) : AudioRecorder {

    private lateinit var recorder: MediaRecorder
    lateinit var file: File

    init {
        createRecorder()
    }

    private fun createRecorder() {
        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    override fun start() {
        val format = SimpleDateFormat("dd_MM_yyyy HH_mm_ss", Locale.ROOT)
        val dateName = format.format(Calendar.getInstance().time)
        file = File(context.filesDir, "$dateName.mp3")
        recorder.apply {
            try {
                setErrorlistener()
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(FileOutputStream(file).fd)
                prepare()
                start()
            }catch (_:Exception){}
        }

    }

    private fun setErrorlistener() {
        recorder.setOnErrorListener { recorder, i, i2 ->
            Toast.makeText(context, context.getString(R.string.recorder_error_text), Toast.LENGTH_SHORT).show()
            recorder.reset()
        }
    }

    override fun stop(newName: String) {
        if (newName.isNotEmpty()) {
            val tmpFile = File(context.filesDir, "$newName.mp3")
            file.renameTo(tmpFile)
        }
        recorder.stop()
        recorder.reset()
    }
}