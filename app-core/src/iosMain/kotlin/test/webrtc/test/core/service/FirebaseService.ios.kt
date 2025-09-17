package test.webrtc.test.core.service

import test.webrtc.test.data.repository.SettingsRepository
import cocoapods.FirebaseMessaging.FIRMessaging
import test.webrtc.test.data.repository.FirebaseMessagingRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

//import cocoapods.FirebaseAnalytics.FIRAnalytics
//import cocoapods.FirebaseCrashlytics.FIRCrashlytics
//import cocoapods.FirebaseMessaging.FIRMessaging

internal class FirebaseServiceImpl private constructor(
    private val settingsRepository: SettingsRepository,
    private val firebaseMessagingRepository: FirebaseMessagingRepository
) : FirebaseService {

    companion object {
        fun create(
            settingsRepository: SettingsRepository,
            firebaseMessagingRepository: FirebaseMessagingRepository
        ): FirebaseService = FirebaseServiceImpl(
            settingsRepository = settingsRepository,
            firebaseMessagingRepository = firebaseMessagingRepository
        )
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {

    }

    override fun fetchToken() {
        val token = FIRMessaging.messaging().FCMToken.toString()
        scope.launch(CoroutineName("Save FCM token") + CoroutineExceptionHandler { _, e ->
            Napier.e(e) { e.message ?: "" }
        }) {
            firebaseMessagingRepository.token(
                token = token
            )
        }
    }

    override fun subscribe(topic: String) {
        FIRMessaging.messaging().tokenWithCompletion { token, error ->
            Napier.e{"FIRMessaging: $token, $error"}
        }
    }

    override fun unsubscribe(topic: String) {

    }


}
