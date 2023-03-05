package com.company.voicenotes.data.player

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.voicenotes.R
import com.company.voicenotes.di.ApplicationScope
import java.io.File
import javax.inject.Inject

@ApplicationScope
class AndroidAudioPlayer @Inject constructor(
    private val context: Context,
) : AudioPlayer {

    private var prevFileName = ""
    private val completedAudio = MutableLiveData("")
    private val player = MediaPlayer()

    override fun playAudio(fileName: String) {
        val file = File(context.filesDir, "$fileName.mp3")
        if (prevFileName != fileName) {
            stop()
            player.apply {
                try {
                    setErrorListener()
                    setCompletionListener()
                    setDataSource(context, file.toUri())
                    prepare()
                } catch (_: Exception) {}
            }
        }
        player.start()
        prevFileName = fileName
    }

    private fun setCompletionListener() {
        player.setOnCompletionListener {
            completedAudio.value = prevFileName
        }
    }

    fun setErrorListener() {
        player.setOnErrorListener { player, i, i2 ->
            Toast.makeText(context, context.getString(R.string.player_error_text), Toast.LENGTH_SHORT).show()
            player.reset()
            false
        }
    }

    override fun pause() {
        player.pause()
    }

    override fun stop() {
        player.stop()
        player.reset()
    }

    override fun getCompletionLiveData(): LiveData<String> {
        return completedAudio
    }
}