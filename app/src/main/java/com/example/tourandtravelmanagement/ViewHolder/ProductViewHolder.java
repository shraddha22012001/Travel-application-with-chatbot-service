package com.example.tourandtravelmanagement.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourandtravelmanagement.Interface.ItemClickListner;
import com.example.tourandtravelmanagement.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName,txtProductAddress,txtProductCity;
    public ImageView imageView;
    public ItemClickListner listner;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=(ImageView)itemView.findViewById(R.id.product_img);
        txtProductName=(TextView)itemView.findViewById(R.id.product_nm);
        txtProductAddress=(TextView)itemView.findViewById(R.id.product_descrip);
        txtProductCity=(TextView)itemView.findViewById(R.id.product_price);

    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner=listner;
    }
    @Override
    public void onClick(View view)
    {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
