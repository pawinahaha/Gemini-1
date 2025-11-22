package edu.gemini;

import edu.gemini.model.AbstractScienceObserver;

public class ScienceObserver extends AbstractScienceObserver {
    public ScienceObserver() {
        super();
    }

    public ScienceObserver(String firstName, String lastName, String address, String email, int id, String department) {
        super(firstName, lastName, address, email, id, department);
    }

    // Convenience constructor (backward compatible)
    public ScienceObserver(int id, String department) {
        super();
        setId(id);
        setDepartment(department);
    }
}