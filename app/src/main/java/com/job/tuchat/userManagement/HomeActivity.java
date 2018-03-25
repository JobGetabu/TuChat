package com.job.tuchat.userManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.job.tuchat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.home_button_alreadyhaveaccount)
    public void homeBtnAlreadyHaveAccClick(){
        Intent loginIntent= new Intent(this,LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnClick(R.id.home_button_register)
    public void homeBtnRegisterClick(){
        Intent registerIntent= new Intent(this,RegisterActivity.class);
        startActivity(registerIntent);
    }
}
