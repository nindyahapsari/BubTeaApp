package com.nindyahapsari.bubteatapp.ViewHolder;


import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nindyahapsari.bubteatapp.Interface.ItemClickListner;
import com.nindyahapsari.bubteatapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView mTeaName;
    public ImageView mBubteaProduct;
    public ItemClickListner listner;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mBubteaProduct = (ImageView) itemView.findViewById(R.id.tea_product_list);
        mTeaName = (TextView) itemView.findViewById(R.id.bubtea_name);

    }

    public void setItemClickListner(ItemClickListner listner){

        this.listner = listner;

    }



    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(), false);

    }
}
