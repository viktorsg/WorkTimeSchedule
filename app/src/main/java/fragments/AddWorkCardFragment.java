package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import SQLite.SQLite;
import activities.R;

public class AddWorkCardFragment extends Fragment {

    private SQLite mDb;

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

    }

    private void initUIListeners() {

    }
}
