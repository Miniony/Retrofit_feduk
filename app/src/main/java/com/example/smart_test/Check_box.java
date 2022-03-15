package com.example.smart_test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Check_box extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);



                // Register checkbox OnCheckedChangeListener object.
                CheckBox checkBoxApple = (CheckBox)findViewById(R.id.checkBoxApple);
                checkBoxApple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        String showMsg = "";
                        if(checked)
                        {
                            // When checkbox is checked.
                            showMsg = "You check " + compoundButton.getText();
                        }else
                        {
                            // When checkbox is unchecked.
                            showMsg = "You uncheck " + compoundButton.getText();
                        }

                        // Create an AlertDialog object.
                        AlertDialog alertDialog = new AlertDialog.Builder(Check_box.this).create();

                        // Set prompt message.
                        alertDialog.setMessage(showMsg);

                        // Show the alert dialog.
                        alertDialog.show();

                    }
                });

                Button showSelectionBtn = (Button)findViewById(R.id.checkBoxBtn);
                showSelectionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get three CheckBox object by id.
                        CheckBox checkBoxApple = (CheckBox) findViewById(R.id.checkBoxApple);
                        CheckBox checkBoxBanana = (CheckBox) findViewById(R.id.checkBoxBanana);
                        CheckBox checkBoxWatermelon = (CheckBox) findViewById(R.id.checkBoxWatermelon);

                        StringBuffer msgBuffer = new StringBuffer();

                        if(checkBoxApple.isChecked())
                        {
                            msgBuffer.append("Apple ");
                        }

                        if(checkBoxBanana.isChecked())
                        {
                            msgBuffer.append("Banana ");
                        }

                        if(checkBoxWatermelon.isChecked())
                        {
                            msgBuffer.append("Watermelon.");
                        }

                        if(msgBuffer.length()==0)
                        {
                            msgBuffer.append("Please select at least one favorite fruit.");
                        }else
                        {
                            msgBuffer.insert(0, " Your favorite fruits are ");
                        }
                        // Create an AlertDialog object.
                        AlertDialog alertDialog = new AlertDialog.Builder(Check_box.this).create();

                        // Set prompt message.
                        alertDialog.setMessage(msgBuffer);

                        // Show the alert dialog.
                        alertDialog.show();
                    }
                });

            }
        }

