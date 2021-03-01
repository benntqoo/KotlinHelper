package com.jrtou.kotlinhelper.extend

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun ViewBinding.reuseRoot(): View = if (root.parent == null) root else {
    val viewGroup: ViewGroup = root.parent as ViewGroup
    viewGroup.removeView(root)
    root
}