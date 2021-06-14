package objectClasses;
import java.time.LocalDateTime;

public class Besuche {
    private LocalDateTime start_Date;
    private LocalDateTime end_Date;
    private int person_id;
    private int location_id;

    public Besuche(LocalDateTime start_Date, LocalDateTime end_Date, int person_id, int location_id) {
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.person_id = person_id;
        this.location_id = location_id;
    }

    public boolean isOverlapping(LocalDateTime start, LocalDateTime end) {
        return ((null == end) || this.start_Date.isBefore(end)) && ((null == this.end_Date) || start.isBefore(this.end_Date));
    }

    public LocalDateTime getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(LocalDateTime start_Date) {
        this.start_Date = start_Date;
    }

    public LocalDateTime getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(LocalDateTime end_Date) {
        this.end_Date = end_Date;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
}
