package com.modulismo.popularmovies.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.modulismo.popularmovies.Models.GridItem;
import com.modulismo.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alex on 7/23/16.
 */
public class PostersAdapter extends ArrayAdapter<GridItem> {
    private static final String LOG_TAG = PostersAdapter.class.getSimpleName();

    private Context context;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();

    public PostersAdapter(Context context, int layoutResourceId, ArrayList<GridItem> mGridData) {
        super(context, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.mGridData = mGridData;
    }

    public void setGridData(ArrayList<GridItem> mGridData){
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View tile = convertView;
        ViewHolder mViewHolder;

        if (tile == null){

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            tile = inflater.inflate(layoutResourceId, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.imageView = (ImageView) tile.findViewById(R.id.grid_item_activity_image);
            tile.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) tile.getTag();
        }

        GridItem item = mGridData.get(position);

        Picasso
                .with(context)
                .load(item.getImage())
                .into(mViewHolder.imageView);

        return tile;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}
