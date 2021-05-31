package top.slantech.jdbjpro

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import top.slantech.slanshare.qq.SlanShareQQConfig
import top.slantech.slanshare.wx.SlanShareWXConfig

@Suppress("ConstantConditionIf")
class CusApplication : Application() {


    private var appContext: Context? = null

    companion object {

        lateinit var instance: CusApplication

        //全局静态代码段效果
        init {

        }

    }

    private var activitySet: MutableSet<Activity> = HashSet()
    var currentActivity: Activity? = null


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        CusApplication.Companion.instance = this


        appContext = applicationContext

        SlanShareQQConfig.init(this, "1110837899")
        SlanShareWXConfig.init(this, "wx8980e6accd73fbe5")


    }


}