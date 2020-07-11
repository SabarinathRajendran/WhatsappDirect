package com.example.sabari.registerwhatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class edit extends AppCompatActivity {

    public int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();

        String username = intent.getStringExtra("number");

        ID = intent.getIntExtra("id",-1);

        final EditText rname = findViewById(R.id.editname);
        final EditText rnumber = findViewById(R.id.editnumber);
        final EditText raddress = findViewById(R.id.editaddress);
        final Button rsubmit = findViewById(R.id.editsubmit);
        final Button rcancel = findViewById(R.id.editcancel);
        final DbHandler db = new DbHandler(this);

        ArrayList<HashMap<String, String>> userList = db.GetUserByUserId(ID);

            HashMap<String, String> n = userList.get(0);

            String name = n.get("name");
            String number = n.get("number");
            String address = n.get("address");

            rname.setText(name);
            rnumber.setText(number);
            raddress.setText(address);

        rsubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ID==-1){
                                    Toast.makeText(edit.this, "we are having trouble!! sorry", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    db.UpdateUserDetails(rname.getText().toString().trim(), rnumber.getText().toString().trim(), raddress.getText().toString().trim(), ID);
                                    Toast.makeText(edit.this, "Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        rcancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(edit.this,LoginActivity.class);
                                startActivity(i);
                            }
                        });
    }
}
