import logic.ReadFile;
import logic.SearchFunctionality;
import objectClasses.Besuche;
import objectClasses.Ort;
import objectClasses.Person;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

public class KontaktDatenbank {
    public KontaktDatenbank() {
    }

    public static void main(String[] args) {
        ArrayList<Person> PersonenArray = ReadFile.returnPerson(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\Data\\contacts2021.db"));
        ArrayList<Ort> OrteArray = ReadFile.returnOrt(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\Data\\contacts2021.db"));
        ArrayList<Besuche> BesucheArray = ReadFile.returnBesuch(new File("C:\\Users\\kevin\\Documents\\Developement\\Kontaktprojekt\\src\\Data\\contacts2021.db"));
        if (args.length != 0) {
            PrintStream var10000;
            int var10001;
            ArrayList resultBesucher;
            int i;
            if ("--personensuche".equals(args[0].split("=")[0])) {
                resultBesucher = SearchFunctionality.searchPerson(args[0].split("=")[1], PersonenArray);

                for(i = 0; i < resultBesucher.size(); ++i) {
                    var10000 = System.out;
                    var10001 = ((Person)resultBesucher.get(i)).getPerson_id();
                    var10000.println("Id: " + var10001 + " Name: " + ((Person)resultBesucher.get(i)).getPerson_name());
                }
            } else if ("--ortssuche".equals(args[0].split("=")[0])) {
                resultBesucher = SearchFunctionality.searchOrt(args[0].split("=")[1], OrteArray);

                for(i = 0; i < resultBesucher.size(); ++i) {
                    var10000 = System.out;
                    var10001 = ((Ort)resultBesucher.get(i)).getLocation_id();
                    var10000.println("Id: " + var10001 + " Name: " + ((Ort)resultBesucher.get(i)).getLocation_name());
                }
            } else if ("--kontaktpersonen".equals(args[0].split("=")[0])) {
                resultBesucher = SearchFunctionality.searchKontaktpersonen(Integer.parseInt(args[0].split("=")[1]), PersonenArray, BesucheArray, OrteArray);

                for(i = 0; i < resultBesucher.size(); ++i) {
                    var10000 = System.out;
                    var10001 = ((Person)resultBesucher.get(i)).getPerson_id();
                    var10000.println("Id: " + var10001 + " Name: " + ((Person)resultBesucher.get(i)).getPerson_name());
                }
            } else if ("--besucher".equals(args[0].split("=")[0])) {
                resultBesucher = SearchFunctionality.searchBesucher(Integer.parseInt(args[0].split("=")[1].split(",")[0]), args[0].split("=")[1].split(",")[1], PersonenArray, BesucheArray, OrteArray);

                for(i = 0; i < resultBesucher.size(); ++i) {
                    var10000 = System.out;
                    var10001 = ((Person)resultBesucher.get(i)).getPerson_id();
                    var10000.println("Id: " + var10001 + " Name: " + ((Person)resultBesucher.get(i)).getPerson_name());
                }
            }
        }

    }
}
