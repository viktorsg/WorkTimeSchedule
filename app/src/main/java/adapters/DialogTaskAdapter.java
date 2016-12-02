package adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import activities.R;
import classes.Task;

public class DialogTaskAdapter extends BaseAdapter {

    private Activity mContext;

    private List<Task> mTaskList;

    public DialogTaskAdapter(Activity context, List<Task> employeesList) {
        this.mContext = context;
        this.mTaskList = employeesList;
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
            convertView = inflater.inflate(R.layout.task_list_item, viewGroup, false);
        }

        TextView taskTextview = (TextView) convertView.findViewById(R.id.task_Text_View);
        taskTextview.setText(mTaskList.get(position).getName());

        return convertView;
    }
}
