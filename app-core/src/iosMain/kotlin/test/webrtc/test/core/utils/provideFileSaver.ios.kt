package test.webrtc.test.core.utils

import test.webrtc.test.core.interfaces.FileSaver

actual fun provideFileSaver(): FileSaver = IOSFileSaver()
