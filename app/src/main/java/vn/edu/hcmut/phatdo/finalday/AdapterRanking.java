package vn.edu.hcmut.phatdo.finalday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peank on 21/01/2018.
 */

public class AdapterRanking extends BaseAdapter{
    //Attributes
    protected Context _context;
    protected List<Integer> _icons;
    protected List<String> _names;
    protected List<Integer> _scores;
    //Constructor
    public AdapterRanking(Context ctx){

        this._context = ctx;
        _icons = new ArrayList<>();
        _names = new ArrayList<>();
        _scores = new ArrayList<>();

        //initialize
        _icons.add(R.drawable.a1);
        _icons.add(R.drawable.a2);
        _icons.add(R.drawable.a3);
        _icons.add(R.drawable.a4);
        _icons.add(R.drawable.a5);
        _icons.add(R.drawable.a6);
        _icons.add(R.drawable.a7);

        _names.add("Latarsha Migues");
        _names.add("Lurline Wickham");
        _names.add("Margurite Hance");
        _names.add("Alverta Edson");
        _names.add("Janell Cavanagh");
        _names.add("Elda Tejada");
        _names.add("Avril Brawner");

        _scores.add(1010);
        _scores.add(990);
        _scores.add(865);
        _scores.add(567);
        _scores.add(435);
        _scores.add(431);
        _scores.add(399);


    }
    //Methods
    @Override
    public int getCount() {
        return _names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.item_ranking_layout,null);
        ImageView img = (ImageView) convertView.findViewById(R.id.icon_r);
        TextView name = (TextView)convertView.findViewById(R.id.rname);
        TextView stt = (TextView)convertView.findViewById(R.id.rstt);
        TextView score = (TextView)convertView.findViewById(R.id.rscore);
        img.setImageResource(_icons.get(position));
        int i = position+1;
        stt.setText(i+"");
        name.setText(_names.get(position));
        score.setText(_scores.get(position)+"");
        if(position==0) score.setTextColor(_context.getResources().getColor( R.color.red));
        return convertView;
    }
}
