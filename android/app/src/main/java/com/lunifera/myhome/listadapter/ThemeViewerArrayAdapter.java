package com.lunifera.myhome.listadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lunifera.myhome.R;
import com.lunifera.myhome.model.Endpoint;

/**
 * Created by bernhardedler on 08.12.15.
 */
public class ThemeViewerArrayAdapter extends ArrayAdapter<Endpoint> {

    CheckBox chkBx;
    Context mContext;
    int layoutResourceId;
    Endpoint[] content;
    Endpoint current;

    public ThemeViewerArrayAdapter(Context context, int resource, Endpoint[] objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.layoutResourceId = resource;
        this.content = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        Endpoint v = content[position];
        current = v;
        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView tv = (TextView) convertView.findViewById(R.id.themeViewerListItemTitle);
        tv.setText(current.getName());

        return convertView;

    }

}
