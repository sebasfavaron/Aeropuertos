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

        AirSystem airSystem = new AirSystem();
        airSystem.addAirport("A", 12., 15.);
        airSystem.addAirport("B", 13., 15.);
        airSystem.addAirport("C", 15., 15.);
        airSystem.addAirport("D", 14., 14.);
        airSystem.addAirport("E", 16., 16.);
        airSystem.addAirport("F", 13., 16.);
        List<String> mul = new ArrayList<>();
        mul.add("Lu");
        mul.add("Mi");
        mul.add("Vi");
        List<String> lun = new ArrayList<>();
        lun.add("Lu");
        List<String> mar = new ArrayList<>();
        mar.add("Ma");
        List<String> sab = new ArrayList<>();
        sab.add("Sa");

        airSystem.addFlight("AB", 1232, mul, "A", "B", new src.Time(10,0), new src.Time(3,0), 100.0);
        airSystem.addFlight("AD", 2232, lun, "A", "D", new src.Time(20,45), new src.Time(5,30), 80.0);
        airSystem.addFlight("EF", 3232, mul, "E", "F", new src.Time(7,3), new src.Time(1,0), 150.0);
        airSystem.addFlight("AF", 4222, mul, "A", "F", new src.Time(10,0), new src.Time(40,0), 50.0);
        airSystem.addFlight("BE", 5222, sab, "B", "E", new src.Time(12,0), new src.Time(2,20), 50.0);
        airSystem.addFlight("BD", 6222, mar, "B", "D", new src.Time(15,0), new src.Time(1,0), 120.0);
        airSystem.addFlight("CF", 7222, mul, "C", "F", new src.Time(20,45), new src.Time(1,30), 30.0);
        airSystem.addFlight("CD", 8222, mul, "C", "D", new src.Time(10,15), new src.Time(66,0), 50.0);
        airSystem.addFlight("AC", 9222, lun, "A", "C", new src.Time(17,0), new src.Time(1,45), 180.0);
        airSystem.addFlight("FB", 1222, sab, "F", "B", new src.Time(20,0), new src.Time(3,0), 50.0);
        airSystem.addFlight("CE", 2222, mul, "C", "E", new src.Time(10,15), new src.Time(4,30), 110.0);
        airSystem.addFlight("CE", 3222, mar, "C", "E", new src.Time(1,0), new src.Time(1,15), 150.0);

        GraphAdjList.PQNode res = airSystem.getAirports().minPrice("A", "B", mul);
        System.out.println("Minimum price: "+res.itinerary);

        res = airSystem.getAirports().minFt("A", "B", mul);
        System.out.println("Minimum flight time: "+res.itinerary);

        res = airSystem.getAirports().minTt("A", "B", mul);
        System.out.println("Minimum total time: "+res.itinerary);

        res = airSystem.getAirports().worldTripPrice("F", mul);
        System.out.println("WorldTripPrice: "+res.itinerary);

        res = airSystem.getAirports().worldTripFlightTime("A", mul);
        System.out.println("WorldTripFlightTime: "+res.itinerary);

        res = airSystem.getAirports().worldTripTotalTime("A", mul);
        System.out.println("WorldTripTotalTime: "+res.itinerary);

        Validator v= new Validator();
        FileChecker fileChecker = new FileChecker(airSystem,v);
        printHelp();
        boolean isRunning = true;
        Scanner sc = new Scanner(System.in);
        FileManager fileManager = new FileManager();

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
                                    List<String> weekdays=toDayList(commands[4]);
                                    String from = commands[5];
                                    String to = commands[6];
                                    src.Time time = v.getTime(commands[7]);
                                    src.Time duration = v.getDuration(commands[8]);
                                    Double price = v.toDouble(commands[9]);
                                    boolean succes=airSystem.addFlight(commands[2], num , weekdays, from, to, time, duration, price);
                                    if (succes) {
                                        System.out.println("Added flight successfully!");
                                    }else {
                                        System.out.println("An airport doesn't exist");
                                    }
                                }
                                break;
                            case "all":
                                if(commands.length!=5 || !v.isFile(commands[3])) {
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    if(commands[2].equals("airports")){
                                        if(commands[4].equals("append") || commands[4].equals("replace")){
                                            if(fileChecker.insertAll("airport", commands[3], commands[4])) {
                                                System.out.println("Insertion successful");
                                            }else{
                                                System.out.println("Insertion unsuccesful");
                                            }
                                        }else{
                                            System.out.println("Operation failed, please enter a valid command");
                                        }
                                    }else if(commands[2].equals("flight")){
                                        if(commands[4].equals("append") || commands[4].equals("replace")){
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
                        findRoute(commands[1],commands[2],commands[3],commands[4],airSystem, fileManager);
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
                    if (commands.length != 3 || !v.validateType(commands[1]) || !v.validateOutput(commands[2]) ) {
                        System.out.println("Operation failed, please enter a valid command");
                    }else
                    {
                        changeOutPutFormat(commands[1],commands[2], fileManager);
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
    private static boolean printToStdOut = true;
    private static boolean printText = true;
    private static void changeOutPutFormat(String type, String fileString, FileManager fm){
        if(fm.formatter()!=null){
            fm.closeFile();
        }
        boolean ret=type.equals("text");
        if(fileString.equals("stdout")){
            printToStdOut=true;
        }
        else{
            fm.openFile(fileString, printText);
            System.out.println("Printing to: "+fileString);
            printToStdOut=false;
        }
        printText=ret;
    }

    private static void findRoute(String source, String dest, String priority, String daysString, AirSystem airSystem, FileManager fm) {
        List<String> days = toDayList(daysString);
        GraphAdjList.PQNode res;
        if (priority.equals("pr")) { //PRICE
            res = airSystem.getAirports().minPrice(source, dest, days);
        } else if (priority.equals("ft")) { //FLIGHT TIME
            res = airSystem.getAirports().minFt(source, dest, days);
        } else {//TOTAL TIME
            res = airSystem.getAirports().minTt(source, dest, days);
        }
        if(printToStdOut) {
            printToScreen(res);
        }else{
            fm.openFile(fm.last(), printText);
            fm.addRecord(res);
            fm.closeFile();
        }

    }

    private static void printToScreen(GraphAdjList.PQNode node) {
        if(printText) {
            int flightTimehours = node.ft.intValue() / 60;
            int flightTimeminutes = node.ft.intValue() % 60;
            int totalTimehours = node.tt.intValue() / 60;
            int totalTimeminutes = node.tt.intValue() % 60;

            System.out.printf("Precio#%f%n", node.price);
            System.out.printf("TiempoVuelo#%02dh%02dm%n", flightTimehours, flightTimeminutes);
            System.out.printf("TiempoTotal#%02dh%02dm%n", totalTimehours, totalTimeminutes);
            int i = 0;
            for (Flight flight : node.itinerary) {
                String day = node.days.get(i);
                System.out.printf("%s#%s#%d#%s#%s%n", flight.getDeparture().getName(), flight.getAirline(), flight.getNumber(), day, flight.getArrival().getName());
                i++;
            }
        }else{

        }
    }

    private static List<String> toDayList(String daysString) {
        String[] line = daysString.split("-");
        List<String> ret = new ArrayList<String>();
        for(int i=0; i<line.length;i++){
            ret.add(line[i]);
        }
        return ret;
    }

    private static void worldTrip(String command, String command1, String command2) {
        System.out.println("___not Implemented___");
    }

    private static void printHelp() {
        System.out.println("\nList of commands:\n   " +
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
                "outputFormat [type{text|KML}] [output{file|stdout}]\n   "+
                "Type 'help' at any moment to bring up this list again\n"
        );
    }
}
