package com.wyx.controler.manager.interfaces

interface ControllerInterface {
    /**
     * 获取api 版本
     */
    fun getApiVersion(): String

    /**
     * 获取设备型号
     */
    fun getAndroidModule(): String

    /**
     * 获取系统版本
     */
    fun getAndroidVersion(): String


    /**
     * 获取固件版本
     */
    fun getFirmwareVersion(): String

    /**
     * 获取SN
     */
    fun getSN(): String

    fun shutdown()
    fun reboot()

    /**
     * 显示或隐藏导航栏
     */
    fun hideNavBar(hide: Boolean) : Boolean

    /**
     * 查询导航栏隐藏状态
     */
    fun getNavBarHideState(): Boolean

    /**
     * 设置滑出通知栏打开或者关闭
     */
    fun setSlidShowNotificationBar(hide: Boolean): Boolean

    /**
     * 查询滑出通知栏打开或者关闭
     */
    fun isSlideShowNotificationBarOpen(): Boolean

    /**
     * 状态栏状态设置
     */
    fun hideStatusBar(hide: Boolean): Boolean

    /**
     * 设置滑出导航栏打开或关闭
     */
    fun getStatusBar():Boolean

    /**
     * 状态栏状态设置
     */
    fun setSlideShowNavBar(hide: Boolean): Boolean

    /**
     * 查询滑出导航栏是否打开
     */
    fun isSlideShowNavBarOpen():Boolean

    /**
     * 获取DPI
     */
    fun getDpi(): String

    /**
     * 静默安装
     * @param apkPath 安装路径
     * @param isStartApk 静默安装后是否打开， true-打开， false-不打开
     */
    fun silentInstallApk(apkPath: String, isStartApk: Boolean): Boolean

    /**
     * 静默卸载
     */
    fun unInstallApk(packageName: String): Boolean

    /**
     * 查询SD卡路径
     */
    fun getSDCardPath(): String

    /**
     * 以 ROOT 权限下执行 shell 命令
     */
    fun execSuCmd(command: String):Boolean

    /**
     * 打开或关闭 adb 连接
     */
    fun setADBOpen(open: Boolean)

    /**
     * 设置默认 Launcher
     * @param packageAndClassName Launcher3 的包名和启动类名
     * "com.android.launcher3/com.android.launcher3
     * .Launcher"
     */
    fun setDefaultLauncher(packageAndClassName: String):Boolean

    /**
     * 设置开机自启
     */
    fun selfStart(packageName: String): Boolean

    /**
     * 设置守护进程应用
     * @param value 守护的时间 0 = 30 秒 ，1 = 60 秒 2=180 秒默认 30 秒
     */
    fun daemon(packageName: String, value: Int): Boolean
}