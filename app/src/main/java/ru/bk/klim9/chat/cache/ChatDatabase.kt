package ru.bk.klim9.chat.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.bk.klim9.chat.cache.friends.FriendsDao
import ru.bk.klim9.chat.cache.messages.MessagesDao
import ru.bk.klim9.chat.domain.friends.FriendEntity
import ru.bk.klim9.chat.domain.messages.MessageEntity

@Database(entities = [FriendEntity::class, MessageEntity::class], version = 8, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {
    abstract val friendsDao: FriendsDao
    abstract val messagesDao: MessagesDao

    companion object {
        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getInstance(context: Context): ChatDatabase {

            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }

            return instance
        }
    }
}