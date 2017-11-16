package src;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    public Validator(){

    }
    public List<String> toWeekDays(String command){
        String[] line = command.split("-");
        List<String> ret = new ArrayList<String>();
        for(int i=0; i<line.length;i++){
            ret.add(line[i]);
        }
        return ret;
    }
    public Character[] toCharacterArray( String s ) {
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
    public src.Time getTime(String command){
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
    public src.Time getDuration(String command){
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
    public Double toDouble(String command) {
        return Double.parseDouble(command);
    }
    public Integer toInteger(String command) {
        return Integer.parseInt(command);
    }
    public boolean validateAirport(String command) {
        if(command.length()<4){
            return true;
        }
        return false;
    }
    public boolean validatePriority(String command) {
        if(command.equals("ft") || command.equals("pr") || command.equals("tt")){
            return true;
        }
        return false;
    }
    public boolean validateType(String command) {
        if(command=="text" || command=="KML" ){
            return true;
        }
        return false;
    }
    public boolean validateOutput(String command) {
        if(isFile(command) || command=="stdout" ){ //No estoy seguro como tiene que funcionar esto
            return true;
        }
        return false;
    }
    public boolean validateWeekDays(String command) {
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
    public boolean validateName(String command) {
        if(command.length()<4){
            return true;
        }
        return false;
    }
    public boolean validateFlightNum(String command) {
        Character[] charArray = toCharacterArray(command);
        for(Character c: charArray){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    public boolean validateTime(String command) {
        Character[] charArray = toCharacterArray(command);
        if(!Character.isDigit(charArray[0]) || !Character.isDigit(charArray[1]) || !Character.isDigit(charArray[3]) ||
                !Character.isDigit(charArray[4]) || charArray[2]!=':'){
            return false;
        }
        return true;
    }
    public boolean validatePrice(String command) {
        Character[] charArray = toCharacterArray(command);
        int counter=0;
        for(Character c: charArray){
            if(c=='.'){
                counter++;
            }
            if((!Character.isDigit(c)&& c!='.') || (c=='.' && counter>1)){
                return false;
            }
        }
        return true;
    }
    public boolean validateDuration(String command) {
        Character[] charArray = toCharacterArray(command);
        if(!Character.isDigit(charArray[0]) || !Character.isDigit(charArray[1]) || !Character.isDigit(charArray[3]) ||
                !Character.isDigit(charArray[4]) || charArray[2]!='h' || charArray[5]!='m'){
            return false;
        }
        return true;
    }


    public boolean isFile(String command) {
        return true; // No se como lo voy a implementar aun.
    }

    public boolean isCoord(String lat, String longitude) {
        Character[] latArray = toCharacterArray(lat);
        Character[] longArray = toCharacterArray(longitude);
        String latString=new String("");
        String longString= new String("");
        int counter=0;
        for(int i=0;i<lat.length();i++){
            Character c=latArray[i];
            if(i==0){
                if(!Character.isDigit(c) && c!='-'){
                    return false;
                }
                latString=latString+c;
            }else{
                if(c=='.'){
                    counter++;
                }
                if(!(Character.isDigit(c)|| (c=='.' && counter<2))){
                    return false;
                }
                latString=latString+c;
            }
        }
        counter=0;
        for(int i=0;i<longArray.length;i++){
            Character c=longArray[i];
            if(i==0){
                if(!Character.isDigit(c) && c!='-'){
                    return false;
                }
                longString=longString+c;
            }else{
                if(c=='.'){
                    counter++;
                }
                if(!(Character.isDigit(c)|| (c=='.' && counter<2))){
                    return false;
                }
                longString=longString+c;
            }
        }
        double latDouble= Double.parseDouble(latString);
        double longDouble= Double.parseDouble(longString);
        if(-91<latDouble && latDouble<91 && -181<longDouble && longDouble<181){
            return true;
        }
        return false;
    }


}
