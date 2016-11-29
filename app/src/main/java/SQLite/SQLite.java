package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import classes.Employee;
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
    private static final String COLUMN_TASK_DESCRIPTION = "taskDescription";
    private static final String COLUMN_TASK_START_TIME = "taskStartTime";
    private static final String COLUMN_TASK_END_TIME = "taskEndTime";
    private static final String COLUMN_TASK_MANAGER = "manager";
    private static final String COLUMN_TASK_TOTAL_WORK_HOURS = "totalWorkHours";
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

        db.execSQL("CREATE TABLE " + WORK_CARDS_TABLE + " (" + COLUMN_CARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CARD_START_TIME + " DATETIME, "
                + COLUMN_CARD_END_TIME + " DATETIME, " + COLUMN_CARD_EMPLOYEE_ID + " INTEGER, " + COLUMN_CARD_TASK_ID + " INTEGER, " + COLUMN_CARD_DESCRIPTION + " TEXT, FOREIGN KEY("
                + COLUMN_CARD_EMPLOYEE_ID + ") REFERENCES " + EMPLOYEES_TABLE + "(" + COLUMN_EMPLOYEE_ID + "), FOREIGN KEY("+ COLUMN_CARD_TASK_ID + ") REFERENCES "
                + WORK_TASKS_TABLE + "(" + COLUMN_TASK_ID + "))");

        db.execSQL("CREATE TABLE " + WORK_TASKS_TABLE + " (" + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASK_DESCRIPTION + " TEXT, "
                + COLUMN_TASK_START_TIME + " DATETIME, " + COLUMN_TASK_END_TIME + " DATETIME, " + COLUMN_TASK_MANAGER + " INTEGER, "
                + COLUMN_TASK_TOTAL_WORK_HOURS + " INTEGER, " + COLUMN_TASK_CURRENT_WORK_HOURS + " INTEGER, " + COLUMN_TASK_STATE + " TEXT, FOREIGN KEY("
                + COLUMN_TASK_MANAGER + ") REFERENCES " + EMPLOYEES_TABLE + "(" + COLUMN_EMPLOYEE_ID + "))");

        db.execSQL("CREATE TABLE " + TASK_EXECUTORS_TABLE + " (" + COLUMN_EXECUTOR_TASK_ID + " INTEGER, " + COLUMN_EXECUTOR_ID + " INTEGER, FOREIGN KEY (" + COLUMN_EXECUTOR_TASK_ID
                + ") REFERENCES " + WORK_TASKS_TABLE + "(" + COLUMN_TASK_ID + "), FOREIGN KEY (" + COLUMN_EXECUTOR_ID + ") REFERENCES " + EMPLOYEES_TABLE + "("
                + COLUMN_EMPLOYEE_ID + "))");

        db.execSQL("INSERT INTO " + EMPLOYEES_TABLE + " (" + COLUMN_EMPLOYEE_USERNAME + ", " + COLUMN_EMPLOYEE_PASSWORD
                + ", " + COLUMN_EMPLOYEE_FIRST_NAME + ", " + COLUMN_EMPLOYEE_LAST_NAME + ", " + COLUMN_EMPLOYEE_JOB_TITLE + ", "
                + COLUMN_EMPLOYEE_LAST_ACTIVE + ") VALUES ('admin', 'admin', 'Viktor', 'Georgiev', 'Manager', null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORK_CARDS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORK_TASKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TASK_EXECUTORS_TABLE);
        onCreate(db);
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EMPLOYEES_TABLE + " WHERE " + COLUMN_EMPLOYEE_USERNAME + " = ? AND " + COLUMN_EMPLOYEE_PASSWORD + " = ?", new String[]{username, password});
        if(cursor.getCount() == 1) {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
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

    public boolean addWorkCard(WorkCard workCard) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CARD_START_TIME, workCard.getStartTime());
        contentValues.put(COLUMN_CARD_END_TIME, workCard.getEndTime());
        contentValues.put(COLUMN_CARD_TASK_ID, workCard.getCardTaskId());
        contentValues.put(COLUMN_CARD_EMPLOYEE_ID, workCard.getCardEmployeeId());
        contentValues.put(COLUMN_CARD_EMPLOYEE_ID, workCard.getCardEmployeeId());
        contentValues.put(COLUMN_CARD_DESCRIPTION, workCard.getCardDescription());

        long row = db.insert(WORK_CARDS_TABLE, null, contentValues);
        if(row != -1) {
            db.rawQuery("UPDATE " + EMPLOYEES_TABLE + " SET " + COLUMN_EMPLOYEE_LAST_ACTIVE + " = " + workCard.getEndTime() + " WHERE " + COLUMN_EMPLOYEE_ID
                    + " = ?", new String[]{String.valueOf(workCard.getCardEmployeeId())});
            return true;
        }

        return false;
    }
}
