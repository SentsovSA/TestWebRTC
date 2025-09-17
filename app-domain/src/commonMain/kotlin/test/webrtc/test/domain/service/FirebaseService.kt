package test.webrtc.test.domain.service

interface FirebaseService {

    fun fetchToken()

    fun subscribe(topic: String)
    fun unsubscribe(topic: String)

}