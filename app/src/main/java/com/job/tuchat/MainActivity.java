package com.job.tuchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.job.tuchat.adapter.TabsFragmentAdapter;
import com.job.tuchat.userManagement.HomeActivity;
import com.job.tuchat.userManagement.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.main_appbar)
    Toolbar mToolbar;
    @BindView(R.id.main_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    TabsFragmentAdapter tabsFragmentAdapter;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("TuChat");

        //firebase
        mAuth = FirebaseAuth.getInstance();

        tabsFragmentAdapter = new TabsFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(tabsFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();
        switch (id){
            case R.id.main_menu_logout :
                mAuth.signOut();
                sendToHome();
                break;
            case R.id.main_menu_account_settings:
                Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settingIntent);
                break;
                default:
                    return true;
        }
        return true;
    }

    private void sendToHome(){
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
   /*     Log.d(TAG,"onStart: "+currentUser.getDisplayName());*/
        if (currentUser == null){
            sendToHome();
        }
        //TODO init firebase real time listening
    }
}
