package logic;

import objectClasses.Besuche;
import objectClasses.Ort;
import objectClasses.Person;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//TODO refactor code, get rid of duplicates

public class ReadFile {
    public static ArrayList<Person> returnPerson(File f) {
        ArrayList<Person> ListeVonPersonen = new ArrayList<>();
        String[] lineSplit;
        boolean isFirstEntity = true;
        try {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("New_Entity: ") && isFirstEntity) {
                    isFirstEntity = false;
                } else if (line.contains("New_Entity: ") && !isFirstEntity) {
                    return ListeVonPersonen;
                } else {
                    lineSplit = line.split(",");
                    Person p = new Person(Integer.parseInt(lineSplit[0].replaceAll("^\"+|\"+$", "")), lineSplit[1].replaceAll("^\"+|\"+$", ""));
                    ListeVonPersonen.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ListeVonPersonen;
    }

    public static ArrayList<Ort> returnOrt(File f) {
        ArrayList<Ort> ListeVonOrten = new ArrayList<>();
        String[] lineSplit;
        int entity = 0;
        try {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("New_Entity: ")) {
                    entity++;
                    if (line.contains("New_Entity: ") && entity == 3) {
                        return ListeVonOrten;
                    }
                    continue;
                }
                if (entity == 2) {
                    lineSplit = line.split(",");
                    Ort o = new Ort(
                            Integer.parseInt(lineSplit[0].replaceAll("^\"+|\"+$", "")),
                            lineSplit[1].replaceAll("^\"+|\"+$", ""),
                            lineSplit[2].replaceAll("^\"+|\"+$", "")
                    );
                    ListeVonOrten.add(o);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ListeVonOrten;
    }

    public static ArrayList<Besuche> returnBesuch(File f) {
        ArrayList<Besuche> ListeVonBesuchen = new ArrayList<>();
        String[] lineSplit;
        int entity = 0;
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

        try {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("New_Entity: ")) {
                    entity++;
                    continue;
                }
                if (entity == 3) {
                    lineSplit = line.split(",");
                    Besuche b = new Besuche(
                            LocalDateTime.parse(lineSplit[0].replaceAll("^\"+|\"+$", "").toString(), formatter),
                            LocalDateTime.parse(lineSplit[1].replaceAll("^\"+|\"+$", "").toString(), formatter),
                            Integer.parseInt(lineSplit[2].replaceAll("^\"+|\"+$", "")),
                            Integer.parseInt(lineSplit[3].replaceAll("^\"+|\"+$", ""))
                    );
                    ListeVonBesuchen.add(b);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ListeVonBesuchen;
    }
}
