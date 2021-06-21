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
    /**
     * support function to determine if ort is indoors
     * @param id Identification of an Ort object
     * @param dataArray List of type Ort
     * @return true if Ort is indoors
     */
    private static boolean searchIfOrtIsInDoors(int id, ArrayList<Ort> dataArray) {
        for (Ort ort : dataArray) {
            if (ort.getLocation_id() == id) {
                return ort.getIn_door().equals("in_door");
            }
        }
        return false;
    }

    /**
     * support function to get a Person via id
     * @param id Identification of Person
     * @param dataArray List of type Person
     * @return Person object with the specified id
     */
    private static Person searchPersonByID(int id, ArrayList<Person> dataArray) {
        for (Person person : dataArray) {
            if (person.getPerson_id() == id) {
                return person;
            }
        }
        return null;
    }

    /**
     * support function to get rid of duplicates in a ArrayList of Person
     * @param list List of type Person
     * @return List free of duplicates
     */
    private static ArrayList<Person> removeDuplicates(ArrayList<Person> list){
        ArrayList<Person> newList = new ArrayList<>();
        for (Person element: list){
            if (!newList.contains(element)){
                newList.add(element);
            }
        }
        return newList;
    }

    /**
     * support function to determine if two time periods are overlapping
     * @param start Start time
     * @param end End time
     * @param inputTime Time compare to
     * @return true if time periods are overlapping
     */
    private static boolean isInTimePeriod(LocalDateTime start, LocalDateTime end, LocalDateTime inputTime){
        return start.isBefore(inputTime) && end.isAfter(inputTime) || end.equals(inputTime)|| start.equals(inputTime);
    }

    /**
     * function to get a ArrayList of Persons, that contain a specified String
     * @param s String that should be contained in the Person name
     * @param dataArray List of all Person objects
     * @return List of type Person, which contains every Person that fits the criteria
     */
    public static ArrayList<Person> searchPerson(String s, ArrayList<Person> dataArray) {
        ArrayList<Person> resultArray = new ArrayList<>();

        //filter names which contain string
        for (Person person : dataArray) {
            if (person.getPerson_name().toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                resultArray.add(person);
            }
        }
        return resultArray;
    }

    /**
     * function to get a ArrayList of Type Ort, whose names contain a specified String
     * @param s String, that should be contained in the name of an Ort
     * @param dataArray List of all Ort Objects
     * @return List of type Ort, with every Ort that fits the criteria
     */
    public static ArrayList<Ort> searchOrt(String s, ArrayList<Ort> dataArray) {
        ArrayList<Ort> resultArray = new ArrayList<>();

        //filter orte whicht contains string
        for (Ort ort : dataArray) {
            if (ort.getLocation_name().toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                resultArray.add(ort);
            }
        }
        return resultArray;
    }

    /**
     * function adds a person to the return list if:
     * 1) person is at the same place, at the same time
     * 2) the visited place is indoors
     * @param personenId identification number of a person
     * @param personDataArray List of all available people
     * @param alleBesucheDataArray List of all visits
     * @param orteDataArray List of all available places
     * @return List of people that fit search criteria
     */
    public static ArrayList<Person> searchKontaktpersonen(int personenId, ArrayList<Person> personDataArray,
                                                          ArrayList<Besuche> alleBesucheDataArray,
                                                          ArrayList<Ort> orteDataArray) {
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
                            //checks if there is a overlap
                            if (besuchteOrte.get(i).isOverlapping(alleBesucheDataArray.get(j).getStart_Date(),
                                    alleBesucheDataArray.get(j).getEnd_Date())){
                                //check to ignore the original person
                                if (alleBesucheDataArray.get(j).getPerson_id() != personenId){
                                    resultArray.add(searchPersonByID(alleBesucheDataArray.get(j).getPerson_id(),
                                            personDataArray));
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

    /**
     * function adds a person to the return list if:
     * 1) person is at the same place at the same time
     * 2) person is a contact of the above person
     *
     * @param ortId id of place to focus search on
     * @param timeInput time of visit
     * @param personDataArray List of all available people
     * @param alleBesucheDataArray List of all visits
     * @param orteDataArray List of all available places
     * @return List of people that fit search criteria
     */

    public static ArrayList<Person> searchBesucher(int ortId, String timeInput, ArrayList<Person> personDataArray,
                                                   ArrayList<Besuche> alleBesucheDataArray,
                                                   ArrayList<Ort> orteDataArray) {
        ArrayList<Person> resultArray = new ArrayList<>();
        ArrayList<Person> tempArray = new ArrayList<>();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("[yyyy-MM-dd'T'HH:mm:ss][yyyy-MM-dd'T'HH:mm]", Locale.ENGLISH);

        LocalDateTime timeFromInput = LocalDateTime.parse(timeInput.replaceAll("^\"+|\"+$", ""),
                formatter);

        Besuche besuchZumVergleichen = new Besuche(timeFromInput, timeFromInput, 0, ortId);

        for (int i = 0; i < alleBesucheDataArray.size(); i++) {
            //checks if id is present in orteArray
            if (ortId == alleBesucheDataArray.get(i).getLocation_id()) {
                //checks if both ids are indoor
                if (searchIfOrtIsInDoors(ortId, orteDataArray)) {
                    //checks if there is a overlap
                    if (isInTimePeriod(alleBesucheDataArray.get(i).getStart_Date(),
                            alleBesucheDataArray.get(i).getEnd_Date(), timeFromInput)){
                        tempArray = searchKontaktpersonen(alleBesucheDataArray.get(i).getPerson_id(),
                                personDataArray, alleBesucheDataArray, orteDataArray);
                        resultArray.addAll(tempArray);
                        resultArray.add(searchPersonByID(alleBesucheDataArray.get(i).getPerson_id(), personDataArray));
                    }
                } else {
                    //checks if there is a overlap, adds only person, not contacts and person
                    if (isInTimePeriod(alleBesucheDataArray.get(i).getStart_Date(),
                            alleBesucheDataArray.get(i).getEnd_Date(), timeFromInput)){
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