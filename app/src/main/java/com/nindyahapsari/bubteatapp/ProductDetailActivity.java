package com.nindyahapsari.bubteatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nindyahapsari.bubteatapp.Models.Products;
import com.nindyahapsari.bubteatapp.Prevalent.Prevalent;
import com.nindyahapsari.bubteatapp.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.ContentResolver.EXTRA_SIZE;
import static com.nindyahapsari.bubteatapp.MenuActivity.EXTRA_TEA_NAME;

public class ProductDetailActivity extends AppCompatActivity {


    private int mQuantity = 0;
    private int mTotalPrice = 0;

    private static final int SMALL_PRICE = 5;
    private static final int MEDIUM_PRICE = 6;
    private static final int LARGE_PRICE = 7;

    private static final String TEA_SIZE_SMALL = "Small ($5/cup)";
    private static final String TEA_SIZE_MEDIUM = "Medium ($6/cup)";
    private static final String TEA_SIZE_LARGE = "Large ($7/cup)";

    private String mMilkType;
    private String mSugarType;
    private String mTeaName;

    private String mSize;

    private String mProductName = "";
    private String mProductImage = "";
    private String mProductId = "";

    // UI view
    private TextView mProdDetailName;
    private ImageView mProdDetailImage;
    private Button mPlaceOrderBtn;

    public final static String EXTRA_TOTAL_PRICE = "0";
    public final static String EXTRA_TEA_NAME = "com.nindyahapsari.bubteatapp.EXTRA_TEA_NAME";
    public final static String EXTRA_SIZE = "com.nindyahapsari.bubteatapp.EXTRA_SIZE";
    public final static String EXTRA_MILK_TYPE = "com.nindyahapsari.bubteatapp.EXTRA_MILK_TYPE";
    public final static String EXTRA_SUGAR_TYPE = "com.nindyahapsari.bubteatapp.EXTRA_SUGAR_TYPE";
    public final static String EXTRA_QUANTITY = "com.nindyahapsari.bubteatapp.EXTRA_QUANTITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mProductId = getIntent().getStringExtra("id");
        mProductName = getIntent().getStringExtra("name");
        mProductImage = getIntent().getStringExtra("tea");

        mProdDetailName = (TextView) findViewById(R.id.detail_text_bubtea);
        mProdDetailImage = (ImageView) findViewById(R.id.detail_image);
        mPlaceOrderBtn = (Button) findViewById(R.id.detail_place_order);

        // Set header name and image depending on which item was clicked in the Menu
        Intent intent = getIntent();
        mTeaName = intent.getStringExtra(EXTRA_TEA_NAME);

        TextView teaNameTextView = (TextView) findViewById(R.id.detail_text_bubtea);
        teaNameTextView.setText(mTeaName);

        // Set cost default to $0.00
        TextView costTextView = (TextView) findViewById(
                R.id.cost_text_view);
        costTextView.setText(getString(R.string.initial_cost));

        setupSizeSpinner();
        setupMilkSpinner();
        setupSugarSpinner();

        getProductDetails(mProductName, mProductImage);

        mPlaceOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plcOrder();

            }
        });

    }

    private void getProductDetails(final String mProductName, final String mProductImage) {

        DatabaseReference dBRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference productRef = dBRef.child("ImageTea");


        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

//                        Products products = dataSnapshot.getValue(Products.class);

                    mProdDetailName.setText(mProductName);
                    Picasso.get().load(mProductImage).into(mProdDetailImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    /**
     * Sets up the dropdown spinner for user to select tea mSize
     */
    private void setupSizeSpinner() {

        Spinner mSizeSpinner = (Spinner) findViewById(R.id.size_spinner);

        // Create an ArrayAdapter using the string array and a default mSizeSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tea_size_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the mSizeSpinner
        mSizeSpinner.setAdapter(adapter);

        // Set the integer mSelected to the constant values
        mSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSize = getString(R.string.choose_size);
                        break;
                    case 1:
                        mSize = getString(R.string.tea_size_small);
                        break;
                    case 2:
                        mSize = getString(R.string.tea_size_medium);
                        break;
                    case 3:
                        mSize = getString(R.string.tea_size_large);
                        break;

                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSize = getString(R.string.choose_size);
            }
        });

    }


    /**
     * Sets up the dropdown spinner for user to select milk type
     */
    private void setupMilkSpinner() {

        Spinner mSizeSpinner = (Spinner) findViewById(R.id.milk_spinner);

        // Create an ArrayAdapter using the string array and a default mSizeSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.milk_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the mSizeSpinner
        mSizeSpinner.setAdapter(adapter);

        // Set the integer mSelected to the constant values
        mSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mMilkType = getString(R.string.choose_milk);
                        break;
                    case 1:
                        mMilkType = getString(R.string.milk_type_none);
                        break;
                    case 2:
                        mMilkType = getString(R.string.milk_type_nonfat);
                        break;
                    case 3:
                        mMilkType = getString(R.string.milk_type_1_percent);
                        break;
                    case 4:
                        mMilkType = getString(R.string.milk_type_2_percent);
                        break;
                    case 5:
                        mMilkType = getString(R.string.milk_type_whole);
                        break;

                }
            }


            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMilkType = getString(R.string.milk_type_none);
            }
        });

    }


    /**
     * Setup the dropdown spinner for user to select amount of sugar
     */
    private void setupSugarSpinner() {

        Spinner mSizeSpinner = (Spinner) findViewById(R.id.sugar_spinner);

        // Create an ArrayAdapter using the string array and a default mSizeSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sugar_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the mSizeSpinner
        mSizeSpinner.setAdapter(adapter);

        // Set the integer mSelected to the constant values
        mSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSugarType = getString(R.string.choose_sugar);
                        break;
                    case 1:
                        mSugarType = getString(R.string.sweet_type_0);
                        break;
                    case 2:
                        mSugarType = getString(R.string.sweet_type_25);
                        break;
                    case 3:
                        mSugarType = getString(R.string.sweet_type_50);
                        break;
                    case 4:
                        mSugarType = getString(R.string.sweet_type_75);
                        break;
                    case 5:
                        mSugarType = getString(R.string.sweet_type_100);
                        break;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSugarType = getString(R.string.sweet_type_100);
            }
        });

    }

    /**
     * Increments the quantity and recalculates the price
     */
    public void increment(View view) {

        mQuantity = mQuantity + 1;
        displayQuantity(mQuantity);
        mTotalPrice = calculatePrice();
        displayCost(mTotalPrice);
    }

    /**
     * Decrements the quantity and recalculates the price
     */
    public void decrement(View view) {
        if (mQuantity > 0) {

            mQuantity = mQuantity - 1;
            displayQuantity(mQuantity);
            mTotalPrice = calculatePrice();
            displayCost(mTotalPrice);
        }
    }


    /**
     * Calculates the TotalPrice of the order.
     *
     * @return total mTotalPrice
     */
    private int calculatePrice() {

        // Calculate the total order mTotalPrice by multiplying by the mQuantity
        switch (mSize) {
            case TEA_SIZE_SMALL:
                mTotalPrice = mQuantity * SMALL_PRICE;
                break;
            case TEA_SIZE_MEDIUM:
                mTotalPrice = mQuantity * MEDIUM_PRICE;
                break;
            case TEA_SIZE_LARGE:
                mTotalPrice = mQuantity * LARGE_PRICE;
                break;
        }
        return mTotalPrice;
    }

    /**
     * Displays the given mQuantity value on the screen then
     * calculates and displays the cost
     */
    private void displayQuantity(int numberOfTeas) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(numberOfTeas));
    }

    private void displayCost(int totalPrice) {
        TextView costTextView = (TextView) findViewById(
                R.id.cost_text_view);

        String convertPrice = NumberFormat.getCurrencyInstance().format(totalPrice);
        costTextView.setText(convertPrice);
    }

    /**
     * This method is call when the user are placing order.
     * Also sending data to DB.
     */
    private void plcOrder() {

        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("id", mProductId.toString());
        cartMap.put("name", mProdDetailName.getText().toString());
        cartMap.put("Quantity", mQuantity);
        cartMap.put("totalprice", mTotalPrice);
        cartMap.put("currentdate", saveCurrentDate);
        cartMap.put("currenttime", saveCurrentTime);

        cartListRef.child("User View").child(Prevalent.mCurrentOnlineUser.getPhone())
                .child("Products").child(saveCurrentDate)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            cartListRef.child("Admin View").child(Prevalent.mCurrentOnlineUser.getPhone())
                                    .child("Products").child(saveCurrentDate)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                Toast.makeText(ProductDetailActivity.this, " Added To Cart ", Toast.LENGTH_SHORT).show();

                                                // Create a new intent to open the {@link OrderSummaryActivity}
                                                Intent intent = new Intent(ProductDetailActivity.this, MenuActivity.class);
                                                intent.putExtra(EXTRA_TOTAL_PRICE, mTotalPrice);
                                                intent.putExtra(EXTRA_TEA_NAME, mTeaName);
                                                intent.putExtra(EXTRA_SIZE, mSize);
                                                intent.putExtra(EXTRA_MILK_TYPE, mMilkType);
                                                intent.putExtra(EXTRA_SUGAR_TYPE, mSugarType);
                                                intent.putExtra(EXTRA_QUANTITY, mQuantity);

                                                startActivity(intent);

                                            }

                                        }
                                    });
                        }

                    }
                });


    }
}



