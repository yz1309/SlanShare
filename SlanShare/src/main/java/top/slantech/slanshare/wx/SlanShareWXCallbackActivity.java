package top.slantech.slanshare.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.tauth.IUiListener;

import java.lang.ref.WeakReference;

import top.slantech.slanshare.R;

/**
 * 微信注册回调
 * Created by slantech on 2021/05/25 18:15
 */
public class SlanShareWXCallbackActivity extends Activity implements IWXAPIEventHandler {
	private static String TAG = "MicroMsg.WXEntryActivity";

    private MyHandler handler;
    private IUiListener listeners;

	private static class MyHandler extends Handler {
		private final WeakReference<SlanShareWXCallbackActivity> wxEntryActivityWeakReference;

		public MyHandler(SlanShareWXCallbackActivity wxEntryActivity){
			wxEntryActivityWeakReference = new WeakReference<SlanShareWXCallbackActivity>(wxEntryActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			int tag = msg.what;

			Log.e("WXCall","handleMessage");
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		handler = new MyHandler(this);
		listeners = SlanShareWXConfig.listener;
        try {
            Intent intent = getIntent();
        	SlanShareWXConfig.api.handleIntent(intent, this);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
		SlanShareWXConfig.api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		Log.e("WXCall","onReq"+req.getType());
		//Toast.makeText(this,   "  type=" + req.getType(), Toast.LENGTH_SHORT).show();

        finish();
	}

	/**
	 * 分享到微信回调
	 * @param resp
	 */
	@Override
	public void onResp(BaseResp resp) {
		int result = 0;
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result = R.string.slanshare_errcode_success;
				listeners.onComplete(null);
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = R.string.slanshare_errcode_cancel;
				listeners.onCancel();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = R.string.slanshare_errcode_deny;
				listeners.onError(null);
				break;
			case BaseResp.ErrCode.ERR_UNSUPPORT:
				result = R.string.slanshare_errcode_unsupported;
				listeners.onError(null);
				break;
			default:
				result = R.string.slanshare_errcode_unknown;
				listeners.onCancel();
				break;
		}
		Log.e("WXCall","onResp"+resp.getType()+","+getString(result));
		//Toast.makeText(this, getString(result) + ", type=" + resp.getType(), Toast.LENGTH_SHORT).show();



        finish();
	}
	

}