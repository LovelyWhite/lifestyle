package com.life.lifestyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.life.lifestyle.Data.*;

import java.util.Vector;


public class LoginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private ProgressBar logProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        logProgress = findViewById(R.id.logProgress);
    }

    public void log(View view) {
        logProgress.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                String ac = account.getText().toString();
                String pw = password.getText().toString();
                Vector<User> user = DataUtli.getUsers(new Tuple("UserID ", ac));
                if (user==null||user.isEmpty()||ac.isEmpty()||pw.isEmpty()) {
                    setResult(-1);//log fal
                } else {
                    if (user.get(0).getUserpassword().equals(pw)) {
                        StaticData.setIsLogin(true);
                        StaticData.setUsers(user.get(0));
                        setResult(1);//log succ
                    } else {
                        setResult(-1);//log fal
                    }
                }
                logProgress.setVisibility(View.INVISIBLE);
                finish();
            }

        }.start();
    }
    public void reg(View view) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String ac = account.getText().toString();
                String pw = password.getText().toString();
                User user = new User();
                user.setUserID(ac);
                user.setUserpassword(pw);
                if (DataUtli.addUsers(user)) {
                    StaticData.setUsers(user);
                    StaticData.setIsLogin(true);
                    setResult(2);//reg succ

                } else {
                    setResult(-2);//reg fal
                }
                finish();
            }

        }.start();
    }
}