package com.iitism.ismart.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.iitism.ismart.API.User;
import com.iitism.ismart.API.UserItem;
import com.iitism.ismart.API.UserRetrofitClient;
import com.iitism.ismart.MainActivity;
import com.iitism.ismart.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    String email,password;
    TextInputEditText edt_email,edt_password;
    Button btn_login;
    TextView signUp,txt_forgot;
    LinearLayout progressLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email=findViewById(R.id.edt_txt_login_email);
        edt_password=findViewById(R.id.edt_txt_login_password);
        signUp=findViewById(R.id.txt_sign_up);
        txt_forgot=findViewById(R.id.txt_forgot_pass);
        progressLayout=findViewById(R.id.progress_layout);
        btn_login=findViewById(R.id.btn_login);


        btn_login.setOnClickListener(this);
        txt_forgot.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btn_login)
        {
            email=edt_email.getText().toString().trim();
            password=edt_password.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                edt_email.setError("Enter email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                edt_password.setError("Enter password");
                return;
            }

            Call<UserItem> call= UserRetrofitClient.getInstance().getApi().getUser(email);
            call.enqueue(new Callback<UserItem>() {
                @Override
                public void onResponse(Call<UserItem> call, Response<UserItem> response) {
                    UserItem userItem=response.body();
                    User user=userItem.getItem();
                    Log.i("retrofit success","name: "+userItem.getItem().getName());
                    SharedPreferences sharedPreferences1 = getSharedPreferences("User", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("email",user.getEmail() );
                    editor.putString("Name",user.getName());
                    editor.putString("city",user.getCity());
                    editor.putString("state",user.getState());
                    editor.putString("phone",user.getPhone());
                    editor.putString("mine",user.getMine());
                    editor.putString("mineId",user.getId());
                    editor.apply();
                }

                @Override
                public void onFailure(Call<UserItem> call, Throwable t) {

                }
            });



            Amplify.Auth.signIn(
                    email,
                    password,
                    this::response,
                    this::fail);

        }
        else if(view==txt_forgot)
        {
            Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
            startActivity(i);
        }
        else if(view==signUp)
        {
            Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
            startActivity(i);
        }

    }
    void response(AuthSignInResult result)
    {
        if(result.isSignInComplete())
        {
            SharedPreferences sharedPreferences1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            editor1.putString("Login", "true");
            editor1.apply();


            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            ContextCompat.getMainExecutor(getApplicationContext()).execute(()  ->
            {
                Toast.makeText(getApplicationContext(), "response: "+result.toString(), Toast.LENGTH_SHORT).show();
            });
        }
    }
    void fail(AuthException error)
    {
        ContextCompat.getMainExecutor(getApplicationContext()).execute(()  ->
        {
            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }



}