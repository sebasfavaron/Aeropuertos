import java.util.*;

public class AirSystem {
    private GraphAdjList<Airport,Flight> airports;

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
}