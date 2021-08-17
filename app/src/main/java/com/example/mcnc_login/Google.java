package com.example.mcnc_login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mcnc_login.data.Billiards;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Google extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng BestBilliards = new LatLng(37.436131, 126.8819581);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(BestBilliards);
        markerOptions.title("베스트 당구장");
        markerOptions.snippet("경기도 광명시 기아로 22 (소하동,3층)");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BestBilliards, 15));
    }
}