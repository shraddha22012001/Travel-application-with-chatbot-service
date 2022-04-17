package com.example.tourandtravelmanagement.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourandtravelmanagement.LoginActivity;
import com.example.tourandtravelmanagement.Model.Hotels;
import com.example.tourandtravelmanagement.Prevalent.Prevalent;
import com.example.tourandtravelmanagement.R;
import com.example.tourandtravelmanagement.Registeractivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BookHotelActivity extends AppCompatActivity {
    private EditText BookingName,BookingAddress,BookingPhone,NoRooms,NoAdults,NoChildren,FromDate,NoDays,CheckinTime;
    private Button ConfirmBooking;
    private String HotelId="",hotelAddress="",hotelName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hotel);
        ConfirmBooking=(Button)findViewById(R.id.confirm_booking_btn);

        HotelId=getIntent().getStringExtra("pid");
        BookingName=(EditText)findViewById(R.id.booking_name);
        BookingAddress=(EditText)findViewById(R.id.booking_address);
        BookingPhone=(EditText)findViewById(R.id.booking_phnno);
        NoRooms=(EditText)findViewById(R.id.no_rooms);
        NoAdults=(EditText)findViewById(R.id.no_adults);
        NoChildren=(EditText)findViewById(R.id.no_Children);
        FromDate=(EditText)findViewById(R.id.visit_date);
        NoDays=(EditText)findViewById(R.id.no_days);
        CheckinTime=(EditText)findViewById(R.id.check_in_time);

        getProductDetails(HotelId);


    }

    private void getProductDetails(String HotelId){

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Hotels");
        productRef.child(HotelId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Hotels hotels  = snapshot.getValue(Hotels.class);
                   hotelName=hotels.getPname();
                  hotelAddress=hotels.getAddress();

                    ConfirmBooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Check();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void Check(){
        if(TextUtils.isEmpty(BookingName.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide your name...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(BookingPhone.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide your phone number...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(BookingAddress.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide your Address...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(NoRooms.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide number of rooms...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(NoAdults.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide number of Adults...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(NoChildren.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide number of Children...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(FromDate.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide Date when you want to book room...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(NoDays.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide number of Days you want to stay...",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(CheckinTime.getText().toString())){
            Toast.makeText(BookHotelActivity.this,"please provide Check-in Time...",Toast.LENGTH_SHORT).show();
        }
        else

        {
            confirmBooking();

        }
    }
  private void confirmBooking(){
      String saveCurrentTime,saveCurrentDate;


      Calendar calforDate=Calendar.getInstance();
      SimpleDateFormat currentDate=new SimpleDateFormat("MM,dd,yyyy");
      saveCurrentDate=currentDate.format(calforDate.getTime());
      SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
      saveCurrentTime=currentTime.format(calforDate.getTime());
      final DatabaseReference Bookingref;
      Bookingref= FirebaseDatabase.getInstance().getReference();
      Bookingref.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
     HashMap<String,Object> bookingMap=new HashMap<>();
      bookingMap.put("hotelname",hotelName);
      bookingMap.put("hoteladdress",hotelAddress);
     bookingMap.put("date",saveCurrentDate);
      bookingMap.put("time",saveCurrentTime);
      bookingMap.put("name",BookingName.getText().toString());
      bookingMap.put("phone",BookingPhone.getText().toString());
      bookingMap.put("address",BookingAddress.getText().toString());
      bookingMap.put("rooms",NoRooms.getText().toString());
      bookingMap.put("adults",NoAdults.getText().toString());
      bookingMap.put("childs",NoChildren.getText().toString());
      bookingMap.put("nodays",NoDays.getText().toString());
      bookingMap.put("fromdate",FromDate.getText().toString());
      bookingMap.put("checkintime",CheckinTime.getText().toString());

      Bookingref.child("Bookings").child(Prevalent.CurrentOnlineUser.getPhone()).updateChildren(bookingMap)
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful())
                      {
                          Toast.makeText(BookHotelActivity.this,"Congratulations ,Your have Successfuly Booked Hotel",Toast.LENGTH_SHORT).show();

                      }
                      else
                      {

                          Toast.makeText(BookHotelActivity.this,"Network Error: Try again",Toast.LENGTH_SHORT).show();

                      }
                  }
              });

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });
  }
}