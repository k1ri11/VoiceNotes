package com.company.voicenotes.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.voicenotes.data.player.AudioPlayer
import com.company.voicenotes.data.recorder.AudioRecorder
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val player: AudioPlayer,
    private val recorder: AudioRecorder,
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            return RecordViewModel(player = player, recorder = recorder) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}