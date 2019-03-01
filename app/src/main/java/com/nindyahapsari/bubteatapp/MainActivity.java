package com.nindyahapsari.bubteatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nindyahapsari.bubteatapp.Models.Users;
import com.nindyahapsari.bubteatapp.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button mSignUpButton, mLoginButton;
    private ImageView mIcon;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignUpButton = (Button) findViewById(R.id.signup_button);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mIcon = (ImageView) findViewById(R.id.bubtea_icon);

        loadingBar = new ProgressDialog(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transitions);

        // for the transition animations
        mSignUpButton.startAnimation(animation);
        mLoginButton.startAnimation(animation);
        mIcon.startAnimation(animation);


        // init Paper before using
        Paper.init(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signupIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(signupIntent);
            }
        });

        String userPhoneKey = Paper.book().read(Prevalent.mUserPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.mUserPasswordKey);

        if (userPhoneKey != "" && userPasswordKey != ""){

            if(!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey)){

                allowAccess(userPhoneKey, userPasswordKey);

                loadingBar.setTitle("Already Logged in...");
                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }

    }


    private void allowAccess(final String phone, final String password){

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {

                                Toast.makeText(MainActivity.this, "Please wait, you are already logged in..", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                Prevalent.mCurrentOnlineUser= usersData;
                                startActivity(intent);

                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
