package com.example.sabari.registerwhatsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sabari on 3/25/2019.
 */

public class whatsapp extends AppCompatActivity {

    public void openWhatsApp(String name,String number){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=91"+number +"&text=Hello%20"+name+"%0A%0AWelcome%20To%20Ciet%0A%0Ahttp://www.cietcbe.edu.in"));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
