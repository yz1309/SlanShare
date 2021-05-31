package top.slantech.slanshare.listener;

import top.slantech.slanshare.SlanSharePlatform;
import top.slantech.slanshare.SlanShareMedia;

/**
 * 面板单击回调
 * Created by slantech on 2021/05/25 17:30
 */
public interface SlanShareBoardListener {
    void onclick(SlanSharePlatform platform, SlanShareMedia media);
}
