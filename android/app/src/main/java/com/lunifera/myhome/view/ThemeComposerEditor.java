package com.lunifera.myhome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.lunifera.myhome.R;
import com.lunifera.myhome.listadapter.ThemeComposerEditorArrayAdapter;
import com.lunifera.myhome.model.Endpoint;

import java.util.ArrayList;
import java.util.List;

public class ThemeComposerEditor extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton fab;


    public static List<Endpoint> selected = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_editor);

        Intent i = getIntent();
        selected = i.getParcelableArrayListExtra("selected");

        ListView listView = (ListView) findViewById(R.id.listViewEndpointsEditor);
        listView.setAdapter(new ThemeComposerEditorArrayAdapter(ThemeComposerEditor.this, R.layout.themecomposereditor_listview, selected.toArray(new Endpoint[selected.size()])));

    }
}
