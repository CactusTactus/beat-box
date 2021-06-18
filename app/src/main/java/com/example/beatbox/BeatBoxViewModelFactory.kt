package com.example.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BeatBoxViewModelFactory(private val assetManager: AssetManager): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeatBoxViewModel::class.java)) {
            return modelClass.getConstructor(AssetManager::class.java).newInstance(assetManager)
        }
        throw IllegalArgumentException()
    }
}