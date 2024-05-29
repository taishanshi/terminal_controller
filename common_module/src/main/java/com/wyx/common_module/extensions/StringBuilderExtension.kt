package com.wyx.common_module.extensions

operator fun StringBuilder.plus(any : Any?) {
    append(any)
}