package test.webrtc.test.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import test.webrtc.test.core.BuildConfig
import test.webrtc.test.core.R
import test.webrtc.test.data.repository.SettingsRepository
import test.webrtc.test.domain.service.FirebaseService
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.random.Random

internal class FirebaseServiceImpl private constructor(
    private val context: Context,
    private val settingsRepository: SettingsRepository,
    /*private val firebaseMessagingRepository: FirebaseMessagingRepository*/
) : FirebaseService {

    companion object {
        fun create(
            context: Context,
            settingsRepository: SettingsRepository,
            /*firebaseMessagingRepository: FirebaseMessagingRepository*/
        ): FirebaseService = FirebaseServiceImpl(
            context = context,
            settingsRepository = settingsRepository,
            /*firebaseMessagingRepository = firebaseMessagingRepository*/
        )
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        FirebaseApp.initializeApp(context)
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    override fun fetchToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("Fetching FCM token failed: ${task.exception}")
                return@addOnCompleteListener
            }
            val token = task.result
            println("FCM token: $token")
            scope.launch(CoroutineName("Save FCM token") + CoroutineExceptionHandler { _, e ->
                Napier.e(e) { e.message ?: "" }
            }) {
                /*firebaseMessagingRepository.token(
                    token = token
                )*/
            }
        }
    }

    override fun subscribe(topic: String) {
        scope.launch {
            settingsRepository.getAccessToken()?.let {
                fetchToken()
            }
        }
    }

    override fun unsubscribe(topic: String) {
        FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener {
            if (it.isSuccessful) {
                Napier.d { "Токен успешно удалён" }
            } else {
                Napier.e {"Ошибка удаления токена"}
            }
        }
    }

}

internal class FirebaseMessagingService : FirebaseMessagingService() {

    private val notificationManager by lazy { getSystemService(NotificationManager::class.java) }

    override fun onCreate() {
        super.onCreate()
        Napier.i("FirebaseMessagingService: onCreate")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Napier.i("FirebaseMessagingService::onMessageReceived::from: ${message.from}")
        if (message.data.isNotEmpty()) {
            Napier.i("FirebaseMessagingService::onMessageReceived::data: ${message.data}")
        }
        message.notification?.let {
            Napier.i("FirebaseMessagingService::onMessageReceived::notification: ${it.title}, ${it.body}")
            notification(
                title = it.title!!,
                text = it.body!!
            )
        }
    }

    override fun onDeletedMessages() {
    }

    override fun onNewToken(token: String) {
        println("FirebaseMessagingService::onNewToken: $token")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("FirebaseMessagingService::onDestroy")
    }

    private fun notification(title: String, text: String) {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, PUSH_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(Random.nextInt(1, Int.MAX_VALUE), builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                PUSH_CHANNEL_ID,
                getString(R.string.notification_channel_name_push),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = getString(R.string.notification_channel_description_push)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val PUSH_CHANNEL_ID = "push"
    }

}