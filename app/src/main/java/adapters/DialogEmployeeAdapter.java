package adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import activities.R;
import classes.Employee;

public class DialogEmployeeAdapter extends BaseAdapter {

    private Activity mContext;

    private List<Employee> mEmployeeList;

    public DialogEmployeeAdapter(Activity context, List<Employee> employeesList) {
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
            convertView = inflater.inflate(R.layout.employee_list_item, viewGroup, false);
        }

        TextView employeeTextView = (TextView) convertView.findViewById(R.id.employee_TextView);
        employeeTextView.setText(mEmployeeList.get(position).getFirstName() + " " + mEmployeeList.get(position).getLastName());

        return convertView;
    }
}
