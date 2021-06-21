package tests;

import logic.ReadFile;
import logic.SearchFunctionality;
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

public class searchPersonTest {

    @Test
    public void searchPersonTest(){
        ArrayList<Person> expectedPersonArray = new ArrayList<>();
        {
            expectedPersonArray.add(new Person(2, "Emily"));
            expectedPersonArray.add(new Person(4, "Emil"));
        }

        ArrayList<Person> dataArray = ReadFile.returnPerson(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db"));

        ArrayList<Person> resultPersonArray = SearchFunctionality.searchPerson("EmIl", dataArray);

        Assertions.assertArrayEquals(expectedPersonArray.toArray(), resultPersonArray.toArray());
    }

    @Test
    public void searchOrtTest(){
        ArrayList<Ort> expectedOrtArray = new ArrayList<>();
        {
            expectedOrtArray.add(new Ort(1, "Supermarkt", "in_door"));
            expectedOrtArray.add(new Ort(2, "Großmarkt", "in_door"));
        }

        ArrayList<Ort> dataArray = ReadFile.returnOrt(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db"));

        ArrayList<Ort> resultOrtArray = SearchFunctionality.searchOrt("MaRkT", dataArray);

        Assertions.assertArrayEquals(expectedOrtArray.toArray(), resultOrtArray.toArray());
    }

    @Test
    public void searchKontaktpersonenTest(){
        ArrayList<Person> expectedPersonArray = new ArrayList<>();

        {
            expectedPersonArray.add(new Person(2, "Emily"));
            expectedPersonArray.add(new Person(3, "Martha"));
        }

        ArrayList<Besuche> dataArrayBesuche = ReadFile.returnBesuch(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db"));
        ArrayList<Ort> dataArrayOrt = ReadFile.returnOrt(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db"));
        ArrayList<Person> dataArrayPerson = ReadFile.returnPerson(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db"));

        ArrayList<Person> resultPersonArray = SearchFunctionality.searchKontaktpersonen(1, dataArrayPerson, dataArrayBesuche, dataArrayOrt);

        Assertions.assertArrayEquals(expectedPersonArray.toArray(), resultPersonArray.toArray());
    }

    @Test
    public void searchBesucherTest(){

        ArrayList<Person> expectedPersonArray = new ArrayList<>();
        {
            expectedPersonArray.add(new Person(1, "Emiilia"));
            expectedPersonArray.add(new Person(2, "Emily"));
            expectedPersonArray.add(new Person(3, "Martha"));
        }

        ArrayList<Person> dataArrayPerson = ReadFile.returnPerson(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db"));
        ArrayList<Besuche> dataArrayBesuche = ReadFile.returnBesuch(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db"));
        ArrayList<Ort> dataArrayOrt = ReadFile.returnOrt(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\tests\\test.db"));

        ArrayList<Person> resultPersonArray = SearchFunctionality.searchBesucher(1, "2021-05-15T15:00:00", dataArrayPerson, dataArrayBesuche, dataArrayOrt);

        for (Person person : resultPersonArray) {
            System.out.println(person.getPerson_name());
        }

        Assertions.assertArrayEquals(expectedPersonArray.toArray(), resultPersonArray.toArray());
    }
}
