package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import SQLite.SQLite;
import activities.R;

public class ReferencesFragment extends Fragment implements View.OnClickListener {

    private SQLite mDb;

    private Button mReferenceByTaskButton;
    private Button mReferenceByEmployeeButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new SQLite(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.references_layout, container, false);

        initUI(view);
        initUIListeners();

        return view;
    }

    private void initUI(View view) {
        mReferenceByTaskButton = (Button) view.findViewById(R.id.reference_By_Task_Button);
        mReferenceByEmployeeButton = (Button) view.findViewById(R.id.reference_By_Employee_Button);
    }

    private void initUIListeners() {
        mReferenceByTaskButton.setOnClickListener(this);
        mReferenceByEmployeeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ReferencesListFragment referencesListFragment = new ReferencesListFragment();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.reference_By_Task_Button:
                bundle = new Bundle();
                bundle.putString("reference", "task");
                referencesListFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.frameLayout, referencesListFragment).addToBackStack(null).commit();
                break;

            case R.id.reference_By_Employee_Button:
                referencesListFragment = new ReferencesListFragment();
                bundle.putString("reference", "employee");
                referencesListFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.frameLayout, referencesListFragment).addToBackStack(null).commit();
                break;
        }
    }
}
