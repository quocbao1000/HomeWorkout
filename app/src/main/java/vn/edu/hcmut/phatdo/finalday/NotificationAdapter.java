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

/**
 * Created by peank on 20/01/2018.
 */

public class NotificationAdapter extends BaseAdapter {
    //Attributes
    protected Context _context;
    protected int _layout;
    protected List<NotificationItem> _listItem;
    //Constructor
    public NotificationAdapter(Context ctx, int layout, List<NotificationItem> lst){
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
        TextView txtNotification;
        NotificationItem item = this._listItem.get(position);
        txtNotification = (TextView)convertView.findViewById(R.id.txtNotification);
        txtNotification.setText(item.getFriendname()+" has just challenged you to take "+item.getCourse()+" exercise");
        //Apply animation for item view
        convertView.startAnimation(AnimationUtils.loadAnimation(this._context,R.anim.anim_list_view));

        return convertView;
    }
}
