package com.wyx.controler.manager

import android.content.Context
import com.wyx.common_module.extensions.log
import com.wyx.common_module.logger.Logger
import com.wyx.controler.manager.interfaces.ControllerInterface
import com.ys.rkapi.MyManager

object YsController: ControllerInterface {
    private val TAG: String = "YsController"
    private lateinit var mMyManager: MyManager
    private var isConnected = false
    private lateinit var mContent:Context

    fun init(context: Context) {
        Logger.d(TAG, "YsController init")
        mMyManager = MyManager.getInstance(context)

        mMyManager.setConnectClickInterface {
            Logger.d(TAG, "连接成功")
            daemon(context.packageName, 2)
            mContent = context
            isConnected = true
            selfStart(mContent.packageName)

            fullScreen()
        }

        mMyManager.bindAIDLService(context)

    }

    fun fullScreen() {
        Logger.d(TAG, "开启全屏")
        hideNavBar(true)
        hideStatusBar(false)
        setSlideShowNavBar(false)

        val launcer = "com.wyx.terminalcontroler/com.wyx.terminalcontroler.ui.LauncherActivity"
        setDefaultLauncher(launcer)
    }

    fun unFullScreen() {
        Logger.d(TAG, "退出全屏")
        hideNavBar(false)
        hideStatusBar(true)
        setSlideShowNavBar(true)


        val launcer = " com.android.theme/com.android.theme.icon_pack.rounded.launcher"
        setDefaultLauncher(launcer)
    }

    override fun getApiVersion(): String {
        try {
            return if(isConnected) {
                TAG.log("获取 api 版本号 ${mMyManager.apiVersion}")
                mMyManager.apiVersion
            } else {
                ""
            }
        } catch (e: Exception) {
            TAG.log("getApiVersion error: $e")
        }
        return ""
    }

    override fun getAndroidModule(): String {
        try {
            return if(isConnected) {
                TAG.log("获取 android module ${mMyManager.androidModle}")
                mMyManager.androidModle
            } else {
                ""
            }
        } catch (e: Exception) {
            TAG.log("getAndroidModule error: $e")
        }
        return ""
    }

    override fun getAndroidVersion(): String {
        try {
            return if(isConnected) {
                TAG.log("获取 android version ${mMyManager.androidVersion}")
                mMyManager.androidVersion
            } else {
                ""
            }
        } catch (e: Exception) {
            TAG.log("getAndroidVersion error: $e")
        }
        return ""
    }

    override fun getFirmwareVersion(): String {
        try {
            return if(isConnected) {
                TAG.log("获取 firmware version ${mMyManager.firmwareVersion}")
                mMyManager.firmwareVersion
            } else {
                ""
            }
        } catch (e: Exception) {
            TAG.log("getFirmwareVersion error: $e")
        }
        return ""
    }

    override fun getSN(): String {
        try {
            return if(isConnected) {
                TAG.log("获取 sn ${mMyManager.sn}")
                mMyManager.sn
            } else {
                ""
            }
        } catch (e: Exception) {
            TAG.log("getSN error: $e")
        }
        return ""
    }

    override fun shutdown() {
        try {
            if(isConnected) {
                TAG.log("shutdown  ")
                mMyManager.shutdown()
            }
        } catch (e: Exception) {
            TAG.log("shutdown error: $e")
        }
    }

    override fun reboot() {
        try {
            if(isConnected) {
                TAG.log("reboot  ")
                mMyManager.reboot()
            }
        } catch (e: Exception) {
            TAG.log("reboot error: $e")
        }
    }

    override fun hideNavBar(hide: Boolean): Boolean {
        var rs = false;
        try {
            if(isConnected) {
                TAG.log("hideNavBar  $hide")
                rs = mMyManager.hideNavBar(hide)
            }
        } catch (e: Exception) {
            TAG.log("hideNavBar error: $e")
        }
        return rs
    }

    override fun getNavBarHideState(): Boolean {
        TAG.log("getNavBarHideState")
        try {
            if(isConnected) {
               return mMyManager.navBarHideState
            }
        } catch (e: Exception) {
            TAG.log("getNavBarHideState error: $e")
        }
        return false
    }

    override fun setSlidShowNotificationBar(hide: Boolean): Boolean {
        var rs = false;
        try {
            if(isConnected) {
                TAG.log("setSlidShowNotificationBar  $hide")
                rs = mMyManager.setSlideShowNotificationBar(hide)
            }
        } catch (e: Exception) {
            TAG.log("setSlideShowNotificationBar error: $e")
        }
        return rs
    }

