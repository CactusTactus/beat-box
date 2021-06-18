package com.example.beatbox

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    var soundRateLiveData = MutableLiveData(100)
}