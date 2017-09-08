package com.potter.a5n1p3r.navigationapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    //ตัวแปรของ View
    private Button btnAddBota;
    private ListView listBotany;
    //list ในกำรเก็บข้อมูลของ MemberData
    private ArrayList<BotanyModelData> listData = new ArrayList<BotanyModelData>();
    //ตัวจัดกำรฐำนข้อมูล
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        mToolbar=(Toolbar)findViewById(R.id.nav_actionbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.activity_manage);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Open,R.string.Close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnAddBota = (Button) findViewById(R.id.btnAddBota);

        listBotany = (ListView)findViewById(R.id.listBota);
        //สร้ำง Event ให้ปุ่ มเพิ่มข้อมูล
        btnAddBota.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ManageActivity.this, AddBotanyActivity.class);
                startActivity(intent);
            }
        });
        //สร้ำงตัวจัดกำรฐำนข้อมูล
        dbHelper = new DatabaseHelper(this);
        //น ำตัวจัดกำรฐำนข้อมูลมำใช้งำน
        database = dbHelper.getWritableDatabase();
        //แสดงรำยกำรสมำชิก
        showList();
    }
    private void showList() {
        //ดึงข้อมูลสมำชิกจำก SQLite Database
        getMember();
        //แสดงสมำชิกใน ListView
        listBotany.setAdapter(new AdapterListViewData(this,listData));
    }
    private void getMember() {
        //ท ำกำร Query ข้อมูลจำกตำรำง botany ใส่ใน Cursor
        Cursor mCursor = database.query(true, "botany", new String[] {
                        "id", "t_name_b", "s_name_b", "detail_b", "bs_b","image" }, null,
                null, null, null, null, null);
        //หรือใช้ Cursor mCursor = database.rawQuery("SELECT * FROM botany", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            listData.clear();
            //ถ้ำมีข้อมูลจะท ำกำรเก็บข้อมูลใส่ List เพื่อน ำไปแสดง
            if(mCursor.getCount() > 0){
                do {
                    int id = mCursor.getInt(mCursor.getColumnIndex("id"));
                    String t_name = mCursor.getString(mCursor.getColumnIndex("t_name_b"));
                    String s_name = mCursor.getString(mCursor.getColumnIndex("s_name_b"));
                    String detail_b = mCursor.getString(mCursor.getColumnIndex("detail_b"));
                    String bs_b = mCursor.getString(mCursor.getColumnIndex("bs_b"));
                    byte[] image = mCursor.getBlob(mCursor.getColumnIndex("image"));
                    listData.add(new BotanyModelData(id, t_name, s_name, detail_b,bs_b,image));
                }while (mCursor.moveToNext());
            }
        }
    }
    //Method แก้ไขข้อมูลใน SQLite
    public void editBotany(int id,String t_name,String s_name,String detail_b,String bs_b,byte[] image){
        //เตรียมค่ำต่ำงๆ เพื่อท ำกำรแก้ไข
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("t_name_b", t_name);
        values.put("s_name_b", s_name);
        values.put("detail_b", detail_b);
        values.put("bs_b", bs_b);
        values.put("image", image);
        //ให้ Database ท ำกำรแก้ไขข้อมูลที่ id ที่ก ำหนด
        database.update("botany", values, "id = ?", new String[] { ""+id });
        //แสดงข้อมูลล่ำสุด
        showList();
    }
    //Method ลบข้อมูลใน SQLite
    public void deleteBotany(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageActivity.this);
        builder.setTitle("Delete botay");
        builder.setMessage("Are you sure detele this botany?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                database.delete("botany", "id = " + id, null);
                Toast.makeText(ManageActivity.this, "Delete Data Id " + id + " Complete",
                        Toast.LENGTH_SHORT).show();
                showList();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
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
            Intent intent = new Intent(this, ArActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_qr) {
            Toast.makeText(getApplicationContext(),"Quick nav_qr",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QrActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(getApplicationContext(),"Quick nav_manage",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, ManageActivity.class);
//            startActivity(intent);
        } else if (id == R.id.nav_favorit) {
            Toast.makeText(getApplicationContext(),"Quick nav_favorite",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FavoritActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_manage);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
