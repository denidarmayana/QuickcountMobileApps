package gens.global.gensquickcount;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import gens.global.gensquickcount.function.Lokasi;
import gens.global.gensquickcount.function.PermissionCallback;

public class MainActivity extends AppCompatActivity implements PermissionCallback {
    Lokasi lokasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lokasi = new Lokasi(this,this);
        lokasi.requestLocationPermission();
    }
    @Override
    public void onPermissionGranted() {
        lokasi.requestLocationUpdates();
    }
    @Override
    public void onPermissionDenied() {
        // Izin ditolak, berikan pesan kepada pengguna
        Toast.makeText(this, "Izin lokasi ditolak. Aplikasi tidak dapat berfungsi dengan baik tanpa izin lokasi.", Toast.LENGTH_SHORT).show();

        // Tampilkan dialog atau snackbar dengan pesan lebih detail atau tautan ke pengaturan
        showPermissionDeniedDialog();
    }
    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izin Lokasi Dibutuhkan");
        builder.setMessage("Aplikasi memerlukan izin lokasi untuk berfungsi dengan baik. Buka pengaturan aplikasi untuk memberikan izin?");
        builder.setPositiveButton("Buka Pengaturan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAppSettings();
            }
        });
        builder.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        lokasi.stopLocationUpdates();
    }
}