package com.example.sabari.registerwhatsapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class message extends AppCompatActivity {

    private EditText mgreeting, mmessage;
    private TextView mname;
    private Button msubmit, mcancel;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mgreeting = findViewById(R.id.hgreeting);
        mmessage = findViewById(R.id.hmessage);
        mname = findViewById(R.id.hname);

        msubmit = findViewById(R.id.hsubmit);
        mcancel = findViewById(R.id.hcancel);

        fillthefields();

        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(message.this, "Working on it!!!!", Toast.LENGTH_SHORT).show();
                addtodb();
            }
        });

    }

    private void fillthefields() {
        Database db = new Database(message.this);
        ArrayList<HashMap<String, String>> details= db.GetUserByUserId(1);

        HashMap<String,String> n = details.get(0);
        mgreeting.setText(n.get("greeting"));
        mmessage.setText(n.get("message"));
    }

    private void addtodb() {
        String message = mmessage.getText().toString().trim();
        String greeting = mgreeting.getText().toString().trim();
        Database db = new Database(message.this);
        if (message.length()==0||greeting.length()==0){
            Toast.makeText(message.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            db.insertUserDetails(greeting, message, 1);
            Toast.makeText(message.this, "Added to Database", Toast.LENGTH_SHORT).show();

        }
    }
}
