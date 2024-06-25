package com.wyx.terminalcontroller.ui.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AutoStartReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            // 启动应用程序的代码
            val launchIntent =
                context!!.packageManager.getLaunchIntentForPackage("com.wyx.terminalcontroller.ui.LauncherActivity")
            launchIntent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(launchIntent)
        }
    }
}