package top.slantech.slanshare;

import android.graphics.Bitmap;

/**
 * Created by slantech on 2021/05/27 16:54
 */

public class SlanShareWeb {
    public String title;
    public String describle;
    public Bitmap bitmap;
    public String imgUrl;
    public String url;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public String getDescrible() {
        return describle == null ? "" : describle;
    }

    public Bitmap getBitmap() {
        return bitmap ;
    }

    public String getImgUrl() {
        return imgUrl == null ? "" : imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
