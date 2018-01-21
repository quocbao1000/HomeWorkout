package vn.edu.hcmut.phatdo.finalday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ListView l = (ListView) findViewById(R.id.list_rank);
        AdapterRanking adapter = new AdapterRanking(this);
        l.setAdapter(adapter);
    }
}
