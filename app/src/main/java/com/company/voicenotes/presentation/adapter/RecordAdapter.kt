package com.company.voicenotes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.company.voicenotes.R
import com.company.voicenotes.databinding.RecordItemBinding
import com.company.voicenotes.di.RecorderFragmentScope
import com.company.voicenotes.model.Record
import com.company.voicenotes.model.RecordViewModel
import javax.inject.Inject

@RecorderFragmentScope
class RecordAdapter @Inject constructor(
    private val viewModel: RecordViewModel,
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    private var previousPlaying = -1
    var recordList: List<Record> = emptyList()
        set(newValue) {
            val diffCallback = RecordDiffUtilCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class RecordViewHolder(private val binding: RecordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Record, position: Int) {
            binding.apply {
                val itemIsPlaying = currentItem.isPlaying
                recordName.text = currentItem.name
                recordDate.text = currentItem.creationDate
                if (itemIsPlaying) {
                    playStopButton.setImageResource(R.drawable.pause_icon)
                    viewModel.playAudio(recordName.text.toString())
                } else {
                    playStopButton.setImageResource(R.drawable.play_icon)
                    viewModel.pausePlaying()
                }
                playStopButton.setOnClickListener {
                    playStopButtonClick(position)
                }
            }
        }

        private fun playStopButtonClick(position: Int) {
            if (previousPlaying != -1 && recordList[previousPlaying].isPlaying && previousPlaying != position) {
                changeRecordState(previousPlaying)
                viewModel.pausePlaying()
            }
            changeRecordState(position)
            previousPlaying = position
        }
    }

    fun changeRecordState(pos: Int){
        val curItem = recordList[pos]
        curItem.isPlaying = !curItem.isPlaying
        notifyItemChanged(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = RecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val currentItem = recordList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

}