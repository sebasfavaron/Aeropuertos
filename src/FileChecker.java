package src;

import java.io.File;
import java.util.Scanner;

public class FileChecker {
    AirSystem airSystem;
    Validator v;

    public FileChecker(AirSystem airSystem, Validator v){
        this.v =v;
        this.airSystem=airSystem;
    }


    public boolean insertAll(String AorF, String file, String AorR) {
        //System.out.println("___not Implemented___");
        boolean flights=AorF.equals("flights");
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
            return true;
        }
        catch(Exception e){
            return false;
        }
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
        if(!v.validateName(line[0]) || !v.validateFlightNum(line[1]) || !v.validateWeekDays(line[2])||v.validateAirport(line[3])){
            return false;
        }
        if(!v.validateAirport(line[4])|| !v.validateTime(line[5])|| !v.validateDuration(line[6])|| !v.validatePrice(line[7])){
            return false;
        }
        return true;
    }
}
