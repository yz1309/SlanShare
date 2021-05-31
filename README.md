# scjdbj-android

#### introduce
a share SDK，include wechat、qq

#### instrcution for use

1. build.gradle import SlanShareSDK、Wechat SDK、QQ SDK

   ```
       //wechat
       implementation 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:+'
   
       //QQ
       implementation 'com.tencent.tauth:qqopensdk:3.51.2'
       
       implementation project(path: ':SlanShare')
   ```

   

   

2. add callback Activity

   wechat create wxapi directory on the package，and create WXEntryActivity implement SlanShareWXCallbackActivity

   

   QQ don't need to add Activity，need to this

   ```
       override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
           super.onActivityResult(requestCode, resultCode, data)
           //QQ分享 应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加如下代码
           Tencent.onActivityResultData(requestCode, resultCode, data, mShareListener)
   
           Log.e("MainC", "requestCode=" + requestCode + ",resultCode=" + resultCode)
   
       }
   ```

   

3. config AndroidManifest.xml

   ```
       <!--SlanShare start-->
       <!--Android 11-第三方应用无法拉起微信适配-->
       <queries>
           <package android:name="com.tencent.mm" />
           <package android:name="com.tencent.mobileqq" />
           <package android:name="com.qzone" />
       </queries>
   
       <!--SlanShare end-->
       
       <application>
       	<!--SlanShare start-->
           <!--wechart start-->
           <activity android:name=".wxapi.WXEntryActivity"
               android:label="@string/app_name"
               android:exported="true"
               android:taskAffinity="top.slantech.jdbjpro"
               android:launchMode="singleTask"
               />
   
   
           <!--wechart end-->
   
   
           <!--qq start-->
           <!--明确引用org.apache.http.legacy库，避免QQ官方open sdk在Android 9上报错 -->
           <uses-library
               android:name="org.apache.http.legacy"
               android:required="false" />
   
           <activity
               android:name="com.tencent.tauth.AuthActivity"
               android:launchMode="singleTask"
               android:noHistory="true">
               <intent-filter>
                   <action android:name="android.intent.action.VIEW" />
   
                   <category android:name="android.intent.category.DEFAULT" />
                   <category android:name="android.intent.category.BROWSABLE" />
   
                   <data android:scheme="tencent11111" />
                   <!--<data android:scheme="tencent你的AppId" />-->
               </intent-filter>
           </activity>
           <activity
               android:name="com.tencent.connect.common.AssistActivity"
               android:configChanges="orientation|keyboardHidden|screenSize"
               android:screenOrientation="behind"
               android:theme="@android:style/Theme.Translucent.NoTitleBar" />
   
   
   
           <!--qq end-->
           <!--SlanShare end-->
       </application>
   ```

   

4. init set

   in Application 

   ```
   SlanShareQQConfig.init(this, "11111")
   SlanShareWXConfig.init(this, "wx2225")
   ```

   

5. other info see demo.

