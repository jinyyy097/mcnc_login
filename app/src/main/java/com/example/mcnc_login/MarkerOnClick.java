package com.example.mcnc_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MarkerOnClick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_on_click);

        Intent intent = getIntent();

        TextView title = (TextView) findViewById(R.id.marker_title);
        TextView address = (TextView) findViewById(R.id.marker_address);

        title.setText(intent.getStringExtra("marker_title"));
        address.setText(intent.getStringExtra("marker_address"));
    }
}