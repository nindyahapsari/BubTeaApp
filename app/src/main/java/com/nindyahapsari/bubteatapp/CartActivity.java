package com.nindyahapsari.bubteatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nindyahapsari.bubteatapp.Models.Cart;
import com.nindyahapsari.bubteatapp.Prevalent.Prevalent;
import com.nindyahapsari.bubteatapp.ViewHolder.CartViewHolder;
import com.nindyahapsari.bubteatapp.ViewHolder.ProductViewHolder;

import io.paperdb.Paper;

import static com.nindyahapsari.bubteatapp.ProductDetailActivity.EXTRA_QUANTITY;
import static com.nindyahapsari.bubteatapp.ProductDetailActivity.EXTRA_TEA_NAME;
import static com.nindyahapsari.bubteatapp.ProductDetailActivity.EXTRA_TOTAL_PRICE;

public class CartActivity extends AppCompatActivity {


    private DatabaseReference mAdminViewDB;
    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    private Button mPaymentMethodBtn;

    private String mTotalPricePaypal = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        Paper.init(this);

        mAdminViewDB = FirebaseDatabase.getInstance().getReference().child("Cart List");

        mRecyclerView = findViewById(R.id.recycler_cart);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mPaymentMethodBtn = (Button) findViewById(R.id.detail_place_order);




        mPaymentMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                proceedPayment();

            }


        });

    }

    private void proceedPayment() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Choose payment method");
        builder.setSingleChoiceItems(R.array.payment_array, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(CartActivity.this, PayPalActivity.class);
                        startActivity(intent);


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();


    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(mAdminViewDB.child("User View")
                        .child(Prevalent.mCurrentOnlineUser.getPhone())
                        .child("Products"), Cart.class)
                .build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                holder.mCartName.setText(model.getName());
                holder.mCartDate.setText(model.getDate());
                holder.mCartQuantity.setText(String.valueOf(model.getQuantity()));
                holder.mCartTotalPrice.setText(String.valueOf(model.getTotalprice()));



            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;

            }

        };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }




}
