package vn.edu.hcmut.phatdo.finalday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Level extends AppCompatActivity {

    TextView txtLevel,txtScore;
    SharedPreferences sharedPreferences;
    Database database;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        database = new Database(Level.this,"accountInfo.sqlite",null,1);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        txtLevel = (TextView)findViewById(R.id.txtLevel);
        txtScore = (TextView)findViewById(R.id.txtScore);
        database.queryData("CREATE TABLE IF NOT EXISTS data(username VARCHAR(30) PRIMARY KEY,level int not null, score int not null)");
        Cursor cursor = database.getData("SELECT * FROM data WHERE username = '"+ username +"'");
        int colLevel = cursor.getColumnIndex("level");
        int colScore = cursor.getColumnIndex("score");
        if(cursor.moveToNext()){
            txtLevel.setText("LEVEL: "+cursor.getInt(colLevel));
            txtScore.setText("SCORE: "+cursor.getInt(colScore));
        }

    }
}
