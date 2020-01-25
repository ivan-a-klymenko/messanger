package ru.bk.klim9.chat.presentation.injection

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.bk.klim9.chat.cache.AccountCacheImpl
import ru.bk.klim9.chat.cache.ChatDatabase
import ru.bk.klim9.chat.cache.SharedPrefsManager
import ru.bk.klim9.chat.data.account.AccountCache
import ru.bk.klim9.chat.data.friends.FriendsCache
import ru.bk.klim9.chat.data.messages.MessagesCache
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAccountCache(prefsManager: SharedPrefsManager, chatDatabase: ChatDatabase): AccountCache = AccountCacheImpl(prefsManager, chatDatabase)

    @Provides
    @Singleton
    fun provideChatDatabase(context: Context): ChatDatabase {
        return ChatDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideFriendsCache(chatDatabase: ChatDatabase): FriendsCache {
        return chatDatabase.friendsDao
    }

    @Provides
    @Singleton
    fun provideMessagesCache(chatDatabase: ChatDatabase): MessagesCache {
        return chatDatabase.messagesDao
    }
}