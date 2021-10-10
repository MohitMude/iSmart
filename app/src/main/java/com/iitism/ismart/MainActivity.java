package com.iitism.ismart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.android.material.textfield.TextInputEditText;
import com.iitism.ismart.API.GatewayItem;
import com.iitism.ismart.API.GatewayMineItem;
import com.iitism.ismart.API.Item;
import com.iitism.ismart.API.MineItem;
import com.iitism.ismart.API.RetrofitClient;
import com.iitism.ismart.Authentication.StateAndCity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView txt_gateway;
    String gateway,mineId;
    TextInputEditText txt_dob;
    DatePickerDialog datepicker;
    private ArrayAdapter<String> gatewayArrayAdapter;
    List<String> gatewayList;
    List<MineItem> mineItemList;
    private static final String TAG = "SpinnerLoadFromNetwork";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        callApi();
        txt_dob.setOnClickListener(this);
    }
    void initialize()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("User", Context.MODE_PRIVATE);
        mineId=sharedPreferences.getString("mineId","");
        txt_gateway=findViewById(R.id.txt_view_gateway);
        txt_dob=findViewById(R.id.edt_txt_date);
        gatewayList= new ArrayList<>();
        mineItemList=new ArrayList<>();
    }

    void callApi()
    {
        Log.i("retrofit success","Id: " + mineId);

        Call<GatewayItem> call = RetrofitClient.getInstance().getApi().getGateway(mineId);
        call.enqueue(new Callback<GatewayItem>() {
            @Override
            public void onResponse(Call<GatewayItem> call, Response<GatewayItem> response) {
                GatewayItem item=response.body();
                GatewayMineItem mineItem=item.getItem();
                gatewayList=mineItem.getGateways();
                Collections.sort(gatewayList);
                gatewayArrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.simple_spinner_dropdown_item,gatewayList);
                gatewayArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                txt_gateway.setAdapter(gatewayArrayAdapter);


            }

            @Override
            public void onFailure(Call<GatewayItem> call, Throwable t) {
                //handle error or failure cases here
                Log.i("retrofit failure",t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==txt_dob)
        {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            datepicker = new DatePickerDialog(MainActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Log.println(Log.ASSERT,"date",txt_dob.getText().toString());
                            txt_dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            Log.println(Log.ASSERT,"date",txt_dob.getText().toString());
                        }
                    }, year, month, day);
            datepicker.show();
        }
    }
}