package com.wyx.common_module.logger

import com.wyx.common_module.extensions.plus
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.LogStrategy
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.util.*

class WyxCsvFormatStrategy(builder: Builder) : FormatStrategy {

    private val NEW_LINE = System.getProperty("line.separator")
    private val NEW_LINE_REPLACEMENT = " <br> "
    private val SEPARATOR = " "
    private val MSG_SEPARATOR = " >> "

    private val date: Date? = builder.date
    private val dateFormat: SimpleDateFormat? = builder.dateFormat
    private val logStrategy: LogStrategy? = builder.logStrategy
    private val tag: String? = builder.tag

    override fun log(priority: Int, tag: String?, message: String) {

        date?.time = System.currentTimeMillis()

        val builder = StringBuilder()

        builder + dateFormat?.format(date)

        // level
        builder + SEPARATOR
        builder + logLevel(priority)

        // tag
        builder + SEPARATOR
        builder + tag
        //thread name

        builder + SEPARATOR
        builder + "["
        builder + Thread.currentThread().name
        builder + "]"

        // message
        /*if (message.contains(NEW_LINE)) {
      // a new line would break the CSV format, so we replace it here
      message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
    }*/

        // message
        /*if (message.contains(NEW_LINE)) {
      // a new line would break the CSV format, so we replace it here
      message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
    }*/
        builder + MSG_SEPARATOR
        builder + message

        // new line
        builder + NEW_LINE

        logStrategy?.log(priority, tag, builder.toString())
    }

    private fun logLevel(value: Int): String? {
        return when (value) {
            Logger.VERBOSE -> "VERBOSE"
            Logger.DEBUG -> "DEBUG"
            Logger.INFO -> "INFO"
            Logger.WARN -> "WARN"
            Logger.ERROR -> "ERROR"
            Logger.ASSERT -> "ASSERT"
            else -> "UNKNOWN"
        }
    }

    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }

    fun equals(a: CharSequence?, b: CharSequence?): Boolean {
        if (a === b) return true
        if (a != null && b != null) {
            val length = a.length
            if (length == b.length) {
                return if (a is String && b is String) {
                    a == b
                } else {
                    for (i in 0 until length) {
                        if (a[i] != b[i]) return false
                    }
                    true
                }
            }
        }
        return false
    }

    class Builder {
        var date: Date? = null
        var dateFormat: SimpleDateFormat? = null
        var logStrategy: LogStrategy? = null
        var tag = ""

        fun date(date: Date?): Builder {
            this.date = date
            return this
        }

        fun dateFormat(simpleDateFormat: SimpleDateFormat): Builder {
            this.dateFormat = simpleDateFormat
            return this
        }

        fun logStrategy(logStrategy: LogStrategy): Builder {
            this.logStrategy = logStrategy
            return this
        }

        fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun build(): WyxCsvFormatStrategy {
            if (date == null) {
                date = Date()
            }
            if (dateFormat == null) {
                dateFormat =
                    SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS", Locale.CHINA)
            }
            if (logStrategy == null) {
                logStrategy = MCDiskLogStrategy()
            }
            return WyxCsvFormatStrategy(this)
        }
    }
}