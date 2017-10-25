package src;

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
