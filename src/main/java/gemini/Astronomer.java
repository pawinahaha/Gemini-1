package edu.gemini;

import edu.gemini.model.AbstractAstronomer;

public class Astronomer extends AbstractAstronomer {
    public Astronomer() {
        super();
    }

    public Astronomer(String firstName, String lastName, String address, String email, int id, String institution) {
        super(firstName, lastName, address, email, id, institution);
    }

    // Convenience constructor (backward compatible)
    public Astronomer(int id, String firstName, String lastName, String institution) {
        super();
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setInstitution(institution);
    }
}