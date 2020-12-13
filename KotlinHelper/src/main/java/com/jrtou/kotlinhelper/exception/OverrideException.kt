package com.jrtou.kotlinhelper.exception

import java.lang.RuntimeException

class OverrideException(functionName: String) : RuntimeException("請 override fun [$functionName]")