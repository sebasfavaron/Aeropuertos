package src;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //lo meto en un metodo asi podemos try-catchear la cosa entera
        Main.paramsManager(args);
    }
    private static void paramsManager(String[] args) {
        printHelp();
        AirSystem airSystem = new AirSystem();
        airSystem.addAirport("ARG", 12., 12.);
        airSystem.addAirport("FRA", 13., 13.);
        airSystem.addAirport("ENG", 15., 15.);
        airSystem.addAirport("AUS", 15., 15.);
        airSystem.addAirport("USA", 15., 15.);
        List<String> l = new ArrayList<>();
        l.add("Lun");
        l.add("Mar");
        l.add("Mie");
        l.add("Jue");
        l.add("Vie");
        airSystem.addFlight("AA", 1234, l, "ARG", "FRA", new src.Time(10,12), new src.Time(2,0), 60.);
        airSystem.deleteFlight("AA#1234");
        airSystem.addFlight("AA", 2232, l, "ARG", "USA", new src.Time(10,12), new src.Time(10,0), 20.);
        airSystem.addFlight("AA", 3232, l, "USA", "ENG", new src.Time(10,12), new src.Time(10,0), 20.);
        airSystem.addFlight("AA", 4231, l, "ENG", "FRA", new src.Time(10,12), new src.Time(22,0), 20.);
        airSystem.addFlight("AA", 5231, l, "FRA", "ENG", new src.Time(10,12), new src.Time(22,0), 20.);
        airSystem.addFlight("AA", 6232, l, "ENG", "ARG", new src.Time(10,12), new src.Time(10,0), 20.);
        airSystem.addFlight("AA", 7232, l, "AUS", "USA", new src.Time(10,12), new src.Time(10,0), 20.);
        airSystem.addFlight("AA", 8232, l, "USA", "ARG", new src.Time(10,12), new src.Time(10,0), 20.);
        airSystem.addFlight("AA", 9232, l, "AUS", "ARG", new src.Time(10,12), new src.Time(10,0), 20.);
        ArrayList<Flight> res = airSystem.getAirports().minPrice("ARG", "FRA", l);
        System.out.println(res);
        res = airSystem.getAirports().minFt("ARG", "FRA", l);
        System.out.println(res);
        res = airSystem.getAirports().minTt("ARG", "FRA", l);
        System.out.println(res);
    }

    private static void printHelp() {
        System.out.println("List of commands:\n   " +
                "insert airport [name] [lat] [lng]\n   " +
                "delete airport [name]\n   " +
                "insert all airports FILE [append|replace]\n   " +
                "delete all airport\n   " +
                "insert flight [airline] [flightNumber] [weekDay] [origin] [destination] [departTime] [duration] [price]\n   " +
                "delete flight [airline] [flightNumber]\n   " +
                "insert all flight FILE [append|replace]\n   " +
                "delete all flight\n   " +
                "findRoute [origin] [destination] [priority{ft|pr|tt}] [weekDays]\n   " +
                "worldTrip [origin] [priority{ft|pr|tt}] [weekDays]\n   " +
                "outputFormat [type{text|KML}] [output{file|stdout}]\n   ");
    }
}
