package activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import SQLite.SQLite;
import activities.R;
import classes.Employee;
import fragments.AddTaskFragment;
import fragments.AddWorkCardFragment;
import fragments.ReferencesFragment;
import fragments.RegisterEmployeeFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private SQLite mDb;

    private RelativeLayout mRegisterRelativeLayout;
    private RelativeLayout mWorkCardRelativeLayout;
    private RelativeLayout mTaskRelativeLayout;
    private RelativeLayout mReferencesRelativeLayout;

    private Button mRegisterEmployeeButton;
    private Button mAddTaskButton;
    private Button mAddWorkCardButton;
    private Button mReferencesButton;

    public Employee mEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initUIListeners();

        mDb = new SQLite(this);
        mEmployee = mDb.getEmployee(getIntent().getExtras().getInt("userId"));
        if(mEmployee.getJobTitle().equalsIgnoreCase("Administrator")) {
            mWorkCardRelativeLayout.setVisibility(View.GONE);
        } else {
            mRegisterRelativeLayout.setVisibility(View.GONE);
            mTaskRelativeLayout.setVisibility(View.GONE);
            mReferencesRelativeLayout.setVisibility(View.GONE);
        }
    }

    private void initUI() {
        mRegisterRelativeLayout = (RelativeLayout) findViewById(R.id.register_Relative_Layout);
        mWorkCardRelativeLayout = (RelativeLayout) findViewById(R.id.work_Card_Relative_Layout);
        mTaskRelativeLayout = (RelativeLayout) findViewById(R.id.task_Relative_Layout);
        mReferencesRelativeLayout = (RelativeLayout) findViewById(R.id.references_Relative_Layout);

        mRegisterEmployeeButton = (Button) findViewById(R.id.register_Employee);
        mAddTaskButton = (Button) findViewById(R.id.add_Task_Button);
        mAddWorkCardButton = (Button) findViewById(R.id.add_workCard_Button);
        mReferencesButton = (Button) findViewById(R.id.references_Button);
    }

    private void initUIListeners() {
        mRegisterEmployeeButton.setOnClickListener(this);
        mAddTaskButton.setOnClickListener(this);
        mAddWorkCardButton.setOnClickListener(this);
        mReferencesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_Employee:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.frameLayout, new RegisterEmployeeFragment()).addToBackStack(null).commit();
                break;

            case R.id.add_Task_Button:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.frameLayout, new AddTaskFragment()).addToBackStack(null).commit();
                break;

            case R.id.add_workCard_Button:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.frameLayout, new AddWorkCardFragment()).addToBackStack(null).commit();
                break;

            case R.id.references_Button:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.frameLayout, new ReferencesFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
