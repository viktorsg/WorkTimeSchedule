package adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import activities.R;
import classes.Employee;
import classes.Task;

public class EmployeeReferenceAdapter extends BaseAdapter {

    private Activity mContext;

    private List<Task> mTaskList;

    public EmployeeReferenceAdapter(Activity context, List<Task> taskList) {
        this.mContext = context;
        this.mTaskList = taskList;
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTaskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.employee_reference_list_item, viewGroup, false);
        }

        Task task = mTaskList.get(position);

        TextView taskNameTextView = (TextView) convertView.findViewById(R.id.task_Name_Text_View);
        taskNameTextView.setText(task.getName());

        TextView hoursTextView = (TextView) convertView.findViewById(R.id.task_Hours_Text_View);
        hoursTextView.setText("Hours: " + task.getHoursWorked());

        return convertView;
    }
}
