package com.example.hospitallocator;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ImportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprofile);
    }
    //TODO: implement on backpressed()
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), NavMapsActivity.class);
        startActivity(intent);
        finish();
    }
}

