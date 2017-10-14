package org.androidtown.sijang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class FirstMainActivity extends FragmentActivity {
    private CallbackManager callbackManager;
    LoginButton login_button;
    String id;
    String name;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_first_main);

        Button change_btn = (Button)findViewById(R.id.button_chagne);
        change_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //Intent intent = new Intent(getApplicationContext(), Review_Write.class);
                startActivity(intent);
            }
        });

        callbackManager = CallbackManager.Factory.create();

        login_button = (LoginButton)findViewById(R.id.login_button);
        login_button.setReadPermissions("email", "public_profile", "user_friends");
        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request =GraphRequest.newMeRequest(loginResult.getAccessToken() ,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    id = (String)object.get("id");
                                    name = (String)object.get("name");
                                    Toast.makeText(getApplicationContext(), "로그인 성공"+name+" : "+id, Toast.LENGTH_SHORT).show();

                                    pref = getSharedPreferences("user_info", MODE_PRIVATE);
                                    editor = pref.edit();
                                    editor.putString("user_id", id);
                                    editor.putString("user_name", name);
                                    editor.commit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "취소.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException exception) {
                Log.e("페북에러" , exception.toString());
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}