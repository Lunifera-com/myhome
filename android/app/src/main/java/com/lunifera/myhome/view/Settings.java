package com.lunifera.myhome.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lunifera.myhome.R;
import com.lunifera.myhome.util.Common;

public class Settings extends AppCompatActivity {

    EditText editTextUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextUri = (EditText) findViewById(R.id.editTextRestUri);

        Button saveButton = (Button) findViewById(R.id.buttonSaveSettings);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
        SharedPreferences settings = getSharedPreferences(Common.PREFS_FILE, 0);
        String uri = settings.getString(Common.PREFS_URI, null);
        if (uri != null){
            editTextUri.setText(uri);
        }

    }

    private void saveSettings() {
        SharedPreferences settings = getSharedPreferences(Common.PREFS_FILE, 0);
        String uri = editTextUri.getText().toString();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Common.PREFS_URI, uri);
        editor.commit();
    }
}
