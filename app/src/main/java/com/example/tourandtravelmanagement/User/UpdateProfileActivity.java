package com.example.tourandtravelmanagement.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourandtravelmanagement.Prevalent.Prevalent;
import com.example.tourandtravelmanagement.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText fullNameChangeTextBtn,userPhoneEditText,addressEditText;
    private TextView saveTextBtn;
    private Button SecurityQuestionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        fullNameChangeTextBtn=(EditText)findViewById(R.id.setting_full_name);
        userPhoneEditText=(EditText)findViewById(R.id.setting_phone_no);
        addressEditText=(EditText)findViewById(R.id.setting_address);
        saveTextBtn=(TextView) findViewById(R.id.update_setting_btn);
        SecurityQuestionBtn=(Button) findViewById(R.id.security_questions_btn);
        SecurityQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateProfileActivity.this, securityquestionActivity.class);

                intent.putExtra("check","settings");
                startActivity(intent);
            }
        });


        saveTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userInfosaved();


            }

        });
    }
        public void userInfosaved(){
            if(TextUtils.isEmpty(fullNameChangeTextBtn.getText().toString())){

                Toast.makeText(this,"name is mendatory..",Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(userPhoneEditText.getText().toString())) {

                Toast.makeText(this,"phone is mendatory",Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(addressEditText.getText().toString())) {

                Toast.makeText(this,"address is mendatory",Toast.LENGTH_SHORT).show();
            }
            else {
                updateOnlyuserinfo();
            }
        }
        public void updateOnlyuserinfo(){

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");

            HashMap<String,Object> userMap=new HashMap<>();
            userMap.put("name",fullNameChangeTextBtn.getText().toString());
            userMap.put("address",addressEditText.getText().toString());
            userMap.put("phoneorder",userPhoneEditText.getText().toString());


            ref.child(Prevalent.CurrentOnlineUser.getPhone()).updateChildren(userMap);
            Toast.makeText(this,"Profile information updated sucessfully",Toast.LENGTH_SHORT).show();


        }
    }
