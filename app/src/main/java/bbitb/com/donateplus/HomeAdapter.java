package bbitb.com.donateplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Daniel Thuo on 15/11/2017.
 */

public class HomeAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ChildrensHomes> mDataSource;

    public HomeAdapter(Context context, ArrayList<ChildrensHomes> items){

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

        View rowView = mInflater.inflate(R.layout.list_item_home, parent, false);

        //Get Title element
        TextView titleTextView = (TextView) rowView.findViewById(R.id.home_list_title);

        //Get Subtitle element
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.home_list_subtitle);

        //Get Detail element
        TextView detailTextView = (TextView) rowView.findViewById(R.id.home_list_detail);

        //Get thumbnail element
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.home_list_thumbnail);

        //1
        ChildrensHomes hom = (ChildrensHomes) getItem(position);

        //2
        titleTextView.setText(hom.title);
        subtitleTextView.setText(hom.description);
        //detailTextView.setText(hom.);

        //3
        Picasso.with(mContext).load(hom.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return rowView;
    }
}
