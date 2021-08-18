package com.example.mcnc_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mcnc_login.data.BilliardsVO;

import java.util.ArrayList;

public class MarkerOnClick extends AppCompatActivity {

    private Intent intentApp;
    Button btn_app_intent;

    //실행 될 APP, 패키지명
    private final String packageName = "com.example.mcnc_login_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_on_click);

        /*
        Google Class에서 보내온 데이터를 받아 get/set
         */
        Intent intent = getIntent();

        TextView title = (TextView) findViewById(R.id.marker_title);
        TextView address = (TextView) findViewById(R.id.marker_address);

        /*
         */


        /*
        APP TO APP 통신
         */
        //버튼 클릭 이벤트
        btn_app_intent = (Button) findViewById(R.id.btn_app_intent);
        //실행 될 intent
        intentApp = this.getPackageManager().getLaunchIntentForPackage(packageName);
        //버튼이 눌리면,
        btn_app_intent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MarkerOnClick.this.startActivity(intentApp);
            }
        });
        /*
         */


    }
}