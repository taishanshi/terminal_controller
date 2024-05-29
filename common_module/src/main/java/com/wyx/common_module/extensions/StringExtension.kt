package com.wyx.common_module.extensions

import com.wyx.common_module.logger.Logger
import java.lang.Exception

/**
 * 默认级别为INFO
 * 会写入日志文件
 * */
fun String.log(message: String?, level: Int = com.orhanobut.logger.Logger.INFO) {
    val msg = message ?: ""
    when (level) {
        com.orhanobut.logger.Logger.VERBOSE ->
            Logger.v(this, msg)
        com.orhanobut.logger.Logger.DEBUG ->
            Logger.d(this, msg)
        com.orhanobut.logger.Logger.INFO ->
            Logger.i(this, msg)
        com.orhanobut.logger.Logger.WARN ->
            Logger.w(this, msg)
        com.orhanobut.logger.Logger.ERROR,
        com.orhanobut.logger.Logger.ASSERT ->
            Logger.e(this, msg)
        else ->
            Logger.i(this, msg)
    }
}

/**
 * DEBUG 日志
 * 不会写入日志文件
 * */
fun String.logd(message: String?) {
    val msg = message ?: ""
    try {
        Logger.d(this, msg)
    } catch (e: Exception) {
        "cloudLiveData".logd(e.toString())
    }
}

/**
 * 包含错误信息的日志 级别为ERROR
 * 会写入日志文件
 * */
fun String.log(throwable: Throwable?, message: String?) {
    throwable?.let {
        val msg = message ?: ""
        Logger.e(this, msg, it)
    }
}


fun String.isInt(): Boolean {
    return try {
        toInt()
        true
    } catch (e: Exception) {
        false
    }
}

fun String?.isNotNullAndNotBlank(): Boolean = !this.isNullOrBlank()


