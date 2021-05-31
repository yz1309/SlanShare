package top.slantech.slanshare.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.List;

import top.slantech.slanshare.SlanSharePlatform;
import top.slantech.slanshare.listener.SlanShareBoardListener;

/**
 * 分享面板-PopupWindow
 * Created by slantech on 2021/05/25 17:36
 */
public class SlanShareBoard extends PopupWindow {
    public SlanShareBoard(Context context, List<SlanSharePlatform> list, SlanShareBoardListener boardListener) {
        super(context);
        this.setWindowLayoutMode(-1,-1);

        SlanShareActionFrame frame = new SlanShareActionFrame(context);

        frame.setPlatformData(list,boardListener);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        frame.setLayoutParams(layoutParams);

        frame.setmDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                SlanShareBoard.this.dismiss();
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                SlanShareBoard.this.dismiss();
            }
        });

        this.setContentView(frame);
        this.setFocusable(true);

    }


}
