package vn.edu.hcmut.phatdo.finalday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChooseFriend extends AppCompatActivity {

    JSONArray jsonArray;
    String username,course;
    ArrayList<Item> lstFriend;
    CustomAdapter adapter;
    ListView lstView;
    ArrayList<Integer> lstImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        lstView = (ListView)findViewById(R.id.lstFriend);
        initial();
        Intent it = getIntent();
        username = it.getStringExtra("username");
        course = it.getStringExtra("course");

        //Toast.makeText(ChooseFriend.this,username, Toast.LENGTH_SHORT).show();
        final RequestQueue requestQueue = Volley.newRequestQueue(ChooseFriend.this);
        String url = "https://trafficbk.000webhostapp.com/friendlist.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(FriendList.this,response.toString(),Toast.LENGTH_SHORT).show();
                try {
                    lstFriend = new ArrayList<>();
                    jsonArray = new JSONArray(response);
                    //Toast.makeText(FriendList.this,jsonArray.length()+"",Toast.LENGTH_SHORT).show();
                    for(int i=0;i<jsonArray.length();i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            lstFriend.add(new Item(randomAvatar(),jsonObject.getString("friendname"), jsonObject.getString("friendgender"), jsonObject.getInt("friendage"),
                                    jsonObject.getString("friendemail")));
                            adapter = new CustomAdapter(ChooseFriend.this, R.layout.item_layout, lstFriend);
                            lstView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user",username);
                return params;
            }
        };

        requestQueue.add(stringRequest);


        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos =position;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Item item = lstFriend.get(pos);
                        String url = "https://trafficbk.000webhostapp.com/request.php";
                        StringRequest js = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ChooseFriend.this,error.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<>();
                                params.put("user",username);
                                params.put("user",username);
                                params.put("guest",item.getName());
                                params.put("course",course);
                                params.put("status","1");
                                return params;
                            }
                        };
                        requestQueue.add(js);
                    }
                });



            }

        });


    }

    private void initial(){
        lstImg = new ArrayList<>();
        lstImg.add(R.drawable.a1);
        lstImg.add(R.drawable.a2);
        lstImg.add(R.drawable.a3);
        lstImg.add(R.drawable.a4);
        lstImg.add(R.drawable.a5);
        lstImg.add(R.drawable.a6);
        lstImg.add(R.drawable.a7);
    }

    private int randomAvatar(){
        Random random = new Random();
        return lstImg.get(random.nextInt(lstImg.size()));
    }
}
