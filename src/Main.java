package src;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebas on 10/24/17.
 */
public class Main {
    public static void main(String[] args) {
        //lo meto en un metodo asi podemos try-catchear la cosa entera
        Main.paramsManager(args);
    }
    private static void paramsManager(String[] args) {
        Main m = new Main();
        m.printHelp();
        AirSystem airSystem = new AirSystem();
        airSystem.addAirport("BUE", 12., 12.);
        airSystem.addAirport("FRA", 13., 13.);
        airSystem.addAirport("ENG", 15., 15.);
        List<String> l = new ArrayList<>();
        l.add("Lun");
        l.add("Mar");
        l.add("Mie");
        l.add("Jue");
        l.add("Vie");
        airSystem.addFlight("AA", 1234, l, "BUE", "FRA", new Time(12), 2., 200.);
        airSystem.addFlight("AA", 2231, l, "FRA", "ENG", new Time(11), 22., 20.);
        airSystem.addFlight("AA", 3232, l, "ENG", "BUE", new Time(10), 10., 2.);
        ArrayList<Flight> res = airSystem.getAirports().minDistance("BUE", "FRA", new GetValue() {
            @Override
            public double get(Flight flight) {
                return flight.getPrice();
            }
        },l);
        System.out.println(res);
    }
    private void printHelp() {
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
