package com.example.tourandtravelmanagement.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tourandtravelmanagement.Model.Hotels;
import com.example.tourandtravelmanagement.R;
import com.example.tourandtravelmanagement.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HotelActivity extends AppCompatActivity {
    private EditText inputtxt;
    private Button SearchBtn;
    private String SearchInput;
    private RecyclerView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        inputtxt=(EditText)findViewById(R.id.search_product_name);
        SearchBtn=(Button)findViewById(R.id.search_product_btn);
        searchList=findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(this));


        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInput=inputtxt.getText().toString();
                onStart();
            }
        });

    }

    public void onStart(){
        super.onStart();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels");

        FirebaseRecyclerOptions<Hotels> options=
                new FirebaseRecyclerOptions.Builder<Hotels>()
                        .setQuery(reference.orderByChild("pname").startAt(SearchInput),Hotels.class).build();



        FirebaseRecyclerAdapter<Hotels,ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Hotels, ProductViewHolder>(options) {

            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Hotels hotels) {
                productViewHolder.txtProductName.setText(hotels.getPname());
                productViewHolder.txtProductAddress.setText(hotels.getAddress());
                productViewHolder.txtProductCity.setText(hotels.getCity());
                Picasso.get().load(hotels.getImage()).into(productViewHolder.imageView);

                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(HotelActivity.this, HotelDetailsActivity.class);
                        intent.putExtra("pid",hotels.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override

            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }
        };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}