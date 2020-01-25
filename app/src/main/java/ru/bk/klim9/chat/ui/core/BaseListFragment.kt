package ru.bk.klim9.chat.ui.core

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.bk.klim9.chat.R

abstract class BaseListFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    protected lateinit var lm: RecyclerView.LayoutManager

    protected abstract val viewAdapter: BaseAdapter<*, *>

    override val layoutId = R.layout.fragment_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lm = LinearLayoutManager(context)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = lm
            adapter = viewAdapter
        }
    }

    protected fun setOnItemClickListener(func: (Any?, View) -> Unit) {
        viewAdapter.setOnClick(func)
    }

    protected fun setOnItemLongClickListener(func: (Any?, View) -> Unit) {
        viewAdapter.setOnClick({_,_ ->}, longClick = func)
    }
}
