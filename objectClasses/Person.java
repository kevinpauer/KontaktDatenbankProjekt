package objectClasses;

import java.util.Objects;

public class Person {
    private int person_id;
    private String person_name;
    public Person(int id, String name) {
        this.person_id = id;
        this.person_name = name;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return person_id == person.person_id && Objects.equals(person_name, person.person_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person_id, person_name);
    }
}
