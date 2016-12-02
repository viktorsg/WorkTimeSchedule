package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;

import classes.Employee;
import classes.Task;
import classes.WorkCard;


public class SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WorkTimeSchedule.db";
    private static final int DATABASE_VERSION = 1;

    /*******************Employees*******************/
    private static final String EMPLOYEES_TABLE = "employees";
    private static final String COLUMN_EMPLOYEE_ID = "employeeId";
    private static final String COLUMN_EMPLOYEE_USERNAME = "employeeUsername";
    private static final String COLUMN_EMPLOYEE_PASSWORD = "employeePassword";
    private static final String COLUMN_EMPLOYEE_FIRST_NAME = "employeeFirstName";
    private static final String COLUMN_EMPLOYEE_LAST_NAME = "employeeLastName";
    private static final String COLUMN_EMPLOYEE_JOB_TITLE = "employeeJobTitle";
    private static final String COLUMN_EMPLOYEE_LAST_ACTIVE = "employeeLastActive";

    /*******************Work Cards*******************/
    private static final String WORK_CARDS_TABLE = "workCards";
    private static final String COLUMN_CARD_ID = "cardId";
    private static final String COLUMN_CARD_START_TIME = "cardStartTime";
    private static final String COLUMN_CARD_END_TIME = "cardEndTime";
    private static final String COLUMN_CARD_TASK_ID = "cardTaskId";
    private static final String COLUMN_CARD_EMPLOYEE_ID = "cardEmployeeId";
    private static final String COLUMN_CARD_DESCRIPTION = "cardDescription";

    /*******************Work Tasks*******************/
    private static final String WORK_TASKS_TABLE = "tasks";
    private static final String COLUMN_TASK_ID = "taskId";
    private static final String COLUMN_TASK_NAME = "taskName";
    private static final String COLUMN_TASK_DESCRIPTION = "taskDescription";
    private static final String COLUMN_TASK_START_DATE = "taskStartTime";
    private static final String COLUMN_TASK_END_DATE = "taskEndTime";
    private static final String COLUMN_TASK_LEADER_ID = "leaderId";
    private static final String COLUMN_TASK_PROVIDED_WORK_HOURS = "totalWorkHours";
    private static final String COLUMN_TASK_CURRENT_WORK_HOURS = "currentWorkHours";
    private static final String COLUMN_TASK_STATE = "state";

    /*******************Task Executors*******************/
    private static final String TASK_EXECUTORS_TABLE = "taskExecutors";
    private static final String COLUMN_EXECUTOR_TASK_ID = "executorTaskId";
    private static final String COLUMN_EXECUTOR_ID = "executorId";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EMPLOYEES_TABLE  + " (" + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMPLOYEE_USERNAME + " TEXT, "
                + COLUMN_EMPLOYEE_PASSWORD + " TEXT, " + COLUMN_EMPLOYEE_FIRST_NAME + " TEXT, " + COLUMN_EMPLOYEE_LAST_NAME + " TEXT, " + COLUMN_EMPLOYEE_JOB_TITLE + " TEXT, " +
                COLUMN_EMPLOYEE_LAST_ACTIVE + " TEXT)");

        db.execSQL("CREATE TABLE " + WORK_CARDS_TABLE + " (" + COLUMN_CARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CARD_START_TIME + " TEXT, "
                + COLUMN_CARD_END_TIME + " TEXT, " + COLUMN_CARD_EMPLOYEE_ID + " INTEGER, " + COLUMN_CARD_TASK_ID + " INTEGER, " + COLUMN_CARD_DESCRIPTION + " TEXT, FOREIGN KEY("
                + COLUMN_CARD_EMPLOYEE_ID + ") REFERENCES " + EMPLOYEES_TABLE + "(" + COLUMN_EMPLOYEE_ID + "), FOREIGN KEY("+ COLUMN_CARD_TASK_ID + ") REFERENCES "
                + WORK_TASKS_TABLE + "(" + COLUMN_TASK_ID + "))");

        db.execSQL("CREATE TABLE " + WORK_TASKS_TABLE + " (" + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_TASK_NAME + " TEXT, " + COLUMN_TASK_DESCRIPTION + " TEXT, "
                + COLUMN_TASK_START_DATE + " TEXT, " + COLUMN_TASK_END_DATE + " TEXT, " + COLUMN_TASK_LEADER_ID + " INTEGER, "
                + COLUMN_TASK_PROVIDED_WORK_HOURS + " INTEGER, " + COLUMN_TASK_CURRENT_WORK_HOURS + " INTEGER, " + COLUMN_TASK_STATE + " INTEGER, FOREIGN KEY("
                + COLUMN_TASK_LEADER_ID + ") REFERENCES " + EMPLOYEES_TABLE + "(" + COLUMN_EMPLOYEE_ID + "))");

        db.execSQL("CREATE TABLE " + TASK_EXECUTORS_TABLE + " (" + COLUMN_EXECUTOR_TASK_ID + " INTEGER, " + COLUMN_EXECUTOR_ID + " INTEGER, FOREIGN KEY (" + COLUMN_EXECUTOR_TASK_ID
                + ") REFERENCES " + WORK_TASKS_TABLE + "(" + COLUMN_TASK_ID + "), FOREIGN KEY (" + COLUMN_EXECUTOR_ID + ") REFERENCES " + EMPLOYEES_TABLE + "("
                + COLUMN_EMPLOYEE_ID + "))");

        db.execSQL("INSERT INTO " + EMPLOYEES_TABLE + " (" + COLUMN_EMPLOYEE_USERNAME + ", " + COLUMN_EMPLOYEE_PASSWORD
                + ", " + COLUMN_EMPLOYEE_FIRST_NAME + ", " + COLUMN_EMPLOYEE_LAST_NAME + ", " + COLUMN_EMPLOYEE_JOB_TITLE + ", "
                + COLUMN_EMPLOYEE_LAST_ACTIVE + ") VALUES ('admin', 'admin', 'Viktor', 'Georgiev', 'Administrator', null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORK_CARDS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORK_TASKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TASK_EXECUTORS_TABLE);
        onCreate(db);
    }

    public int checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_EMPLOYEE_ID + " FROM " + EMPLOYEES_TABLE + " WHERE " + COLUMN_EMPLOYEE_USERNAME + " = ? AND "
                + COLUMN_EMPLOYEE_PASSWORD + " = ?", new String[]{username, password});
        cursor.moveToFirst();
        if(cursor.getCount() == 1) {
            int userId = cursor.getInt(0);
            cursor.close();
            db.close();
            return userId;
        }

        cursor.close();
        db.close();
        return -1;
    }

    public boolean addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMPLOYEE_USERNAME, employee.getUsername());
        contentValues.put(COLUMN_EMPLOYEE_PASSWORD, employee.getPassword());
        contentValues.put(COLUMN_EMPLOYEE_FIRST_NAME, employee.getFirstName());
        contentValues.put(COLUMN_EMPLOYEE_LAST_NAME, employee.getLastName());
        contentValues.put(COLUMN_EMPLOYEE_JOB_TITLE, employee.getJobTitle());
        contentValues.put(COLUMN_EMPLOYEE_LAST_ACTIVE, employee.getLastActive());

        long row = db.insert(EMPLOYEES_TABLE, null, contentValues);
        return row != -1;
    }

    public Employee getEmployee(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EMPLOYEES_TABLE + " WHERE " + COLUMN_EMPLOYEE_ID + " = ?", new String[]{String.valueOf(userId)});
        cursor.moveToFirst();

        Employee employee = new Employee();
        employee.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_ID)));
        employee.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_USERNAME)));
        employee.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_PASSWORD)));
        employee.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_FIRST_NAME)));
        employee.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_LAST_NAME)));
        employee.setJobTitle(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_JOB_TITLE)));
        employee.setLastActive(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_LAST_ACTIVE)));

        cursor.close();
        db.close();

        return employee;
    }

    public List<Employee> getAllEmployees() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Employee> employeeList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EMPLOYEES_TABLE + " WHERE " + COLUMN_EMPLOYEE_JOB_TITLE + " NOT LIKE ?", new String[]{"Administrator"});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String jobTitle = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_JOB_TITLE));
            Employee employee = new Employee();

            employee.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_ID)));
            employee.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_USERNAME)));
            employee.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_PASSWORD)));
            employee.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_FIRST_NAME)));
            employee.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_LAST_NAME)));
            employee.setJobTitle(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_JOB_TITLE)));
            employee.setLastActive(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_LAST_ACTIVE)));

            employeeList.add(employee);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return employeeList;
    }

    public boolean addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK_NAME, task.getName());
        contentValues.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        contentValues.put(COLUMN_TASK_START_DATE, task.getStartDate());
        contentValues.put(COLUMN_TASK_END_DATE, task.getEndDate());
        contentValues.put(COLUMN_TASK_LEADER_ID, task.getLeaderId());
        contentValues.put(COLUMN_TASK_PROVIDED_WORK_HOURS, task.getProvidedHours());
        contentValues.put(COLUMN_TASK_STATE, task.getState());

        long row = db.insert(WORK_TASKS_TABLE, null, contentValues);
        return row != -1;
    }

    public List<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> tasksList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + WORK_TASKS_TABLE  + " WHERE " + COLUMN_TASK_STATE + " = ? OR " + COLUMN_TASK_STATE + " = ?",
                                    new String[]{String.valueOf(Task.NEW), String.valueOf(Task.STARTED)});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Task task = new Task();

            task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
            task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
            task.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_START_DATE)));
            task.setEndDate(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_END_DATE)));
            task.setLeaderId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_LEADER_ID)));
            task.setProvidedHours(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_PROVIDED_WORK_HOURS)));
            task.setCurrentHours(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CURRENT_WORK_HOURS)));
            task.setState(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_STATE)));

            tasksList.add(task);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return tasksList;
    }

    public boolean addWorkCard(WorkCard workCard) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CARD_START_TIME, workCard.getStartTime());
        contentValues.put(COLUMN_CARD_END_TIME, workCard.getEndTime());
        contentValues.put(COLUMN_CARD_TASK_ID, workCard.getTaskId());
        contentValues.put(COLUMN_CARD_EMPLOYEE_ID, workCard.getEmployeeId());
        contentValues.put(COLUMN_CARD_DESCRIPTION, workCard.getDescription());

        long row = db.insert(WORK_CARDS_TABLE, null, contentValues);
        if(row != -1) {
            db.execSQL("UPDATE " + EMPLOYEES_TABLE + " SET " + COLUMN_EMPLOYEE_LAST_ACTIVE + " = '" + workCard.getEndTime() + "' WHERE " + COLUMN_EMPLOYEE_ID
                    + " = ?", new String[]{String.valueOf(workCard.getEmployeeId())});

            Cursor cursor = db.rawQuery("SELECT * FROM " + WORK_TASKS_TABLE + " WHERE " + COLUMN_TASK_ID + " = ?", new String[]{String.valueOf(workCard.getTaskId())});
            cursor.moveToFirst();

            int providedHours = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_PROVIDED_WORK_HOURS));
            int currentHours = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CURRENT_WORK_HOURS));

            try {
                SimpleDateFormat format = new SimpleDateFormat("HH-mm d-MM-yyyy", Locale.getDefault());
                Date beginDate = format.parse(workCard.getStartTime());
                Date endDate = format.parse(workCard.getEndTime());

                long mills = endDate.getTime() - beginDate.getTime();
                long hours = mills / (1000 * 60 * 60);

                if(currentHours == 0 && hours > 0) {
                    db.execSQL("UPDATE " + WORK_TASKS_TABLE + " SET " + COLUMN_TASK_STATE + " = " + Task.STARTED + " WHERE " + COLUMN_TASK_ID
                            + " = ?", new String[]{String.valueOf(workCard.getTaskId())});
                }

                currentHours += hours;
                if(currentHours >= providedHours) {
                    db.execSQL("UPDATE " + WORK_TASKS_TABLE + " SET " + COLUMN_TASK_STATE + " = " + Task.COMPLETED + " WHERE " + COLUMN_TASK_ID
                            + " = ?", new String[]{String.valueOf(workCard.getTaskId())});
                }

                db.execSQL("UPDATE " + WORK_TASKS_TABLE + " SET " + COLUMN_TASK_CURRENT_WORK_HOURS + " = " + currentHours + " WHERE " + COLUMN_TASK_ID
                        + " = ?", new String[]{String.valueOf(workCard.getTaskId())});
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cursor = db.rawQuery("SELECT * FROM " + TASK_EXECUTORS_TABLE + " WHERE " + COLUMN_EXECUTOR_TASK_ID + "= ? AND "
                                 + COLUMN_EXECUTOR_ID + " = ?", new String[]{String.valueOf(workCard.getTaskId()), String.valueOf(workCard.getEmployeeId())});
            if(cursor.getCount() == 0) {
                contentValues = new ContentValues();
                contentValues.put(COLUMN_EXECUTOR_TASK_ID, workCard.getTaskId());
                contentValues.put(COLUMN_EXECUTOR_ID, workCard.getEmployeeId());
                row = db.insert(TASK_EXECUTORS_TABLE, null, contentValues);
            }

            cursor.close();

            return row != -1;
        }

        return false;
    }
}