    override fun isSlideShowNotificationBarOpen(): Boolean {
        TAG.log("isSlidShowNotificationBar")
        try {
            if(isConnected) {
                return mMyManager.isSlideShowNotificationBarOpen
            }
        } catch (e: Exception) {
            TAG.log("isSlidShowNotificationBar error: $e")
        }
        return false
     }

    override fun hideStatusBar(hide: Boolean): Boolean {
        var rs = false;
        try {
            if(isConnected) {
                TAG.log("hideStatusBar  $hide")
                rs = mMyManager.hideStatusBar(hide)
            }
        } catch (e: Exception) {
            TAG.log("hideStatusBar error: $e")
        }
        return rs
    }

    override fun getStatusBar(): Boolean {
        TAG.log("getStatusBar")
        try {
            if(isConnected) {
                return mMyManager.statusBar
            }
        } catch (e: Exception) {
            TAG.log("getStatusBar error: $e")
        }
        return false
    }

    override fun setSlideShowNavBar(hide: Boolean): Boolean {
        var rs = false;
        try {
            if(isConnected) {
                TAG.log("setSlideShowNavBar  $hide")
                rs = mMyManager.setSlideShowNavBar(hide)
            }
        } catch (e: Exception) {
            TAG.log("setSlideShowNavBar error: $e")
        }
        return rs
    }

    override fun isSlideShowNavBarOpen(): Boolean {
        TAG.log("getStatusBar")
        try {
            if(isConnected) {
                return mMyManager.isSlideShowNavBarOpen
            }
        } catch (e: Exception) {
            TAG.log("getStatusBar error: $e")
        }
        return false
    }

    override fun getDpi(): String {
        try {
            return if(isConnected) {
                TAG.log("获取 dpi: ${mMyManager.dpi}")
                mMyManager.dpi
            } else {
                ""
            }
        } catch (e: Exception) {
            TAG.log("getDpi error: $e")
        }
        return ""
    }

    override fun silentInstallApk(apkPath: String, isStartApk: Boolean): Boolean {
        TAG.log("silentInstallApk : $apkPath,  isStartApk: $isStartApk")
        try {
            if(isConnected) {
                return mMyManager.silentInstallApk(apkPath, isStartApk)
            }
        } catch (e: Exception) {
            TAG.log("silentInstallApk error: $e")
        }
        return false
    }

    override fun unInstallApk(packageName: String): Boolean {
        TAG.log("unInstallApk : $packageName")
        try {
            if(isConnected) {
                return mMyManager.unInstallApk(packageName)
            }
        } catch (e: Exception) {
            TAG.log("unInstallApk error: $e")
        }
        return false
    }

    override fun getSDCardPath(): String {
        try {
            return if(isConnected) {
                TAG.log("获取 sd card path : ${mMyManager.sDcardPath}")
                mMyManager.sDcardPath
            } else {
                ""
            }
        } catch (e: Exception) {
            TAG.log("getSDCardPath error: $e")
        }
        return ""
    }

    override fun execSuCmd(command: String): Boolean {
        TAG.log("execSuCmd : $command")
        try {
            if(isConnected) {
                return mMyManager.execSuCmd(command)
            }
        } catch (e: Exception) {
            TAG.log("execSuCmd error: $e")
        }
        return false
    }

    override fun setADBOpen(open: Boolean) {
        TAG.log("setADBOpen : $open")
        try {
            if(isConnected) {
                mMyManager.setADBOpen(open)
            }
        } catch (e: Exception) {
            TAG.log("setADBOpen error: $e")
        }
    }

    override fun setDefaultLauncher(packageAndClassName: String): Boolean {
        TAG.log("setDefaultLauncher : $packageAndClassName")
        try {
            if(isConnected) {
                return mMyManager.setDefaultLauncher(packageAndClassName)
            }
        } catch (e: Exception) {
            TAG.log("setDefaultLauncher error: $e")
        }
        return false
    }

    override fun selfStart(packageName: String): Boolean {
        TAG.log("selfStart : $packageName")
        try {
            if(isConnected) {
                return mMyManager.selfStart(packageName)
            }
        } catch (e: Exception) {
            TAG.log("selfStart error: $e")
        }
        return false
    }

    override fun daemon(packageName: String, value: Int): Boolean {
        TAG.log("daemon : $packageName, value: $value")
        try {
            if(isConnected) {
                return mMyManager.daemon(packageName, value)
            }
        } catch (e: Exception) {
            TAG.log("daemon error: $e")
        }
        return false
    }
}