package ru.bk.klim9.chat.ui.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import ru.bk.klim9.chat.R
import ru.bk.klim9.chat.domain.friends.FriendEntity
import ru.bk.klim9.chat.domain.messages.ContactEntity
import ru.bk.klim9.chat.domain.messages.GetMessagesWithContact
import ru.bk.klim9.chat.domain.messages.MessageEntity
import ru.bk.klim9.chat.remote.service.ApiService
import ru.bk.klim9.chat.ui.home.HomeActivity
import ru.bk.klim9.chat.ui.home.MessagesActivity
import org.json.JSONObject
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    val context: Context, val getMessagesWithContact: GetMessagesWithContact) : ContextWrapper(context) {

    companion object {
        const val MESSAGE = "message"
        const val JSON_MESSAGE = "firebase_json_message"
        const val TYPE = "type"
        const val TYPE_ADD_FRIEND = "addFriend"
        const val TYPE_APPROVED_FRIEND = "approveFriendRequest"
        const val TYPE_CANCELLED_FRIEND_REQUEST = "cancelFriendRequest"
        const val TYPE_SEND_MESSAGE = "sendMessage"

        const val notificationId = 110
    }

    var mManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createChannels()
    }


    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // create android channel
            val androidChannel = NotificationChannel(
                context.packageName,
                "${context.packageName}.notification_chanel", NotificationManager.IMPORTANCE_DEFAULT
            )
            // Sets whether notifications posted to this channel should display notification lights
            androidChannel.enableLights(true)
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true)
            // Sets the notification light color for notifications posted to this channel
            androidChannel.lightColor = Color.GREEN
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

            mManager.createNotificationChannel(androidChannel)
        }
    }


    fun sendNotification(remoteMessage: RemoteMessage?) {
        if (remoteMessage?.data == null) {
            return
        }

        val message = remoteMessage.data[MESSAGE]
        val jsonMessage = JSONObject(message).getJSONObject(JSON_MESSAGE)

        val type = jsonMessage.getString(TYPE)
        when (type) {
            TYPE_ADD_FRIEND -> sendAddFriendNotification(jsonMessage)
            TYPE_APPROVED_FRIEND -> sendApprovedFriendNotification(jsonMessage)
            TYPE_CANCELLED_FRIEND_REQUEST -> sendCancelledFriendNotification(jsonMessage)
            TYPE_SEND_MESSAGE -> sendMessageNotification(jsonMessage)
        }
    }

    private fun sendAddFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_ADD_FRIEND)

        createNotification(
            getString(R.string.friend_request),
            "${friend.name} ${context.getString(R.string.wants_add_as_friend)}",
            intent
        )
    }


    private fun sendApprovedFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_APPROVED_FRIEND)

        createNotification(
            getString(R.string.friend_request_approved),
            "${friend.name} ${context.getString(R.string.approved_friend_request)}",
            intent
        )
    }

    private fun sendCancelledFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_CANCELLED_FRIEND_REQUEST)

        createNotification(
            getString(R.string.friend_request_cancelled),
            "${friend.name} ${context.getString(R.string.cancelled_friend_request)}",
            intent
        )
    }

    private fun sendMessageNotification(jsonMessage: JSONObject) {
        val message = parseMessage(jsonMessage)

        getMessagesWithContact(GetMessagesWithContact.Params(message.senderId, true))

        val intent = Intent(context, HomeActivity::class.java)

        intent.putExtra(ApiService.PARAM_CONTACT_ID, message.contact?.id)
        intent.putExtra(ApiService.PARAM_NAME, message.contact?.name)
        intent.putExtra("type", TYPE_SEND_MESSAGE)

        val text = if (message.type == 1) message.message else getString(R.string.photo)

        createNotification(
            "${message.contact?.name} ${context.getString(R.string.send_message)}",
            text,
            intent
        )
    }


    private fun parseFriend(jsonMessage: JSONObject): FriendEntity {

        val requestUser = if (jsonMessage.has(ApiService.PARAM_REQUEST_USER)) {
            jsonMessage.getJSONObject(ApiService.PARAM_REQUEST_USER)
        } else {
            jsonMessage.getJSONObject(ApiService.PARAM_APPROVED_USER)
        }

        val friendsId = jsonMessage.getLong(ApiService.PARAM_FRIENDS_ID)

        val id = requestUser.getLong(ApiService.PARAM_USER_ID)
        val name = requestUser.getString(ApiService.PARAM_NAME)
        val email = requestUser.getString(ApiService.PARAM_EMAIL)
        val status = requestUser.getString(ApiService.PARAM_STATUS)
        val image = requestUser.getString(ApiService.PARAM_USER_ID)

        return FriendEntity(id, name, email, friendsId, status, image)
    }

    private fun parseMessage(jsonMessage: JSONObject): MessageEntity {
        val senderUser = jsonMessage.getJSONObject(ApiService.PARAM_SENDER_USER)
        val senderName = senderUser.getString(ApiService.PARAM_NAME)
        val senderImage = senderUser.getString(ApiService.PARAM_IMAGE)
        val lastSeen = senderUser.getLong(ApiService.PARAM_LAST_SEEN)

        val id = jsonMessage.getLong(ApiService.PARAM_MESSAGE_ID)
        val senderId = jsonMessage.getLong(ApiService.PARAM_SENDER_USER_ID)
        val receiverId = jsonMessage.getLong(ApiService.PARAM_RECEIVED_USER_ID)
        val message = jsonMessage.getString(ApiService.PARAM_MESSAGE)
        val type = jsonMessage.getInt(ApiService.PARAM_MESSAGE_TYPE)

        return MessageEntity(id, senderId, receiverId, message, 0, type, ContactEntity(senderId, senderName, senderImage, lastSeen))
    }

    private fun createNotification(title: String, message: String, intent: Intent) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        intent.action = "notification $notificationId"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        val contentIntent = PendingIntent.getActivity(
            context, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mBuilder = NotificationCompat.Builder(
            context, context.applicationContext.packageName
        )
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setContentText(message)
        mBuilder.setContentIntent(contentIntent)
        mManager.notify(notificationId, mBuilder.build())
    }
}