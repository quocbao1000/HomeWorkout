package vn.edu.hcmut.phatdo.finalday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class AdapterGridView extends BaseAdapter {
    //Attributes
    protected Context _context;
    protected List<ExerciseItem> _listItem;
    //Constructor
    public AdapterGridView(Context ctx){
        this._context = ctx;
    }
    //Methods
    @Override
    public int getCount() {
        return 7;
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
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.grid_item,null);
        ImageView img;;
        img = (ImageView) convertView.findViewById(R.id.cirimage);
        if(position <= 2)
        img.setImageResource(R.drawable.ic_circheck);

        return convertView;
    }
}
