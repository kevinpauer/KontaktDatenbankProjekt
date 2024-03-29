package objectClasses;

import java.util.Objects;

public class Ort {
    private int location_id;
    private String location_name;
    private String in_door;

    public Ort(int id, String name, String inOrOut) {
        this.location_id = id;
        this.location_name = name;
        this.in_door = inOrOut;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getIn_door() {
        return in_door;
    }

    public void setIn_door(String in_door) {
        this.in_door = in_door;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ort ort = (Ort) o;
        return location_id == ort.location_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location_id);
    }
}
