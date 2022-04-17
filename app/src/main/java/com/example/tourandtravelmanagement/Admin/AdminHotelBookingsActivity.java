package com.example.tourandtravelmanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourandtravelmanagement.Model.Bookings;
import com.example.tourandtravelmanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AdminHotelBookingsActivity extends AppCompatActivity {
    private RecyclerView bookingList;
    private DatabaseReference bookingref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hotel_bookings);
        bookingref = FirebaseDatabase.getInstance().getReference().child("Bookings");
        bookingList = findViewById(R.id.booking_list);
        bookingList.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Bookings> options = new FirebaseRecyclerOptions.Builder<Bookings>()
                .setQuery(bookingref, Bookings.class).build();

        FirebaseRecyclerAdapter<Bookings, AdminOrderViewHolder> adapter =
                new FirebaseRecyclerAdapter<Bookings, AdminOrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrderViewHolder adminOrderViewHolder, int i, @NonNull Bookings adminOrders) {

                        adminOrderViewHolder.userName.setText("Name:" + adminOrders.getName());
                        adminOrderViewHolder.userPhoneNumber.setText("Phone:" + adminOrders.getPhone());
                        adminOrderViewHolder.userhotelname.setText("Hotel Name:" + adminOrders.getHotelname());
                        adminOrderViewHolder.userDateTime.setText("Booked on:" + adminOrders.getDate() + " " + adminOrders.getTime());
                        adminOrderViewHolder.userfromdate.setText("Visit On:" + adminOrders.getFromdate() );
                        adminOrderViewHolder.ShowOrderBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uID = getRef(i).getKey();

                                Intent intent = new Intent(AdminHotelBookingsActivity.this, AdminHotelBookingDetailsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });


                        adminOrderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHotelBookingsActivity.this);
                                builder.setTitle("Are you Sure ,You Want To Delete this Booking ?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 0) {
                                            String uID = getRef(i).getKey();
                                            RemoveOrder(uID);
                                        } else {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_layout, parent, false);
                        return new AdminOrderViewHolder(view);
                    }
                };
        bookingList.setAdapter(adapter);
        adapter.startListening();
    }


    public static class AdminOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userPhoneNumber, userhotelname, userDateTime, userfromdate;
        public Button ShowOrderBtn;

        public AdminOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.booking_user_name);
            userPhoneNumber = itemView.findViewById(R.id.booking_PhoneNumber);
            userfromdate = itemView.findViewById(R.id.from_date);
            userDateTime = itemView.findViewById(R.id.book_date_time);
            userhotelname = itemView.findViewById(R.id.hotel_name);
            ShowOrderBtn = itemView.findViewById(R.id.show_orders_btn);

        }
    }

    private void RemoveOrder(String uID) {


        bookingref.child(uID).removeValue();


    }
}