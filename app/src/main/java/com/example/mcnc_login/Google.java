package com.example.mcnc_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mcnc_login.data.BilliardsVO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Google extends AppCompatActivity implements OnMapReadyCallback{

    ArrayList<BilliardsVO> BilliardsVO = new ArrayList<>();
    ArrayList<LatLng> LocationBilliards = new ArrayList<>();

    private GoogleMap mMap;
    Context context;

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
        BilliardsVO = getBilliardsVO();

        for (int i=0; i < BilliardsVO.size(); i++)
        {
            LocationBilliards.add(new LatLng
                    (
                            Double.parseDouble(BilliardsVO.get(i).getREFINE_WGS84_LAT()),
                            Double.parseDouble(BilliardsVO.get(i).getREFINE_WGS84_LOGT())));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(LocationBilliards.get(i));
            markerOptions.title(BilliardsVO.get(i).getBIZPLC_NM());
            markerOptions.snippet(BilliardsVO.get(i).getREFINE_ROADNM_ADDR());
            mMap.addMarker(markerOptions);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LocationBilliards.get(0), 12));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                for(int i=0;i<BilliardsVO.size();i++)
                {
                    if(marker.getTitle().equals(BilliardsVO.get(i).getBIZPLC_NM()))
                    {
                        Intent intent = new Intent(getApplicationContext().getApplicationContext(), MarkerOnClick.class);
                        intent.putExtra("marker_title", BilliardsVO.get(i).getBIZPLC_NM());
                        intent.putExtra("marker_address", BilliardsVO.get(i).getREFINE_ROADNM_ADDR());
                        startActivity(intent);
                    }
                }

            }
        });

    }

    private ArrayList<BilliardsVO> getBilliardsVO() {

        ArrayList<BilliardsVO> list_BilliardsVO = new ArrayList<>();
        Gson gson = new Gson();

        try {

            InputStream is = getAssets().open("Billiards.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("row");

            int index = 0;

            while (index < jsonArray.length()) {
                BilliardsVO billiardsVO = gson.fromJson(jsonArray.get(index).toString(), BilliardsVO.class);
                list_BilliardsVO.add(billiardsVO);


                index++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list_BilliardsVO;
    }


}