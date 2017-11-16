package src;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class FileChecker {
    AirSystem airSystem;
    Validator v;

    public FileChecker(AirSystem airSystem, Validator v){
        this.v =v;
        this.airSystem=airSystem;
    }


    public boolean insertAll(String AorF, String file, String AorR) {
        boolean flights=AorF.equals("flight");
        boolean append=AorR.equals("append");
        Scanner scan;
        try {
            scan = new Scanner(new File(file));

            while (scan.hasNext()){
                String a = scan.next();
                if(flights){
                    if(!validateFlightString(a)) return false;
                }else{
                    if(!validateAirportString(a)) return false;
                }
            }
            if(!append){
                if(flights){
                    airSystem.deleteAllFlights();
                }else{
                    airSystem.deleteAllAirports();
                }
            }
            scan.close();
            scan = new Scanner(new File(file));
            while (scan.hasNext()){
                String a = scan.next();
                if(flights){
                    addFlights(a);
                }else{
                    addAirports(a);
                }
            }
            scan.close();
            return true;
        }
        catch(Exception e){
            System.out.println("File not found");
            return false;
        }
    }

    private void addFlights(String a) {
        String[] line = a.split("#",8);
        Integer num=v.toInteger(line[1]);
        List<String> weekdays=v.toWeekDays(line[2]);
        String from = line[3];
        String to = line[4];
        src.Time time = v.getTime(line[5]);
        src.Time duration = v.getDuration(line[6]);
        Double price = v.toDouble(line[7]);
        airSystem.addFlight(line[0], num , weekdays, from, to, time, duration, price);
        System.out.println("Added Flight successfully!");
    }
    private void addAirports(String a) {
        String[] line = a.split("#",3);
        Double lat=v.toDouble(line[1]);
        Double longitude=v.toDouble(line[2]);
        airSystem.addAirport(line[0], lat, longitude);
        System.out.println("Added Airport successfully!");
    }

    private boolean validateAirportString(String a) {
        String[] line = a.split("#",3);
        if(line.length!=3){
            return false;
        }
        return v.validateAirport(line[0]) && v.isCoord(line[1],line[2]);
    }

    private boolean validateFlightString(String a) {
        String[] line = a.split("#",8);
        if(line.length!=8){
            return false;
        }
        if(!v.validateName(line[0]) || !v.validateFlightNum(line[1]) || !v.validateWeekDays(line[2])||!v.validateAirport(line[3])){
            return false;
        }
        if(!v.validateAirport(line[4])|| !v.validateTime(line[5])|| !v.validateDuration(line[6])|| !v.validatePrice(line[7])){
            return false;
        }
        return true;
    }
}
