package gens.global.gensquickcount.fubction;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

public class Lokasi {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Lokasi(Context context){
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
    }
    public void startLocationUpdates() {
        // Cek izin lokasi
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Izin diberikan, lanjutkan dengan mendapatkan lokasi
            requestLocationUpdates();
        } else {
            // Jika belum diberikan, minta izin
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void requestLocationUpdates() {
        // Tentukan kriteria untuk pembaruan lokasi
        String provider = LocationManager.GPS_PROVIDER;
        long minTime = 1000; // dalam milidetik
        float minDistance = 10; // dalam meter

        // Berlangganan pembaruan lokasi
        locationManager.requestLocationUpdates(provider, minTime, minDistance, locationListener);
    }

    public void stopLocationUpdates() {
        // Hentikan pembaruan lokasi
        locationManager.removeUpdates(locationListener);
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            // Proses lokasi baru di sini
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            double altitude = location.getAltitude();
            Toast.makeText(context, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }
}
