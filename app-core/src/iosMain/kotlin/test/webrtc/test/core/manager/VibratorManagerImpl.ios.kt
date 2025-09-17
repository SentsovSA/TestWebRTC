package test.webrtc.test.core.manager

import test.webrtc.test.domain.manager.VibratorManager
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

internal class VibratorManagerImpl : VibratorManager {

    companion object {
        fun create(
        ): VibratorManager = VibratorManagerImpl(
        )
    }

    override fun vibrate(vibrate: VibratorManager.Vibrate) {
        //AudioServicesPlayAlertSound(kSystemSoundID_Vibrate)
        UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleLight)
            .impactOccurred()
    }

    override fun cancel() {
    }

}