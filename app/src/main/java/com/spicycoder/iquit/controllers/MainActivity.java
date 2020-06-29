package com.spicycoder.iquit.controllers;

import android.os.Bundle;

import com.spicycoder.iquit.R;
import com.spicycoder.iquit.controllers.Interfaces.AddDataPassInterface;
import com.spicycoder.iquit.models.AddictionManager;
import com.spicycoder.iquit.models.AddictionUserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.danlew.android.joda.JodaTimeAndroid;

public class MainActivity extends AppCompatActivity implements AddDataPassInterface {
    FloatingActionButton fab;
    AddictionManager addictionManager;
    int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        JodaTimeAndroid.init(this);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.addFragment);
                fab.hide();
            }
        });

        addictionManager = new AddictionManager();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void passData(AddictionUserModel data) {
        Log.i("CheckInfo","Addiction passed "+data.getAddiction().getName());
        addictionManager.addNewAddiction(data);
    }

    @Override
    public void goToViewFragment(int index) {
        position = index;
        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment)
                .navigate(R.id.ViewFragment);
    }

    @Override
    public void goToEmergencyFragment(int index) {
        position = index;
        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment)
                .navigate(R.id.emergencyFragment);
    }


}
