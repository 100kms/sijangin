package org.androidtown.sijang.Util;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;
import com.tsengvn.typekit.Typekit;

public class GlobalApplication extends Application {
    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        KakaoSDK.init(new KakaoSDKAdapter());
        Typekit.getInstance().addNormal(Typekit.createFromAsset(this, "fonts/BMDOHYEON_ttf.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/BMDOHYEON_ttf.ttf"));
    }

    public static GlobalApplication getGlobalApplicationContext() {
        return obj;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }
}