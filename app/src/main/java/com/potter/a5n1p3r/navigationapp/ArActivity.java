package com.potter.a5n1p3r.navigationapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ArActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        mToolbar=(Toolbar)findViewById(R.id.nav_actionbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.activity_ar);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Open,R.string.Close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        //actionbar
        int id = item.getItemId();
        if (id == R.id.nav_botany) {
            Toast.makeText(getApplicationContext(), "botany.", Toast.LENGTH_SHORT).show();   // Toast might be not display this is for any other action
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            return true;
        }else if (id==R.id.nav_map){
            Toast.makeText(getApplicationContext(),"Map",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            return true;
        }else if (id==R.id.nav_ar){
            Toast.makeText(getApplicationContext(),"Augmented reality",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, ArActivity.class);
//            startActivity(intent);
            return true;
        }else if (id==R.id.nav_qr){
            Toast.makeText(getApplicationContext(),"Quick Response",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QrActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
// Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_botany) {
            // Handle the camera action
            Toast.makeText(getApplicationContext(),"Quick nav_botany",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_map) {
            Toast.makeText(getApplicationContext(),"Quick nav_map",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_ar) {
            Toast.makeText(getApplicationContext(),"Quick nav_ar",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, ArActivity.class);
//            startActivity(intent);
        } else if (id == R.id.nav_qr) {
            Toast.makeText(getApplicationContext(),"Quick nav_qr",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QrActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(getApplicationContext(),"Quick nav_manage",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ManageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_favorit) {
            Toast.makeText(getApplicationContext(),"Quick nav_favorite",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FavoritActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_ar);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
