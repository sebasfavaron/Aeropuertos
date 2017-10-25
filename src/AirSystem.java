package src;

import java.sql.Time;
import java.util.*;

public class AirSystem {

    private GraphAdjList airports;

    public List<Flight> compareByPrice(List<String> days){

        Comparator<Flight> cost=new Comparator<Flight>() {
            @Override
            public int compare(Flight flight, Flight t1) {
                return (int)(flight.getPrice()-t1.getPrice());
            }
        };
        // pasarle a una funcion del grafo que busque con nuestro comparador y que checke el dia
        return new ArrayList<Flight>();
    }

    public void addAirport(String airportName, Double lat, Double lng) {
        airports.addVertex(new Airport(airportName, lat, lng));
    }

    public void addFlight(String airline, Integer flightNumber, List<String> weekDays, String origin, String destination, Time departTime, Double duration, Double price) {
        Airport departure = airports.getVertex(origin);
        Airport arrival = airports.getVertex(destination);
        airports.addArc(departure, arrival, new Flight(airline, flightNumber, weekDays, departure, arrival, departTime, duration, price));
    }

    public void deleteAirport(String airportName) {
        airports.removeVertex(airports.getVertex(airportName));
    }

    public void deleteAllAirports() {
        airports.removeAllVertex();
    }
}