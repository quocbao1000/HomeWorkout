package vn.edu.hcmut.phatdo.finalday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static vn.edu.hcmut.phatdo.finalday.R.id.imgAvatar;

/**
 * Created by peank on 20/01/2018.
 */

public class CustomAdapter extends BaseAdapter{

    //Attributes
    protected Context _context;
    protected int _layout;
    protected List<Item> _listItem;
    //Constructor
    public CustomAdapter(Context ctx, int layout, List<Item> lst){
        this._context = ctx;
        this._layout = layout;
        this._listItem = lst;
    }
    //Methods
    @Override
    public int getCount() {
        return _listItem.size();
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
        convertView = layoutInflater.inflate(this._layout,null);
        ImageView img;
        TextView txtName, txtAge, txtDesc, txtEmail;
        Item item = this._listItem.get(position);
        img = (ImageView)convertView.findViewById(imgAvatar);
        txtName = (TextView)convertView.findViewById(R.id.txtName);
//        txtDesc = (TextView)convertView.findViewById(R.id.txtDesc);
//        txtEmail = (TextView)convertView.findViewById(R.id.txtEmail);
        txtAge = (TextView)convertView.findViewById(R.id.txtAge);
        //img.setImageResource(item.getAvatar());
        txtName.setText(item.getName());
//        txtDesc.setText("Gender: "+item.getDescription());
        //txtEmail.setText("Email: "+item.getEmail());
        txtAge.setText(item.getAge()+" years old, "+item.getDescription());
        img.setImageResource(item.getAvatar());

        //Apply animation for item view
        convertView.startAnimation(AnimationUtils.loadAnimation(this._context,R.anim.anim_list_view));

        return convertView;
    }
}
