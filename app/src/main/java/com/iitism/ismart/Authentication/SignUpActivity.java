package com.iitism.ismart.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.iitism.ismart.API.Item;
import com.iitism.ismart.API.MineItem;
import com.iitism.ismart.API.RetrofitClient;
import com.iitism.ismart.API.User;
import com.iitism.ismart.API.UserRetrofitClient;
import com.iitism.ismart.MainActivity;
import com.iitism.ismart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
 String name,email,password,phone;
 String selectedState,selectedCity,selectedMine;
 TextInputEditText edt_email,edt_password,edt_name,edt_phone;
 AutoCompleteTextView txt_state,txt_city,txt_mine;
 Button signUp;
 private ArrayAdapter<String> stateArrayAdapter;
 private ArrayAdapter<String> cityArrayAdapter;
 private ArrayAdapter<String> mineArrayAdapter;

    private ArrayList<String> statesList;
    private ArrayList<String> citiesList;
    ArrayList<StateAndCity> ListAll;
    List<MineItem> mineItemList;
    ArrayList<String> selectedMineList;


 private static final String TAG = "SpinnerLoadFromNetwork";
 long a=6000000000L,b=9999999999L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        obj_list();
        setState();
        callApi();



        signUp.setOnClickListener(this);
        txt_state.setOnItemClickListener((parent, arg1, pos, id) -> {
            selectedState= stateArrayAdapter.getItem(pos);
            Log.i("retrofit success", selectedState);
            setCity(selectedState);
        });

        txt_city.setOnItemClickListener((adapterView, view, pos, l) -> {
             selectedCity= cityArrayAdapter.getItem(pos);
             setMine(selectedState,selectedCity);
            Log.i("retrofit success",  selectedCity);
        });

        txt_mine.setOnItemClickListener((adapterView, view, pos, l) -> {
            selectedMine=mineArrayAdapter.getItem(pos);

        });

    }

    public  void init()
    {
        edt_name=findViewById(R.id.edt_txt_sign_up_name);
        edt_phone=findViewById(R.id.edt_txt_sign_up_phone);
        edt_email=findViewById(R.id.edt_txt_sign_up_email);
        edt_password=findViewById(R.id.edt_txt_sign_up_password);

        txt_state=findViewById(R.id.txt_view_state);
        txt_city=findViewById(R.id.txt_view_city);
        txt_mine=findViewById(R.id.txt_view_mine);
        signUp=findViewById(R.id.btn_sign_up);

        statesList = new ArrayList<>();
        citiesList=new ArrayList<>();
        ListAll=new ArrayList<>();
        mineItemList=new ArrayList<>();
        selectedMineList=new ArrayList<>();

    }

    @Override
    public void onClick(View view) {
        if(view==signUp)
        {
            name = Objects.requireNonNull(edt_name.getText()).toString().trim();
            phone = Objects.requireNonNull(edt_phone.getText()).toString().trim();
            email = Objects.requireNonNull(edt_email.getText()).toString().trim();
            password = Objects.requireNonNull(edt_password.getText()).toString().trim();


            if (TextUtils.isEmpty(name)) {
                edt_name.setError("Enter Name");
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                edt_phone.setError("Enter phone no.");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                edt_email.setError("Enter email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                edt_password.setError("Enter password");
                return;
            }
            long p = Long.parseLong(phone);

            if (p < a || p > b) {
                edt_phone.setError("Enter Correct Phone No.");
                return;
            }
            if(TextUtils.isEmpty(selectedState))
            {
                txt_state.setError("Please select state");
            }
            if(TextUtils.isEmpty(selectedCity))
            {
                txt_city.setError("Please select city");
            }
            if(TextUtils.isEmpty(selectedMine))
            {
                txt_mine.setError("Please select mine");
            }


            //call signup function





            AuthSignUpOptions options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), email)
                    .build();

            Amplify.Auth.signUp(email,password,options,
                    result -> success(result.toString()),
                    this::fail);

        }
    }

    void success(String message)
    {
        Log.i("AuthQuickStart", "Result: " +message);
        SignUp(name,email,phone,selectedState,selectedCity,selectedMine);
        ContextCompat.getMainExecutor(getApplicationContext()).execute(()  ->
        {
            Toast.makeText(getApplicationContext(),"Verification mail sent.Please verify!!", Toast.LENGTH_LONG).show();
        });
        Intent i=new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }
    void fail(AuthException message)
    {
        ContextCompat.getMainExecutor(getApplicationContext()).execute(()  ->
        {
            Toast.makeText(getApplicationContext(),"Sign up failed.Try again.", Toast.LENGTH_LONG).show();
        });
        Log.e("AuthQuickStart", "Sign up failed", message);
    }




    void obj_list()
    {
        try
        {
            // Convert the string returned to a JSON object
            JSONObject jsonObject=new JSONObject(getJson());
            // Get Json array
            JSONArray array=jsonObject.getJSONArray("array");
            // Navigate through an array item one by one
            for(int i=0;i<array.length();i++)
            {
                JSONObject object=array.getJSONObject(i);
                String state=object.getString("state");
                String city=object.getString("name");
                StateAndCity stateAndCity = new StateAndCity(state,city);
                ListAll.add(stateAndCity);

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String getJson()
    {
        String json=null;
        try
        {
            InputStream is = getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    void setState()
    {
        Set<String> set = new LinkedHashSet<>();
        for(int i=0;i<ListAll.size();i++)
          set.add(ListAll.get(i).getStateName());

        statesList.clear();
        statesList.addAll(set);
        Collections.sort(statesList);
        stateArrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.simple_spinner_dropdown_item, statesList);
        stateArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        txt_state.setAdapter(stateArrayAdapter);
    }

    void setCity(String selectedState)
    {
        citiesList.clear();

        for(int i=0;i<ListAll.size();i++)
        {
            String city=ListAll.get(i).getCityName();
            String state=ListAll.get(i).getStateName();
            if(state.equals(selectedState))
            {
                citiesList.add(city);
            }
        }
        Collections.sort(citiesList);
        cityArrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.simple_spinner_dropdown_item,citiesList);
        cityArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        txt_city.setAdapter(cityArrayAdapter);

    }

    void setMine(String state,String city)
    {
       selectedMineList.clear();

       for(int i=0;i<mineItemList.size();i++)
       {
           String apiState,apiCity;
           apiState=mineItemList.get(i).getState();
           apiCity=mineItemList.get(i).getCity();
           if(apiState.toLowerCase().equals(state.toLowerCase()) && apiCity.toLowerCase().equals(city.toLowerCase()))
           {
               selectedMineList.add(mineItemList.get(i).getName());
           }
       }
        Collections.sort(selectedMineList);
        mineArrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.simple_spinner_dropdown_item,selectedMineList);
        mineArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        txt_mine.setAdapter(mineArrayAdapter);

    }

    void callApi()
    {


        Call<Item> call = RetrofitClient.getInstance().getApi().getMine();
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                 Item item=response.body();
                  mineItemList=item.getItems();

                 for(int i=0;i<mineItemList.size();i++)
                     Log.i("retrofit success",mineItemList.get(i).getName());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                //handle error or failure cases here
                Log.i("retrofit failure",t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void SignUp(String name,String email,String phone,String state,String city,String mine)
    {
        String mineId="";

        for(int i=0;i<mineItemList.size();i++)
        {
            if(mineItemList.get(i).getName().equals(mine))
            {
                mineId=mineItemList.get(i).getId();
            }
        }
        User newUser=new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setState(state);
        newUser.setCity(city);
        newUser.setMine(mine);
        newUser.setId(mineId);




        Call<User> call= UserRetrofitClient.getInstance().getApi().putUser(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("retrofit success",response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }

}