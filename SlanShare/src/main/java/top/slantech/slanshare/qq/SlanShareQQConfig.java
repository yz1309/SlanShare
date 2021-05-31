package top.slantech.slanshare.qq;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

import top.slantech.slanshare.R;
import top.slantech.slanshare.SlanShareMedia;
import top.slantech.slanshare.SlanSharePlatform;
import top.slantech.slanshare.SlanShareWeb;

/**
 * 微信分享初始化
 *
 * 注册官网
 * http://open.weixin.qq.com
 *
 * API调用说明
 * https://wiki.open.qq.com/wiki/mobile/API%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E#portal-header
 *
 * 环境搭建
 * https://wiki.open.qq.com/index.php?title=Android_SDK%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA
 * Created by slantech on 2021/05/25 16:53
 */
public class SlanShareQQConfig {
    private static final String TAG = "QQConfig";

    private static boolean debugLog = true;

    public static boolean isInit = false;

    private static boolean isFinish = false;

    private static Object lockObject = new Object();

    // IWXAPI 是第三方app和微信通信的openApi接口
    public static Tencent api;

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
                    Log.e("QQConfig", "context is null !!!");
                return;
            }
            if (isInit) {
                if (debugLog)
                    Log.e("QQConfig", "has inited !!!");
                return;
            }
            Context context = paramContext.getApplicationContext();

            try {
                api = Tencent.createInstance(appID, paramContext);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            //UMUtils.setAppkey(context, sAppkey);

            if (debugLog)
                Log.i("QQConfig", "current appID is " + appID);


            synchronized (lockObject) {
                isFinish = true;
            }
        } catch (Exception exception) {
            if (debugLog)
                Log.e("QQConfig", "init e is " + exception);
        } catch (Throwable throwable) {
            if (debugLog)
                Log.e("QQConfig", "init e is " + throwable);
        }

        if (!isInit)
            isInit = true;
    }

    public static void shareQQ(Activity activity, SlanSharePlatform platform, SlanShareWeb media, IUiListener listener) {
        if(SlanShareQQConfig.api == null)
        {
            Toast.makeText(activity,"请配置QQappID",Toast.LENGTH_SHORT);
        }

        final Bundle params = new Bundle();

        int typeVal = SlanShareMedia.getSharePlanformTarget(platform.toString());

        if (typeVal == QQShare.SHARE_TO_QQ_TYPE_DEFAULT) {

            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, typeVal);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, media.getTitle());
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, media.getDescrible());
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, media.getUrl());
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, media.getImgUrl());
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getString(R.string.app_name));
            //params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");

            SlanShareQQConfig.api.shareToQQ(activity, params, listener);
        } else {
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, typeVal);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, media.getTitle());
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, media.getDescrible());
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, media.getUrl());
            params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, media.getImgUrl());
            params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, activity.getString(R.string.app_name));
            //params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");

            ArrayList<String> imgs = new ArrayList<>();
            imgs.add(media.getImgUrl());

            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgs);
            SlanShareQQConfig.api.shareToQzone(activity, params, listener);
        }

    }
}

