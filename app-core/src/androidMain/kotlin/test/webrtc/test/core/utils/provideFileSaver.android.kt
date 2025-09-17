package test.webrtc.test.core.utils

import test.webrtc.test.core.interfaces.FileSaver
import org.koin.core.context.GlobalContext.get

actual fun provideFileSaver(): FileSaver = getKoinInstance()

fun getKoinInstance(): FileSaver = get().get()
