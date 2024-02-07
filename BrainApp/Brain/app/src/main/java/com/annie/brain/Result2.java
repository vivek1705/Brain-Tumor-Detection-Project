package com.annie.brain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        TextView txtResult2;
        txtResult2 =findViewById(R.id.txtResult2);

        txtResult2.setText("Brain Tumor Not Detected");
    }
}