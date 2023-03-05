package com.company.voicenotes.data.recorder

interface AudioRecorder {
    fun start()
    fun stop(newName: String = "")
}