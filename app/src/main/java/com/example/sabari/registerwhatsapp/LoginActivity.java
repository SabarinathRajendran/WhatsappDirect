package com.example.sabari.registerwhatsapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private whatsapp whatsap;

    public int i;

    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final DbHandler db = new DbHandler(this);
        final ArrayList<HashMap<String, String>> userList = db.GetUsers();
        final ListView lv = (ListView) findViewById(R.id.user_list);
        final ListAdapter adapter = new SimpleAdapter(LoginActivity.this, userList, R.layout.list_row,new String[]{"name","number","address"}, new int[]{R.id.name, R.id.number, R.id.address});
        lv.setAdapter(adapter);

        whatsap = new whatsapp();

        Button back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                TextView v = view.findViewById(R.id.address);

                String i =v.getText().toString().trim();

                v = view.findViewById(R.id.name);

                String j =v.getText().toString().trim();

                Toast.makeText(LoginActivity.this, i, Toast.LENGTH_SHORT).show();

                openWhatsApp(j,i);
            }
        });

        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View v, int position, long id) {
                //Do your tasks here

                TextView u = v.findViewById(R.id.address);

                username =u.getText().toString().trim();

                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setTitle("Nice!!");
                alert.setMessage("What do you wanna do");
                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        //do your work here

                        Cursor data = db.getUserID(username);
                        int itemID = -1;
                        while (data.moveToNext()){
                            itemID = data.getInt(0);
                        }
                        if (itemID>-1){
                            Intent edit = new Intent(LoginActivity.this, com.example.sabari.registerwhatsapp.edit.class);
                            edit.putExtra("id",itemID);
                            edit.putExtra("number",username);
                            startActivity(edit);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Error!, No data found", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(LoginActivity.this, "mmm "+position, Toast.LENGTH_SHORT).show();
                        Cursor data = db.getUserID(username);
                        int itemID = -1;
                        while (data.moveToNext()){
                            itemID = data.getInt(0);
                        }
                        if (itemID>-1){
                            db.DeleteUser(itemID);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Error!, No data found", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });

                alert.show();

                return true;
            }
        });
    }
    public void openWhatsApp(String name,String number){
        try {
            /*Database db = new Database(LoginActivity.this);
            ArrayList<HashMap<String, String>> details= db.GetUserByUserId(1);

            HashMap<String,String> n = details.get(0);
            String greeting = n.get("greeting");
            String message = n.get("message");

            if (greeting.length()==0||message.length()==0) {*/
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=91" + number + "&text=Hello%20" + name + "%0A%0AWelcome%20To%20Ciet%0A%0Ahttps://www.youtube.com/watch?v=0EKl7DdxHNA%0A%0Ahttp://www.cietcbe.edu.in"));
                startActivity(intent);
            /*}
            else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=91" + number + greeting + name + "%0A%0A"+message));
                startActivity(intent);
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}