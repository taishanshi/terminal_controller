package com.wyx.controler.manager

import com.wyx.controler.manager.interfaces.ControllerInterface

class DefaultController: ControllerInterface {
    override fun getApiVersion(): String {
        return ""
    }

    override fun getAndroidModule(): String {
        return ""
    }

    override fun getAndroidVersion(): String {
        return ""
    }

    override fun getFirmwareVersion(): String {
        return ""
    }

    override fun getSN(): String {
        return ""
    }

    override fun shutdown() {

    }

    override fun reboot() {
    }

    override fun hideNavBar(hide: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNavBarHideState(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSlidShowNotificationBar(hide: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSlideShowNotificationBarOpen(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hideStatusBar(hide: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getStatusBar(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSlideShowNavBar(hide: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSlideShowNavBarOpen(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDpi(): String {
        TODO("Not yet implemented")
    }

    override fun silentInstallApk(apkPath: String, isStartApk: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun unInstallApk(packageName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSDCardPath(): String {
        TODO("Not yet implemented")
    }

    override fun execSuCmd(command: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun setADBOpen(open: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setDefaultLauncher(packageAndClassName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun selfStart(packageName: String): Boolean {
        return false
    }

    override fun daemon(packageName: String, value: Int): Boolean {
        return false
    }
}