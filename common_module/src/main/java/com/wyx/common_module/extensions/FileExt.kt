package com.wyx.common_module.extensions

import java.io.File

fun File.getLength(): Long {
    if (this.isDirectory) {
        return this.getDirLength()
    }
    return getFileLength()
}

fun File.getDirLength(): Long {
    if (!this.isDir()) return -1
    var length = 0L
    this.listFiles()?.forEach {
        if (it.isDirectory) length += it.getDirLength()
        length += it.length()
    }
    return length
}

fun File.getFileLength(): Long {
    if (!this.isFil()) return -1
    return length()
}

fun File?.isDir(): Boolean = this != null && this.exists() && isDirectory

fun File?.isFil(): Boolean = this != null && this.exists() && isFile

