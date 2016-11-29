package com.worktimeschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import SQLite.SQLite;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLite mDb;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDb = new SQLite(this);

        initUI();
        initUIListeners();
    }

    private void initUI() {
        mUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

        mLoginButton = (Button) findViewById(R.id.loginButton);
    }

    private void initUIListeners() {
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                boolean isUserRegistered = mDb.checkUser(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
                if(isUserRegistered) {

                } else {

                }

                break;
        }
    }
}
