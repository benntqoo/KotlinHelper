package com.jrtou.kotlinhelper.helper

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

object KeyboardHelper {
    private const val TAG = "KeyboardHelper"

    fun hideKeyboard(activity: AppCompatActivity) {
        val currentFocus = activity.currentFocus
        currentFocus ?: return

        val inputManager: InputMethodManager? = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager ?: return

        Log.i(TAG, "hideKeyboard: 輸入法是否打開 ${inputManager.isActive}")
        if (inputManager.isActive && isKeyBoardShow(activity)) inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    fun isKeyBoardShow(activity: AppCompatActivity): Boolean {
        activity.window?.decorView ?: return false
        val screenH = activity.window.decorView.height
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return screenH * 2 / 3 > rect.bottom
    }

    /**
     * 沒作用
     */
    fun showKeyboard(activity: AppCompatActivity, editText: EditText?) {
        editText ?: return

        val inputManager: InputMethodManager? = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager ?: return

        Log.i(TAG, "showKeyboard: 輸入法是否打開 ${inputManager.isActive}")
        editText.requestFocus()
        if (inputManager.isActive && !isKeyBoardShow(activity)) inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}