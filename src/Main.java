package src;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;

public class Main {
    public static void main(String[] args) {
        //lo meto en un metodo asi podemos try-catchear la cosa entera
        Main.paramsManager(args);
    }
    private static void paramsManager(String[] args) {

        AirSystem airSystem = new AirSystem();
        airSystem.addAirport("A", 12., 12.);
        airSystem.addAirport("B", 13., 13.);
        airSystem.addAirport("C", 15., 15.);
        airSystem.addAirport("D", 14., 14.);
        airSystem.addAirport("E", 16., 16.);
        List<String> l = new ArrayList<>();
        l.add("Lun");
        l.add("Mar");
        l.add("Mie");
        l.add("Jue");
        l.add("Vie");
        airSystem.addFlight("AB", 2232, l, "A", "B", new src.Time(0,0), new src.Time(2,0), 20.0);
        airSystem.addFlight("BC", 1111, l, "B", "C", new src.Time(3,1), new src.Time(1,0), 1.0);
        airSystem.addFlight("CD", 5555, l, "C", "D", new src.Time(5,2), new src.Time(1,0), 1.0);
        airSystem.addFlight("DE", 3232, l, "D", "E", new src.Time(7,3), new src.Time(1,0), 1.0);
        airSystem.addFlight("AE", 2222, l, "A", "E", new src.Time(0,0), new src.Time(4,0), 50.0);
        airSystem.addFlight("BE", 4231, l, "B", "E", new src.Time(2,1), new src.Time(1,0), 10000.0);
        /*
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
        List<String> lun = new ArrayList<>();
        lun.add("Lun");
        List<String> mar = new ArrayList<>();
        mar.add("Mar");
        List<String> mier = new ArrayList<>();
        mier.add("Mie");
        List<String> juev = new ArrayList<>();
        juev.add("Jue");
        List<String> vie = new ArrayList<>();
        vie.add("Vie");
        List<String> sab = new ArrayList<>();
        List<String> dom = new ArrayList<>();
        airSystem.addFlight("AA", 2232, l, "ARG", "FRA", new src.Time(10,12), new src.Time(77,0), 20.);
        airSystem.addFlight("AA", 5555, l, "ARG", "USA", new src.Time(10,12), new src.Time(1,0), 900.);
        airSystem.addFlight("AA", 3232, l, "USA", "ENG", new src.Time(10,12), new src.Time(1,0), 20.);
        airSystem.addFlight("AA", 4231, l, "ENG", "FRA", new src.Time(10,12), new src.Time(1,0), 20.);
        */
        //airSystem.addFlight("AA", 1234, l, "ARG", "FRA", new src.Time(10,12), new src.Time(2,0), 60.);
        //airSystem.deleteFlight("AA#1234");
        //airSystem.addFlight("AA", 2232, lun, "ARG", "USA", new src.Time(10,12), new src.Time(10,0), 20.);
        //airSystem.addFlight("AA", 5555, lun, "ARG", "USA", new src.Time(14,12), new src.Time(10,0), 900.);
        //airSystem.addFlight("FF", 8898, l, "ARG", "FRA", new src.Time(10,12), new src.Time(6,0), 999.);
        //airSystem.addFlight("AA", 4444, l, "ARG", "FRA", new src.Time(9,12), new src.Time(1,0), 20.);
        //airSystem.addFlight("AA", 3232, mar, "USA", "ENG", new src.Time(10,12), new src.Time(10,0), 20.);
        //airSystem.addFlight("AA", 4231, mier, "ENG", "FRA", new src.Time(10,12), new src.Time(22,0), 20.);
       // airSystem.addFlight("AA", 5231, l, "FRA", "ENG", new src.Time(10,12), new src.Time(22,0), 20.);
       // airSystem.addFlight("AA", 6232, l, "ENG", "ARG", new src.Time(10,12), new src.Time(10,0), 20.);
       // airSystem.addFlight("AA", 7232, l, "AUS", "USA", new src.Time(10,12), new src.Time(10,0), 20.);
       // airSystem.addFlight("AA", 8232, l, "USA", "ARG", new src.Time(10,12), new src.Time(10,0), 20.);
      //  airSystem.addFlight("AA", 9232, l, "AUS", "ARG", new src.Time(10,12), new src.Time(10,0), 20.);
        ArrayList<Flight> res = airSystem.getAirports().minPrice("A", "E", l);
        System.out.println(res);
        res = airSystem.getAirports().minFt("A", "E", l);
        System.out.println(res);
        //res = airSystem.getAirports().minTt("ARG", "FRA", lun);
        //System.out.println(res);

        /*
        AirSystem airSystem = new AirSystem();
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
                                if(commands.length!=5 || !validateName(commands[2]) || !isCoord(commands[3],commands[4])) {
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    Double lat=toDouble(commands[3]);
                                    Double longitude=toDouble(commands[4]);
                                    airSystem.addAirport(commands[2], lat, longitude);
                                    System.out.println("Added Airport successfully!");
                                }
                                break;
                            case "flight":
                                if(commands.length!=10 || !validateName(commands[2]) || !validateFlightNum(commands[3])
                                        || !validateWeekDays(commands[4])|| !validateAirport(commands[5])
                                        ||!validateAirport(commands[6]) || !validateTime(commands[7])
                                        ||!validateDuration(commands[8]) ||!validatePrice(commands[9])) {
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    Integer num=toInteger(commands[3]);
                                    List<String> weekdays=toWeekDays(commands[4]);
                                    String from = commands[5];
                                    String to = commands[6];
                                    src.Time time = getTime(commands[7]);
                                    src.Time duration = getDuration(commands[8]);
                                    Double price = toDouble(commands[9]);
                                    airSystem.addFlight(commands[2], num , weekdays, from, to, time, duration, price);
                                    System.out.println("Added flight successfully!");
                                }
                                break;
                            case "all":
                                if(commands.length!=5 || !isFile(commands[3])) {
                                    System.out.println("isFile");
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    if(commands[2].equals("airports")){
                                        if(commands[4].equals("append") || commands[4].equals("remove")){
                                            insertAll("airport", commands[3], commands[4]);
                                            System.out.println("Insertion successful");
                                        }else{
                                            System.out.println("appendremove");
                                            System.out.println("Operation failed, please enter a valid command");
                                        }
                                    }else if(commands[2].equals("flight")){
                                        if(commands[4].equals("append") || commands[4].equals("remove")){
                                            insertAll("flight", commands[3], commands[4]);
                                            System.out.println("Insertion successful");
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
                                if(commands.length!=3 || !validateAirport(commands[2])) {
                                    System.out.println("Operation failed, please enter a valid command");
                                }else{
                                    airSystem.deleteAirport(commands[2]);
                                    System.out.println("Airport deleted successfully");
                                }
                                break;
                            case "flight":
                                if(commands.length!=4 || !validateName(commands[2]) || !validateFlightNum(commands[3])){
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
                    if(commands.length!=5 || !validateAirport(commands[1]) || !validateAirport(commands[2])
                            || !validatePriority(commands[3])|| !validateWeekDays(commands[4])){
                        System.out.println("Operation failed, please enter a valid command");
                    }else{
                        System.out.println("Finding route....");
                        findRoute(commands[1],commands[2],commands[3],commands[4]);
                    }

                    break;

                case "worldTrip":
                    if (commands.length != 4 || !validateAirport(commands[1]) || !validatePriority(commands[2])
                            || !validateWeekDays(commands[3])){
                        System.out.println("Operation failed, please enter a valid command");
                    }else
                    {
                        System.out.println("Finding route....");
                        worldTrip(commands[1],commands[2],commands[3]);
                    }
                    break;

                case "outputFormat":
                    if (commands.length != 3 || !validateType(commands[1]) || validateOutput(commands[2]) ) {
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
        */
    }

