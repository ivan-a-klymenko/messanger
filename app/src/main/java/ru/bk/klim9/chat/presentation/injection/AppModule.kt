package ru.bk.klim9.chat.presentation.injection

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import ru.bk.klim9.chat.cache.ChatDatabase
import ru.bk.klim9.chat.data.account.AccountCache
import ru.bk.klim9.chat.data.account.AccountRemote
import ru.bk.klim9.chat.data.account.AccountRepositoryImpl
import ru.bk.klim9.chat.data.friends.FriendsCache
import ru.bk.klim9.chat.data.friends.FriendsRemote
import ru.bk.klim9.chat.data.friends.FriendsRepositoryImpl
import ru.bk.klim9.chat.data.media.MediaRepositoryImpl
import ru.bk.klim9.chat.data.messages.MessagesCache
import ru.bk.klim9.chat.data.messages.MessagesRemote
import ru.bk.klim9.chat.data.messages.MessagesRepositoryImpl
import ru.bk.klim9.chat.domain.account.AccountRepository
import ru.bk.klim9.chat.domain.friends.FriendsRepository
import ru.bk.klim9.chat.domain.media.MediaRepository
import ru.bk.klim9.chat.domain.messages.MessagesRepository
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideAccountRepository(remote: AccountRemote, cache: AccountCache): AccountRepository {
        return AccountRepositoryImpl(remote, cache)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(remote: FriendsRemote, accountCache: AccountCache, friendsCache: FriendsCache): FriendsRepository {
        return FriendsRepositoryImpl(accountCache, remote, friendsCache)
    }

    @Provides
    @Singleton
    fun provideMediaRepository(context: Context): MediaRepository {
        return MediaRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMessagesRepository(remote: MessagesRemote, cache: MessagesCache, accountCache: AccountCache): MessagesRepository {
        return MessagesRepositoryImpl(remote, cache, accountCache)
    }
}