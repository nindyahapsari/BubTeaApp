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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nindyahapsari.bubteatapp.Models.Users;
import com.nindyahapsari.bubteatapp.Prevalent.Prevalent;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity {

    private EditText mInputPhone, mInputPassword;
    private Button mUserLoginBtn;
    private ProgressDialog loadingBar;
    private CheckBox mCheckBoxRememberMe;

    private String mParentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserLoginBtn = (Button) findViewById(R.id.login_button_user);
        mInputPhone = (EditText) findViewById(R.id.login_phone_input);
        mInputPassword = (EditText) findViewById(R.id.login_password);
        loadingBar = new ProgressDialog(this);
        mCheckBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_checkbox);

        // initialize Paper
        Paper.init(this);


        mUserLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }


    private void loginUser() {

        String phone = mInputPhone.getText().toString();
        String password = mInputPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone, password);
        }


    }

    private void AllowAccessToAccount(final String phone, final String password)
    {
        if(mCheckBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.mUserPhoneKey, phone);
            Paper.book().write(Prevalent.mUserPasswordKey, password);
        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(mParentDbName).child(phone).exists())
                {
                    Users usersData = dataSnapshot.child(mParentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
//                            if (mParentDbName.equals("Admins"))
//                            {
//                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
//                                loadingBar.dismiss();
//
////                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
////                                startActivity(intent);
//                            }
                                if (mParentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent homeIntent = new Intent(LoginActivity.this, MenuActivity.class);
                                Prevalent.mCurrentOnlineUser= usersData;
                                startActivity(homeIntent);
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
