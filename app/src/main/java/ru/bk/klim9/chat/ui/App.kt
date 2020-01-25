package ru.bk.klim9.chat.ui

import android.app.Application
import dagger.Component
import ru.bk.klim9.chat.presentation.injection.AppModule
import ru.bk.klim9.chat.presentation.injection.CacheModule
import ru.bk.klim9.chat.presentation.injection.RemoteModule
import ru.bk.klim9.chat.presentation.injection.ViewModelModule
import ru.bk.klim9.chat.ui.account.AccountActivity
import ru.bk.klim9.chat.ui.account.AccountFragment
import ru.bk.klim9.chat.ui.core.navigation.RouteActivity
import ru.bk.klim9.chat.ui.firebase.FirebaseService
import ru.bk.klim9.chat.ui.friends.FriendRequestsFragment
import ru.bk.klim9.chat.ui.friends.FriendsFragment
import ru.bk.klim9.chat.ui.home.HomeActivity
import ru.bk.klim9.chat.ui.home.ChatsFragment
import ru.bk.klim9.chat.ui.home.MessagesActivity
import ru.bk.klim9.chat.ui.home.MessagesFragment
import ru.bk.klim9.chat.ui.login.LoginFragment
import ru.bk.klim9.chat.ui.register.RegisterActivity
import ru.bk.klim9.chat.ui.register.RegisterFragment
import ru.bk.klim9.chat.ui.user.UserActivity
import ru.bk.klim9.chat.ui.user.UserFragment
import javax.inject.Singleton

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}

@Singleton
@Component(modules = [AppModule::class, CacheModule::class, RemoteModule::class, ViewModelModule::class])
interface AppComponent {

    //activities
    fun inject(activity: RegisterActivity)
    fun inject(activity: RouteActivity)
    fun inject(activity: HomeActivity)
    fun inject(activity: AccountActivity)
    fun inject(activity: MessagesActivity)
    fun inject(activity: UserActivity)

    //fragments
    fun inject(fragment: RegisterFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: ChatsFragment)
    fun inject(fragment: FriendsFragment)
    fun inject(fragment: FriendRequestsFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: MessagesFragment)
    fun inject(fragment: UserFragment)

    //services
    fun inject(service: FirebaseService)
}