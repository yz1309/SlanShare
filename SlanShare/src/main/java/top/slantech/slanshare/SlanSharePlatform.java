package top.slantech.slanshare;

/**
 * Created by slantech on 2021/05/27 15:53
 */

public final class SlanSharePlatform {
    public String title;
    public int icon;
    public SlanShareMedia platform;

    public SlanSharePlatform() {
    }

    public SlanSharePlatform(String media) {
        this.platform = SlanShareMedia.convertToEnum(media);
    }
}
