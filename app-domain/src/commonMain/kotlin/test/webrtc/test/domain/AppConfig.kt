package test.webrtc.test.domain

interface AppConfig

fun interface AppConfigFactory<T : AppConfig> {
    operator fun invoke() : T
}