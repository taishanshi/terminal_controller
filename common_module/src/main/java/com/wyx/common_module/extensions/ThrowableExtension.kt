package com.wyx.common_module.extensions

import com.wyx.common_module.logger.Logger


fun Throwable.e(msg: String?, tag: String) {
    if (null != msg) {
        Logger.e(tag, msg, this)
    } else {
        Logger.e(tag, "nothing", this)
    }
}