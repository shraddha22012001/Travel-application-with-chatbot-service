package com.example.tourandtravelmanagement.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourandtravelmanagement.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddNewPlaceActivity extends AppCompatActivity {
    private String CityNmae,Description,Address,Pname,saveCurrentDate,saveCurrentTime;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDescription,InputProductAddress,InputProductCity;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private StorageReference ProductImageRef;
    private String ProductRandomKey,downloadImageUrl;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place);
        Toast.makeText(AddNewPlaceActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
        ProductImageRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef= FirebaseDatabase.getInstance().getReference().child("Mainspots");
        AddNewProductButton=(Button) findViewById(R.id.add_new_place_btn);
        InputProductName=(EditText)findViewById(R.id.place_name);
        InputProductDescription=(EditText)findViewById(R.id.place_description);
        InputProductAddress=(EditText)findViewById(R.id.place_address);
        InputProductCity=(EditText)findViewById(R.id.place_city);
        InputProductImage=(ImageView)findViewById(R.id.select_product);
        loadingBar =new ProgressDialog(this);
        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }

    private void OpenGallery(){
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick&& resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData(){
        Description=InputProductDescription.getText().toString();
        Address=InputProductAddress.getText().toString();
        CityNmae=InputProductCity.getText().toString();
        Pname=InputProductName.getText().toString();

        if(ImageUri==null){
            Toast.makeText(this,"Place Image is mandatory",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this,"Please write Place Description",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Address)){
            Toast.makeText(this,"Please write Place Address",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(CityNmae)){
            Toast.makeText(this,"Please write  City Name",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Pname)){
            Toast.makeText(this,"Please write Place Name",Toast.LENGTH_SHORT).show();

        }
        else{
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Adding New Place");
        loadingBar.setMessage("please wait ,While we are Adding new place");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calender=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calender.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calender.getTime());
        ProductRandomKey = saveCurrentDate + saveCurrentTime;


        StorageReference filepath=ProductImageRef.child(ImageUri.getLastPathSegment() + ProductRandomKey);
        final UploadTask uploadTask=filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(AddNewPlaceActivity.this,"Error"+ message,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddNewPlaceActivity.this,"Image Uploaded Successfully..",Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }
                        downloadImageUrl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            downloadImageUrl=task.getResult().toString();


                            Toast.makeText(AddNewPlaceActivity.this, "got Product Image url  Successfully..", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveProductInfoToDatabase() {
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid",ProductRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("city",CityNmae);
        productMap.put("address",Address);
        productMap.put("pname",Pname);
        ProductRef.child(ProductRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    loadingBar.dismiss();
                    finish();
                    Toast.makeText(AddNewPlaceActivity.this,"Place added successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingBar.dismiss();
                    String message=task.getException().toString();
                    Toast.makeText(AddNewPlaceActivity.this,"Error"+ message,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}