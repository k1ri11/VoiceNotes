package com.company.voicenotes.di

import android.content.Context
import com.company.voicenotes.model.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

annotation class ApplicationScope

@ApplicationScope
@Component(modules = [AppModule::class])
interface AppComponent {
    val appContext: Context
    val viewModelFactory: ViewModelFactory

    fun recorderFragmentComponent(): RecorderFragmentComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appContext: Context,
        ): AppComponent
    }}