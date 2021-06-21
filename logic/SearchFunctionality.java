package logic;

import objectClasses.Besuche;
import objectClasses.Ort;
import objectClasses.Person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class SearchFunctionality {
    public static ArrayList<Person> searchPerson(String s, ArrayList<Person> dataArray) {
        ArrayList<Person> resultArray = new ArrayList<>();

        //Liste von Personen, welche diesen String beinhalten, erzeugen
        for (Person person : dataArray) {
            if (person.getPerson_name().toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                resultArray.add(person);
            }
        }
        return resultArray;
    }

    public static ArrayList<Ort> searchOrt(String s, ArrayList<Ort> dataArray) {
        ArrayList<Ort> resultArray = new ArrayList<>();

        //Liste von Orten, welche diesen String beinhalten, erzeugen
        for (Ort ort : dataArray) {
            if (ort.getLocation_name().toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                resultArray.add(ort);
            }
        }
        return resultArray;
    }

    private static boolean searchIfOrtIsInDoors(int id, ArrayList<Ort> dataArray) {
        for (Ort ort : dataArray) {
            if (ort.getLocation_id() == id) {
                return ort.getIn_door().equals("in_door");
            }
        }
        return false;
    }

    private static Person searchPersonByID(int id, ArrayList<Person> dataArray) {
        for (Person person : dataArray) {
            if (person.getPerson_id() == id) {
                return person;
            }
        }
        return null;
    }

    private static ArrayList<Person> removeDuplicates(ArrayList<Person> list){
        ArrayList<Person> newList = new ArrayList<>();
        for (Person element: list){
            if (!newList.contains(element)){
                newList.add(element);
            }
        }
        return newList;
    }

    public static ArrayList<Person> searchKontaktpersonen(int personenId, ArrayList<Person> personDataArray, ArrayList<Besuche> alleBesucheDataArray, ArrayList<Ort> orteDataArray) {
        ArrayList<Person> resultArray = new ArrayList<>();
        ArrayList<Besuche> besuchteOrte = new ArrayList<>();

        //get list Besucher
        for (Besuche value : alleBesucheDataArray) {
            if (value.getPerson_id() == personenId) {
                besuchteOrte.add(value);
            }
        }

        if (besuchteOrte.size() == 0) {
            return resultArray;
        } else {
            for (int i = 0; i < besuchteOrte.size(); i++) {
                //checks if location is indoors
                if (searchIfOrtIsInDoors(besuchteOrte.get(i).getLocation_id(), orteDataArray)) {
                    for (int j = 0; j < alleBesucheDataArray.size(); j++) {
                        //checks if two people have the same location
                        if (alleBesucheDataArray.get(j).getLocation_id() == besuchteOrte.get(i).getLocation_id()) {
                            if (besuchteOrte.get(i).isOverlapping(alleBesucheDataArray.get(j).getStart_Date(), alleBesucheDataArray.get(j).getEnd_Date())){
                                if (alleBesucheDataArray.get(j).getPerson_id() != personenId){
                                    resultArray.add(searchPersonByID(alleBesucheDataArray.get(j).getPerson_id(), personDataArray));
                                }
                            }
                        }
                    }
                }
            }
        }

        resultArray.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getPerson_name().compareTo(o2.getPerson_name());
            }
        });
        resultArray = removeDuplicates(resultArray);

        return resultArray;
    }

    private static boolean isInTimePeriod(LocalDateTime start, LocalDateTime end, LocalDateTime inputTime){
        return start.isBefore(inputTime) && end.isAfter(inputTime) || end.equals(inputTime)|| start.equals(inputTime);
    }

    public static ArrayList<Person> searchBesucher(int ortId, String timeInput, ArrayList<Person> personDataArray,
                                                   ArrayList<Besuche> alleBesucheDataArray, ArrayList<Ort> orteDataArray) {
        ArrayList<Person> resultArray = new ArrayList<>();
        ArrayList<Person> tempArray = new ArrayList<>();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("[yyyy-MM-dd'T'HH:mm:ss][yyyy-MM-dd'T'HH:mm]", Locale.ENGLISH);

        LocalDateTime timeFromInput = LocalDateTime.parse(timeInput.replaceAll("^\"+|\"+$", ""), formatter);

        Besuche besuchZumVergleichen = new Besuche(timeFromInput, timeFromInput, 0, ortId);

        for (int i = 0; i < alleBesucheDataArray.size(); i++) {
            //checks if id is present in orteArray
            if (ortId == alleBesucheDataArray.get(i).getLocation_id()) {
                //checks if both ids are indoor
                if (searchIfOrtIsInDoors(ortId, orteDataArray)) {
                    if (isInTimePeriod(alleBesucheDataArray.get(i).getStart_Date(), alleBesucheDataArray.get(i).getEnd_Date(), timeFromInput)){
                        tempArray = searchKontaktpersonen(alleBesucheDataArray.get(i).getPerson_id(), personDataArray, alleBesucheDataArray, orteDataArray);
                        resultArray.addAll(tempArray);
                        resultArray.add(searchPersonByID(alleBesucheDataArray.get(i).getPerson_id(), personDataArray));
                    }
                } else {
                    if (isInTimePeriod(alleBesucheDataArray.get(i).getStart_Date(), alleBesucheDataArray.get(i).getEnd_Date(), timeFromInput)){
                        resultArray.add(searchPersonByID(alleBesucheDataArray.get(i).getPerson_id(), personDataArray));
                    }
                }
            }
        }

        resultArray = removeDuplicates(resultArray);

        resultArray.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getPerson_name().compareTo(o2.getPerson_name());
            }
        });

        return resultArray;
    }
}