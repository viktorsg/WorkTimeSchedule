package fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import activities.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import SQLite.SQLite;
import adapters.DialogEmployeeAdapter;
import classes.Employee;
import classes.Task;

public class AddTaskFragment extends Fragment implements View.OnClickListener {

    private SQLite mDb;

    private EditText mNameEditText;
    private EditText mDescriptionEditText;
    private EditText mProvidedHoursEditText;

    private TextView mStartDateTextView;
    private TextView mEndDateTextView;
    private TextView mLeaderTextView;

    private Button mAddTaskButton;

    private Employee mSelectedEmployee;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new SQLite(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_task_layout, container, false);

        initUI(view);
        initUIListeners();

        return view;
    }

    private void initUI(View view) {
        mNameEditText = (EditText) view.findViewById(R.id.name_Edit_Text);
        mDescriptionEditText = (EditText) view.findViewById(R.id.description_Edit_Text);
        mProvidedHoursEditText = (EditText) view.findViewById(R.id.hours_Edit_Text);

        mStartDateTextView = (TextView) view.findViewById(R.id.start_Date_Text_View);
        mEndDateTextView = (TextView) view.findViewById(R.id.end_Date_Text_View);
        mLeaderTextView = (TextView) view.findViewById(R.id.leader_Text_View);

        mAddTaskButton = (Button) view.findViewById(R.id.add_Task_Button);
    }

    private void initUIListeners() {
        mStartDateTextView.setOnClickListener(this);
        mEndDateTextView.setOnClickListener(this);
        mLeaderTextView.setOnClickListener(this);

        mAddTaskButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_Date_Text_View:
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        mStartDateTextView.setText(dayOfMonth + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                dialog.show();

                break;

            case R.id.end_Date_Text_View:
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        mEndDateTextView.setText(dayOfMonth + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                dialog.show();

                break;

            case R.id.leader_Text_View:
                final Dialog custom_dialog = new Dialog(getActivity());
                custom_dialog.setTitle("Employees");
                custom_dialog.setContentView(R.layout.employees_dialog_layout);
                ListView employeesListView = (ListView) custom_dialog.findViewById(R.id.employees_List_View);
                final List<Employee> employeeList = mDb.getAllEmployees();
                employeesListView.setAdapter(new DialogEmployeeAdapter(getActivity(), employeeList));
                employeesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        mSelectedEmployee = employeeList.get(position);
                        mLeaderTextView.setText(mSelectedEmployee.getFirstName() + " " + mSelectedEmployee.getLastName());

                        custom_dialog.dismiss();
                    }
                });

                custom_dialog.show();

                break;

            case R.id.add_Task_Button:
                View focus = getActivity().getCurrentFocus();
                if (focus != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                List<String> errors = new ArrayList<>();
                if (mNameEditText.getText().toString().length() == 0) {
                    errors.add("Name");
                }

                if (mDescriptionEditText.getText().toString().length() == 0) {
                    errors.add("Description");
                }

                if (mProvidedHoursEditText.getText().toString().length() == 0) {
                    errors.add("Provided Hours");
                }

                if (mStartDateTextView.getText().toString().length() == 0) {
                    errors.add("Start Date");
                }

                if (mEndDateTextView.getText().toString().length() == 0) {
                    errors.add("End Date");
                }

                if (mLeaderTextView.getText().toString().length() == 0) {
                    errors.add("Leader");
                }

                if (errors.size() == 0) {
                    Task task = new Task();
                    task.setName(mNameEditText.getText().toString());
                    task.setDescription(mDescriptionEditText.getText().toString());
                    task.setStartDate(mStartDateTextView.getText().toString());
                    task.setEndDate(mEndDateTextView.getText().toString());
                    task.setLeaderId(mSelectedEmployee.getID());
                    task.setProvidedHours(Integer.parseInt(mProvidedHoursEditText.getText().toString()));
                    task.setState("new");

                    final boolean added = mDb.addTask(task);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Work Schedule")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(added) {
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(AddTaskFragment.this).commit();
                                    }
                                    dialogInterface.dismiss();
                                }
                            });
                    if(added) {
                        alertDialogBuilder.setMessage("Task added successfully!");
                    } else {
                        alertDialogBuilder.setMessage("Error adding task!");
                    }

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

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

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                break;
        }
    }
}
