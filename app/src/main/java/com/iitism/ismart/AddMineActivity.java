package com.iitism.ismart;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Mine;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Iterator;

public class AddMineActivity extends AppCompatActivity implements View.OnClickListener{
TextInputEditText txt_name,txt_state,txt_city,txt_gateway;
Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mine);

        txt_name=findViewById(R.id.edt_txt_mine_name);
        txt_state=findViewById(R.id.edt_txt_mine_state);
        txt_city=findViewById(R.id.edt_txt_mine_city);
        txt_gateway=findViewById(R.id.edt_txt_mine_gateway);
        btn_submit=findViewById(R.id.btn_submit_mine_detail);

        btn_submit.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if(view==btn_submit)
        {
            String txtName,txtState,txtCity;
            txtName=txt_name.getText().toString().trim();
            txtState=txt_state.getText().toString().trim();
            txtCity=txt_city.getText().toString().trim();
//            Mine mine=Mine.builder()
//                    .name(txtName)
//                    .state(txtState)
//                    .city(txtCity)
//                    .build();
//            Amplify.API.mutate("ismart-staging",ModelMutation.create(mine),
//                    response -> Log.i("API", "with id: " + response.getData().getId()),
//                    error -> Log.e("API", "Create failed", error)
//            );


            Amplify.API.query("ismart-staging", ModelQuery.list(Mine.class),
                    response -> {
                        Log.i("MyAmplifyApp", response.getData().toString());
                   Iterator<Mine> it=response.getData().iterator();

//                        for (Mine mine : response.getData().getItems().) {
//                            Log.i("MyAmplifyApp", mine.toString());
//                        }
                    },
                    error -> Log.e("MyAmplifyApp", "Query failure", error)
            );


        }
    }
}