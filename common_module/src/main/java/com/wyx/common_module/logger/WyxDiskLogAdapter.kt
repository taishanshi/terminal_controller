package com.wyx.common_module.logger

import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.Logger

class WyxDiskLogAdapter(formatStrategy: FormatStrategy?) : LogAdapter {
    private var formatStrategy: FormatStrategy? = null

    constructor() : this(WyxCsvFormatStrategy.Builder().build())

    init {
        this.formatStrategy = formatStrategy
    }

    override fun isLoggable(priority: Int, tag: String?): Boolean {
        return priority >= Logger.INFO
    }

    override fun log(priority: Int, tag: String?, message: String) {
        formatStrategy!!.log(priority, tag, message)
    }
}