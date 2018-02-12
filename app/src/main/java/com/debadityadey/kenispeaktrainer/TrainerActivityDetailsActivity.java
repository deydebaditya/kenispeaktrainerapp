package com.debadityadey.kenispeaktrainer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Debaditya on 1/31/2018.
 */

public class TrainerActivityDetailsActivity extends AppCompatActivity {

    TextView trainer_name;
    RadioGroup reasonRadioGroup;
    RadioButton holiday, leave, sessions;
    EditText date_input, school_input, holiday_reason, leave_reason, num_of_sessions;
    DatePicker datePicker;
    RelativeLayout holidayFrag, leaveFrag, sessionFrag;
    Button nextHoliday, nextLeave, nextSession;

    boolean datePickerActive = false;

    String date = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traineractivity_details);
        initializeWidgets();

        trainer_name.setText(getIntent().getStringExtra("name"));

        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerActive = true;
                datePicker.setVisibility(View.VISIBLE);
                reasonRadioGroup.setVisibility(View.INVISIBLE);
                datePicker.init(2018, Calendar.MONTH, Calendar.DAY_OF_MONTH, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        date = String.valueOf(dayOfMonth)+"-"+String.valueOf(monthOfYear)+"-"+String.valueOf(year);
                        datePicker.setVisibility(View.INVISIBLE);
                        date_input.setText(date);
                        datePickerActive = false;
                        reasonRadioGroup.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        reasonRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.holiday_radio:
                        holidayFrag.setVisibility(View.VISIBLE);
                        leaveFrag.setVisibility(View.INVISIBLE);
                        sessionFrag.setVisibility(View.INVISIBLE);
                        nextHoliday.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(holiday_reason.getText().toString().equals("")){
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Please enter a reason!", Toast.LENGTH_SHORT).show();
                                }
                                else if(school_input.getText().toString().equals("")){
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Please enter a valid school name!", Toast.LENGTH_SHORT).show();
                                }
                                else if(date.equals("")){
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Please select a valid date!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    new SendRequestForHoliday(date,getIntent().getStringExtra("pri_mob_num"),holiday_reason.getText().toString(),school_input.getText().toString()).execute();
                                }
                            }
                        });
                        break;

                    case R.id.leave_radio:
                        holidayFrag.setVisibility(View.INVISIBLE);
                        leaveFrag.setVisibility(View.VISIBLE);
                        sessionFrag.setVisibility(View.INVISIBLE);
                        nextLeave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(leave_reason.getText().toString().equals("")){
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Please enter a reason!", Toast.LENGTH_SHORT).show();
                                }
                                else if(school_input.getText().toString().equals("")){
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Please enter a valid school name!", Toast.LENGTH_SHORT).show();
                                }
                                else if(date.equals("")){
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Please select a valid date!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    new SendRequestForLeave(date,getIntent().getStringExtra("pri_mob_num"),leave_reason.getText().toString(),school_input.getText().toString()).execute();
                                }
                            }
                        });
                        break;

                    case R.id.sessions_radio:
                        holidayFrag.setVisibility(View.INVISIBLE);
                        leaveFrag.setVisibility(View.INVISIBLE);
                        sessionFrag.setVisibility(View.VISIBLE);
                        nextSession.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        break;
                }
            }
        });


    }

    private void initializeWidgets(){
        trainer_name = (TextView)findViewById(R.id.trainerName);
        reasonRadioGroup = (RadioGroup)findViewById(R.id.activity_radio_group);
        holiday = (RadioButton)findViewById(R.id.holiday_radio);
        leave = (RadioButton)findViewById(R.id.leave_radio);
        sessions = (RadioButton)findViewById(R.id.sessions_radio);
        date_input = (EditText)findViewById(R.id.date_input);
        school_input = (EditText)findViewById(R.id.school_input);
        datePicker = (DatePicker)findViewById(R.id.datePicker_activity);
        holidayFrag = (RelativeLayout)findViewById(R.id.holiday_frag);
        leaveFrag = (RelativeLayout)findViewById(R.id.leave_frag);
        sessionFrag = (RelativeLayout)findViewById(R.id.sessions_frag);
        nextHoliday = (Button) findViewById(R.id.nextHoliday);
        nextLeave = (Button)findViewById(R.id.nextLeave);
        nextSession = (Button)findViewById(R.id.nextSession);
        holiday_reason = (EditText)findViewById(R.id.holiday_reason_input);
        leave_reason =(EditText)findViewById(R.id.leave_reason_input);
        num_of_sessions = (EditText)findViewById(R.id.number_of_sessions_input);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && datePickerActive ){
            datePicker.setVisibility(View.INVISIBLE);
            date_input.setText(date);
            datePickerActive = false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class SendRequestForLeave extends AsyncTask {

        private String date,pri_mob_num,reason,school_name;
        ProgressDialog progressDialog, dismissProgressTemp;
        String URL = "http://ec2-18-216-46-195.us-east-2.compute.amazonaws.com/keni/query_save_leave.php";

        private SendRequestForLeave(String date, String pri_mob_num, String reason, String school_name){
            this.date = date;
            this.pri_mob_num = pri_mob_num;
            this.reason = reason;
            this.school_name = school_name;
            progressDialog = new ProgressDialog(TrainerActivityDetailsActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Saving Responses");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Saving your responses! Please wait...");
            dismissProgressTemp = progressDialog.show(TrainerActivityDetailsActivity.this,"Saving Responses","Saving your responses! Please wait...",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            dismissProgressTemp.dismiss();
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            RequestQueue queue = Volley.newRequestQueue(TrainerActivityDetailsActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject responseObject = new JSONObject(response);
                                if(responseObject.get("conn").toString().equals("CONN_SUCCESS")){
                                    if(responseObject.get("schoolFound").toString().equals("VALID_SCHOOL")){
                                        if(responseObject.get("insert").toString().equals("IN_SUCC")){
                                            Toast.makeText(TrainerActivityDetailsActivity.this, "Data has been saved successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(responseObject.get("insert").toString().equals("IN_FAIL")){
                                            Toast.makeText(TrainerActivityDetailsActivity.this, "Data save has failed!", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(TrainerActivityDetailsActivity.this, "Unexpected Error occurred while saving data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else if(responseObject.get("schoolFound").toString().equals("INVALID_SCHOOL")){
                                        Toast.makeText(TrainerActivityDetailsActivity.this, "Please enter a valid school name!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(TrainerActivityDetailsActivity.this, "Unexpected error occurred!", Toast.LENGTH_SHORT).show();
                                    }

                                }else if(responseObject.get("conn").toString().equals("CONN_FAIL")){
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Connection to server failed!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Unexpected error occurred while connecting to server!", Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch(JSONException e){
                                e.printStackTrace();
                                Log.e("JSON","JSON Exception has occurred");
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VolleyError",error.toString());
                    Toast.makeText(TrainerActivityDetailsActivity.this, "Malformed or erroneous response from server!", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("date",date);
                    params.put("pri_mob_num",pri_mob_num);
                    params.put("school_name",school_name);
                    params.put("reason",reason);
                    return params;
                }
            };
            queue.add(stringRequest);
            return null;
        }
    }

    private class SendRequestForHoliday extends AsyncTask {

        private String date,pri_mob_num,reason,school_name;
        ProgressDialog progressDialog, dismissProgressTemp;
        String URL = "http://ec2-18-216-46-195.us-east-2.compute.amazonaws.com/keni/query_save_holiday.php";

        private SendRequestForHoliday(String date, String pri_mob_num, String reason, String school_name){
            this.date = date;
            this.pri_mob_num = pri_mob_num;
            this.reason = reason;
            this.school_name = school_name;
            progressDialog = new ProgressDialog(TrainerActivityDetailsActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Saving Responses");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Saving your responses! Please wait...");
            dismissProgressTemp = progressDialog.show(TrainerActivityDetailsActivity.this,"Saving Responses","Saving your responses! Please wait...",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            dismissProgressTemp.dismiss();
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            RequestQueue queue = Volley.newRequestQueue(TrainerActivityDetailsActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject responseObject = new JSONObject(response);
                                if(responseObject.get("conn").toString().equals("CONN_SUCCESS")){
                                    if(responseObject.get("schoolFound").toString().equals("VALID_SCHOOL")){
                                        if(responseObject.get("insert").toString().equals("IN_SUCC")){
                                            Toast.makeText(TrainerActivityDetailsActivity.this, "Data has been saved successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(responseObject.get("insert").toString().equals("IN_FAIL")){
                                            Toast.makeText(TrainerActivityDetailsActivity.this, "Data save has failed!", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(TrainerActivityDetailsActivity.this, "Unexpected Error occurred while saving data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else if(responseObject.get("schoolFound").toString().equals("INVALID_SCHOOL")){
                                        Toast.makeText(TrainerActivityDetailsActivity.this, "Please enter a valid school name!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(TrainerActivityDetailsActivity.this, "Unexpected error occurred!", Toast.LENGTH_SHORT).show();
                                    }

                                }else if(responseObject.get("conn").toString().equals("CONN_FAIL")){
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Connection to server failed!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(TrainerActivityDetailsActivity.this, "Unexpected error occurred while connecting to server!", Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch(JSONException e){
                                e.printStackTrace();
                                Log.e("JSON","JSON Exception has occurred");
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VolleyError",error.toString());
                    Toast.makeText(TrainerActivityDetailsActivity.this, "Malformed or erroneous response from server!", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("date",date);
                    params.put("pri_mob_num",pri_mob_num);
                    params.put("school_name",school_name);
                    params.put("reason",reason);
                    return params;
                }
            };
            queue.add(stringRequest);
            return null;
        }
    }
}
