package com.yankin.common.vibrate

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator

class VibrateUtil(private val context: Context) {
    private val vibrator by lazy { context.getSystemService(VIBRATOR_SERVICE) as? Vibrator }
    private val audioManager by lazy { context.getSystemService(AUDIO_SERVICE) as? AudioManager }

    fun vibrate(duration: Long) {
        if (!isVibrationOn()) return
        vibrator?.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    fun hasVibrator() = vibrator?.hasVibrator() ?: false

    private fun isVibrationOn(): Boolean = when (audioManager?.ringerMode) {
        AudioManager.RINGER_MODE_VIBRATE -> true
        AudioManager.RINGER_MODE_NORMAL -> true
        else -> false
    }
}