package com.example.sabari.registerwhatsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class DetailsInput extends AppCompatActivity {

    private EditText mname, mnumber, maddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_input);
    }
}
