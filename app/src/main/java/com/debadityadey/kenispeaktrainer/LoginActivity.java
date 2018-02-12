package com.debadityadey.kenispeaktrainer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by Debaditya on 2/3/2018.
 */

public class LoginActivity extends AppCompatActivity {

    Button submit;
    EditText pri_mob_num,password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initializeWidgets();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("conn","Going Online!");
                new SendRequestForLogin(password.getText().toString(),pri_mob_num.getText().toString()).execute();
            }
        });
    }

    private void initializeWidgets(){
        submit = (Button) findViewById(R.id.loginBut);

        password = (EditText)findViewById(R.id.login_pass);
        pri_mob_num = (EditText)findViewById(R.id.login_mob);
    }

    private class SendRequestForLogin extends AsyncTask {

        ProgressDialog progressDialog, dismissProgressTemp;

        String URL = "http://ec2-18-216-46-195.us-east-2.compute.amazonaws.com/keni/query_login.php";

        private String password, pri_mob_num;

        SendRequestForLogin(String password, String pri_mob_num){
            this.password = password;
            this.pri_mob_num = pri_mob_num;

            progressDialog = new ProgressDialog(LoginActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Logging In!");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Logging In! Please wait...");
            dismissProgressTemp = progressDialog.show(LoginActivity.this,"Logging In!","Logging In! Please wait...",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.e("INFO","onPostExecute called");
            dismissProgressTemp.dismiss();
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject responseObject = new JSONObject(response);
                                if(((String)responseObject.get("conn")).equals("CONN_SUCCESS")){
                                    Log.e("conn","Connection Successful!");
                                    if(((String)responseObject.get("loginStatus")).equals("LOGIN_SUCCESS")){
                                        Log.e("login","Logged in");
                                        Toast.makeText(LoginActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, TrainerActivityDetailsActivity.class);
                                        intent.putExtra("name",responseObject.get("name").toString());
                                        intent.putExtra("pri_mob_num",responseObject.get("pri_mob_num").toString());
                                        startActivity(intent);
                                    }
                                    else if(((String)responseObject.get("loginStatus")).equals("LOGIN_FAIL")){
                                        Log.e("login","Login failed");
                                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Log.e("login","Unexpected Error");
                                        Toast.makeText(LoginActivity.this, "Unexpected Error occured!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Log.e("conn","Connection Fail");
                                    Toast.makeText(LoginActivity.this, "Connection failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error",error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("pri_mob_num",pri_mob_num);
                    params.put("password",password);

                    return params;
                }
            };
            queue.add(stringRequest);
            return null;
        }
    }
}
