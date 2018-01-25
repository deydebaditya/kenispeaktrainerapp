package com.debadityadey.kenispeaktrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Debaditya on 1/25/2018.
 */

public class SignUpActivity extends AppCompatActivity {

    Button submit;
    EditText name,email,password,confirm_password,pri_mob_num,alt_mob_num,landline,add_line1,add_line2,
                add_line3,city,state,pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeWidgets();

    }

    private void initializeWidgets(){
        submit = (Button) findViewById(R.id.submit);

        name = (EditText)findViewById(R.id.signup_name);
        email = (EditText)findViewById(R.id.signup_email);
        password = (EditText)findViewById(R.id.signup_pass);
        confirm_password = (EditText)findViewById(R.id.signup_confirm_pass);
        pri_mob_num = (EditText)findViewById(R.id.signup_pri_mob_num);
        alt_mob_num = (EditText)findViewById(R.id.signup_alt_mob_num);
        landline = (EditText)findViewById(R.id.signup_landline);
        add_line1 = (EditText)findViewById(R.id.signup_add_line1);
        add_line2 = (EditText)findViewById(R.id.signup_add_line2);
        add_line3 = (EditText)findViewById(R.id.signup_add_line3);
        city = (EditText)findViewById(R.id.signup_city);
        state = (EditText)findViewById(R.id.signup_state);
        pincode = (EditText)findViewById(R.id.signup_pincode);
    }
}
