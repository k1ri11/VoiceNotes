package com.company.voicenotes.data.player

import androidx.lifecycle.LiveData

interface AudioPlayer {

    fun playAudio(fileName: String)
    fun pause()
    fun stop()
    fun getCompletionLiveData(): LiveData<String>
}