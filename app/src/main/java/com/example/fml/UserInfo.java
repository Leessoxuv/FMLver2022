package com.example.fml;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import android.view.*;
import android.content.*;

import com.bumptech.glide.Glide;
import com.example.fml.DB.UserDB;
import com.example.fml.DB.UserInfoDB;
import com.example.fml.DB.UserModule;
import com.example.fml.DB.UsersModule;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class UserInfo extends AppCompatActivity implements RealmChangeListener<Realm> {
    ImageView userProfile;
    TextView Tv_id;
    EditText Et_Gender, Et_Name, Et_Birth, Et_Number;
    Button uploadimg, Btn_Save;
    Realm userInfoRealm;
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


        userInfoRealm.init(this);
        RealmConfiguration userModuleConfig = new RealmConfiguration.Builder()
                .modules(new UserModule())
                .name("UserInfo.realm")
                .build();
        userInfoRealm = Realm.getDefaultInstance();
        userInfoRealm = Realm.getInstance(userModuleConfig);
        userInfoRealm.setDefaultConfiguration(userModuleConfig);
        userInfoRealm.addChangeListener(this);
        showResult();
        onDestroy();


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
                if(Et_Birth.isFocusable() && !s.toString().equals("")) {
                    try{
                        textlength = Et_Birth.getText().toString().length();
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                        return;
                    }

                    if (textlength == 4 && before != 1) {

                        Et_Birth.setText(Et_Birth.getText().toString()+".");
                        Et_Birth.setSelection(Et_Birth.getText().length());

                    }else if (textlength == 7&& before != 1){

                        Et_Birth.setText(Et_Birth.getText().toString()+".");
                        Et_Birth.setSelection(Et_Birth.getText().length());

                    }else if(textlength == 5 && !Et_Birth.getText().toString().contains(".")){

                        Et_Birth.setText(Et_Birth.getText().toString().substring(0,4)+"."+Et_Birth.getText().toString().substring(4));
                        Et_Birth.setSelection(Et_Birth.getText().length());

                    }else if(textlength == 8 && !Et_Birth.getText().toString().substring(7,8).equals(".")){

                        Et_Birth.setText(Et_Birth.getText().toString().substring(0,7)+"."+Et_Birth.getText().toString().substring(7));
                        Et_Birth.setSelection(Et_Birth.getText().length());

                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        Btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (realm.where(UserDB.class).equalTo("email", Tv_id.getText().
                                toString()).count() > 0) {
                            realm.cancelTransaction();
                        }
                        UserInfoDB userInfo = realm.createObject(UserInfoDB.class);
                        userInfo.setGender(Et_Gender.getText().toString());
                        userInfo.setName(Et_Name.getText().toString());
                        userInfo.setBirth(Et_Birth.getText().toString());
                        userInfo.setNumber(Et_Number.getText().toString());
                    }
                }, () -> {
                    Toast.makeText(UserInfo.this, "회원정보가 저장되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent4 = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent4);
                }, error -> Toast.makeText(UserInfo.this, "회원정보가 저장되지않았습니다.", Toast.LENGTH_LONG).show());
            }
        });
    }
    private void showResult( ) {
        String userid = userInfoRealm.where(UserDB.class).findFirst().getEmail();
        Tv_id.setText(userid);
    }

    @Override
    public void onChange(Realm realm) {
        showResult();
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
    protected void onDestory() {
        super.onDestroy();
        userInfoRealm.close();
    }
}
