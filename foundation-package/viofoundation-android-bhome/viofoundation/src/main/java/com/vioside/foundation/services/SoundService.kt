package com.vioside.foundation.services

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.media.SoundPool
import android.os.Build
import com.vioside.foundation.extensions.localFileURL
import kotlinx.coroutines.delay

data class Sound (
    var soundId: Int,
    var soundDuration: Int
)

interface SoundService {
    fun loadSound(fileName: String): Sound?
    suspend fun playSounds(sounds: List<Sound>)
}

@Suppress("DEPRECATION")
class AndroidSoundService(
    val context: Context
): SoundService {

    private var soundPool: SoundPool

    init {
        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
    }

    override fun loadSound(fileName: String): Sound? {
        val url = fileName.localFileURL(context)
        if(url.isEmpty()) return null
        return try {
            val soundId = soundPool.load(url, 1)
            val duration = getSoundDuration(url)
            Sound(soundId, duration)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun playSounds(sounds: List<Sound>) {
        sounds.forEach { sound ->
            soundPool.play(sound.soundId, 1.0f, 1.0f, 1, 0, 1.0f)
            delay(sound.soundDuration.toLong())
        }
    }

    private fun getSoundDuration(url: String): Int {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(url)
        val durationStr =
            mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        return durationStr.toInt()
    }

}