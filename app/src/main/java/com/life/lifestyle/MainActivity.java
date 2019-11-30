package com.life.lifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.life.lifestyle.Data.StaticData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private calenFrag calender;
    private drawFrag draw;
    private billFrag bill;
    private todayY today;
    private modFrag mod;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calender = new calenFrag();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                getFragmentManager().beginTransaction().add(R.id.frame_content, calender).commitAllowingStateLoss();
            }
        }.start();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void undo(View view) {
        draw.undo();
    }

    public void redo(View view) {
        draw.redo();
    }

    public void clear(View view) {
        draw.clear();
    }

    public void erazer(View view) {
        draw.erazer();
    }

    public void save(View view) {
        draw.save();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (today != null) {
            return today.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void login(View view) {
        if (StaticData.isLogin()) {
            Intent intent = new Intent(MainActivity.this, ImformationActivity.class);
            startActivityForResult(intent,1);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent,1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.drawPainter) {
            if (draw == null) {
                draw = new drawFrag();
            }
            getFragmentManager().beginTransaction().replace(R.id.frame_content, draw).commitAllowingStateLoss();
        } else if (id == R.id.dateShe) {
            if (calender == null) {
                calender = new calenFrag();
            }
            getFragmentManager().beginTransaction().replace(R.id.frame_content, calender).commitAllowingStateLoss();

        } else if (id == R.id.billRec) {
            if (bill == null) {
                bill = new billFrag();
            }
            getFragmentManager().beginTransaction().replace(R.id.frame_content, bill).commitAllowingStateLoss();


        } else if (id == R.id.todayY) {
            if (today == null) {
                today = new todayY();
            }
            getFragmentManager().beginTransaction().replace(R.id.frame_content, today).commitAllowingStateLoss();

        } else if (id == R.id.modHol) {
            if (mod == null) {
                mod = new modFrag();
            }
            getFragmentManager().beginTransaction().replace(R.id.frame_content, mod).commitAllowingStateLoss();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            switch (resultCode)
            {
                case -1:Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();break;
                case 1:Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();break;
                case -2:Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();break;
                case 2:Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();break;
            }
        }
    }
}

