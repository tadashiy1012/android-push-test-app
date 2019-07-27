package jp.yama.pushtestapp

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d("yama", ">token: $token")
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        if (p0 != null) {
            val notification = p0.notification
            if (notification != null) {
                Log.d("yama", ">notification: ${notification.body}")

            }
            val data = p0.data
            if (data != null) {
                Log.d("yama", ">data: ${data.size}")
            }
        }
    }

}