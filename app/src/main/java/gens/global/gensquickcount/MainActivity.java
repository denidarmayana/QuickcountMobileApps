package gens.global.gensquickcount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import gens.global.gensquickcount.function.MySession;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 102;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 104;
    private static final int CORE_LOCATION_PERMISSION_REQUEST_CODE = 105;
    final int TIMEOUT_DURATION = 2000;
    Handler handler;
    Runnable runnable;
    MySession mySession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySession = new MySession(this);
        checkAndRequestPermissions();
    }
    public void cekSession() {
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                mySession.checkLogin();
            }
        };
    }
    private void checkAndRequestPermissions() {
        String[] permissions = {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        };

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, getPermissionRequestCode(permission));
                return;
            }
        }
    }
    private int getPermissionRequestCode(String permission) {
        switch (permission) {
            case android.Manifest.permission.CAMERA:
                return CAMERA_PERMISSION_REQUEST_CODE;
            case android.Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return STORAGE_PERMISSION_REQUEST_CODE;
            case android.Manifest.permission.READ_EXTERNAL_STORAGE:
                return READ_STORAGE_PERMISSION_REQUEST_CODE;
            case android.Manifest.permission.ACCESS_FINE_LOCATION:
                return LOCATION_PERMISSION_REQUEST_CODE;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                return CORE_LOCATION_PERMISSION_REQUEST_CODE;
            default:
                return -1; // Unknown permission
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE || requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    cekSession();
                    handler.postDelayed(runnable, TIMEOUT_DURATION);
                }else{
                    cekSession();
                    handler.postDelayed(runnable, TIMEOUT_DURATION);
                }
            }
        }
    }

}