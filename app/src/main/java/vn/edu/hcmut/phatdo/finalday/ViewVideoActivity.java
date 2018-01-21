package vn.edu.hcmut.phatdo.finalday;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ViewVideoActivity extends AppCompatActivity {

    Database database;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        final ExerciseItem item = (ExerciseItem) intent.getSerializableExtra("item");
        VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+item.getVideo());
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        TextView txtDesc = (TextView)findViewById(R.id.txtDescription);
        txtDesc.setText(item.getDescription());
        TextView txtName = (TextView)findViewById(R.id.txtName);
        txtName.setText(item.getName());
        database = new Database(ViewVideoActivity.this,"accountInfo.sqlite",null,1);
        database.queryData("CREATE TABLE IF NOT EXISTS data(username VARCHAR(30) PRIMARY KEY,level int not null, score int not null)");
        final Cursor cursor = database.getData("SELECT * FROM data WHERE username = '"+ username +"'");
        final int colLevel = cursor.getColumnIndex("level");
        final int colScore = cursor.getColumnIndex("score");
        final Dialog dialog = new Dialog(ViewVideoActivity.this);
        dialog.setCanceledOnTouchOutside(true);
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Toast.makeText(ViewVideoActivity.this,"DONE!",Toast.LENGTH_SHORT).show();
                if(cursor.moveToNext()){
                    int lastLevel = cursor.getInt(colLevel);
                    int level;
                    int score = cursor.getInt(colScore)+item.getScore();
                    level = score / 10;
                    if(level>lastLevel){

                        dialog.setContentView(R.layout.levelup_layout);
                        dialog.show();
                        TextView txtLevel = (TextView)dialog.findViewById(R.id.txtNewLevel);
                        txtLevel.setText(level+"");
                    }
                    database.queryData("UPDATE data SET score = "+score+", level = "+level);
                }
                else{
                    int level;
                    int score = item.getScore();
                    level = score / 10;
                    database.queryData("INSERT INTO data VALUES('"+username+"',"+level+","+score+")");
                }
            }
        }.start();



    }

}
