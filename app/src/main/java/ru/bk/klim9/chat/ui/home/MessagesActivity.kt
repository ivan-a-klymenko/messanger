package ru.bk.klim9.chat.ui.home

import android.os.Bundle
import ru.bk.klim9.chat.ui.App
import ru.bk.klim9.chat.ui.core.BaseActivity
import ru.bk.klim9.chat.ui.core.BaseFragment

class MessagesActivity : BaseActivity() {
    override var fragment: BaseFragment = MessagesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}
