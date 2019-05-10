package com.example.sportgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Login_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login Form");
    }

    public void btn_signupForm(View view) {
        Intent i = new Intent(this,Signup.class);
        startActivity(i);
    }

    public void btn_sportRecorder(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
