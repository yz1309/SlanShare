package top.slantech.jdbjpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import kotlinx.android.synthetic.main.init_activity_main.*
import top.slantech.slanshare.SlanShareAction
import top.slantech.slanshare.SlanShareMedia
import top.slantech.slanshare.SlanSharePlatform
import top.slantech.slanshare.SlanShareWeb
import top.slantech.slanshare.listener.SlanShareBoardListener
import java.lang.ref.WeakReference

class IndexActivity : AppCompatActivity() {
    lateinit var mShareListener: IUiListener
    lateinit var mShareAction: SlanShareAction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        initShare()

        activity_main_tv_share.setOnClickListener {
            mShareAction.open()
        }

    }


    private fun initShare() {
        mShareListener =
            CustomShareListener(this)

        mShareAction = SlanShareAction(this).setDisplayList(
            SlanShareMedia.WeChat,
            SlanShareMedia.WeChat_Circle,
            SlanShareMedia.QQ,
            SlanShareMedia.QQ_Qone
        )
            .setShareBoardClickCallback(object :
                SlanShareBoardListener {
                override fun onclick(platform: SlanSharePlatform?, media: SlanShareMedia?) {

                    var shareWeb = SlanShareWeb();
                    shareWeb.title = "我是分享的标题";
                    shareWeb.describle = "我是分享的描述";
                    shareWeb.url = "http://www.jindianjiang.com/officialWebsite"
                    shareWeb.imgUrl = "http://cdn.jindianjiang.com/appFile/1618550830954.png"
                    //检查发送时的缩略图大小是否超过32k
                    //shareWeb.bitmap =  BitmapFactory.decodeResource(resources, R.drawable.slanshare_qq)
                    SlanShareAction(this@IndexActivity)
                        .withShareContent(shareWeb)
                        .setPlatform(platform)
                        .setCallback(mShareListener).share()
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //QQ分享 应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加如下代码
        Tencent.onActivityResultData(requestCode, resultCode, data, mShareListener)

        Log.e("MainC", "requestCode=" + requestCode + ",resultCode=" + resultCode)

    }

    private class CustomShareListener(activity: IndexActivity) :
        IUiListener {
        private val mActivity: WeakReference<IndexActivity?>


        override fun onComplete(p0: Any?) {
            Log.e("MainC", "onComplete")
            Toast.makeText(mActivity.get()?.baseContext, "分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel() {
            Log.e("MainC", "onCancel")
            Toast.makeText(mActivity.get()?.baseContext, "分享取消", Toast.LENGTH_SHORT).show()
        }

        override fun onWarning(p0: Int) {
        }

        override fun onError(p0: UiError?) {
            Log.e("MainC", "onError")
            Toast.makeText(mActivity.get()?.baseContext, "分享失败", Toast.LENGTH_SHORT).show()
        }

        init {
            mActivity = WeakReference<IndexActivity?>(activity)
        }
    }
}