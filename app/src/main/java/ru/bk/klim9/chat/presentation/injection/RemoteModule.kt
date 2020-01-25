package ru.bk.klim9.chat.presentation.injection

import dagger.Module
import dagger.Provides
import ru.bk.klim9.chat.BuildConfig
import ru.bk.klim9.chat.data.account.AccountRemote
import ru.bk.klim9.chat.data.friends.FriendsRemote
import ru.bk.klim9.chat.data.messages.MessagesRemote
import ru.bk.klim9.chat.remote.account.AccountRemoteImpl
import ru.bk.klim9.chat.remote.core.Request
import ru.bk.klim9.chat.remote.friends.FriendsRemoteImpl
import ru.bk.klim9.chat.remote.messages.MessagesRemoteImpl
import ru.bk.klim9.chat.remote.service.ApiService
import ru.bk.klim9.chat.remote.service.ServiceFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = ServiceFactory.makeService(BuildConfig.DEBUG)

    @Singleton
    @Provides
    fun provideAccountRemote(request: Request, apiService: ApiService): AccountRemote {
        return AccountRemoteImpl(request, apiService)
    }

    @Singleton
    @Provides
    fun provideFriendsRemote(request: Request, apiService: ApiService): FriendsRemote {
        return FriendsRemoteImpl(request, apiService)
    }

    @Singleton
    @Provides
    fun provideMessagesRemote(request: Request, apiService: ApiService): MessagesRemote {
        return MessagesRemoteImpl(request, apiService)
    }
}