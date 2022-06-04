package com.example.hospitallocator;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), NavMapsActivity.class);
        startActivity(intent);
        finish();
    }
}
