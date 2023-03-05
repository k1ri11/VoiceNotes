package com.company.voicenotes.di

import androidx.lifecycle.LifecycleOwner
import com.company.voicenotes.databinding.FragmentRecorderBinding
import com.company.voicenotes.presentation.RecorderViewController
import dagger.BindsInstance
import dagger.Subcomponent

annotation class RecorderFragmentViewScope

@RecorderFragmentViewScope
@Subcomponent
interface RecorderViewComponent {
    val root: FragmentRecorderBinding
    val viewLifecycleOwner: LifecycleOwner
    val recorderViewController: RecorderViewController

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance root: FragmentRecorderBinding,
            @BindsInstance viewLifecycleOwner: LifecycleOwner,
        ): RecorderViewComponent
    }
}