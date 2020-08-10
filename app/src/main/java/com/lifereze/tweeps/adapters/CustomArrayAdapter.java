package com.lifereze.tweeps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lifereze.tweeps.R;

public class CustomArrayAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mArrayElements;

    public CustomArrayAdapter(Context mContext,String[] mArrayElements){
        this.mContext = mContext;
        this.mArrayElements = mArrayElements;
    }

    @Override
    public Object getItem(int position){
        return null;
    }
    @Override
    public long getItemId(int position){
        return 0;
    }
    @Override
    public int getCount(){
        return mArrayElements.length;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView;
        listView = inflater.inflate(R.layout.tweet_category_list,null);
        TextView categoryText = (TextView) listView.findViewById(R.id.tweetsCategoryTextView);
        categoryText.setText(mArrayElements[position]);
        return listView;
    }
}
