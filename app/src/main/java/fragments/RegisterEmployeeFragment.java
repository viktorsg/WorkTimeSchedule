package fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import activities.R;

import java.util.ArrayList;
import java.util.List;

import SQLite.SQLite;
import classes.Employee;


public class RegisterEmployeeFragment extends Fragment implements View.OnClickListener {

    private SQLite mDb;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mJobTitleEditText;

    private Button mAddEmployeeButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new SQLite(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_employee_layout, container, false);
        initUI(view);
        initUIListeners();

        return view;
    }

    private void initUI(View view) {
        mUsernameEditText = (EditText) view.findViewById(R.id.username_Edit_Text);
        mPasswordEditText = (EditText) view.findViewById(R.id.password_Edit_Text);
        mFirstNameEditText = (EditText) view.findViewById(R.id.first_Name_Edit_Text);
        mLastNameEditText = (EditText) view.findViewById(R.id.last_Name_Edit_Text);
        mJobTitleEditText = (EditText) view.findViewById(R.id.job_Title_Edit_Text);

        mAddEmployeeButton = (Button) view.findViewById(R.id.add_Employee_Button);
    }

    private void initUIListeners() {
        mAddEmployeeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        View focus = getActivity().getCurrentFocus();
        if (focus != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        switch (view.getId()) {
            case R.id.add_Employee_Button:
                List<String> errors = new ArrayList<>();
                if (mUsernameEditText.getText().toString().length() == 0) {
                    errors.add("Username");
                }

                if (mPasswordEditText.getText().toString().length() == 0) {
                    errors.add("Password");
                }

                if (mFirstNameEditText.getText().toString().length() == 0) {
                    errors.add("First name");
                }

                if (mLastNameEditText.getText().toString().length() == 0) {
                    errors.add("Last name");
                }

                if (mJobTitleEditText.getText().toString().length() == 0) {
                    errors.add("Job title");
                }

                if (errors.size() == 0) {
                    Employee employee = new Employee();
                    employee.setUsername(mUsernameEditText.getText().toString());
                    employee.setPassword(mPasswordEditText.getText().toString());
                    employee.setFirstName(mFirstNameEditText.getText().toString());
                    employee.setLastName(mLastNameEditText.getText().toString());
                    employee.setJobTitle(mJobTitleEditText.getText().toString());
                    final boolean added = mDb.addEmployee(employee);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Work Schedule")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(added) {
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(RegisterEmployeeFragment.this).commit();
                                    }
                                    dialogInterface.dismiss();
                                }
                            });
                    if(added) {
                        alertDialogBuilder.setMessage("Employee added successfully!");
                    } else {
                        alertDialogBuilder.setMessage("Error adding employee!");
                    }

                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Work Schedule")
                                      .setMessage(TextUtils.join(", ", errors) + " can not be empty!")
                                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialogInterface, int i) {
                                              dialogInterface.dismiss();
                                          }
                                      });

                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();
                }

                break;
        }
    }
}
