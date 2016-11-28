package com.worktimeschedule;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fragments.RegisterEmployeeFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button mRegisterEmployeeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initUIListeners();

    }

    private void initUI() {
        mRegisterEmployeeButton = (Button) findViewById(R.id.register_Employee);
    }

    private void initUIListeners() {
        mRegisterEmployeeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_Employee:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.frameLayout, new RegisterEmployeeFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
