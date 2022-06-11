package com.example.fml;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import android.view.*;
import android.content.*;

import com.example.fml.DB.UserDB;

import io.realm.Realm;

public class join extends AppCompatActivity {
    EditText Et_Joinemail, Et_Joinpw, Et_Repw;
    TextView Tv_ckview;
    Button Btn_check, Btn_joinSuceess;
    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        Et_Joinemail = (EditText) findViewById(R.id.Et_Joinemail);
        Et_Joinpw = (EditText) findViewById(R.id.Et_Joinpw);
        Et_Repw = (EditText) findViewById(R.id.Et_Repw);
        Tv_ckview = (TextView) findViewById(R.id.Tv_ckview);
        Btn_check = (Button) findViewById(R.id.Btn_check);
        Btn_joinSuceess = (Button) findViewById(R.id.Btn_JoinSucceess);

        mRealm = Realm.getDefaultInstance();


        Btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Et_Repw.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(Et_Joinpw.getText() == null) {
                            Tv_ckview.setText("비밀번호를 재입력하세요.");
                        }
                        if(Et_Joinpw.getText().toString().equals(Et_Repw.getText().toString())) {
                            Tv_ckview.setText("비밀번호가 맞습니다.");
                        }
                        else {
                            Tv_ckview.setText("동일한 비밀번호를 입력하세요.");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });
        Btn_joinSuceess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (realm.where(UserDB.class).equalTo("email", Et_Joinemail.getText().
                                toString()).count() > 0) {
                            realm.cancelTransaction();
                        }
                        UserDB user = realm.createObject(UserDB.class);
                        user.setEmail(Et_Joinemail.getText().toString());
                        user.setPassword(Et_Joinpw.getText().toString());
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(join.this, "회원가입이 되었습니다.", Toast.LENGTH_LONG).show();
                        Intent intent3 = new Intent(getApplicationContext(), UserInfo.class);
                        startActivity(intent3);
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(join.this, "회원가입이 되지않았습니다.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}