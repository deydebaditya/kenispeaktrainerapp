package com.debadityadey.kenispeaktrainer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Debaditya on 1/25/2018.
 */
//TODO : ProgressDialog not showing, check it!

    //TODO : Password ko Blowfish lagana hai, chutiyaap ho jayega nahi to
public class SignUpActivity extends AppCompatActivity {

    Button submit;
    EditText name,email,password,confirm_password,pri_mob_num,alt_mob_num,landline,add_line1,add_line2,
                add_line3,city,state,pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeWidgets();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(confirm_password.getText().toString())){
                    //TODO : Non required fields ko empty string set karna hai, agar bhara nahi hai to
                    //TODO : Validation checks lagane hai.
                    Log.e("conn","Going online!");
                    new SendRequestForSignUp(name.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString(),
                            pri_mob_num.getText().toString(),
                            alt_mob_num.getText().toString(),
                            landline.getText().toString(),
                            add_line1.getText().toString(),
                            add_line2.getText().toString(),
                            add_line3.getText().toString(),
                            city.getText().toString(),
                            state.getText().toString(),
                            pincode.getText().toString()).execute();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private class SendRequestForSignUp extends AsyncTask{

        ProgressDialog progressDialog, dismissProgressTemp;

        String URL = "http://ec2-18-216-46-195.us-east-2.compute.amazonaws.com/keni/query_signup.php";

        private String name, email, password, pri_mob_num, alt_mob_num, landline, add_line1, add_line2,
                        add_line3, city, state, pincode;

        SendRequestForSignUp(String name, String email, String password, String pri_mob_num,
                             String alt_mob_num, String landline, String add_line1, String add_line2,
                             String add_line3, String city, String state, String pincode){
            this.name = name;
            this.email = email;
            this.password = password;
            this.pri_mob_num = pri_mob_num;
            this.alt_mob_num = alt_mob_num;
            this.landline = landline;
            this.add_line1 = add_line1;
            this.add_line2 = add_line2;
            this.add_line3 = add_line3;
            this.city = city;
            this.state = state;
            this.pincode = pincode;
            progressDialog = new ProgressDialog(SignUpActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Signing Up!");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Signing you up! Please wait...");
            dismissProgressTemp = progressDialog.show(SignUpActivity.this,"Signing Up!","Signing you up! Please wait...",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.e("INFO","onPostExecute called");
            dismissProgressTemp.dismiss();
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(final Object[] objects) {
            RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response){
                            try{
                                Log.e("Response",response);
                                JSONObject responseObject = new JSONObject(response);
                                String isConnectOkay = (String)responseObject.get("conn");
                                if(isConnectOkay.equals("CONN_SUCCESS")){
                                    if(((String)responseObject.get("duplicateEntry")).equals("1")){
                                        Toast.makeText(SignUpActivity.this,"Entry already exists!",Toast.LENGTH_SHORT).show();
                                        //progressDialog.dismiss();
                                    }
                                    else{
                                        if(((String)responseObject.get("queryInsert")).equals("IN_SUCC")){
                                            Toast.makeText(SignUpActivity.this,"Sign Up Successful!",Toast.LENGTH_SHORT).show();
                                            finish();
                                            //progressDialog.dismiss();
                                        }
                                        else if(((String)responseObject.get("queryInsert")).equals("IN_FAIL")){
                                            Toast.makeText(SignUpActivity.this,"Sign Up failed!",Toast.LENGTH_SHORT).show();
                                            //progressDialog.dismiss();
                                        }
                                        else{
                                            Toast.makeText(SignUpActivity.this,"Unexpected Error!",Toast.LENGTH_SHORT).show();
                                            //progressDialog.dismiss();
                                        }
                                    }
                                }
                                else if(isConnectOkay.equals("CONN_FAIL")){
                                    Toast.makeText(SignUpActivity.this, "Connection to server failed!", Toast.LENGTH_SHORT).show();
                                    //progressDialog.dismiss();
                                }
                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error",error.toString());
                    Toast.makeText(SignUpActivity.this, "Connection error!", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("email",email);
                    params.put("password",password);
                    params.put("pri_mob_num",pri_mob_num);
                    params.put("alt_mob_num",alt_mob_num);
                    params.put("landline",landline);
                    params.put("add_line1",add_line1);
                    params.put("add_line2",add_line2);
                    params.put("add_line3",add_line3);
                    params.put("city",city);
                    params.put("state",state);
                    params.put("pincode",pincode);
                    Log.e("Params",params.toString());
                    return params;
                }
            };
            Log.e("Request",stringRequest.toString());
            queue.add(stringRequest);
            return null;
        }
    }
}
