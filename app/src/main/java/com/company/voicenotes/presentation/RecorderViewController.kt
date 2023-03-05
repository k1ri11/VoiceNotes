package com.company.voicenotes.presentation

import android.Manifest
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.voicenotes.R
import com.company.voicenotes.databinding.FragmentRecorderBinding
import com.company.voicenotes.di.RecorderFragmentViewScope
import com.company.voicenotes.model.Record
import com.company.voicenotes.model.RecordViewModel
import com.company.voicenotes.presentation.adapter.RecordAdapter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@RecorderFragmentViewScope
class RecorderViewController @Inject constructor(
    private val fragment: Fragment,
    private val binding: FragmentRecorderBinding,
    private val adapter: RecordAdapter,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: RecordViewModel,
) {
    private var audioFile: File? = null
    private var isRecording = false

    private val requestPermissionsLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ::onGotPermissionsResult
    )


    fun setupViews() {
        setupListener()
        setupAdapter()
        refreshAudios()
        subscribeCompletionAudio()
    }

    private fun subscribeCompletionAudio() {
        viewModel.completedAudio.observe(viewLifecycleOwner) { completedName ->
            val list = adapter.recordList
            for (i in list.indices) {
                if (list[i].name == completedName) {
                    list[i].isPlaying = false
                    adapter.notifyItemChanged(i)
                    break
                }
            }
        }
    }

    private fun setupAdapter() {
        binding.rvRecords.adapter = adapter
        binding.rvRecords.layoutManager = LinearLayoutManager(fragment.requireActivity())
    }

    private fun setupListener() {
        binding.recordButton.setOnClickListener {
            toggleRecordingButton()
        }
    }

    private fun onGotPermissionsResult(grantResults: Map<String, Boolean>) {
        if (grantResults.entries.all { it.value }) {
            setupViews()
        } else {
            Toast.makeText(fragment.requireContext(), "no permissions", Toast.LENGTH_LONG).show()
        }
    }

    fun checkPermissions() {
        requestPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            )
        )
    }

    private fun toggleRecordingButton() {
        if (isRecording) {
            binding.recordButton.setImageResource(R.drawable.mic_icon)
            isRecording = false
            showBottomSheetDialog()
        } else {
            viewModel.startRecording()
            isRecording = true
            binding.recordButton.setImageResource(R.drawable.pause_icon)
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetFragment()
        bottomSheetDialog.showNow(fragment.childFragmentManager, "bottomSheetDialog")
        bottomSheetDialog.dialog?.setOnDismissListener {
            viewModel.stopRecording(bottomSheetDialog.newName)
            refreshAudios()
        }
    }
    private fun refreshAudios() {
        val fileList =
            fragment.requireContext().filesDir.listFiles { _, s -> s.endsWith(".mp3") }
        val records = mutableListOf<Record>()
        val format = SimpleDateFormat("dd.MM.yyyy в HH:mm", Locale.ROOT)
        fileList?.forEach {
            val fileName = it.nameWithoutExtension
            val creationDate = format.format(it.lastModified())
            val tmpRecord = Record(
                name = fileName,
                creationDate = creationDate
            )
            records.add(tmpRecord)
        }
        adapter.recordList = records
    }

}