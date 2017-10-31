package org.androidtown.sijang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.androidtown.sijang.MainView.MainActivity;


public class FirstMainActivity extends FragmentActivity {

    SessionCallback callback;
    String id;
    SharedPreferences pref;
    SharedPreferences check_pref;
    SharedPreferences.Editor editor;

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
        check_pref = getSharedPreferences("user_info", MODE_PRIVATE);
        if(check_pref.getString("user_id","").equalsIgnoreCase("") || check_pref.getString("user_id","").equalsIgnoreCase("guest")){
            callback = new SessionCallback();
            Session.getCurrentSession().addCallback(callback);
        }else{
            Intent intent = new Intent(FirstMainActivity.this, MainActivity.class);
            startActivity(intent);
        }


        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, 466);
        }

        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
            }, 466);
        }



        Button empty_btn = (Button)findViewById(R.id.empty_btn);
//
        empty_btn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        pref = getSharedPreferences("user_info", MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putString("user_name", "guest");
                        editor.putString("user_id", "guest");
                        editor.commit();
                        Intent intent = new Intent(FirstMainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            System.out.println("===========if===========================");
            return;
        }
        System.out.println("===========noif===========================");
        super.onActivityResult(requestCode, resultCode, data);
    }



    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.d(message);

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {

                }

                @Override
                public void onNotSignedUp() {
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    pref = getSharedPreferences("user_info", MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putString("user_name", userProfile.getNickname());
                    editor.putString("user_id", Long.toString(userProfile.getId()));
                    editor.commit();
                    Intent intent = new Intent(FirstMainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            // 세션 연결이 실패했을때
            // 어쩔때 실패되는지는 테스트를 안해보았음 ㅜㅜ
        }
    }
}