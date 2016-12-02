package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import activities.R;
import classes.Employee;
import classes.Task;


public class ReferenceAdapter extends BaseAdapter {

    private Activity mContext;

    private List<Task> mTaskList;
    private List<Employee> mEmployeeList;

    public ReferenceAdapter(Activity context, List<Task> taskList, List<Employee> employeeList) {
        this.mContext = context;
        this.mTaskList = taskList;
        this.mEmployeeList = employeeList;
    }

    @Override
    public int getCount() {
        if(mTaskList != null) {
            return mTaskList.size();
        }

        return mEmployeeList.size();
    }

    @Override
    public Object getItem(int position) {
        if(mTaskList != null) {
            return mTaskList.get(position);
        }

        return mEmployeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.reference_list_item, viewGroup, false);
        }

        TextView referenceTextView = (TextView) convertView.findViewById(R.id.reference_Text_View);
        if(mTaskList != null) {
            referenceTextView.setText(mTaskList.get(position).getName());
        } else {
            referenceTextView.setText(mEmployeeList.get(position).getFirstName() + " " + mEmployeeList.get(position).getLastName());
        }

        return convertView;
    }
}
