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
import adapters.EmployeeReferenceAdapter;
import adapters.TaskReferenceAdapter;
import classes.Task;


public class EmployeeReferenceFragment extends Fragment {

    private SQLite mDb;

    private List<Task> mTasksList;

    private ListView mEmployeeReferenceListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new SQLite(getActivity());
        mTasksList = mDb.getTasksByEmployee(getArguments().getInt("employee"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.employee_reference_layout, container, false);

        initUI(view);

        mEmployeeReferenceListView.setAdapter(new EmployeeReferenceAdapter(getActivity(), mTasksList));

        return view;
    }

    private void initUI(View view) {
        mEmployeeReferenceListView = (ListView) view.findViewById(R.id.employee_reference_List_View);
    }
}
