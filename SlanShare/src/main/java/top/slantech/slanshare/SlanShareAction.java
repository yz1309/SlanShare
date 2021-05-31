package top.slantech.slanshare;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.tencent.tauth.IUiListener;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import top.slantech.slanshare.listener.SlanShareBoardListener;
import top.slantech.slanshare.qq.SlanShareQQConfig;
import top.slantech.slanshare.widget.SlanShareBoard;
import top.slantech.slanshare.wx.SlanShareWXConfig;

/**
 * 分享动作
 * Created by slantech on 2021/05/25 17:20
 */
public class SlanShareAction {
    private Activity activity;
    private SlanShareBoard board;
    private View view = null;
    private List<SlanSharePlatform> list = new ArrayList<>();
    private SlanSharePlatform platform;
    private Object shareContent;
    private IUiListener listener;
    // private IUiListener BaseUiListener;

    private SlanShareBoardListener boardListener;

    public SlanShareAction(Activity activity) {
        if (activity == null)
            return;
        this.activity = (Activity) (new WeakReference(activity)).get();
    }

    public SlanShareAction setDisplayList(SlanShareMedia... medias) {
        this.list.clear();


        for (int i = 0; i < medias.length; i++) {
            SlanShareMedia media = medias[i];
            this.list.add(media.toSlanPlatform());
        }

        return this;
    }

    public IUiListener getCallback(){
        return listener;
    }

    public SlanShareAction setCallback(IUiListener listener) {
        this.listener = listener;
        return this;
    }

    public SlanShareAction setShareBoardClickCallback(SlanShareBoardListener boardListener) {
        this.boardListener = boardListener;
        return this;
    }

    public SlanShareAction setPlatform(SlanSharePlatform platform) {
        this.platform = platform;
        return this;
    }

    public SlanSharePlatform getPlatform() {
        return this.platform;
    }

    public SlanShareAction withShareContent(Object object) {
        if (object instanceof SlanShareWeb) {
            this.shareContent = object;
        }
        return this;
    }

    public void open() {
        this.board = new SlanShareBoard(this.activity, list, this.boardListener);

        if (this.view == null) {
            this.view = this.activity.getWindow().getDecorView();
        }

        this.board.showAtLocation(this.view, 80, 0, 0);
    }

    public void share() {
        SlanShareWeb media = null;
        if (this.shareContent instanceof SlanShareWeb) {
            media = (SlanShareWeb) this.shareContent;
        }

        if (media == null) {
            Log.e("WXShareAction", "请设置分享内容");
            return;
        }
        if (media.getBitmap() != null) {
            share(media);
            return;
        }
        if (media.getBitmap() == null && !TextUtils.isEmpty(media.getImgUrl())) {
            SlanShareWeb finalMedia = media;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bmp = BitmapFactory.decodeStream(new URL(finalMedia.getImgUrl()).openStream());
                        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                        bmp.recycle();
                        finalMedia.setBitmap(thumbBmp);
                        share(finalMedia);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void share(SlanShareWeb media) {
        if (SlanShareMedia.getSharePlanformIsWX(this.platform.platform.toString())) {
            SlanShareWXConfig.shareWechat(activity,this.platform,media,listener);
        } else {
            SlanShareQQConfig.shareQQ(    activity,this.platform,media,listener);
        }
    }


}