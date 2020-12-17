package com.jrtou.kotlinhelper.method

/**
 * 獲取秒
 */
val Long.sec
    get() = this / 1000 % 60

/**
 * 獲取秒文字 補0
 */
val Long.stringSec
    get() = (this / 1000 % 60).run { if (this.toString().length < 2) "0$this" else this.toString() }

/**
 * timestamp 取得分鐘
 */
val Long.min
    get() = this / (1000 * 60) % 60

/**
 * 獲取分文字 補0
 */
val Long.stringMin
    get() = (this / (1000 * 60) % 60).run { if (this.toString().length < 2) "0$this" else this.toString() }

/**
 * timestamp 取得小時
 */
val Long.hour
    get() = this / (1000 * 60 * 60) % 60

/**
 * 獲取時文字 補0
 */
val Long.stringHour
    get() = (this / (1000 * 60 * 60) % 60).run { if (this <= 0) "" else if (this < 10) "0$this" else this.toString() }
