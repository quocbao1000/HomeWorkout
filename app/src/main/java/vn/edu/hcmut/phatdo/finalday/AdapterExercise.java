package vn.edu.hcmut.phatdo.finalday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peank on 20/01/2018.
 */

public class AdapterExercise extends BaseAdapter {
    //Attributes
    protected Context _context;
    protected int _layout;
    protected List<ExerciseItem> _listItem;
    //Constructor
    public AdapterExercise(Context ctx, int layout, List<ExerciseItem> lst){
        this._context = ctx;
        this._layout = layout;
        this._listItem = lst;
    }
    //Methods
    @Override
    public int getCount() {
        return _listItem.size()+1;
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

        if(position == 0){
            convertView = layoutInflater.inflate(R.layout.header,null);
            GridView gridView = (GridView) convertView.findViewById(R.id.gridview);
            AdapterGridView adapterGridView = new AdapterGridView(_context);
            gridView.setAdapter(adapterGridView);
            gridView.setEnabled(false);
        }
        else {
            convertView = layoutInflater.inflate(this._layout,null);
            ImageView img;
            TextView txtName, txtAge, txtDesc;
            ExerciseItem item = this._listItem.get(position - 1);
            img = (ImageView) convertView.findViewById(R.id.imgAvatar);
            img.setImageResource(item.getAvatar());
            txtName = (TextView) convertView.findViewById(R.id.txtExerciseName);
            txtName.setText(item.getName());
            convertView.startAnimation(AnimationUtils.loadAnimation(this._context,R.anim.anim_list_view));
        }
        //Apply animation for item view

        return convertView;
    }
}
