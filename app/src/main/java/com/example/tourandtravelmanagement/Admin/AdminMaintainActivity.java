package com.example.tourandtravelmanagement.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tourandtravelmanagement.HomeActivity;
import com.example.tourandtravelmanagement.R;
import com.example.tourandtravelmanagement.User.HotelActivity;
import com.example.tourandtravelmanagement.User.MainSpotActivity;
import com.example.tourandtravelmanagement.User.ParksActivity;
import com.example.tourandtravelmanagement.User.RestaurantsActivity;

public class AdminMaintainActivity extends AppCompatActivity {
    private ImageView Main_spt_Button,Hotels_Botton,Restaurants_btn,Parks_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain);
        Main_spt_Button=(ImageView)findViewById(R.id.mainspot_btn);
        Hotels_Botton=(ImageView)findViewById(R.id.Hotels_btn);
        Restaurants_btn=(ImageView)findViewById(R.id.resturants_btn);

        Parks_btn=(ImageView)findViewById(R.id.parks_btn);
        Parks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminMaintainActivity.this, MaintainParksActivity.class);
                startActivity(intent);
            }
        });
        Main_spt_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminMaintainActivity.this, MaintainMainspotsActivity.class);
                startActivity(intent);
            }
        });
        Hotels_Botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminMaintainActivity.this, MaintainHotelActivity.class);
                startActivity(intent);
            }
        });
        Restaurants_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminMaintainActivity.this, MaintainRestaurantsActivity.class);
                startActivity(intent);
            }
        });
    }
}