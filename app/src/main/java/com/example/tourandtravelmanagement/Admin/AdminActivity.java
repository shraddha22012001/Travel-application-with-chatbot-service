package com.example.tourandtravelmanagement.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tourandtravelmanagement.LoginActivity;
import com.example.tourandtravelmanagement.R;
import com.example.tourandtravelmanagement.User.securityquestionActivity;

import io.paperdb.Paper;

public class AdminActivity extends AppCompatActivity {
private Button logout_Botton,Hotel_order,modify_ifo;
private ImageView Hoteladd,mainspotadd,restaurantsadd,parksadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        logout_Botton=(Button)findViewById(R.id.admin_logout_btn);
        modify_ifo=(Button)findViewById(R.id.maintain_btn);
        Hotel_order=(Button)findViewById(R.id.check_orders_btn);
        Hoteladd=(ImageView) findViewById(R.id.hotels);
        mainspotadd=(ImageView) findViewById(R.id.mainspots);
        parksadd=(ImageView) findViewById(R.id.parks);
        restaurantsadd=(ImageView) findViewById(R.id.restaurants);

        Hoteladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddNewHotelActivity.class);
                startActivity(intent);
            }
        });
        parksadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddNewParksActivity.class);
                startActivity(intent);
            }
        });
        mainspotadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddNewPlaceActivity.class);
                startActivity(intent);
            }
        });
        restaurantsadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddNewRestuarantActivity.class);
                startActivity(intent);
            }
        });
        logout_Botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                finish();
            }
        });
        modify_ifo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminMaintainActivity.class);
                startActivity(intent);
            }
        });
        Hotel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminHotelBookingsActivity.class);
                startActivity(intent);
            }
        });
    }
}