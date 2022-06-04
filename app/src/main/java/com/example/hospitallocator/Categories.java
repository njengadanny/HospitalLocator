package com.example.hospitallocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Categories extends AppCompatActivity {

    // Define the object for Radio Group,
    // Submit and Clear buttons
    private RadioGroup radioGroup;
    Button proceed, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        // Bind the components to their respective objects
        // by assigning their IDs
        // with the help of findViewById() method
        proceed = findViewById(R.id.proceed_btn);
        radioGroup = findViewById(R.id.radioGroup);

        // Uncheck or reset the radio buttons initially
        radioGroup.clearCheck();

        // Add the Listener to the RadioGroup
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton
                                radioButton
                                = (RadioButton)group
                                .findViewById(checkedId);
                    }
                });

        // Add the Listener to the Submit Button
        proceed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                // When submit button is clicked,
                // Ge the Radio Button which is set
                // If no Radio Button is set, -1 will be returned
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(Categories.this,"No answer has been selected",Toast.LENGTH_SHORT).show();
                }
                else {

                    RadioButton radioButton = radioGroup.findViewById(selectedId);

                    // Now display the value of selected item
                    // by the Toast message
                    Toast.makeText(Categories.this,radioButton.getText(),Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent(getApplicationContext(), NavMapsActivity.class);
                startActivity(intent);
            }
        });

        // Add the Listener to the Submit Button
//        clear.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v)
//            {
//
//                // Clear RadioGroup
//                // i.e. reset all the Radio Buttons
//                radioGroup.clearCheck();
//            }
//        });
    }
}
