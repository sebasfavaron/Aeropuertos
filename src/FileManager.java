package src;

import java.util.Formatter;
import java.util.List;

public class FileManager {
    private Formatter formatter;
    private boolean printText;
    private String last;

    public boolean openFile(String fileName, boolean printText){
        try{
            formatter = new Formatter(fileName);
            this.printText=printText;
            this.last=fileName;
        }catch(Exception e){
            System.out.println("Error opening file");
            return false;
        }
        return true;
    }
    public void addRecord(GraphAdjList.PQNode node){
        if(printText) {
            int flightTimehours = node.ft.intValue() / 60;
            int flightTimeminutes = node.ft.intValue() % 60;
            int totalTimehours = node.tt.intValue() / 60;
            int totalTimeminutes = node.tt.intValue() % 60;

            formatter.format("Precio#%f%n", node.price);
            formatter.format("TiempoVuelo#%02dh%02dm%n", flightTimehours, flightTimeminutes);
            formatter.format("TiempoTotal#%02dh%02dm%n", totalTimehours, totalTimeminutes);
            int i = 0;
            for (Flight flight : node.itinerary) {
                String day = node.days.get(i);
                formatter.format("%s#%s#%d#%s#%s%n", flight.getDeparture().getName(), flight.getAirline(), flight.getNumber(), day, flight.getArrival().getName());
                i++;
            }
        }else{

        }
    }
    public void closeFile(){
        formatter.close();
    }
    public Formatter formatter(){
        return formatter;
    }

    public String last() {
        return last;
    }
}
