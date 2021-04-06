package com.jrtou.kotlinhelper.helper

import java.util.regex.Pattern

/**
 * Created by Jrtou on 2018/6/12.
 */
object RegularHelper {
    /**
     * 確認帳號格式
     *
     * @return boolean
     */
    fun checkAccountFormat(account: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]+$")
        val matcher = pattern.matcher(account)
        return matcher.matches()
    }

    /**
     * 確認手機格式
     *
     * @return boolean
     */
    fun checkMobileFormat(mobile: String): Boolean {
        val pattern = Pattern.compile("^09\\d{2}-?\\d{3}-?\\d{3}$")
        val matcher = pattern.matcher(mobile)
        return matcher.matches()
    }

    /**
     * 確認Email格式
     *
     * @return boolean
     */
    fun checkEmailFormat(email: String): Boolean {
        val pattern = Pattern.compile("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4})*$")
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun checkByCustomFormat(checkStrings: String, formatString: String): Boolean {
        val pattern = Pattern.compile(formatString)
        val matcher = pattern.matcher(checkStrings)
        return matcher.matches()
    }
}