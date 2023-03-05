package com.company.voicenotes.model

data class Record(
    val name: String,
    val duration: Int = 360,
    val creationDate: String = "21.01.2023",
    var isPlaying: Boolean = false
)
