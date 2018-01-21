package vn.edu.hcmut.phatdo.finalday;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int REQUEST_CAMERA = 89;
    private final int REQUEST_IMAGE_CAPTURE = 90;

    String username, fullname, email,gender;
    int age;
    Boolean isClose;
    ListView lstExercise;
    ArrayList<ExerciseItem> arrExercise,arrSuggest,lstTemp,lstTemp2;
    AdapterExercise adapterExercise;
    ArrayList<NotificationItem> arrayList;
    ListView lstNotification;
    Menu mainmenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isClose = true;
        Intent it = getIntent();
        username = it.getStringExtra("username");
        fullname = it.getStringExtra("fullname");
        email = it.getStringExtra("email");
        gender = it.getStringExtra("gender");
        age = it.getIntExtra("age",0);

        //Toast.makeText(MainActivity.this,username,Toast.LENGTH_SHORT).show();

        lstExercise = (ListView)findViewById(R.id.lstExercise);
        lstNotification = (ListView) findViewById(R.id.lstNotification);
        if(Build.VERSION.SDK_INT >=21) {
            //Set color cho status bar
            Window window = MainActivity.this.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));

            //Set color cho thanh dieu huong
            //window.setNavigationBarColor(getResources().getColor(R.color.my_statusbar_color));
            //Set color cho action bar (title bar)
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionBar = MainActivity.this.getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView txtUser = (TextView) headerView.findViewById(R.id.txtUser);
        txtUser.setText(fullname);
        TextView txtEmail = (TextView) headerView.findViewById(R.id.txtEmail);
        txtEmail.setText(email);

        arrExercise = new ArrayList<>();
        arrSuggest = new ArrayList<>();
        arrExercise.add(new ExerciseItem(R.drawable.pushup,R.raw.day1,"Male","PUSH-UPS","Lay prone on the ground with arms supporting your body.\n" +
                "Keep your body straight while raising and lowering your body with your arm.\n" +
                "This excercise works the best, shoulders, triceps, back and legs.",12,2));
        arrExercise.add(new ExerciseItem(R.drawable.declinepushup,R.raw.declinepushup,"Male","DECLINE PUSH-UPS","Start on all fours with your knees under your butt and your hands under your shoulders.\n" +
                "Then elevate your feet on a chair or bench, and push your body up and downs mainly using your arm strength.\n" +
                "Remember to keep your body straight.",20,4));
        arrExercise.add(new ExerciseItem(R.drawable.dumbell,R.raw.pullup,"FeMale","DUMBELLROW   ","Start on all fours with your knees under your butt and your hands under your shoulders.\n" +
                "\n" +
                "Then elevate your feet on a chair or bench, and push your body up and downs mainly using your arm strength.\n",22,5));
        arrExercise.add(new ExerciseItem(R.drawable.splitsquat,R.raw.splitsquat,"FeMale","SPLIT SQUAT","Start in a forearm side plank on your left side with your left elbow resting on the floor below your shoulder. Place your right arm behind your head. Rotate your torso toward the floor, bringing your right elbow to meet your left hand. Don't let your hips drop.",30,6));
        arrExercise.add(new ExerciseItem(R.drawable.pullup,R.raw.dumbellrow,"Male","PULL UP","Start in a high plank with your wrists under your shoulders and your feet hip-width apart. Push your hips up and back to move into a Downward Dog with your heels reaching toward the floor. Keep your core tight and shift your weight forward to come back into a high plank. ",35,7));
        arrExercise.add(new ExerciseItem(R.drawable.squatjump,R.raw.squatjump,"Male","SQUAT JUMP","Start in a high plank with shoulders above your wrists and abs tight. Step right foot and right hand to right side, immediately following with left foot and left hand. Take a few \"steps\" in one direction, then walk in the opposite direction. Continue for 1 minute.",40,2));
        lstTemp = new ArrayList<>();
        lstTemp2 = new ArrayList<>();
        //Suggest video via gender and age
        for(int i=0;i<arrExercise.size();i++){
            ExerciseItem item = arrExercise.get(i);
            if(item.getGender().equals(gender)&&item._age<=age){
                arrSuggest.add(item);
            }
            else{
                lstTemp.add(item);
            }
        }

        for(int i=0;i<lstTemp.size();i++){
            ExerciseItem item = lstTemp.get(i);
            if(item.getGender().equals(gender)){
                arrSuggest.add(item);
            }
            else {
                lstTemp2.add(item);
            }
        }
        for(int i=0;i<lstTemp2.size();i++){
            arrSuggest.add(lstTemp2.get(i));
        }


        adapterExercise = new AdapterExercise(MainActivity.this,R.layout.exercise_item_layout,arrSuggest);
        lstExercise.setAdapter(adapterExercise);
        lstExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                pos = position;
                ExerciseItem item = arrSuggest.get(position-1);
                Intent intent = new Intent(MainActivity.this,ViewVideoActivity.class);
                intent.putExtra("item",item);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        lstExercise.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final ExerciseItem item = arrSuggest.get(position-1);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setIcon(R.drawable.challenge);
                alertDialog.setMessage("Challenge your friends?");
                alertDialog.setTitle("GET FUN TOGETHER");
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setPositiveButton("Choose friend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this,ChooseFriend.class);
                        intent.putExtra("username",username);
                        intent.putExtra("course",item.getName());
                        startActivity(intent);
                    }
                });
                alertDialog.show();
                return true;
            }
        });



        String url = "https://trafficbk.000webhostapp.com/getChallenge.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length()>0){
                        mainmenu.findItem(R.id.itemNotification).setIcon(R.drawable.ic_notifications_active_black_24dp);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", fullname);
                return params;
            }
        };
        requestQueue.add(stringRequest);





        lstNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Enter", Toast.LENGTH_SHORT).show();
                final NotificationItem item = arrayList.get(position);


                String url = "https://trafficbk.000webhostapp.com/deleteChallenge.php";
                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("user", fullname);
                        params.put("guest",item.getFriendname());
                        params.put("course",item.getCourse());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

                lstNotification.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MainActivity.this,ViewVideoActivity.class);
                for(int i=0;i<arrSuggest.size();i++){
                    ExerciseItem course = arrSuggest.get(i);

                    if(course.getName().equals(item.getCourse())){
                        arrayList.remove(position);
                        if(arrayList.size()==0){
                            mainmenu.findItem(R.id.itemNotification).setIcon(R.drawable.ic_notifications);
                        }
                        else mainmenu.findItem(R.id.itemNotification).setIcon(R.drawable.ic_notifications_active_black_24dp);
                        intent.putExtra("item",course);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        break;
                    }
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mainmenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itemFriendList) {
            Intent it = new Intent(MainActivity.this,FriendList.class);
            it.putExtra("username",username);
            startActivity(it);
            return true;
        }
        else if(id == R.id.itemNotification){

            if(isClose) {
                isClose = false;
                lstNotification.setVisibility(View.VISIBLE);
                lstNotification.bringToFront();
                String url = "https://trafficbk.000webhostapp.com/getChallenge.php";
                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            arrayList = new ArrayList<>();
                            if(jsonArray.length()>0)
                                mainmenu.findItem(R.id.itemNotification).setIcon(R.drawable.ic_notifications_active_black_24dp);
                            else
                                mainmenu.findItem(R.id.itemNotification).setIcon(R.drawable.ic_notifications);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                arrayList.add(new NotificationItem(jsonObject.getString("friendname"), jsonObject.getString("course")));
                            }
                            NotificationAdapter adt = new NotificationAdapter(MainActivity.this, R.layout.notification_layout, arrayList);
                            lstNotification.setAdapter(adt);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("user", fullname);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
            else {
                isClose = true;
                lstNotification.setVisibility(View.INVISIBLE);
            }



        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.itemLogin:
                intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                break;
            case R.id.itemLogout:
                SharedPreferences s = getSharedPreferences("loginData",MODE_PRIVATE);
                SharedPreferences.Editor sedt = s.edit();
                sedt.putBoolean("isRemembered",false);
                sedt.remove("username");
                sedt.remove("password");
                sedt.commit();
                intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_change_intent_2,R.anim.anim_change_intent);
                MainActivity.this.finish();
                break;
            case R.id.itemRank:
                intent = new Intent(MainActivity.this,RankingActivity.class);
                startActivity(intent);
                break;
            case R.id.itemLevel:
                intent = new Intent(MainActivity.this,Level.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Camera request
        if(requestCode==REQUEST_CAMERA && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Image capture
        if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK&&data!=null){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.image_show_layout);
            dialog.setCanceledOnTouchOutside(false);
            ImageView imgCap = (ImageView)dialog.findViewById(R.id.imgCaptured);
            Button btnSave = (Button)dialog.findViewById(R.id.btnSaveImgCap);
            imgCap.setImageBitmap(bitmap);
            dialog.show();
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
