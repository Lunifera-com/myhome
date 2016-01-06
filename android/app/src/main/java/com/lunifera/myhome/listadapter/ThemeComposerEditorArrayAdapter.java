package com.lunifera.myhome.listadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lunifera.myhome.R;
import com.lunifera.myhome.model.DimmerEndpoint;
import com.lunifera.myhome.model.Endpoint;
import com.lunifera.myhome.model.OnOffEndpoint;
import com.lunifera.myhome.model.RGBWEndpoint;

/**
 * Created by bernhardedler on 08.12.15.
 */
public class ThemeComposerEditorArrayAdapter extends ArrayAdapter<Endpoint> {

    CheckBox chkBx;
    Context mContext;
    int layoutResourceId;
    Endpoint[] content;

    public ThemeComposerEditorArrayAdapter(Context context, int resource, Endpoint[] objects) {
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
        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView tvTitle = (TextView) convertView.findViewById(R.id.editorListItemTitle);
        tvTitle.setText(v.getName());
        TextView tvUnit = (TextView) convertView.findViewById(R.id.editorListItemUnit);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.editorListValuePlaceholder);
        if (v instanceof OnOffEndpoint){
            tvUnit.setText("ON/OFF");
            if (layout.getChildCount()<=1) {
                Spinner s = new Spinner(convertView.getContext());
                String[] values = {"ON", "OFF"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(convertView.getContext(), android.R.layout.simple_spinner_item, values);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                s.setAdapter(spinnerArrayAdapter);
                layout.addView(s);
            }
        } else if (v instanceof RGBWEndpoint){
            tvUnit.setText("RGBW");
            if (layout.getChildCount()<=1) {
                EditText t = new EditText(convertView.getContext());
                t.setText("value");
                layout.addView(t);
            }
        } else if (v instanceof DimmerEndpoint){
            tvUnit.setText("Dim Value");
            if (layout.getChildCount()<=1) {
                EditText t = new EditText(convertView.getContext());
                t.setText("value");
                layout.addView(t);
            }
        }

        return convertView;

    }
}
