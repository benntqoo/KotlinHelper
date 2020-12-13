package com.jrtou.kotlinhelper.method

import android.content.res.Resources

val Float.dp
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.px
    get() = this / Resources.getSystem().displayMetrics.density

val Int.dp
    get() = this.toFloat().dp

val Int.px
    get() = this.toFloat().px

val Double.dp
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Double.px
    get() = this / Resources.getSystem().displayMetrics.density