    private static void findRoute(String command, String command1, String command2, String command3) {
        System.out.println("___not Implemented___");
    }

    private static void worldTrip(String command, String command1, String command2) {
        System.out.println("___not Implemented___");
    }

    private static void insertAll(String flight, String command, String command1) {
        System.out.println("___not Implemented___");
    }

    private static List<String> toWeekDays(String command){
        Character[] charArray = toCharacterArray(command);
        List<String> ret = new ArrayList<>();
        String days=new String();
        for(Character c: charArray){
            if(c=='-'){
                ret.add(new String(days));
                days="";
            }else{
                days=days+c;
            }
        }
        return ret;
    }
    private static Character[] toCharacterArray( String s ) {
        if ( s == null ) {
            return null;
        }

        int len = s.length();
        Character[] array = new Character[len];
        for (int i = 0; i < len ; i++) {
            array[i] = new Character(s.charAt(i));
        }

        return array;
    }
    private static src.Time getTime(String command){
        Character[] charArray = toCharacterArray(command);
        String hourString="";
        String minuteString;
        String current="";
        for(Character c: charArray){
            if(c==':'){
                hourString=current;
                current="";
            }else{
                current=current+c;
            }
        }
        minuteString=current;

        int hours= Integer.parseInt(hourString);
        int minutes= Integer.parseInt(minuteString);
        return new src.Time(hours, minutes);
    }
    private static src.Time getDuration(String command){
        Character[] charArray = toCharacterArray(command);
        String hourString="";
        String minuteString="";
        String current="";
        for(Character c: charArray){
            if(c=='h'){
                hourString=current;
                current="";
            }else if(!(c=='m')){
                current=current+c;
            }
        }
        minuteString=current;
        int hours= Integer.parseInt(hourString);
        int minutes= Integer.parseInt(minuteString);
        return new src.Time(hours, minutes);
    }

