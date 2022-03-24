package com.example.tourandtravelmanagement.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourandtravelmanagement.Model.Parks;
import com.example.tourandtravelmanagement.Model.Restaurants;
import com.example.tourandtravelmanagement.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ParkDetailsActivity extends AppCompatActivity {
    private Button mapBtn;
    private ImageView productImage;
    private TextView productDescription, productName, productCity,productAddress;
    private String productId = "";
    FusedLocationProviderClient fusedlocationproviderclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_details);
        productId = getIntent().getStringExtra("pid");
        fusedlocationproviderclient = LocationServices.getFusedLocationProviderClient(this);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productAddress = (TextView) findViewById(R.id.product_address_details);
        productCity = (TextView) findViewById(R.id.product_price_details);

        mapBtn = (Button) findViewById(R.id.pd_add_to_cart_btn);
        getProductDetails(productId);

    }

    protected void onStart() {
        super.onStart();
    }


    public void getProductDetails(String productId) {
        String sSource="";
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Parks");
        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Parks parks = snapshot.getValue(Parks.class);
                    productName.setText(parks.getPname());
                    productCity.setText(parks.getCity());
                    productDescription.setText(parks.getDescription());
                    productAddress.setText(parks.getAddress());
                    Picasso.get().load(parks.getImage()).into(productImage);
                    String sDestination =parks.getAddress();
                    mapBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ActivityCompat.checkSelfPermission(ParkDetailsActivity.this
                                    , Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                            {
                                fusedlocationproviderclient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Location> task) {
                                        Location location=task.getResult();
                                        if(location != null)
                                        {
                                            try {
                                                Geocoder geocoder = new Geocoder(ParkDetailsActivity.this
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
                                ActivityCompat.requestPermissions(ParkDetailsActivity.this
                                        ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                            }

                            Toast.makeText(getApplicationContext(),"Displaying Path To "+ parks.getAddress(),Toast.LENGTH_SHORT).show();
                            DisplayTrack(sSource,sDestination);

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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