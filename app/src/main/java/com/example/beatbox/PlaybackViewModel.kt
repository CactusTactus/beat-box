package com.example.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class PlaybackViewModel : BaseObservable() {
    var rate = 100
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val playbackSpeed: Int
        get() = rate
}