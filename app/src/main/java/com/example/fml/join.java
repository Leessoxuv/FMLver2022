package com.example.fml;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.*;
import android.view.*;
import android.content.*;

import com.example.fml.DB.FMLUsersrealm;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class join extends AppCompatActivity {
    EditText Et_Joinemail, Et_Joinpw, Et_Repw;
    TextView Tv_ckidview, Tv_ckview;
    Button Btn_echeck, Btn_pcheck, Btn_joinSuceess;
    Realm FMLUsersrealm;
    private String Jemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        Et_Joinemail = (EditText) findViewById(R.id.Et_Joinemail);
        Et_Joinpw = (EditText) findViewById(R.id.Et_Joinpw);
        Et_Repw = (EditText) findViewById(R.id.Et_Repw);
        Tv_ckidview = (TextView) findViewById(R.id.Tv_ckidview);
        Tv_ckview = (TextView) findViewById(R.id.Tv_ckview);
        Btn_echeck = (Button) findViewById(R.id.Btn_echeck);
        Btn_pcheck = (Button) findViewById(R.id.Btn_pcheck);
        Btn_joinSuceess = (Button) findViewById(R.id.Btn_JoinSucceess);

        FMLUsersrealm = Realm.getDefaultInstance();


        Btn_echeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ckemail = Et_Joinemail.getText().toString();
                RealmResults<FMLUsersrealm> userid = FMLUsersrealm.where(FMLUsersrealm.class)
                        .equalTo("email", ckemail)
                        .findAll();
                if (!Et_Joinemail.getText().toString().equals(userid)) {
                    Tv_ckidview.setText("사용 가능한 이메일 입니다. ");
                } else if (Et_Joinpw.getText().toString().equals(userid)) {
                    Tv_ckidview.setText("아이디가 이미 존재합니다. ");
                } else {
                    Tv_ckidview.setText(" ");
                }
            }
        });


        Btn_pcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Et_Joinpw.getText().toString().equals(Et_Repw.getText().toString())) {
                    Tv_ckview.setText("비밀번호가 일치합니다. ");
                    Btn_joinSuceess.setEnabled(true);
                    Btn_joinSuceess.setBackgroundColor(Color.parseColor("#eeddff"));
                } else if (Et_Joinpw.getText().toString() != Et_Repw.getText().toString()) {
                    Tv_ckview.setText("비밀번호가 일치하지 않습니다.");
                } else if (Et_Repw.getText().toString() == null) {
                    Tv_ckview.setText("동일한 비밀번호를 입력하세요.");
                } else {
                    Tv_ckview.setText(" ");
                }
            }
        });
    }

    public void Joinclick(View view) {
        FMLUsersrealm = Realm.getDefaultInstance();
        FMLUsersrealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                FMLUsersrealm user = realm.createObject(FMLUsersrealm.class);
                user.setEmail(Et_Joinemail.getText().toString());
                user.setPassword(Et_Joinpw.getText().toString());
                Jemail = Et_Joinemail.getText().toString();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(join.this, "회원가입이 되었습니다.", Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(join.this, UserInfo.class);
                intent3.putExtra("email",Jemail);
                startActivity(intent3);
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(join.this, "회원가입이 되지않았습니다.", Toast.LENGTH_LONG).show();
                onDestory();
            }
        });
    }
    protected void onDestory() {
        super.onDestroy();
        FMLUsersrealm.close();
    }
}

