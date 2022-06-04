package com.example.hospitallocator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Register extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView logo = findViewById(R.id.enquiry_bk);
        logo.setImageResource(R.drawable.enquiry_bk);

        firstName = findViewById(R.id.txt_fname);
        lastName = findViewById(R.id.txt_lname);
        email = findViewById(R.id.txt_email);
        register_btn = findViewById(R.id.btn_register);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(firstName))
                {
                    Toast.makeText(getApplicationContext(), "You must enter first name to register!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isEmpty(lastName))
                {
                    Toast.makeText(getApplicationContext(), "You must enter last name to register!", Toast.LENGTH_SHORT).show();
//                    lastName.setError("Last name is required!");
                    return;
                }

                if (!isEmail(email))
                {
                    Toast.makeText(getApplicationContext(), "You must enter email to register!", Toast.LENGTH_SHORT).show();
//                    email.setError("Enter valid email!");
                    return;
                }

                Intent intent=new Intent(getApplicationContext(), Categories.class);
                startActivity(intent);
            }
        });
    }


    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

//    void checkDataEntered() {
//        if (isEmpty(firstName)) {
//            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
//            t.show();
//            return;
//        }
//
//        if (isEmpty(lastName)) {
//            lastName.setError("Last name is required!");
//            return;
//        }
//
//        if (isEmail(email) == false) {
//            email.setError("Enter valid email!");
//            return;
//        }
//
//    }

}
