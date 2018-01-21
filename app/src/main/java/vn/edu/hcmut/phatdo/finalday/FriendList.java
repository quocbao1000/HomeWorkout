package vn.edu.hcmut.phatdo.finalday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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

public class FriendList extends AppCompatActivity {

    JSONArray jsonArray;
    String username;
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
        //Toast.makeText(FriendList.this,username,Toast.LENGTH_SHORT).show();
        final RequestQueue requestQueue = Volley.newRequestQueue(FriendList.this);
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
                            adapter = new CustomAdapter(FriendList.this, R.layout.item_layout, lstFriend);
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




//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                jsonArray = response;
//                lstFriend = new ArrayList<>();
//                Toast.makeText(FriendList.this,response.length()+"",Toast.LENGTH_SHORT).show();
//                for(int i=0;i<response.length();i++){
//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//                            lstFriend.add(new Item(jsonObject.getString("friendname"),jsonObject.getString("friendgender"),jsonObject.getInt("friendage"),
//                                    jsonObject.getString("friendemail")));
//                            adapter = new CustomAdapter(FriendList.this,R.layout.item_layout,lstFriend);
//                            lstView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//
//                    }
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(FriendList.this,error.toString(),Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("user",username);
//                return super.getParams();
//            }
//        };
        requestQueue.add(stringRequest);
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
