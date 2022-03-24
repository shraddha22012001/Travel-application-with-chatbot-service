package com.example.tourandtravelmanagement.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourandtravelmanagement.Prevalent.Prevalent;
import com.example.tourandtravelmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class securityquestionActivity extends AppCompatActivity {
    private String check="";
    private TextView pagetitle,titlequestions;
    private EditText phonenumber,question1,question2;
    private Button VerifyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securityquestion);
        pagetitle=(TextView)findViewById(R.id.Page_title);
        titlequestions=(TextView)findViewById(R.id.reset_2);
        phonenumber=findViewById(R.id.find_phone_no);
        question1=findViewById(R.id.Question_1);
        question2=findViewById(R.id.Question_2);
        VerifyButton=findViewById(R.id.Verify_btn);
        check=getIntent().getStringExtra("check");

    }
    protected void onStart(){
        super.onStart();

        phonenumber.setVisibility(View.GONE);

        if(check.equals("settings"))
        {
            displayPriviousanswers();
            pagetitle.setText("Security Questions");
            VerifyButton.setText("Set ");

            titlequestions.setText("Please set Answers for following security questions..");
            VerifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setanswers();

                }
            });


        }
        else if(check.equals("login")){
            phonenumber.setVisibility(View.VISIBLE);
            VerifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyuser();
                }
            });


        }
    }

    private void verifyuser() {
        String phone=phonenumber.getText().toString();
        String answer11=question1.getText().toString().toLowerCase();
        String answer22=question2.getText().toString().toLowerCase();
        if(!phone.equals("") && !answer11.equals("") && !answer22.equals("")) {

            final DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference().child("Users")
                    .child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.hasChild("Security Questions")) {
                            String ans1 = snapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2 = snapshot.child("Security Questions").child("answer2").getValue().toString();

                            if (!ans1.equals(answer11)) {
                                Toast.makeText(securityquestionActivity.this, "Your first Answer is wrong....", Toast.LENGTH_SHORT).show();
                            } else if (!ans2.equals(answer22)) {
                                Toast.makeText(securityquestionActivity.this, "Your Second Answer is wrong....", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(securityquestionActivity.this);
                                builder.setTitle("New Password");

                                final EditText newpassword = new EditText(securityquestionActivity.this);
                                newpassword.setHint("Enter New Password");
                                builder.setView(newpassword);
                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!newpassword.getText().toString().equals("")) {
                                            ref.child("password")
                                                    .setValue(newpassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(securityquestionActivity.this, "Password Changed Successfully...", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(securityquestionActivity.this, "Please Enter password....", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();

                            }
                        }
                        else {
                            Toast.makeText(securityquestionActivity.this, "You have not set security Questions....", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(securityquestionActivity.this, "This phone number does not exists....", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            Toast.makeText(securityquestionActivity.this, "Please complete the form....", Toast.LENGTH_SHORT).show();
        }
    }

    private void setanswers(){
        String answer1=question1.getText().toString().toLowerCase();
        String answer2=question2.getText().toString().toLowerCase();

        if(!question1.equals("") && !question2.equals("")) {
            DatabaseReference ref= FirebaseDatabase.getInstance()
                    .getReference().child("Users")
                    .child(Prevalent.CurrentOnlineUser.getPhone());

            HashMap<String,Object> userdataMap=new HashMap<>();
            userdataMap.put("answer1",answer1);
            userdataMap.put("answer2",answer2);

            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(securityquestionActivity.this,"You have answered security questions successfully....",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });

        }
        else {
            Toast.makeText(securityquestionActivity.this,"Plaese answer both questions..",Toast.LENGTH_SHORT).show();
        }
    }

    private void displayPriviousanswers(){
        DatabaseReference ref= FirebaseDatabase.getInstance()
                .getReference().child("Users")
                .child(Prevalent.CurrentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String ans1=snapshot.child("answer1").getValue().toString();
                    String ans2=snapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}