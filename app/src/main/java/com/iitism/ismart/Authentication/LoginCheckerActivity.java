package com.iitism.ismart.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.iitism.ismart.MainActivity;

public class LoginCheckerActivity extends AppCompatActivity {
    String loginValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences=getSharedPreferences("Login", Context.MODE_PRIVATE);
        loginValue=sharedPreferences.getString("Login","");

        if(loginValue.equals("true"))
        {
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();

        }
        else
        {
            Intent i=new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();

        }
    }
}