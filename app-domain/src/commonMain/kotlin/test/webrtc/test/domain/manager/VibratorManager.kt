package test.webrtc.test.domain.manager

interface VibratorManager {

    fun vibrate(vibrate: Vibrate)

    fun cancel()

    enum class Vibrate {
        light,
        heavy
    }

}