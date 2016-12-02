package fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import SQLite.SQLite;
import activities.MainActivity;
import activities.R;
import adapters.DialogEmployeeAdapter;
import adapters.DialogTaskAdapter;
import classes.Employee;
import classes.Task;
import classes.WorkCard;

public class AddWorkCardFragment extends Fragment implements View.OnClickListener {

    private SQLite mDb;

    private TextView mStartDateTextView;
    private TextView mEndDateTextView;
    private TextView mStartTimeTextView;
    private TextView mEndTimeTextView;
    private TextView mTaskTextView;

    private EditText mDescriptionEditText;

    private Button mAddWorkCardButton;

    private Task mSelectedTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new SQLite(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_workcard_layout, container, false);

        initUI(view);
        initUIListeners();

        return view;
    }

    private void initUI(View view) {
        mStartDateTextView = (TextView) view.findViewById(R.id.start_Date_Text_View);
        mEndDateTextView = (TextView) view.findViewById(R.id.end_Date_Text_View);
        mStartTimeTextView = (TextView) view.findViewById(R.id.start_Time_Text_View);
        mEndTimeTextView = (TextView) view.findViewById(R.id.end_Time_Text_View);
        mTaskTextView = (TextView) view.findViewById(R.id.task_Text_View);

        mDescriptionEditText = (EditText) view.findViewById(R.id.description_Edit_Text);

        mAddWorkCardButton = (Button) view.findViewById(R.id.add_card_Button);
    }

    private void initUIListeners() {
        mStartDateTextView.setOnClickListener(this);
        mEndDateTextView.setOnClickListener(this);
        mStartTimeTextView.setOnClickListener(this);
        mEndTimeTextView.setOnClickListener(this);
        mTaskTextView.setOnClickListener(this);

        mAddWorkCardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        View focus = getActivity().getCurrentFocus();
        if (focus != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        switch (view.getId()) {
            case R.id.start_Date_Text_View:
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        mStartDateTextView.setText(dayOfMonth + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;

            case R.id.end_Date_Text_View:
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        mEndDateTextView.setText(dayOfMonth + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;

            case R.id.start_Time_Text_View:
                c = Calendar.getInstance();
                int mHourOfDay = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hour = String.valueOf(hourOfDay);
                        String min = String.valueOf(minute);
                        if(hourOfDay < 10) {
                            hour = "0" + hour;
                        }
                        if(minute < 10) {
                            min = "0" + min;
                        }

                        mStartTimeTextView.setText(hour + "-" + min);
                    }
                }, mHourOfDay, mMinute, true);
                timePickerDialog.show();

                break;

            case R.id.end_Time_Text_View:
                c = Calendar.getInstance();
                mHourOfDay = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hour = String.valueOf(hourOfDay);
                        String min = String.valueOf(minute);
                        if(hourOfDay < 10) {
                            hour = "0" + hour;
                        }
                        if(minute < 10) {
                            min = "0" + min;
                        }

                        mEndTimeTextView.setText(hour + "-" + min);
                    }
                }, mHourOfDay, mMinute, true);
                timePickerDialog.show();

                break;

            case R.id.task_Text_View:
                final Dialog taskDialog = new Dialog(getActivity());
                taskDialog.setContentView(R.layout.tasks_dialog_layout);
                ListView tasksListView = (ListView) taskDialog.findViewById(R.id.tasks_List_View);
                final List<Task> tasksList = mDb.getAllTasks();
                tasksListView.setAdapter(new DialogTaskAdapter(getActivity(), tasksList));
                tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        mSelectedTask = tasksList.get(position);
                        mTaskTextView.setText(mSelectedTask.getName());

                        taskDialog.dismiss();
                    }
                });
                taskDialog.show();

                break;

            case R.id.add_card_Button:
                List<String> errors = new ArrayList<>();
                if (mStartDateTextView.getText().toString().length() == 0) {
                    errors.add("Start Date");
                }

                if (mEndDateTextView.getText().toString().length() == 0) {
                    errors.add("End Date");
                }

                if (mStartTimeTextView.getText().toString().length() == 0) {
                    errors.add("Start Time");
                }

                if (mEndTimeTextView.getText().toString().length() == 0) {
                    errors.add("End Time");
                }

                if (mTaskTextView.getText().toString().length() == 0) {
                    errors.add("Task");
                }

                if (mDescriptionEditText.getText().toString().length() == 0) {
                    errors.add("Description");
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                if (errors.size() == 0) {
                    WorkCard workCard = new WorkCard();
                    workCard.setStartTime(mStartTimeTextView.getText().toString() + " " + mStartDateTextView.getText().toString());
                    workCard.setEndTime(mEndTimeTextView.getText().toString() + " " + mEndDateTextView.getText().toString());
                    workCard.setTaskId(mSelectedTask.getId());
                    workCard.setEmployeeId(((MainActivity)getActivity()).mEmployee.getID());
                    workCard.setDescription(mDescriptionEditText.getText().toString());

                    final boolean added = mDb.addWorkCard(workCard);
                    alertDialogBuilder.setTitle("Work Schedule")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(added) {
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(AddWorkCardFragment.this).commit();
                                    }
                                    dialogInterface.dismiss();
                                }
                            });
                    if(added) {
                        alertDialogBuilder.setMessage("WorkCard added successfully!");
                    } else {
                        alertDialogBuilder.setMessage("Error adding workCard!");
                    }
                } else {
                    alertDialogBuilder.setTitle("Work Schedule")
                            .setMessage(TextUtils.join(", ", errors) + " can not be empty!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
        }
    }
}
