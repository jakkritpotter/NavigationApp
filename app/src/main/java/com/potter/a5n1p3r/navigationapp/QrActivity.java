package com.potter.a5n1p3r.navigationapp;

import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class QrActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    private Button scan_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        mToolbar=(Toolbar)findViewById(R.id.nav_actionbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.activity_qr);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Open,R.string.Close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        scan_btn = (Button) findViewById(R.id.btnScan);
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("สแกน QR Code พรรณไม้");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_actionbar, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        //actionbar
        int id = item.getItemId();
        if (id == R.id.nav_botany) {
            Toast.makeText(getApplicationContext(), "Botany", Toast.LENGTH_SHORT).show();   // Toast might be not display this is for any other action
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
            Intent intent = new Intent(this, ArActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            return true;
        }else if (id==R.id.nav_qr){
            Toast.makeText(getApplicationContext(),"Quick Response",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, QrActivity.class);
//            startActivity(intent);
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
            Intent intent = new Intent(this, ArActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_qr) {
            Toast.makeText(getApplicationContext(),"Quick nav_qr",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, QrActivity.class);
//            startActivity(intent);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_qr);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
