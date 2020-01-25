package ru.bk.klim9.chat.ui.firebase

import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.bk.klim9.chat.domain.account.UpdateToken
import ru.bk.klim9.chat.ui.App
import javax.inject.Inject
import android.os.Looper
import org.json.JSONObject


class FirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var updateToken: UpdateToken

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        App.appComponent.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Handler(Looper.getMainLooper()).post {
            notificationHelper.sendNotification(remoteMessage)

        }
    }

    override fun onNewToken(token: String?) {
        Log.e("fb token", ": $token")
        if (token != null) {
            updateToken(UpdateToken.Params(token))
        }
    }
}
