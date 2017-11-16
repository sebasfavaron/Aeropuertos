package src;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;

public class Main {
    public static void main(String[] args) {
        //lo meto en un metodo asi podemos try-catchear la cosa entera
        paramsManager(args);
    }
    private static void paramsManager(String[] args) {

        /*AirSystem airSystem = new AirSystem();
        airSystem.addAirport("A", 12., 12.);
        airSystem.addAirport("B", 13., 13.);
        airSystem.addAirport("C", 15., 15.);
        airSystem.addAirport("D", 14., 14.);
        airSystem.addAirport("E", 16., 16.);
        airSystem.addAirport("F", 16., 16.);
        List<String> l = new ArrayList<>();
        l.add("Lun");
        l.add("Mar");
        l.add("Mie");
        l.add("Jue");
        l.add("Vie");
        airSystem.addFlight("AB", 2232, l, "A", "B", new src.Time(0,0), new src.Time(1,0), 1.0);
        airSystem.addFlight("EF", 3232, l, "E", "F", new src.Time(7,3), new src.Time(1,0), 1.0);
        airSystem.addFlight("AF", 2222, l, "A", "F", new src.Time(0,0), new src.Time(400,0), 50.0);
        airSystem.addFlight("BE", 2222, l, "B", "E", new src.Time(0,0), new src.Time(1,0), 50.0);

        ArrayList<Flight> res = airSystem.getAirports().minPrice("A", "F", l);
        System.out.println(res);
        res = airSystem.getAirports().minFt("A", "F", l);
        System.out.println(res);*/


        AirSystem airSystem = new AirSystem();
        Validator v= new Validator();
        FileChecker fileChecker = new FileChecker(airSystem,v);
        printHelp();
        boolean isRunning = true;
        Scanner sc = new Scanner(System.in);

        while(isRunning) {
            System.out.println("What do you want to do next?(insert command)");
            String[] commands = sc.nextLine().split(" ");
            String command = commands[0];

            switch (command) {
                case "print":
                    airSystem.getAirports().printArcs();
                    break;
                case "insert":
                    if (commands.length < 3) {
                        System.out.println("Operation failed, please enter a valid command");
                    }else {
                        String command1=commands[1];
                        switch (command1){
                            case "airport":
                                if(commands.length!=5 || !v.validateName(commands[2]) || !v.isCoord(commands[3],commands[4])) {
                                    System.out.println(!v.validateName(commands[2]));
                                    System.out.println(!v.isCoord(commands[3],commands[4]));
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    Double lat=v.toDouble(commands[3]);
                                    Double longitude=v.toDouble(commands[4]);
                                    airSystem.addAirport(commands[2], lat, longitude);
                                    System.out.println("Added Airport successfully!");
                                }
                                break;
                            case "flight":
                                if(commands.length!=10 || !v.validateName(commands[2]) || !v.validateFlightNum(commands[3])
                                        || !v.validateWeekDays(commands[4])|| !v.validateAirport(commands[5])
                                        ||!v.validateAirport(commands[6]) || !v.validateTime(commands[7])
                                        ||!v.validateDuration(commands[8]) ||!v.validatePrice(commands[9])) {
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    Integer num=v.toInteger(commands[3]);
                                    List<String> weekdays=v.toWeekDays(commands[4]);
                                    String from = commands[5];
                                    String to = commands[6];
                                    src.Time time = v.getTime(commands[7]);
                                    src.Time duration = v.getDuration(commands[8]);
                                    Double price = v.toDouble(commands[9]);
                                    airSystem.addFlight(commands[2], num , weekdays, from, to, time, duration, price);
                                    System.out.println("Added flight successfully!");
                                }
                                break;
                            case "all":
                                if(commands.length!=5 || !v.isFile(commands[3])) {
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    if(commands[2].equals("airports")){
                                        if(commands[4].equals("append") || commands[4].equals("remove")){
                                            if(fileChecker.insertAll("airport", commands[3], commands[4])) {
                                                System.out.println("Insertion successful");
                                            }else{
                                                System.out.println("Insertion unsuccesful");
                                            }
                                        }else{
                                            System.out.println("appendremove");
                                            System.out.println("Operation failed, please enter a valid command");
                                        }
                                    }else if(commands[2].equals("flight")){
                                        if(commands[4].equals("append") || commands[4].equals("remove")){
                                            if(fileChecker.insertAll("flight", commands[3], commands[4])){
                                                System.out.println("Insertion successful");
                                            }else{
                                                System.out.println("Insertion unsuccessful");
                                            }
                                        }else{
                                            System.out.println("Operation failed, please enter a valid command");
                                        }
                                    }else{
                                        System.out.println("Operation failed, please enter a valid command");
                                    }
                                }
                                break;
                        }
                    }
                    break;

                case "delete":
                    if (commands.length < 3) {
                        System.out.println("Operation failed, please enter a valid command");
                    }else {
                        String command1=commands[1];
                        switch (command1){
                            case "airport":
                                if(commands.length!=3 || !v.validateAirport(commands[2])) {
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    airSystem.deleteAirport(commands[2]);
                                    System.out.println("Airport deleted successfully");
                                }
                                break;
                            case "flight":
                                if(commands.length!=4 || !v.validateName(commands[2]) || !v.validateFlightNum(commands[3])){
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    airSystem.deleteFlight(commands[2], commands[3]);
                                    System.out.println("Flight deleted successfully");
                                }
                                break;
                            case "all":
                                if(commands.length!=3) {
                                    System.out.println("Operation failed, please enter a valid command");
                                }else if (commands[2]=="airports") {
                                    airSystem.deleteAllAirports();
                                    System.out.println("All airports deleted successfully");
                                }else if(commands[2]=="flight") {
                                    airSystem.deleteAllFlights();
                                    System.out.println("All flights deleted successfully");
                                }else{
                                    System.out.println("Operation failed, please enter a valid command");
                                }
                                break;
                        }
                    }
                    break;

                case "findRoute":
                    if(commands.length!=5 || !v.validateAirport(commands[1]) || !v.validateAirport(commands[2])
                            || !v.validatePriority(commands[3])|| !v.validateWeekDays(commands[4])){
                        System.out.println("Operation failed, please enter a valid command");
                    }else{
                        System.out.println("Finding route....");
                        findRoute(commands[1],commands[2],commands[3],commands[4]);
                    }

                    break;

                case "worldTrip":
                    if (commands.length != 4 || !v.validateAirport(commands[1]) || !v.validatePriority(commands[2])
                            || !v.validateWeekDays(commands[3])){
                        System.out.println("Operation failed, please enter a valid command");
                    }else
                    {
                        System.out.println("Finding route....");
                        worldTrip(commands[1],commands[2],commands[3]);
                    }
                    break;

                case "outputFormat":
                    if (commands.length != 3 || !v.validateType(commands[1]) || v.validateOutput(commands[2]) ) {
                        System.out.println("Operation failed, please enter a valid command");
                    }else
                    {

                    }
                    break;
                case "quit":
                    isRunning=false;
                    break;
                case "help":
                    printHelp();
                    break;

                default:
                    System.out.println("Wrong command, please try again");

            }

        }

    }

    private static void findRoute(String command, String command1, String command2, String command3) {
        System.out.println("___not Implemented___");
    }

    private static void worldTrip(String command, String command1, String command2) {
        System.out.println("___not Implemented___");
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
