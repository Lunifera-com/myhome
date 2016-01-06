package com.lunifera.myhome.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lunifera.myhome.R;
import com.lunifera.myhome.listadapter.ThemeViewerArrayAdapter;
import com.lunifera.myhome.model.Endpoint;
import com.lunifera.myhome.model.Model;
import com.lunifera.myhome.model.OnOffEndpoint;
import com.lunifera.myhome.model.RGBWEndpoint;
import com.lunifera.myhome.util.Common;
import com.lunifera.myhome.util.OnSwipeTouchListener;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.jsontype.NamedType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ThemeViewer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private Endpoint[] availableThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.listViewThemes);
        listView.setOnItemClickListener(createOnItemClicklistener());
        listView.setOnTouchListener(new OnSwipeTouchListener(ThemeViewer.this) {
            @Override
            public void onSwipeLeft() {
                Intent i = new Intent(ThemeViewer.this, EndpointViewer.class);
                startActivity(i);

            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return super.onTouch(view, motionEvent);
            }
        });

        SharedPreferences settings = getSharedPreferences(Common.PREFS_FILE, 0);
        String uri = settings.getString(Common.PREFS_URI, null);
        uri = "http://192.168.0.107:8080/myhome/Endpoints/getAllEndpoints";
        if (uri != null) {
            Snackbar.make(findViewById(R.id.drawer_layout), uri, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            new JsonThread().execute(uri);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.theme_viewer, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_action_create_theme) {
            Intent intent = new Intent(this, ThemeComposerSelector.class);
            startActivity(intent);
        } else if (id == R.id.menu_action_select_theme) {
//            Intent intent = new Intent(this, ThemeComposerSelector.class);
//            startActivity(intent);
        } else if (id == R.id.menu_action_select_endpoint) {
//            Intent intent = new Intent(this, ThemeComposerSelector.class);
//            startActivity(intent);
        } else if (id == R.id.menu_action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private AdapterView.OnItemClickListener createOnItemClicklistener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Endpoint e = (Endpoint) listView.getItemAtPosition(position);
                new AlertDialog.Builder(ThemeViewer.this)
                        .setTitle("(De)Activate Theme")
                        .setMessage("Are you sure you want to activate the Theme " + e.getName() + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
            listView.setAdapter(new ThemeViewerArrayAdapter(ThemeViewer.this, R.layout.themeviewer_listview, m.getEndpoints().toArray(new Endpoint[m.getEndpoints().size()])));
        }
    }
}