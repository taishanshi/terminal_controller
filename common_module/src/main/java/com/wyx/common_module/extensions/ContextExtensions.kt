package com.wyx.common_module.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File

fun Context.toast(msg: String, during: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, during).show()
}

fun Context.toast(msgId: Int, during: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msgId, during).show()
}

fun Activity.toast(msg: String, during: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, during).show()
}


fun Activity.toast(msgId: Int, during: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msgId, during).show()
}

fun Fragment.toast(msg: String, during: Int = Toast.LENGTH_SHORT) {
    if (context == null) return
    requireContext().toast(msg, during)
}

fun Fragment.toast(msgId: Int, during: Int = Toast.LENGTH_SHORT) {
    if (context == null) return
    requireContext().toast(msgId, during)
}


fun Context.isGranted(vararg permissions: String): Boolean {
    for (permission in permissions) {
        if (!isGranted(permission)) {
            return false
        }
    }
    return true
}

fun Context.isGranted(permission: String): Boolean {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
        this,
        permission
    )
}

fun Context.launchAppDetailsSetting() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$packageName")
    if (!isIntentAvailable(intent)) return
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

fun Context.isIntentAvailable(intent: Intent): Boolean {
    return packageManager
        .queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        ).size > 0
}

fun Fragment.launchAppDetailsSetting() {
    context?.launchAppDetailsSetting()
}

fun Context.launchNotification() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, applicationInfo.uid)
        if (!isIntentAvailable(intent)) return
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } else {
        launchAppDetailsSetting()
    }
}

fun Activity.launchNotification() {
    this.applicationContext.launchNotification()
}


fun Context.versionCode(): Int {
    return packageManager.getPackageInfo(packageName, 0).versionCode
}


fun Context.installApp(filePath: String?) {
    if (filePath.isNotNullAndNotBlank()) {
        installApp(File(filePath!!))
    }
}

fun Context.installApp(file: File?) {
    if (file?.exists() == true) {
        startActivity(getInstallAppIntent(file, true))
    }
}

fun Context.getInstallAppIntent(file: File, isNewTask: Boolean = false): Intent {
    val intent = Intent(Intent.ACTION_VIEW)
    var data: Uri
    val type = "application/vnd.android.package-archive"
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        data = Uri.fromFile(file)
    } else {
        val authority: String = "$packageName.provider"
        data = FileProvider.getUriForFile(this, authority, file)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    grantUriPermission(
        packageName,
        data,
        Intent.FLAG_GRANT_READ_URI_PERMISSION
    )
    intent.setDataAndType(data, type)
    return if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) else intent
}