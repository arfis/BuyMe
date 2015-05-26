package adapter;

import java.util.ArrayList;

import com.blackpython.R;
import data.DrawerItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DrawerListAdapter extends ArrayAdapter<DrawerItem> {

    Context mContext;
    int layoutResourceId;
    LayoutInflater inflater;
    ArrayList<DrawerItem> data = null;

    public DrawerListAdapter(Context mContext, int layoutResourceId, ArrayList<DrawerItem> data){

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DrawerItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if (convertView == null) {
            listItem = inflater.inflate(R.layout.drawerlist_row, null);
        }

        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);
        TextView textViewCount = (TextView) listItem.findViewById(R.id.count);
        RelativeLayout background = (RelativeLayout) listItem.findViewById(R.id.itemback);

        if(getItemViewType(position)==0)
        {
            background.setBackgroundColor(mContext.getResources().getColor(R.color.greyLight));
        }
        else
        {
            background.setBackgroundColor(mContext.getResources().getColor(R.color.greyDark));
        }

        DrawerItem folder = data.get(position);
        imageViewIcon.setImageResource(folder.icon);
        textViewName.setText(folder.name);
        if(folder.count == 0)
            textViewCount.setVisibility(View.GONE);
        else
            textViewCount.setText(Integer.toString(folder.count));

        return listItem;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0) return 0;
        return 1;
    }
}