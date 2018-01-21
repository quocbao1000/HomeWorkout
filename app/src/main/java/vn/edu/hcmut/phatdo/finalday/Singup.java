package vn.edu.hcmut.phatdo.finalday;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
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

import java.util.HashMap;
import java.util.Map;


public class Singup extends AppCompatActivity {

    Button btnCreate;
    EditText txtUsername, txtPassword, txtRePassword,txtAge,txtFullname,txtGender,txtEmail;
    String res;
    String username,password,repassword;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        res ="";
        username="";
        password="";

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.signup_layout);
        dialog.setCanceledOnTouchOutside(false);
        btnCreate = (Button)dialog.findViewById(R.id.btnCreate);
        txtUsername = (EditText)dialog.findViewById(R.id.txtUsername);
        txtPassword = (EditText)dialog.findViewById(R.id.txtPassword);
        txtRePassword = (EditText)dialog.findViewById(R.id.txtRePassword);
        txtFullname = (EditText)dialog.findViewById(R.id.txtFullname);
        txtAge = (EditText)dialog.findViewById(R.id.txtAge);
        txtGender = (EditText)dialog.findViewById(R.id.txtGender);
        txtEmail = (EditText)dialog.findViewById(R.id.txtEmail);
        dialog.show();
        //Check if user saved their account information

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txtUsername.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                repassword = txtRePassword.getText().toString().trim();
                if(username.equals("")){
                    Toast.makeText(Singup.this,"Username can't be null",Toast.LENGTH_SHORT).show();
                }
                else if(txtFullname.getText().toString().equals("")){
                    Toast.makeText(Singup.this,"Full name can't be null",Toast.LENGTH_SHORT).show();
                }
                else if(txtEmail.getText().toString().equals("")){
                    Toast.makeText(Singup.this,"Email can't be null",Toast.LENGTH_SHORT).show();
                }
                else if(txtAge.getText().toString().equals("")){
                    Toast.makeText(Singup.this,"Age can't be null",Toast.LENGTH_SHORT).show();
                }
                else if(txtGender.getText().toString().equals("")){
                    Toast.makeText(Singup.this,"Gender can't be null",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(repassword)){
                    Toast.makeText(Singup.this,"Invalid password",Toast.LENGTH_SHORT).show();
                }
                else {
                    RequestQueue requestQueue = Volley.newRequestQueue(Singup.this);
                    String url = "https://trafficbk.000webhostapp.com/create_account.php";
                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            res = response;
                            new CountDownTimer(2000, 1000) {
                                ProgressDialog p;
                                boolean b = false;

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    if (b == false) {
                                        b = true;
                                        p = ProgressDialog.show(Singup.this, "Login...",
                                                "Loading, please wait...", true);
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    p.dismiss();
                                    if (!res.equals("")) {
                                        if (res.equals("{\"respond\":\"successful\"}")) {
                                            Intent intent = new Intent(Singup.this, Login.class);
                                            Singup.this.startActivity(intent);
                                            dialog.dismiss();
                                            Toast.makeText(Singup.this, "Successful", Toast.LENGTH_SHORT).show();
                                            Singup.this.finish();
                                        }
                                        else{
                                            Toast.makeText(Singup.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(Singup.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }.start();
                            //Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap();
                            params.put("user", txtUsername.getText().toString());
                            params.put("pass", txtPassword.getText().toString());
                            params.put("fullname",txtFullname.getText().toString().trim());
                            params.put("age",txtAge.getText().toString().trim());
                            params.put("gender",txtGender.getText().toString().trim());
                            params.put("email",txtEmail.getText().toString().trim());
                            return params;
                        }
                    };
                    requestQueue.add(jsonObjectRequest);


                }
            }});
    }
}
