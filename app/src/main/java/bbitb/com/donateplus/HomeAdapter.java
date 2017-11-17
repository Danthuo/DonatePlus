package bbitb.com.donateplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Daniel Thuo on 15/11/2017.
 */

public class HomeAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<HomesList> mDataSource;

    public HomeAdapter(Context context, ArrayList<HomesList> items){

        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
        //gets the items in the data source
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
        //gets the items in the data source
    }

    @Override
    public long getItemId(int position) {
        return position;
        //gets the position in the data source so that the listview knows where to place the items
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
