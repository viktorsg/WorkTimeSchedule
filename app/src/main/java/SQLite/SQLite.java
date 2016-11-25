package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import classes.Employee;


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


    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EMPLOYEES_TABLE  + " (" + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMPLOYEE_USERNAME + " TEXT, "
                + COLUMN_EMPLOYEE_PASSWORD + " TEXT, " + COLUMN_EMPLOYEE_FIRST_NAME + " TEXT, " + COLUMN_EMPLOYEE_LAST_NAME + " TEXT, " + COLUMN_EMPLOYEE_JOB_TITLE + " TEXT, " +
                COLUMN_EMPLOYEE_LAST_ACTIVE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEES_TABLE);
        onCreate(db);
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
}
