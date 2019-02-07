package com.nindyahapsari.bubteatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button mCreateAccountButton ;
    private EditText mInputName ,mInputPhone ,mInputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //An Activity is not fully initialized and ready to look up views until after setContentView(...) is called in onCreate()
        mCreateAccountButton = (Button) findViewById(R.id.register_btn);
        mInputName = (EditText) findViewById(R.id.register_name);
        mInputPhone = (EditText) findViewById(R.id.register_email);
        mInputPassword = (EditText) findViewById(R.id.register_password);
        loadingBar = new ProgressDialog(this);


        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount();
            }
        });
    }


    private void createAccount() {

        String name = mInputName.getText().toString();
        String phone = mInputPhone.getText().toString();
        String password = mInputPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please type your Name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please type your Email...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please type your Password...", Toast.LENGTH_SHORT).show();
        } else
            {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validateEmail(name, phone, password);
        }

    }


    private void validateEmail( final String name, final String phone, final String password) {

        final DatabaseReference mRootRef;

        mRootRef = FirebaseDatabase.getInstance().getReference();


        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {

                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("name", name);
                    userDataMap.put("phone", phone);
                    userDataMap.put("password", password);

                    mRootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Your account has been created!", Toast.LENGTH_SHORT).show();

                                        loadingBar.dismiss();

                                        Intent loginActivityIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(loginActivityIntent);
                                    }
                                    else {
//                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: Please try again in a few moment...", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "This" + phone + "already exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please use another Email", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

