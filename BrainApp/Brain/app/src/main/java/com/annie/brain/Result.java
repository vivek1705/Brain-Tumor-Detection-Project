package com.annie.brain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView txtResult;
        txtResult =findViewById(R.id.txtResult);
        Intent output = getIntent();
        String yes= output.getStringExtra("name");

        if(yes.equals("0")){
            txtResult.setText("Brain Tumor is Detected");
        }

    }
}