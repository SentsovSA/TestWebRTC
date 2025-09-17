package test.webrtc.test.ui.utils

expect object FlashlightController {
    fun toggle()
    fun turnOn()
    fun turnOff()
    fun isSupported(): Boolean
}