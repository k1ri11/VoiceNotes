package com.company.voicenotes.di

import android.content.Context
import com.company.voicenotes.data.player.AndroidAudioPlayer
import com.company.voicenotes.data.player.AudioPlayer
import com.company.voicenotes.data.recorder.AndroidAudioRecorder
import com.company.voicenotes.data.recorder.AudioRecorder
import dagger.Binds
import dagger.Module
import dagger.Provides

@ApplicationScope
@Module
interface AppModule{

    @ApplicationScope
    @Binds
    fun getRecorder(recorder: AndroidAudioRecorder): AudioRecorder

    @ApplicationScope
    @Binds
    fun getPlayer(player: AndroidAudioPlayer): AudioPlayer
}
