package com.lunifera.myhome.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.lunifera.myhome.R;
import com.lunifera.myhome.listadapter.ThemeComposerSelectorArrayAdapter;
import com.lunifera.myhome.listadapter.ThemeViewerArrayAdapter;
import com.lunifera.myhome.model.Endpoint;
import com.lunifera.myhome.model.Model;
import com.lunifera.myhome.model.OnOffEndpoint;
import com.lunifera.myhome.model.RGBWEndpoint;
import com.lunifera.myhome.util.Common;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.jsontype.NamedType;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThemeComposerSelector extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton fab;


    Endpoint[] availableEndpoints = new Endpoint[]{};
    public static List<Endpoint> selected = new ArrayList<>();
    private ThemeComposerSelectorArrayAdapter sAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selector);
        fab = (FloatingActionButton) findViewById(R.id.fab_theme_composer_selector);
        fab.setOnClickListener(getFabOnClickListener());
        SharedPreferences settings = getSharedPreferences(Common.PREFS_FILE, 0);
        String uri = settings.getString(Common.PREFS_URI, null);
        /* uri = "http://jsonplaceholder.typicode.com/posts"; */
        uri = "http://10.0.2.2:8080/restdummy/Endpoints/getAllEndpoints";
        if (uri != null) {
            Snackbar.make(findViewById(R.id.themeComposerSelectorMca), uri, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            new JsonThread().execute(uri);
        }
    }


    public void getAvailableEndpoints(String uri){
        try {
            JsonParser parser = new JsonFactory().createJsonParser(new URL(uri));
            Model m = new ObjectMapper().readValue(parser, Model.class);
            Log.d("Success", "hello");

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private View.OnClickListener getFabOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemeComposerSelector.this, ThemeComposerEditor.class);
                intent.putExtra("selected",(Serializable) selected);
                startActivity(intent);
            }
        };
    }

    class JsonThread extends AsyncTask<String, Void, Model> {
        @Override
        protected Model doInBackground(String... params) {
            Model m = null;
            try {
                JsonParser parser = new JsonFactory().createJsonParser(new URL(params[0]));
                ObjectMapper om = new ObjectMapper();
                om.registerSubtypes(
                        new NamedType(OnOffEndpoint.class, "OnOffEndpoint"),
                        new NamedType(RGBWEndpoint.class, "RGBWEndpoint"));
                om.configure(SerializationConfig.Feature.AUTO_DETECT_FIELDS, true);
                m = om.readValue(parser, Model.class);
                return m != null ? m : new Model();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return m != null ? m : new Model();
        }

        @Override
        protected void onPostExecute(Model m) {
            listView.setAdapter(new ThemeComposerSelectorArrayAdapter(ThemeComposerSelector.this, R.layout.themecomposerselector_listview, m.getEndpoints().toArray(new Endpoint[m.getEndpoints().size()])));
        }
    }

}
