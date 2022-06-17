package com.example.fml;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.*;
import android.view.*;
import android.content.*;

import com.bumptech.glide.Glide;
import com.example.fml.DB.FMLUsersrealm;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ResourceBundle;

import io.realm.Realm;
import io.realm.RealmChangeListener;


public class UserInfo extends AppCompatActivity implements RealmChangeListener<Realm> {
    private static final int REQUEST_ACCESS_FINE_LOCATION = 1000;
    ImageView userProfile;
    TextView Tv_id;
    EditText Et_Gender, Et_Name, Et_Birth, Et_Number;
    Button uploadimg, Btn_Save;
    Realm FMLUsersrealm;
    int textlength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        userProfile = (ImageView) findViewById(R.id.userProfile);
        Tv_id = (TextView) findViewById(R.id.Tv_id);
        Et_Gender = (EditText) findViewById(R.id.Et_Gender);
        Et_Name = (EditText) findViewById(R.id.Et_Name);
        Et_Birth = (EditText) findViewById(R.id.Et_Birth);
        Et_Number = (EditText) findViewById(R.id.Et_Number);
        uploadimg = (Button) findViewById(R.id.uploadImg);
        Btn_Save = (Button) findViewById(R.id.Btn_Save);


        FMLUsersrealm = Realm.getDefaultInstance();
        FMLUsersrealm.addChangeListener(this);
        showResult();


        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenti = new Intent(Intent.ACTION_PICK);
                intenti.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intenti, 0);
            }
        });

        Et_Birth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Et_Birth.isFocusable() && !s.toString().equals("")) {
                    try {
                        textlength = Et_Birth.getText().toString().length();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (textlength == 4 && before != 1) {

                        Et_Birth.setText(Et_Birth.getText().toString() + ".");
                        Et_Birth.setSelection(Et_Birth.getText().length());

                    } else if (textlength == 7 && before != 1) {

                        Et_Birth.setText(Et_Birth.getText().toString() + ".");
                        Et_Birth.setSelection(Et_Birth.getText().length());

                    } else if (textlength == 5 && !Et_Birth.getText().toString().contains(".")) {

                        Et_Birth.setText(Et_Birth.getText().toString().substring(0, 4) + "." + Et_Birth.getText().toString().substring(4));
                        Et_Birth.setSelection(Et_Birth.getText().length());

                    } else if (textlength == 8 && !Et_Birth.getText().toString().substring(7, 8).equals(".")) {

                        Et_Birth.setText(Et_Birth.getText().toString().substring(0, 7) + "." + Et_Birth.getText().toString().substring(7));
                        Et_Birth.setSelection(Et_Birth.getText().length());

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void showResult( ) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String userid = bundle.getString("email");
        Tv_id.setText(userid);
    }

    @Override
    public void onChange(Realm realm) {
        showResult();
    }

    public void Saveclick(View view) {
        FMLUsersrealm = Realm.getDefaultInstance();
        FMLUsersrealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                FMLUsersrealm userInfo = realm.where(FMLUsersrealm.class).equalTo("email", Tv_id.getText().toString()).findFirst();
                userInfo.setGender(Et_Gender.getText().toString());
                userInfo.setName(Et_Name.getText().toString());
                userInfo.setBirth(Et_Birth.getText().toString());
                userInfo.setNumber(Et_Number.getText().toString());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(UserInfo.this, "회원정보가 저장되었습니다.", Toast.LENGTH_LONG).show();
                Intent intent4 = new Intent(UserInfo.this, Main.class);
                startActivity(intent4);
                finish();
            }
        }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(UserInfo.this, "회원정보가 저장되지않았습니다.", Toast.LENGTH_LONG).show();
                    onDestroy();
                }
                });
        }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == UserInfo.RESULT_OK) {
                //data 에서 uri를 가져온다.
                //미리보기로 출력하기
                Uri uri = data.getData();
                Glide.with(this) //context 부분을 어떻게 처리하는지 유의하자.
                        .load(uri) //실시간 데이터로 교체할것.
                        .centerCrop()
                        .into(userProfile);
            }
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        FMLUsersrealm.close();
    }

}
