package edu.gemini.model;

public abstract class AbstractScienceObserver extends Person {
    private int id;
    private String department;

    public AbstractScienceObserver() {
        super();
    }

    public AbstractScienceObserver(String firstName, String lastName, String address, String email, int id, String department) {
        super(firstName, lastName, address, email);
        this.id = id;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}