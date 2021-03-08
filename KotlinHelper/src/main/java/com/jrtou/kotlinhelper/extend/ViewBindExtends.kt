package com.jrtou.kotlinhelper.extend

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

fun ViewBinding.reuseRoot(): View = if (root.parent == null) root else {
    val viewGroup: ViewGroup = root.parent as ViewGroup
    viewGroup.removeView(root)
    root
}

//fun RecyclerView.setupWithRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
//    clearOnScrollListeners()
//    addOnScrollListener(object : RecyclerView.OnScrollListener() {
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            val topRowVerticalPosition = if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
//            swipeRefreshLayout.isEnabled = topRowVerticalPosition >= 0
//        }
//    })
//}