    private static Double toDouble(String command) {
        return Double.parseDouble(command);
    }
    private static Integer toInteger(String command) {
        return Integer.parseInt(command);
    }
    private static boolean validateAirport(String command) {
        if(command.length()<4){
            return true;
        }
        return false;
    }
    private static boolean validatePriority(String command) {
        if(command=="ft" || command=="pr" || command == "tt"){
            return true;
        }
        return false;
    }
    private static boolean validateType(String command) {
        if(command=="text" || command=="KML" ){
            return true;
        }
        return false;
    }
    private static boolean validateOutput(String command) {
        if(isFile(command) || command=="stdout" ){ //No estoy seguro como tiene que funcionar esto
            return true;
        }
        return false;
    }
    private static boolean validateWeekDays(String command) {
        Character[] charArray = toCharacterArray(command);
        List<String> days = new ArrayList<>();
        String day=new String();
        for(Character c: charArray){
            if(c=='-'){
                days.add(new String(day));
                day="";
            }else if(Character.isAlphabetic(c)){
                day=day+c;
            }else{
                return false;
            }
        }
        days.add(day);
        if(days.size()>7){
            return false;
        }
        List<String> weekdays = new ArrayList<>();
        weekdays.add("Lu");weekdays.add("Ma");weekdays.add("Mi"); weekdays.add("Ju"); weekdays.add("Vi"); weekdays.add("Sa"); weekdays.add("Do");
        for (String str: days){
            if(!weekdays.contains(str)){
                return false;
            }
        }
        return true;
    }
    private static boolean validateName(String command) {
        if(command.length()<4){
            return true;
        }
        return false;
    }
    private static boolean validateFlightNum(String command) {
        Character[] charArray = toCharacterArray(command);
        for(Character c: charArray){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    private static boolean validateTime(String command) {
        Character[] charArray = toCharacterArray(command);
        if(!Character.isDigit(charArray[0]) || !Character.isDigit(charArray[1]) || !Character.isDigit(charArray[3]) ||
            !Character.isDigit(charArray[4]) || charArray[2]!=':'){
            return false;
        }
        return true;
    }
    private static boolean validatePrice(String command) {
        Character[] charArray = toCharacterArray(command);
        int counter=0;
        for(Character c: charArray){
            if(c=='.'){
                counter++;
            }
            if(!Character.isDigit(c) || counter>1){
                return false;
            }
        }
        return true;
    }
    private static boolean validateDuration(String command) {
        Character[] charArray = toCharacterArray(command);
        if(!Character.isDigit(charArray[0]) || !Character.isDigit(charArray[1]) || !Character.isDigit(charArray[3]) ||
                !Character.isDigit(charArray[4]) || charArray[2]!='h' || charArray[5]!='m'){
            return false;
        }
        return true;
    }
    private static boolean isFile(String command) {
        return true; // No se como lo voy a implementar aun.
    }
    private static boolean isCoord(String lat, String longitude) {
        Character[] latArray = toCharacterArray(lat);
        Character[] longArray = toCharacterArray(longitude);
        String latString=new String("");
        String longString= new String("");
        for(int i=0;i<lat.length();i++){
            Character c=latArray[i];
            if(i==0){
                if(!Character.isDigit(c) && c!='-'){
                    return false;
                }
                latString=latString+c;
            }else{
                if(!Character.isDigit(c)){
                    return false;
                }
                latString=latString+c;
            }
        }
        for(int i=0;i<longArray.length;i++){
            Character c=longArray[i];
            if(i==0){
                if(!Character.isDigit(c) && c!='-'){
                    return false;
                }
                longString=longString+c;
            }else{
                if(!Character.isDigit(c)){
                    return false;
                }
                longString=longString+c;
            }
        }
        int latInt= Integer.parseInt(latString);
        int longInt= Integer.parseInt(longString);
        if(-91<latInt && latInt<91 && -181<longInt && longInt<181){
            return true;
        }
        return false;
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
