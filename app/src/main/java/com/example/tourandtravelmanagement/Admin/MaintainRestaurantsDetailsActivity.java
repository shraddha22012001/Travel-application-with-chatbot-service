package com.example.tourandtravelmanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourandtravelmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MaintainRestaurantsDetailsActivity extends AppCompatActivity {
    private Button applyChangesBtn,DeleteProductBtn;
    private EditText name,city,address,description;
    private ImageView imageView;
    private String productId="";
    private DatabaseReference productsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_restaurants_details);
        productId=getIntent().getStringExtra("pid");
        productsRef= FirebaseDatabase.getInstance().getReference().child("Restaurants").child(productId);



        applyChangesBtn=(Button)findViewById(R.id.Apply_changes_btn);
        DeleteProductBtn=(Button)findViewById(R.id.Delete_product_btn);
        name=(EditText)findViewById(R.id.Admin_place_nm);
        description=(EditText)findViewById(R.id.Admin_place_descrip);
        city=(EditText)findViewById(R.id.Admin_place_city);
        address=(EditText)findViewById(R.id.Admin_place_adress);
        imageView=(ImageView) findViewById(R.id.Admin_place_img);


        displaysepecificproductinfo();

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyChanges();

            }
        });

        DeleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();

            }
        });
    }

    private void deleteProduct() {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MaintainRestaurantsDetailsActivity.this,"Place removed Successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void ApplyChanges() {

        String pName=name.getText().toString();
        String pCity=city.getText().toString();
        String pDescription=description.getText().toString();
        String pAddress=address.getText().toString();

        if(pName.equals("")){
            Toast.makeText(this,"Please enter Place Name",Toast.LENGTH_SHORT).show();

        }
        else if(pCity.equals("")){
            Toast.makeText(this,"Please enter Place City",Toast.LENGTH_SHORT).show();

        }
        else if(pAddress.equals("")){
            Toast.makeText(this,"Please enter Place Address",Toast.LENGTH_SHORT).show();

        }
        else if(pDescription.equals("")){
            Toast.makeText(this,"Please enter Place Description",Toast.LENGTH_SHORT).show();

        }
        else {
            HashMap<String,Object> productMap=new HashMap<>();
            productMap.put("pid",productId);
            productMap.put("description",pDescription);
            productMap.put("city",pCity);
            productMap.put("pname",pName);
            productMap.put("paddress",pAddress);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MaintainRestaurantsDetailsActivity.this,"Changes applied successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }

    private void displaysepecificproductinfo() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String pName=snapshot.child("pname").getValue().toString();
                    String pDescription=snapshot.child("description").getValue().toString();
                    String pCity=snapshot.child("city").getValue().toString();
                    String pImage=snapshot.child("image").getValue().toString();
                    String pAddress=snapshot.child("address").getValue().toString();


                    name.setText(pName);
                    description.setText(pDescription);
                    city.setText(pCity);
                    address.setText(pAddress);
                    Picasso.get().load(pImage).into(imageView);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}