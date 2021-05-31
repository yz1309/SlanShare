package top.slantech.slanshare.listener;

/**
 * 分享结果回调
 * 暂时没用到，
 * Created by slantech on 2021/05/25 17:48
 */
public interface SlanShareListeners {
    void onStart( );

    void onResult( );

    void onError( );

    void onCancel( );
}
