package top.slantech.slanshare.wx;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;

import top.slantech.slanshare.SlanShareMedia;
import top.slantech.slanshare.SlanSharePlatform;
import top.slantech.slanshare.SlanShareUtil;
import top.slantech.slanshare.SlanShareWeb;

/**
 * 微信分享初始化
 *
 * 注册官网
 * http://open.weixin.qq.com
 *
 * Android接入指南
 * https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/Android.html
 *
 * Android 开发手册
 * https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Share_and_Favorites/Android.html
 *
 * 开发工具包（SDK）
 * https://developers.weixin.qq.com/doc/oplatform/Downloads/Android_Resource.html
 * Created by slantech on 2021/05/25 16:53
 */
public class SlanShareWXConfig {
    private static final String TAG = "WXConfig";

    private static boolean debugLog = true;

    public static boolean isInit = false;

    private static boolean isFinish = false;

    private static Object lockObject = new Object();

    public static IUiListener listener;

    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI api;

    public static boolean getInitStatus() {
        boolean bool = false;
        synchronized (lockObject) {
            bool = isFinish;
        }
        return bool;
    }

    public static void init(Context paramContext, String appID) {
        try {
            if (paramContext == null) {
                if (debugLog)
                    Log.e("WXConfig", "context is null !!!");
                return;
            }
            if (isInit) {
                if (debugLog)
                    Log.e("WXConfig", "has inited !!!");
                return;
            }
            Context context = paramContext.getApplicationContext();

            try {
                // 通过WXAPIFactory工厂，获取IWXAPI的实例
                api = WXAPIFactory.createWXAPI(context, appID, true);

                // 将应用的appId注册到微信
                api.registerApp(appID);
            } catch (Throwable throwable) {
            }

            //UMUtils.setAppkey(context, sAppkey);

            if (debugLog)
                Log.i("WXConfig", "current appID is " + appID);


            synchronized (lockObject) {
                isFinish = true;
            }
        } catch (Exception exception) {
            if (debugLog)
                Log.e("WXConfig", "init e is " + exception);
        } catch (Throwable throwable) {
            if (debugLog)
                Log.e("WXConfig", "init e is " + throwable);
        }

        if (!isInit)
            isInit = true;
    }

    public static void shareWechat(Activity activity, SlanSharePlatform platform, SlanShareWeb media,IUiListener _listener) {

        if(SlanShareWXConfig.api == null)
        {
            Toast.makeText(activity,"请配置微信appID",Toast.LENGTH_SHORT);
        }

        listener = _listener;

        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = media.getUrl();

        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = media.title;
        msg.description = media.getDescrible();
        msg.thumbData = SlanShareUtil.bmpToByteArray(media.getBitmap(), true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "";
        req.message = msg;

        req.scene = SlanShareMedia.getSharePlanformTarget(platform.toString());

        SlanShareWXConfig.api.sendReq(req);
    }
}

