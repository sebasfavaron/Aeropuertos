package src;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

import java.io.File;
import java.util.Formatter;

public class FileManager {
    private Formatter formatter;
    private boolean printText;
    private String last;

    public boolean openFile(String fileName, boolean printText){
        this.printText=printText;
        this.last=fileName;
        return true;
    }
    public void addRecord(GraphAdjList.PQNode node){
        if(node==null){
            formatter.format("Not found");
            return;
        }
        int flightTimehours = node.ft.intValue() / 60;
        int flightTimeminutes = node.ft.intValue() % 60;
        int totalTimehours = node.tt.intValue() / 60;
        int totalTimeminutes = node.tt.intValue() % 60;

        if(printText) {

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
            String description = String.format("Precio#%f TiempoVuelo#%02dh%02dm TiempoTotal#%02dh%02dm", node.price, flightTimehours, flightTimeminutes, totalTimehours, totalTimeminutes);
            final Kml kml = new Kml();
            Airport start= node.itinerary.get(0).getDeparture();
            Airport finish= node.itinerary.get(node.itinerary.size()-1).getArrival();
            Document doc = kml.createAndSetDocument().withName("Flight from "+ start.getName() +" to "+finish.getName());

            Folder folderSummary = doc.createAndAddFolder();
            folderSummary.withName("Summary");

            folderSummary.withDescription(description);

            Folder folderItinerary = doc.createAndAddFolder();
            folderItinerary.withName("Itinerary");

            Flight firstFlight = node.itinerary.get(0);
            String day = node.days.get(0);

            createPlacemarkFirst(folderItinerary,firstFlight.getDeparture().getLng(), firstFlight.getDeparture().getLat(),
                    firstFlight.getDeparture().getName() ,day);

            int i=0;
            for(Flight flight : node.itinerary){
                day=node.days.get(i);
                createPlacemarkItinerary(folderItinerary, flight.getArrival().getLng(), flight.getArrival().getLat(), flight.getArrival().getName(),
                        day, flight.toString());
                i++;
            }
            try {
                kml.marshal(new File(last));
            }catch(Exception e){
                System.out.println("Error creating kml");
            }
        }
    }

    private static void createPlacemarkFirst(Folder folder, double longitude, double latitude, String airportName, String day) {
        Placemark placemark = folder.createAndAddPlacemark();
        placemark.withName(airportName)
                .withDescription("Departure day: " + day)
                .createAndSetPoint().addToCoordinates(longitude,latitude);

        placemark.createAndSetPoint().addToCoordinates(longitude, latitude); // set coordinates
    }
    private static void createPlacemarkItinerary(Folder folder, double longitude, double latitude, String airportName, String day,
                                                    String arrivalFlight) {
        Placemark placemark = folder.createAndAddPlacemark();
        placemark.withName(airportName)
                .withDescription("Arrival flight: "+ arrivalFlight + ". Arrival day: " + day)
                .createAndSetPoint().addToCoordinates(longitude,latitude);

        placemark.createAndSetPoint().addToCoordinates(longitude, latitude); // set coordinates
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

    public void setFormatter(String formatter) {
        try{
            this.formatter = new Formatter(formatter);
        }catch(Exception e){
            System.out.println("Loading error");
        }
    }
}
