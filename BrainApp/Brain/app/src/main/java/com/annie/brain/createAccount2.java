package com.annie.brain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class createAccount2 extends AppCompatActivity {

    ImageButton btnNext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account2);

        btnNext2 = findViewById(R.id.btnNext2);

        btnNext2.setOnClickListener(view -> {

            Intent intent = new Intent(createAccount2.this, Detect.class);
            startActivity(intent);
            finish();
        });
    }
}