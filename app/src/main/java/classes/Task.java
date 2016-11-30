package classes;

import java.util.List;

public class Task {

    private int Id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private int leaderId;
    private List<Employee> taskEmployees;
    private int providedHours;
    private int currentHours;
    private String state;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public List<Employee> getTaskEmployees() {
        return taskEmployees;
    }

    public void setTaskEmployees(List<Employee> taskEmployees) {
        this.taskEmployees = taskEmployees;
    }

    public int getProvidedHours() {
        return providedHours;
    }

    public void setProvidedHours(int providedHours) {
        this.providedHours = providedHours;
    }

    public int getCurrentHours() {
        return currentHours;
    }

    public void setCurrentHours(int currentHours) {
        this.currentHours = currentHours;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
