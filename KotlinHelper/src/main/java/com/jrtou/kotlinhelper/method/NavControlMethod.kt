package com.jrtou.kotlinhelper.method

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 *使用方式
<androidx.fragment.app.FragmentContainerView
android:id="@+id/fg_host_home"
android:layout_width="match_parent"
android:name="androidx.navigation.fragment.NavHostFragment"
android:layout_height="0dp"
app:defaultNavHost="false"/>
 */
fun findNestNavController(fragmentManager: FragmentManager, @IdRes layoutResId: Int): NavController {
    val navHostFragment = fragmentManager.findFragmentById(layoutResId)
    require(navHostFragment is NavHostFragment)
    return navHostFragment.navController
}


fun NavController.customNavDestination(@NavigationRes navRes: Int, getStartDestination: () -> Int): NavController {
    val graphInflater = navInflater
    val navGraph = graphInflater.inflate(navRes)
    navGraph.startDestination = getStartDestination()
    graph = navGraph
    return this
}