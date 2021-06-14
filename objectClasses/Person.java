package objectClasses;

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
}
