package vn.edu.hcmut.phatdo.finalday;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Login extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String username, password;
    String res;
    Button btnLogin,btnSignUp;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        res ="";
        username = "";
        password = "";

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.login_layout);
        dialog.setCanceledOnTouchOutside(false);
        //Create file xml to save the past login data
        sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);
        //Create editor to write file
        final SharedPreferences.Editor edt = sharedPreferences.edit();
        //Initialization for view objects
        btnLogin = (Button)dialog.findViewById(R.id.btnLogin);
        btnSignUp = (Button)dialog.findViewById(R.id.btnSignUp);
        final EditText edtUsername = (EditText)dialog.findViewById(R.id.txtUsername);
        final EditText edtPassword = (EditText)dialog.findViewById(R.id.txtPassword);
        final CheckBox cbxRemember = (CheckBox)dialog.findViewById(R.id.cbxRemember);
        //Check if user saved their account information

        if(sharedPreferences.getBoolean("isRemembered",false)){
            Intent intent = new Intent(Login.this,MainActivity.class);

            intent.putExtra("username",sharedPreferences.getString("username",""));
            intent.putExtra("fullname",sharedPreferences.getString("fullname",""));
            intent.putExtra("email",sharedPreferences.getString("email",""));
            intent.putExtra("age",sharedPreferences.getInt("age",0));
            intent.putExtra("gender",sharedPreferences.getString("gender",""));
            Login.this.startActivity(intent);
            dialog.dismiss();
            Login.this.finish();
        }
        else{
            dialog.show();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUsername.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                String url = "https://trafficbk.000webhostapp.com/login.php";
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        res = response;
                        //Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                        new CountDownTimer(1000, 500) {
                            ProgressDialog p;
                            boolean b = false;

                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (b == false) {
                                    b = true;
                                    p = ProgressDialog.show(Login.this, "Login...",
                                            "Loading, please wait...", true);
                                }
                            }

                            @Override
                            public void onFinish() {
                                p.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(res);
                                    if(jsonObject!=null){
                                        if(jsonObject.getString("respond").equals("successful")){
                                            if(cbxRemember.isChecked()){
                                                edt.putString("username",username);
                                                edt.putString("password",password);
                                                edt.putString("fullname",jsonObject.getString("fullname"));
                                                edt.putString("email",jsonObject.getString("email"));
                                                edt.putString("gender",jsonObject.getString("gender"));
                                                edt.putInt("age",jsonObject.getInt("age"));
                                                edt.putBoolean("isRemembered",true);
                                                edt.commit();

                                            }
                                            else{
                                                edt.putBoolean("isRemembered",false);
                                                edt.remove("username");
                                                edt.remove("password");
                                                edt.commit();
                                            }
                                            Intent intent = new Intent(Login.this,MainActivity.class);
                                            intent.putExtra("username",username);
                                            intent.putExtra("fullname",jsonObject.getString("fullname"));
                                            intent.putExtra("email",jsonObject.getString("email"));
                                            intent.putExtra("age",jsonObject.getInt("age"));
                                            intent.putExtra("gender",jsonObject.getString("gender"));
                                            Login.this.startActivity(intent);
                                            dialog.dismiss();
                                            Login.this.finish();
                                        }
                                    }
                                    else {
                                        edt.putBoolean("isRemembered",false);
                                        edt.remove("username");
                                        edt.remove("password");
                                        edt.commit();
                                        Toast.makeText(Login.this,"Failed to login",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                        }
                        }.start();
                        //Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap();
                        params.put("user",edtUsername.getText().toString());
                        params.put("pass",edtPassword.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(jsonObjectRequest);

            }});


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Singup.class);
                startActivity(intent);
                Login.this.finish();
            }
        });
    }
}
