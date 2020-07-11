package com.example.sabari.registerwhatsapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ajts.androidmads.library.SQLiteToExcel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText mname, mnumber, maddress;
    private Button msend, maddtodb, viewdb, mexportdb;
    private SQLiteDatabase db;

    private whatsapp whatsap;

    //private android.support.v7.widget.Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* mtoolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(mtoolbar)*/;

        whatsap = new whatsapp();

        mname = findViewById(R.id.nametext);
        mnumber= findViewById(R.id.numbertext);
        maddress= findViewById(R.id.addresstext);
        msend = findViewById(R.id.whatsappsend);
        maddtodb = findViewById(R.id.addtodb);
        mexportdb = findViewById(R.id.exportdb);
        viewdb = findViewById(R.id.viewdb);

        mexportdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    // Ask for permision
                    ActivityCompat.requestPermissions(MainActivity.this,new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                else {
                    callaction();
                }

            }
        });

        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nmessage = mname.getText().toString().trim();
                String number = mnumber.getText().toString().trim();
                openWhatsApp(nmessage, number);
            }
        });

        maddtodb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mname.getText().toString().trim();
                String number = mnumber.getText().toString().trim();
                String address = maddress.getText().toString().trim();
                DbHandler dbHandler = new DbHandler(MainActivity.this);
                if (name.length()==0||number.length()==0||address.length()==0){
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbHandler.insertUserDetails(name, number, address);
                    Toast.makeText(MainActivity.this, "Added to Database", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void callaction() {

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // Export SQLite DB as EXCEL FILE
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), "usersdb", directory_path);
        sqliteToExcel.exportSingleTable("userdetails","users.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(MainActivity.this, "Finished, Exported successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, "Error! cannot export", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openWhatsApp(String name,String number){
        try {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=91" + number + "&text=Hello%20" + name + "%0A%0AWelcome%20To%20Ciet%0A%0Ahttps://www.youtube.com/watch?v=0EKl7DdxHNA%0A%0Ahttp://www.cietcbe.edu.in"));
                startActivity(intent);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

   /* @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;

    }



    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will

        // automatically handle clicks on the Home/Up button, so long

        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();



        //noinspection SimplifiableIfStatement

        if (id == R.id.Whatsappmessage) {

            Intent i =new Intent(MainActivity.this, message.class);
            startActivity(i);

            return true;

        }


        return super.onOptionsItemSelected(item);

    }
    */

}

