package top.slantech.slanshare;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

/**
 * Created by slantech on 2021/05/27 15:51
 */

public enum SlanShareMedia {
    WeChat, WeChat_Circle,
    QQ, QQ_Qone;


    public static SlanShareMedia convertToEnum(String media) {
        SlanShareMedia[] var1 = values();
        SlanShareMedia[] var2 = var1;
        int var3 = var1.length;

        for (int i = 0; i < var3; ++i) {
            SlanShareMedia temp = var2[i];
            if (temp.toString().trim().equals(media)) {
                return temp;
            }
        }

        return null;
    }

    public SlanSharePlatform toSlanPlatform() {
        SlanSharePlatform slanPlatform = new SlanSharePlatform();
        if (this.toString().equals("WeChat")) {
            slanPlatform.title = "微信";
            slanPlatform.icon = R.drawable.slanshare_wechat;
        }
        if (this.toString().equals("WeChat_Circle")) {
            slanPlatform.title = "微信朋友圈";
            slanPlatform.icon = R.drawable.slanshare_wechat_circle;
        }
        if (this.toString().equals("QQ")) {
            slanPlatform.title = "QQ好友";
            slanPlatform.icon = R.drawable.slanshare_qq;
        }
        if (this.toString().equals("QQ_Qone")) {
            slanPlatform.title = "QQ好友";
            slanPlatform.icon = R.drawable.slanshare_qq_qone;
        }

        slanPlatform.platform = this;
        return slanPlatform;
    }

    public static int getSharePlanformTarget(String media) {
        int rtn = 0;
        if (media.equals("WeChat")) {
            rtn = SendMessageToWX.Req.WXSceneSession;
        }
        if (media.equals("WeChat_Circle")) {
            rtn = SendMessageToWX.Req.WXSceneTimeline;
        }
        if (media.equals("QQ")) {
            rtn = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
        }
        if (media.equals("QQ_Qone")) {
            rtn = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
        }

        return rtn;
    }

    public static boolean getSharePlanformIsWX(String media) {
        Boolean rtn = false;
        if (media.equals("WeChat")) {
            rtn = true;
        }
        if (media.equals("WeChat_Circle")) {
            rtn = true;
        }
        return rtn;
    }

    public static boolean getSharePlanformIsQQ(String media) {
        Boolean rtn = false;
        if (media.equals("QQ")) {
            rtn = true;
        }
        if (media.equals("QQ_Qone")) {
            rtn = true;
        }
        return rtn;
    }

}
