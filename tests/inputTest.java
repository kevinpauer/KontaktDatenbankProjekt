package tests;

import logic.ReadFile;
import objectClasses.Besuche;
import objectClasses.Ort;
import objectClasses.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class inputTest {

    private final File f = new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db");

    @Test
    public void returnPersonTest(){
        ArrayList<Person> expectedPersonList = new ArrayList<>();
        {
            expectedPersonList.add(new Person(1, "Emiilia"));
            expectedPersonList.add(new Person(2, "Emily"));
            expectedPersonList.add(new Person(3, "Martha"));
            expectedPersonList.add(new Person(4, "Emil"));
        }

        ArrayList<Person> resultPersonArray = ReadFile.returnPerson(f);

        Assertions.assertArrayEquals(expectedPersonList.toArray(), resultPersonArray.toArray());
    }

    @Test
    public void returnOrtTest(){
        ArrayList<Ort> expectedOrtArray = new ArrayList<>();
        {
            expectedOrtArray.add(new Ort(1, "Supermarkt", "in_door"));
            expectedOrtArray.add(new Ort(2, "Großmarkt", "in_door"));
        }

        ArrayList<Ort> resultOrtArray = ReadFile.returnOrt(f);

        Assertions.assertArrayEquals(expectedOrtArray.toArray(), resultOrtArray.toArray());
    }

    @Test
    public void returnBesuchTest(){
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("[yyyy-MM-dd'T'HH:mm:ss][yyyy-MM-dd'T'HH:mm]", Locale.ENGLISH);

        ArrayList<Besuche> expectedBesucheArray = new ArrayList<>();
        {
            expectedBesucheArray.add(new Besuche(LocalDateTime.parse("2021-05-15T15:00:00", formatter),
                    LocalDateTime.parse("2021-05-15T16:00:00", formatter), 1, 1));
            expectedBesucheArray.add(new Besuche(LocalDateTime.parse("2021-05-15T14:00:00", formatter),
                    LocalDateTime.parse("2021-05-15T15:00:01", formatter), 2, 1));
            expectedBesucheArray.add(new Besuche(LocalDateTime.parse("2021-05-15T14:16:00", formatter),
                    LocalDateTime.parse("2021-05-15T15:15:00", formatter), 3, 1));
        }

        ArrayList<Besuche> resultBesucheArray = ReadFile.returnBesuch(f);

        Assertions.assertArrayEquals(expectedBesucheArray.toArray(), resultBesucheArray.toArray());
    }
}
