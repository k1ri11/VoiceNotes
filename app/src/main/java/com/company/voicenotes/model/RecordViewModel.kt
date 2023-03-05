package com.company.voicenotes.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.company.voicenotes.data.player.AudioPlayer
import com.company.voicenotes.data.recorder.AudioRecorder
import java.io.File

class RecordViewModel(
    private val player: AudioPlayer,
    private val recorder: AudioRecorder
): ViewModel() {

    val completedAudio = player.getCompletionLiveData()

    fun pausePlaying(){
        player.pause()
    }

    fun playAudio(fileName: String){
        player.playAudio(fileName)
    }

    fun startRecording(){
        recorder.start()
    }

    fun stopRecording(newName: String = ""){
        recorder.stop(newName)
    }

}