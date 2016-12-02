package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import SQLite.SQLite;
import activities.R;
import adapters.TaskReferenceAdapter;
import classes.Employee;

public class TasksReferenceFragment extends Fragment {

    private SQLite mDb;

    private List<Employee> mEmployeeList;

    private ListView mTaskReferenceListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new SQLite(getActivity());
        mEmployeeList = mDb.getEmployeesByTask(getArguments().getInt("task"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.task_reference_layout, container, false);

        initUI(view);

        mTaskReferenceListView.setAdapter(new TaskReferenceAdapter(getActivity(), mEmployeeList));

        return view;
    }

    private void initUI(View view) {
        mTaskReferenceListView = (ListView) view.findViewById(R.id.task_reference_List_View);
    }
}
