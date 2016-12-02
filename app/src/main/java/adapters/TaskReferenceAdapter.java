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


public class TaskReferenceAdapter extends BaseAdapter {

    private Activity mContext;

    private List<Employee> mEmployeeList;

    public TaskReferenceAdapter(Activity context, List<Employee> employeesList) {
        this.mContext = context;
        this.mEmployeeList = employeesList;
    }

    @Override
    public int getCount() {
        return mEmployeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEmployeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.task_reference_list_item, viewGroup, false);
        }

        Employee employee = mEmployeeList.get(position);

        TextView employeeTextView = (TextView) convertView.findViewById(R.id.employee_Name_Text_View);
        employeeTextView.setText(employee.getFirstName() + " " + employee.getLastName());

        TextView hoursTextView = (TextView) convertView.findViewById(R.id.hours_Text_View);
        hoursTextView.setText("Hours: " + employee.getTaskHours());

        return convertView;
    }
}
