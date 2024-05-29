package com.wyx.common_module.extensions

import android.content.Context

const val BYTE = 1
const val KB = 1024
const val MB = 1048576
const val GB = 1073741824

fun byte2FitMemorySize(byteSize: Long): String = when {
    byteSize < 0 -> "0K"
    byteSize < KB -> String.format("%.2fB", byteSize.toFloat())
    byteSize < MB -> String.format("%.2fK", byteSize.toFloat() / KB)
    byteSize < GB -> String.format("%.2fM", byteSize.toFloat() / MB)
    else -> String.format("%.2fM", byteSize.toFloat() / GB)
}

fun Int.dp2px(context: Context) = (context.resources.displayMetrics.density * this + 0.5).toInt()
