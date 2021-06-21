import logic.ReadFile;
import logic.SearchFunctionality;
import objectClasses.Besuche;
import objectClasses.Ort;
import objectClasses.Person;
import java.io.File;
import java.util.ArrayList;

public class KontaktDatenbank {
    public static void main(String[] args) {

        File f = new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\Data\\contacts2021.db");

        ArrayList<Person> PersonenArray = ReadFile.returnPerson(f);
        ArrayList<Ort> OrteArray = ReadFile.returnOrt(f);
        ArrayList<Besuche> BesucheArray = ReadFile.returnBesuch(f);

        if (args.length != 0) {
            int i;
            if ("--personensuche".equals(args[0].split("=")[0])) {
                ArrayList<Person> resultPersonensuche = SearchFunctionality.searchPerson(args[0].split("=")[1], PersonenArray);
                for(i = 0; i < resultPersonensuche.size(); ++i) {
                    if (i == resultPersonensuche.size() - 1) {
                        System.out.print((resultPersonensuche.get(i)).getPerson_name());
                    } else {
                        System.out.print((resultPersonensuche.get(i)).getPerson_name() + ", ");
                    }
                }
            } else if ("--ortssuche".equals(args[0].split("=")[0])) {
                ArrayList<Ort> resultOrtssuche = SearchFunctionality.searchOrt(args[0].split("=")[1], OrteArray);
                for(i = 0; i < resultOrtssuche.size(); ++i) {
                    if (i == resultOrtssuche.size() - 1) {
                        System.out.print((resultOrtssuche.get(i)).getLocation_name());
                    } else {
                        System.out.print((resultOrtssuche.get(i)).getLocation_name() + ", ");
                    }
                }
            } else if ("--kontaktpersonen".equals(args[0].split("=")[0])) {
                ArrayList<Person> resultKontaktpersonen = SearchFunctionality.searchKontaktpersonen(Integer.parseInt(args[0].split("=")[1]), PersonenArray, BesucheArray, OrteArray);
                for(i = 0; i < resultKontaktpersonen.size(); ++i) {
                    if (i == resultKontaktpersonen.size() - 1) {
                        System.out.print(((Person)resultKontaktpersonen.get(i)).getPerson_name());
                    } else {
                        System.out.print(((Person)resultKontaktpersonen.get(i)).getPerson_name() + ", ");
                    }
                }
            } else if ("--besucher".equals(args[0].split("=")[0])) {
                ArrayList<Person> resultBesucher = SearchFunctionality.searchBesucher(
                        Integer.parseInt(args[0].split("=")[1].split(",")[0]),
                        args[0].split("=")[1].split(",")[1], PersonenArray,
                        BesucheArray, OrteArray);
                for(i = 0; i < resultBesucher.size(); ++i) {
                    for(i = 0; i < resultBesucher.size(); ++i) {
                        if (i == resultBesucher.size() - 1) {
                            System.out.print(((Person)resultBesucher.get(i)).getPerson_name());
                        } else {
                            System.out.print(((Person)resultBesucher.get(i)).getPerson_name() + ", ");
                        }
                    }
                }
            }
        }
    }
}
