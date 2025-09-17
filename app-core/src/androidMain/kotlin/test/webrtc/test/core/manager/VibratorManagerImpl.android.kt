package test.webrtc.test.core.manager

import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.os.VibrationAttributes
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.getSystemService
import test.webrtc.test.domain.manager.VibratorManager

internal class VibratorManagerImpl(
    private val context: Context
) : VibratorManager {

    companion object {
        fun create(
            context: Context
        ): VibratorManager = VibratorManagerImpl(
            context = context
        )
    }

    private var vibrator: Vibrator? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService<android.os.VibratorManager>()
            vibratorManager?.defaultVibrator?.let {
                if (it.hasVibrator()) {
                    vibrator = it
                }
            }
        } else {
            context.getSystemService<Vibrator>()?.let {
                if (it.hasVibrator()) {
                    vibrator = it
                }
            }
        }
    }

    override fun vibrate(vibrate: VibratorManager.Vibrate) {
        val vibrator = this.vibrator ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                vibrator.vibrate(effect, VibrationAttributes.createForUsage(VibrationAttributes.USAGE_ALARM))
            } else {
                val attrs = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                @Suppress("DEPRECATION")
                vibrator.vibrate(effect, attrs)
            }
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(250)
        }
    }

    /*override fun wave() {
        val vibrator = this.vibrator ?: return
        val timings = longArrayOf(0, 400, 800, 600, 800, 800, 800, 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (vibrator.hasAmplitudeControl()) {
                val amplitudes = intArrayOf(0, 255, 0, 255, 0, 255, 0, 255)
                vibrator.vibrate(VibrationEffect.createWaveform(
                    timings, amplitudes, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(timings, VibrationEffect.DEFAULT_AMPLITUDE)
            }
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(1000)
        }
    }*/

    override fun cancel() {
        vibrator?.cancel()
    }

}