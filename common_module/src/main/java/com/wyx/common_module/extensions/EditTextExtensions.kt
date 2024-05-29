package com.wyx.common_module.extensions

import android.text.InputFilter
import android.widget.EditText

fun EditText.forbiddenBlank() {
    filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
        if (source.isBlank()) "" else null
    }
    )
}