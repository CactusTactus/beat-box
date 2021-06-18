package com.example.beatbox

import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.lang.IllegalArgumentException

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUND_POOL_STREAMS_AMOUNT = 5

class BeatBox(private val assetManager: AssetManager) {
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()

    var rate = 1.0f
        set(value) {
            if (value < 0 || value > 1.0f) throw IllegalArgumentException()
            field = value
        }

    val sounds: List<Sound>

    init {
        sounds = loudSounds()
    }

    fun play(sound: Sound) {
        sound.id?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, rate)
        }
    }

    fun release() {
        soundPool.release()
    }

    private fun loudSounds(): List<Sound> {
        val soundNames: Array<String>

        try {
            soundNames = assetManager.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()
        var sound: Sound
        soundNames.forEach { fileName ->
            sound = Sound("$SOUNDS_FOLDER/$fileName")
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "Could not load sound $fileName", ioe)
            }
        }
        return sounds
    }

    private fun load(sound: Sound) {
        val assetFileDescriptor = assetManager.openFd(sound.assetPath)
        val id = soundPool.load(assetFileDescriptor, 1)
        sound.id = id
    }
}