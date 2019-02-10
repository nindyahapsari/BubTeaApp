package com.nindyahapsari.bubteatapp.ViewHolder;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nindyahapsari.bubteatapp.Interface.ItemClickListner;
import com.nindyahapsari.bubteatapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mCartDate;
    public TextView mCartName;
    public TextView mCartQuantity;
    public TextView mCartTotalPrice;
    private ItemClickListner listner;






    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        mCartDate = (TextView) itemView.findViewById(R.id.summary_date_value);
        mCartName = (TextView) itemView.findViewById(R.id.summary_name_value);
        mCartQuantity = (TextView) itemView.findViewById(R.id.summary_quantity_value);
        mCartTotalPrice = (TextView) itemView.findViewById(R.id.summary_totalprice_value);


    }

    public void setItemClickListner(ItemClickListner itemClickListner){

        this.listner = listner;

    }


    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(), false);


    }
}
