package com.example.fml;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.fml.DB.FMLUsersrealm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.Realm;

public class login extends AppCompatActivity {
    EditText Et_Loginemail, Et_Loginpw;
    Button Btn_login, Btn_join;
    Realm FMLUsersrealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getHashKey();

        Et_Loginemail = (EditText) findViewById(R.id.Et_Loginemail);
        Et_Loginpw = (EditText) findViewById(R.id.Et_Loginpw);
        Btn_login = (Button) findViewById(R.id.Btn_login);
        Btn_join = (Button) findViewById(R.id.Btn_join);

        FMLUsersrealm = Realm.getDefaultInstance();


        Log.d(TAG, "Realm 디렉토리 : " + FMLUsersrealm.getPath());
        Log.d(TAG, "Realm 환경설정 값: " + FMLUsersrealm.getConfiguration());

        Btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), join.class);
                startActivity(intent2);
            }
        });

    }

    public void loginclick(View view) {
        String Lemail = Et_Loginemail.getText().toString();
        String Lpassword = Et_Loginpw.getText().toString();
        FMLUsersrealm = Realm.getDefaultInstance();
        FMLUsersrealm user = FMLUsersrealm.where(FMLUsersrealm.class)
                .equalTo("email", Lemail)
                .equalTo("password", Lpassword)
                .findFirst();
        if (user.equals(Lemail)) {
            Intent intent1 = new Intent(login.this, Main.class);
            startActivity(intent1);

        } else {
            Toast.makeText(login.this, "일치하는 회원이 없습니다.", Toast.LENGTH_LONG).show();
        }

    }


    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
    protected void onDestory() {
        super.onDestroy();
        FMLUsersrealm.close();
    }

}