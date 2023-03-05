package com.company.voicenotes.di

import androidx.fragment.app.Fragment
import com.company.voicenotes.model.RecordViewModel
import com.company.voicenotes.presentation.BottomSheetFragment
import com.company.voicenotes.presentation.adapter.RecordAdapter
import dagger.BindsInstance
import dagger.Subcomponent

annotation class RecorderFragmentScope

@Subcomponent
@RecorderFragmentScope
interface RecorderFragmentComponent {
    val fragment: Fragment
    val viewModel: RecordViewModel
    val adapter: RecordAdapter

    fun recorderViewComponentComponent(): RecorderViewComponent.Factory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance fragment: Fragment,
            @BindsInstance viewModel: RecordViewModel
        ): RecorderFragmentComponent
    }
}