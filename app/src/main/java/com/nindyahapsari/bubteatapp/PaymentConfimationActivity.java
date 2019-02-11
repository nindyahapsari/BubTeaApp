package com.nindyahapsari.bubteatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PaymentConfimationActivity extends AppCompatActivity {

    TextView mTotalPrice, mPaymentDetails, mPaymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confimation);


        mTotalPrice = (TextView) findViewById(R.id.confirm_totalprice_value);
        mPaymentDetails = (TextView) findViewById(R.id.confirm_payment_details_value);
        mPaymentStatus = (TextView) findViewById(R.id.confirm_payment_status_value);


        Intent intent = getIntent();


        try {

            JSONObject jsonObject = new JSONObject(intent.getStringExtra("Total Price"));
            showDetail(jsonObject.getJSONObject("response"), intent.getStringExtra("Payment Details"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showDetail(JSONObject response, String paymentAmount) {

        try {

//            mTotalPrice.setText(response.getString(String.format("$", paymentAmount)));
            mPaymentDetails.setText(response.getString("Payment Details"));
            mPaymentStatus.setText(response.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



