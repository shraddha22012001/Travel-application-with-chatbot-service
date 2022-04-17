package com.example.tourandtravelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tourandtravelmanagement.User.HotelActivity;
import com.example.tourandtravelmanagement.User.MainSpotActivity;
import com.example.tourandtravelmanagement.User.MapActivity;
import com.example.tourandtravelmanagement.User.ParksActivity;
import com.example.tourandtravelmanagement.User.RestaurantsActivity;
import com.example.tourandtravelmanagement.User.UpdateProfileActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private ImageView Map_Button,Main_spt_Button,Hotels_Botton,logout_Botton,Restaurants_btn,Profile_update,Parks_btn;
    private FloatingActionButton chatbot;
    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Map_Button=(ImageView) findViewById(R.id.map_btn);
        Main_spt_Button=(ImageView)findViewById(R.id.mainspot_btn);
        Hotels_Botton=(ImageView)findViewById(R.id.Hotels_btn);
        logout_Botton=(ImageView)findViewById(R.id.logout);
        Restaurants_btn=(ImageView)findViewById(R.id.resturants_btn);
        Profile_update=(ImageView)findViewById(R.id.Profile);
        chatbot=(FloatingActionButton)findViewById(R.id.fab);
Parks_btn=(ImageView)findViewById(R.id.parks_btn);


Parks_btn.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(HomeActivity.this, ParksActivity.class);
        startActivity(intent);
    }
});
        Map_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        Main_spt_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this, MainSpotActivity.class);
                startActivity(intent);
            }
        });
     Hotels_Botton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(HomeActivity.this, HotelActivity.class);
             startActivity(intent);
         }
     });
     Restaurants_btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(HomeActivity.this, RestaurantsActivity.class);
             startActivity(intent);
         }
     });
     Profile_update.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(HomeActivity.this, UpdateProfileActivity.class);
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


     chatbot.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
     });

    }
}