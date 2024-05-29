package com.wyx.common_module.extensions

import java.util.*


fun Calendar.year() = get(Calendar.YEAR)
fun Calendar.month() = get(Calendar.MONTH)
fun Calendar.dayOfMonth() = get(Calendar.DAY_OF_MONTH)
fun Calendar.hour() = get(Calendar.HOUR_OF_DAY)
fun Calendar.minute() = get(Calendar.MINUTE)
fun Calendar.second() = get(Calendar.SECOND)