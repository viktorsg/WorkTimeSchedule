package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import SQLite.SQLite;
import activities.MainActivity;
import activities.R;
import adapters.ReferenceAdapter;
import classes.Employee;
import classes.Task;


public class ReferencesListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private SQLite mDb;

    private String mReference;

    private ListView mReferencesListView;

    private List<Task> mTaskList;
    private List<Employee> mEmployeeList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new SQLite(getActivity());

        if(getArguments() != null) {
            mReference = getArguments().getString("reference");
            if(mReference != null) {
                if(mReference.equalsIgnoreCase("task")) {
                    mTaskList = mDb.getAllTasks();
                } else if(mReference.equalsIgnoreCase("employee")) {
                    mEmployeeList = mDb.getAllEmployees();
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.references_list_layout, container, false);

        initUI(view);
        initUIListeners();

        mReferencesListView.setAdapter(new ReferenceAdapter(getActivity(), mTaskList, mEmployeeList));

        return view;
    }

    private void initUI(View view) {
        mReferencesListView = (ListView) view.findViewById(R.id.references_List_View);
    }

    private void initUIListeners() {
        mReferencesListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(mTaskList != null) {
            Task task = mTaskList.get(position);
            TasksReferenceFragment tasksReferenceFragment = new TasksReferenceFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("task", task.getId());
            tasksReferenceFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.frameLayout, tasksReferenceFragment).addToBackStack(null).commit();
        } else if (mEmployeeList != null) {
            Employee employee = mEmployeeList.get(position);
        }
    }
}
