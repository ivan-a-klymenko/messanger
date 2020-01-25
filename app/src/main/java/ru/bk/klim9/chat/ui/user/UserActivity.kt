package ru.bk.klim9.chat.ui.user

import android.os.Bundle
import ru.bk.klim9.chat.ui.App
import ru.bk.klim9.chat.ui.core.BaseActivity
import ru.bk.klim9.chat.ui.core.BaseFragment

class UserActivity : BaseActivity() {
    override var fragment: BaseFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}
