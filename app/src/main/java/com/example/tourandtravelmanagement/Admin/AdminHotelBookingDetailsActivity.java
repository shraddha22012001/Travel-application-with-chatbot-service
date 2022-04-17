package com.example.tourandtravelmanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tourandtravelmanagement.Model.Bookings;
import com.example.tourandtravelmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminHotelBookingDetailsActivity extends AppCompatActivity {
    private TextView username,phone,address,hotelname,hoteladdress,bookingdate,bookingfrom,bookingnodays,bookingnoadults,bookingnochilds,checkintime,bookingnorooms;
    private String productId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hotel_booking_details);
        productId=getIntent().getStringExtra("uid");
        username=(TextView) findViewById(R.id.book_user_name);
        phone=(TextView) findViewById(R.id.book_user_phone);
        address=(TextView) findViewById(R.id.book_user_permanent_add);
        hotelname=(TextView) findViewById(R.id.book_user_hotel_name);
        hoteladdress=(TextView) findViewById(R.id.book_user_hotel_address);
        bookingdate=(TextView) findViewById(R.id.book_user_Booked_at);
        bookingfrom=(TextView) findViewById(R.id.book_user_from_date);
        bookingnodays=(TextView) findViewById(R.id.book_user_no_of_days);
        bookingnoadults=(TextView) findViewById(R.id.book_user_no_adults);
        bookingnochilds=(TextView) findViewById(R.id.book_user_no_childs);
        checkintime=(TextView) findViewById(R.id.book_user_checkin_time);
        bookingnorooms=(TextView) findViewById(R.id.book_user_no_rooms);

        getProductDetails(productId);
    }
    private void getProductDetails(String productId) {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Bookings");
        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Bookings products=snapshot.getValue(Bookings.class);

                  username.setText("User Name:" + products.getName());
                    phone.setText("User Phone Number:" + products.getPhone());
                    address.setText("User Permanent Address:" + products.getAddress());
                    hotelname.setText("Hotel Name:" + products.getHotelname());
                    hoteladdress.setText("Hotel Address:" + products.getHoteladdress());
                    bookingdate.setText("Booked On :"  +products.getDate() + " " + products.getTime());
                    bookingfrom.setText("Visit On:" + products.getFromdate());
                    checkintime.setText("Check-In Time:" + products.getCheckintime());
                    bookingnoadults.setText("Adults:" + products.getAdults());
                    bookingnochilds.setText("Children:" + products.getChilds());
                    bookingnodays.setText("Days he/she want to stay:" + products.getNodays());
                    bookingnorooms.setText("Rooms :" + products.getRooms());




                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}