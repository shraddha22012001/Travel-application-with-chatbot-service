package com.example.tourandtravelmanagement.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.example.tourandtravelmanagement.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapActivity extends AppCompatActivity {

    //widgets
    private EditText etSource,etdestination;
    Button btTrack;
    private TextView textview1;

   FusedLocationProviderClient fusedlocationproviderclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        etSource=(EditText)findViewById(R.id.et_source);
        etdestination=(EditText)findViewById(R.id.et_destination);
        btTrack=(Button)findViewById(R.id.bt_track);
        textview1 = findViewById(R.id.textview_marquee);
        textview1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textview1.setSelected(true);
        fusedlocationproviderclient = LocationServices.getFusedLocationProviderClient(this);
        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String sSource = etSource.getText().toString().trim();
                String sDestination=etdestination.getText().toString().trim();
                if(sSource.equals("")&&sDestination.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter  location",Toast.LENGTH_SHORT).show();
                }
                else if(sSource.equals("")){
                    if(ActivityCompat.checkSelfPermission(MapActivity.this
                    ,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                    {
                        fusedlocationproviderclient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location=task.getResult();
                                if(location != null)
                                {
                                    try {
                                        Geocoder geocoder = new Geocoder(MapActivity.this
                                                , Locale.getDefault());
                                        List<Address> addresses = geocoder.getFromLocation(
                                                location.getLatitude(),location.getLongitude(),1
                                        );
                                       String sSource = "" + addresses.get(0).getAddressLine(0);
                                    }
                                    catch (IOException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                    }
                    else{
                        ActivityCompat.requestPermissions(MapActivity.this
                        ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                    }


                    DisplayTrack(sSource,sDestination);
                }
                else{
                    DisplayTrack(sSource,sDestination);
                }
            }
        });
    }

    private void DisplayTrack(String sSource, String sDestination) {
        try{
            Uri uri=Uri.parse("https://www.google.co.in/maps/dir/" + sSource +"/" +sDestination);
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e)
        {
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}



