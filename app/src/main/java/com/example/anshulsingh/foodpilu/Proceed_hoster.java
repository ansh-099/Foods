package com.example.anshulsingh.foodpilu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Proceed_hoster extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_hoster);

        if(getIntent().getExtras() == null){
            Log.d("hey","s");
        }
        String i = getIntent().getStringExtra("yoooyooyoo");

        Toast.makeText(this, i, Toast.LENGTH_SHORT).show();

    }



}
