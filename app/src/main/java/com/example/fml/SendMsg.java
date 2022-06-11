package com.example.fml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.*;
import android.view.*;
import android.content.*;

import java.io.IOException;
import java.util.List;

public class SendMsg extends AppCompatActivity {
    TextView Tv_Gps;
    EditText Et_sendNum;
    Button Btn_SendMsg;
    final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    Geocoder g = new Geocoder(this);
    List<Address> address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendmessage);

        Tv_Gps = (TextView) findViewById(R.id.Tv_Gps);
        Et_sendNum = (EditText) findViewById(R.id.Et_SendNum);
        Btn_SendMsg = (Button) findViewById(R.id.Btn_SendMsg);
        startLocationService();


        Btn_SendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = Et_sendNum.getText().toString();
                String sms = Tv_Gps.getText().toString();

                try {
                    //전송
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "위치가 전송되었습니다. ", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "위치가 전송되지않았습니다. ", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
    }

    private void startLocationService() {

        // get manager instance
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // set listener
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);
    }


    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            //capture location data sent by current provider
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "위도 : "+ latitude + "\n경도:"+ longitude;
            Log.i("GPSLocationService", msg);
            List<Address> list = null;
            try {
                //미리 구해놓은 위도값 mLatitude;
                //미리 구해놓은 경도값 mLongitude;

                list = g.getFromLocation(
                        latitude, // 위도
                        longitude, // 경도
                        10); // 얻어올 값의 개수
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류");
            }
            if (list != null) {
                if (list.size()==0) {
                    Tv_Gps.setText("해당되는 주소 정보는 없습니다");
                } else {
                    Tv_Gps.setText(list.get(0).getAddressLine(0));
                }
            }



        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    public void GpsView () {
            }
        }



