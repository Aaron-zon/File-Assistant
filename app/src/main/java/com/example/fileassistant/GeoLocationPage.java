package com.example.fileassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class GeoLocationPage extends AppCompatActivity implements View.OnClickListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private Button gps_btn;
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_location_page);

        gps_btn = findViewById(R.id.gps_btn);
        gps_btn.setOnClickListener(this);
        // 检查位置权限
        if (checkLocationPermission()) {
            // 权限已授予，可以获取位置信息
            getLocation();
        } else {
            // 请求位置权限
            requestLocationPermission();
        }
    }

    // 检查位置权限
    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // 请求位置权限
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE
        );
    }

    // 获取位置信息
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkLocationPermission()) {
                    // 权限已授予，获取位置信息
                    try {
                        // 获取最后一次已知的位置
                        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastKnownLocation != null) {
                            double latitude = lastKnownLocation.getLatitude();
                            double longitude = lastKnownLocation.getLongitude();

                            // 在这里处理获取到的经纬度信息
                        } else {
//                            locationManager.requestLocationUpdates(
//                                    LocationManager.NETWORK_PROVIDER,
//                                    1000,
//                                    1,
//                                    locationListener
//                            );
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
                                    locationManager.requestLocationUpdates(
                                            LocationManager.NETWORK_PROVIDER,
                                            1000,
                                            1,
                                            locationListener
                                    );
//                                    locationManager.requestLocationUpdates(
//                                            LocationManager.GPS_PROVIDER,
//                                            500,
//                                            1,
//                                            locationListener
//                                    );
//                                }
//                            }, 50); // 等待 2 秒后再请求位置更新

//                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                                // 已经授予位置权限，可以请求位置信息更新
//                                locationManager.requestLocationUpdates(
//                                        LocationManager.GPS_PROVIDER,
//                                        1000,
//                                        1,
//                                        locationListener
//                                );
//                            } else {
//                                // 请求位置权限
//                                ActivityCompat.requestPermissions(
//                                        this,
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        LOCATION_PERMISSION_REQUEST_CODE
//                                );
//                            }
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
        }
    }

    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 位置权限已授予，可以获取位置信息
                getLocation();
            } else {
            }
        }
    }

    @Override
    public void onClick(View v) {
        flag = true;
        getLocation();
    }
//    public static final int LOCATION_CODE = 301;
//    private Button gps_btn;
//    private String locationProvider = null;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_geo_location_page);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
//        }
//
//        gps_btn = findViewById(R.id.gps_btn);
//        gps_btn.setOnClickListener(this);
//    }
//
    public LocationListener locationListener = new LocationListener() {
        // この機能は、プロバイダーの状態が使用可能、一時的に使用不可、サービスなしの状態を直接切り替えるとトリガーされます。
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println(1);
        }

        // この機能は、GPSがオンになっているなど、プロバイダーが有効になっているときにトリガーされます
        @Override
        public void onProviderEnabled(String provider) {
            System.out.println(1);

        }

        // この機能は、GPSがオフになっているなど、プロバイダーが無効になったときにトリガーされます
        @Override
        public void onProviderDisabled(String provider) {
            System.out.println(1);

        }

        //この関数は、座標が変更されるとトリガーされますが、プロバイダーが同じ座標を渡す場合、トリガーされません。
        @Override
        public void onLocationChanged(Location location) {
            Log.d("GEO", "onLocationChanged");
            if (location != null) {
                if (flag) {
                    flag = false;
                    String provider = location.getProvider();
                    locationManager.removeUpdates(locationListener);
                    Log.d("GEO", provider);
                }

            }
        }
    };
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case LOCATION_CODE:
//                if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    try {
//                        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 1, locationListener);
//                    } catch (SecurityException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    finish();
//                }
//                break;
//        }
//    }

//        @Override
//    public void onClick(View v) {
//        // gps
//        getLocation();
//    }


//    private void getLocation() {
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        List<String> providers = locationManager.getProviders(true);
//        if (providers.contains(LocationManager.GPS_PROVIDER)) {
////GPSの場合
//            locationProvider = LocationManager.GPS_PROVIDER;
//        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
////ネットワークの場合
//            locationProvider = LocationManager.NETWORK_PROVIDER;
//        } else {
//            Intent i = new Intent();
//            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(i);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
//            } else {
//                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                if (lastKnownLocation != null) {
//                    double latitude = lastKnownLocation.getLatitude();
//                    double longitude = lastKnownLocation.getLongitude();
//                }
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
//            }
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
//        }
//    }

}