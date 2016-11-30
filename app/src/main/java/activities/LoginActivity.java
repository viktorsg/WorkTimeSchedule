package activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import SQLite.SQLite;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLite mDb;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private TextView mWrongLoginTextView;

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

        mWrongLoginTextView = (TextView) findViewById(R.id.wrongLoginTextView);

        mLoginButton = (Button) findViewById(R.id.loginButton);
    }

    private void initUIListeners() {
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                int userId = mDb.checkUser(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
                if(userId != -1) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                } else {
                    mWrongLoginTextView.setVisibility(View.VISIBLE);
                    mUsernameEditText.setText("");
                    mPasswordEditText.setText("");

                    mUsernameEditText.clearFocus();
                    mPasswordEditText.clearFocus();
                }

                break;
        }
    }
}
