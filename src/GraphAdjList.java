package src;

import org.omg.CORBA.TIMEOUT;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.util.*;

/**
 *
 * Clase para Grafos con nodos Aeropuertos y aristas Vuelos. No soporta multigrafos ni lazos
 *
 */
public abstract class GraphAdjList{

    private class Node {
        public Airport info;
        public boolean visited;
        public List<Arc> adj;

        public Node(Airport info) {
            this.info = info;
            this.visited = false;
            this.adj = new ArrayList<Arc>();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((info == null) ? 0 : info.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Node other = (Node) obj;
            if (info == null) {
                if (other.info != null)
                    return false;
            } else if (!info.equals(other.info))
                return false;
            return true;
        }
    }

    private class Arc {
        public Flight info;
        public Node neighbor;
        public boolean visited;

        public Arc(Flight info, Node neighbor) {
            super();
            this.info = info;
            this.neighbor = neighbor;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((neighbor == null) ? 0 : neighbor.hashCode());
            return result;
        }

        // Considero que son iguales si "apuntan" al mismo nodo (para no agregar
        // dos ejes al mismo nodo)
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Arc other = (Arc) obj;
            if (info == null) {
                if (other.info != null)
                    return false;
            } else if (!info.equals(other.info))
                return false;
            return true;
        }

    }
    private HashMap<Airport, Node> nodes;
    private List<Node> nodeList;

    protected boolean isDirected(){
        return true;
    }

    public GraphAdjList() {
        this.nodes = new HashMap<Airport, Node>();
        this.nodeList = new LinkedList<>();
    }

    public boolean isEmpty() {
        return nodes.size() == 0;
    }


    public void addVertex(Airport vertex) {
        for (Node node : nodeList){
            if (node.info.getLat().equals(vertex.getLat()) && node.info.getLng().equals(vertex.getLng())) {
                System.out.println("An airport already exists in that position");
                return;
            }
        }
        if (!nodes.containsKey(vertex)) {
            Node n = new Node(vertex);
            nodes.put(vertex, n);
            nodeList.add(n);
        }
    }

    //HECHA POR SEBAS, NO SE SI ESTA BIEN
    public Airport getVertex(String vertex) {
        for(Node node : nodeList) {
            if(node.info.toString().equals(vertex))
                return node.info;
        }
        return null;
    }

    public void addArc(Airport v, Airport w, Flight e) {
        Node origin = nodes.get(v);
        Node dest = nodes.get(w);
        if (origin != null && dest != null && !origin.equals(dest)) {
            Arc arc = new Arc(e, dest);
            if (!origin.adj.contains(arc)) {
                origin.adj.add(arc);
                if (!isDirected()) {
                    dest.adj.add(new Arc(e, origin));
                }
            }
        }
    }
    public void printArcs(){
        for(Node node: nodeList){
            System.out.println("-Airport: "+node.info.getName());
            for(Arc adj : node.adj){
                if(adj.info.getDeparture().equals(node.info)){
                    System.out.print("Departure: ");
                }else{
                    System.out.print("Arrival: ");
                }
                System.out.println(adj.info.toString());
            }
        }
    }

    //HECHA POR SEBAS, NO SE SI ESTA BIEN
    public Flight getArc(String flightName) {
        for(Node node : nodeList) {
            for(Arc adj : node.adj) {
                if(adj.info.toString().equals(flightName))
                    return adj.info;
            }
        }
        return null;
    }

    public int arcCount() {
        int count = 0;
        for (Node n : nodeList)
            count += n.adj.size();
        if (!isDirected())
            count /= 2;
        return count;
    }


    public void removeArc(Airport v, Airport w) {
        Node origin = nodes.get(v);
        if (origin == null)
            return;
        Node dest = nodes.get(w);
        if (dest == null)
            return;
        origin.adj.remove(new Arc(null, dest));
        if (!isDirected())
            dest.adj.remove(new Arc(null, origin));
    }

    public void removeAllArc() {
        for(Node node : nodes.values()) {
            //piso todas las listas de Arc's, borrando asi las aristas entre aeropuertos
            node.adj = new ArrayList<>();
        }
    }

    public Flight isArc(Airport v, Airport w) {
        Node origin = nodes.get(v);
        if (origin == null)
            return null;

        for (Arc e : origin.adj) {
            if (e.neighbor.info.equals(w)) {
                return e.info;
            }
        }
        return null;

    }

    public int outDegree(Airport v) {
        Node node = nodes.get(v);
        if (node != null) {
            return node.adj.size();
        }
        return 0;
    }


    public int inDegree(Airport v) {
        if (!isDirected())
            return outDegree(v);
        int count = 0;
        Node node = nodes.get(v);
        for (Node n : nodeList) { // Recorremos lista de nodos
            if (!n.equals(node)) {
                for (Arc adj : n.adj)
                    // Recorremos lista de adyacencia
                    if (adj.neighbor.equals(node))
                        count++;
            }
        }
        return count;
    }


    public List<Airport> neighbors(Airport v) {
        Node node = nodes.get(v);
        if (node == null)
            return null;

        List<Airport> l = new ArrayList<Airport>(node.adj.size());
        for (Arc e : node.adj) {
            l.add(e.neighbor.info);
        }
        return l;
    }


    public void removeVertex(Airport v) {
        Node node = nodes.get(v);
        if (node == null)
            return;

        // Primero removerlo de la lista de adyacencia de sus vecinos
        Arc e = new Arc(null, node);
        for (Node n : nodeList) {
            if (!n.equals(node))
                n.adj.remove(e);
        }

        // Eliminar el nodo
        nodes.remove(v);
    }

    public void removeAllVertex() {
        this.nodes = new HashMap<Airport, Node>();
        this.nodeList = new LinkedList<>();
    }

    public int vertexCount() {
        return nodes.size();
    }

    public List<Airport> DFS(Airport origin) {
        Node node = nodes.get(origin);
        if (node == null)
            return null;
        clearMarks();
        List<Airport> l = new ArrayList<Airport>();
        this.DFS(node, l);
        return l;
    }

    protected void clearMarks() {
        for (Node n : nodeList) {
            n.visited = false;
        }
    }


    protected void DFS(Node origin, List<Airport> l) {
        if (origin.visited)
            return;
        l.add(origin.info);
        origin.visited = true;
        for (Arc e : origin.adj)
            DFS(e.neighbor, l);
    }


    public List<Airport> BFS(Airport origin) {
        Node node = nodes.get(origin);
        if (node == null)
            return null;
        clearMarks();
        List<Airport> l = new ArrayList<Airport>();

        Queue<Node> q = new LinkedList<Node>();
        q.add(node);
        node.visited = true;
        while (!q.isEmpty()) {
            node = q.poll();
            l.add(node.info);
            for (Arc e : node.adj) {
                if (!e.neighbor.visited) {
                    e.neighbor.visited = true;  // Para evitar volve a encolarlo
                    q.add(e.neighbor);
                }
            }
        }
        return l;
    }

    public boolean isConnected() {
        if (isEmpty()) {
            return true;
        }
        clearMarks();
        List<Node> l = nodeList;
        List<Airport> laux = new ArrayList<Airport>();
        DFS(l.get(0), laux);
        for (Node node : l) {
            if (!node.visited) {
                return false;
            }
        }
        return true;
    }

    public int connectedComponents() {
        clearMarks();
        int count = 0;
        Node node;
        while ((node = unvisited()) != null) {
            count++;
            DFS(node, new ArrayList<Airport>());
        }
        return count;
    }

    private Node unvisited() {
        for(Node node : nodeList) {
            if (! node.visited )
                return node;
        }
        return null;
    }

    public class PQNode{
        Node node;
        //Double value;
        src.Time time;
        ArrayList<Flight> itinerary;
        ArrayList<String> days;
        public Double price;
        public Double ft;
        public Double tt;
        public PQNode(Node node, Double price, Double ft, Double tt, src.Time time){
            this.node=node;
            //this.value=value;
            this.time = time;
            itinerary=new ArrayList<>();
            days=new ArrayList<>();
            this.price=price;
            this.ft=ft;
            this.tt=tt;
            //itinerary.push(flight);
        }
        public PQNode(Node node, Double price, Double ft, Double tt, Flight flight,String day, src.Time time){
            this(node, price,ft,tt, time);
            itinerary.add(flight);
            days.add(day);
        }
        public PQNode(Node node,Double price, Double ft, Double tt, ArrayList<Flight> stack, Flight flight,ArrayList<String> days,String day, src.Time time){
            this.node=node;
            //this.value=value;
            this.time = time;
            itinerary=new ArrayList<>(stack);
            itinerary.add(flight);
            this.days=new ArrayList<>(days);
            this.days.add(day);
            this.price=price;
            this.ft=ft;
            this.tt=tt;
        }
    }
    public PQNode minPrice(String from, String to, List<String> days){
        return minDistance(from, to, new Comparator<PQNode>() {
            @Override
            public int compare(PQNode pqNode, PQNode t1) {
                return (int)( pqNode.price-t1.price);
            }
        }, days);
    }
    public PQNode minFt(String from,String to,List<String> days){
        return minDistance(from, to, new Comparator<PQNode>() {
            @Override
            public int compare(PQNode pqNode, PQNode t1) {
                return (int)(pqNode.ft-t1.ft);
            }
        }, days);
    }
    public PQNode minTt(String from, String to, List<String> days){
        return minDistance(from, to, new Comparator<PQNode>() {
            @Override
            public int compare(PQNode pqNode, PQNode t1) {
                return (int)(pqNode.tt-t1.tt);
            }
        }, days);
        /*
        Node f = null, t = null;
        for(Airport a : nodes.keySet()) {
            if(a.getName().equals(from))
                f = nodes.get(a);
            if(a.getName().equals(to))
                t = nodes.get(a);

        }
        if(f == null||t == null) {
            //throw new MyException();
            return null;
        }
        clearMarks();	//	Luego voy a marcar los nodos que ya use para no llegar la lista en loop
        //tendria que cambiar el codigo para q acepte comparators
        PriorityQueue<PQNode> pq = new PriorityQueue<>(new Comparator<PQNode>() {
            @Override
            public int compare(PQNode node, PQNode t1) {
                //la cambie para que, por ej, 11.1 sea mayor a 11.0. Sino con '(int)' retornaba 0
                double diff = node.value-t1.value;
                if(diff == 0)
                    return 0;
                else if(diff > 0)
                    return 1;
                else
                    return -1;
            }
        });
        for (Arc arc : f.adj) {
            boolean added = false;
            for(String day : arc.info.getWeekDay()) {
                if (!added && days.contains(day) && arc.info.getDeparture().getName().equals(f.info.getName())) {
                    src.Time time = new Time(parseDay(day), arc.info.getDepartureTime().getHour(), arc.info.getDepartureTime().getMinute());
                    src.Time auxtime = new Time(parseDay(day), arc.info.getDepartureTime().getHour() + arc.info.getDuration().getHour(), arc.info.getDepartureTime().getMinute() + arc.info.getDuration().getMinute());
                    pq.offer(new PQNode(arc.neighbor, (double) arc.info.getDuration().getHour() * 60 + arc.info.getDuration().getMinute(), arc.info, auxtime));
                    added = true;
                }
            }
        }
        //PQNode aux;
        //Stack<Flight> itinerary=new Stack();

        while(!pq.isEmpty()) {
            PQNode aux = pq.poll();
            if(aux.node == t)
                return aux.itinerary;
            //if(!aux.node.visited) {
                //aux.node.visited = true;	//Si o si hay que marcarlo cuando lo saco.
                for(Arc	arc : aux.node.adj) {
                    if(!aux.itinerary.contains(arc) && arc.info.getDeparture().getName().equals(aux.node.info.getName())) {
                        //arc.visited=true;
                        //Flight prevFlight = aux.itinerary.get(aux.itinerary.size() - 1);
                        for (String day:arc.info.getWeekDay()) {
                            Time salida=new Time(parseDay(day),arc.info.getDepartureTime().getHour(),arc.info.getDepartureTime().getMinute());
                            src.Time auxtime = new Time(parseDay(day),arc.info.getDepartureTime().getHour()+arc.info.getDuration().getHour(), arc.info.getDepartureTime().getMinute()+arc.info.getDuration().getMinute());
                            pq.offer(new PQNode(arc.neighbor, aux.value +tiempoDeEspera(aux.time,salida)+arc.info.getDuration().getHour()*60+arc.info.getDuration().getMinute(), aux.itinerary, arc.info,auxtime));
                        }
                    }
                }
            //}
        }
        return new ArrayList<>();
        */
    }

    private PQNode minDistance(String from, String to, Comparator<PQNode> cmp, List<String> days){
        Node f = null, t = null;
        for(Airport a : nodes.keySet()) {
            if(a.getName().equals(from))
                f = nodes.get(a);
            if(a.getName().equals(to))
                t = nodes.get(a);
        }
        if(f == null||t == null) {
            //throw new MyException();
            return null;
        }
        clearMarks();	//	Luego voy a marcar los nodos que ya use para no llegar la lista en loop
        //tendria que cambiar el codigo para q acepte comparators
        PriorityQueue<PQNode> pq = new PriorityQueue<>(cmp);
        for (Arc arc : f.adj) {
            boolean added = false;
            for(String day : arc.info.getWeekDay()){
                if(!added && days.contains(day) && arc.info.getDeparture().getName().equals(f.info.getName())) {
                    src.Time time = new Time(parseDay(day), arc.info.getDepartureTime().getHour(), arc.info.getDepartureTime().getMinute());
                    pq.offer(new PQNode(arc.neighbor, arc.info.getPrice(),(double) arc.info.getDuration().getHour() * 60 + arc.info.getDuration().getMinute(),(double) arc.info.getDuration().getHour() * 60 + arc.info.getDuration().getMinute(), arc.info,day, time));
                    added = true;
                }
            }
        }
        //PQNode aux;
        //Stack<Flight> itinerary=new Stack();
        while(!pq.isEmpty()) {
            PQNode aux = pq.poll();
            if(aux.node == t)
                return aux;
            //if(!aux.node.visited) {
                //aux.node.visited = true;	//Si o si hay que marcarlo cuando lo saco.
                for(Arc	arc : aux.node.adj) {
                    if(!aux.itinerary.contains(arc) && arc.info.getDeparture().getName().equals(aux.node.info.getName())) {
                        //Flight prevFlight = aux.itinerary.get(aux.itinerary.size() - 1);
                        for (String day:arc.info.getWeekDay()) {
                            Time salida=new Time(parseDay(day),arc.info.getDepartureTime().getHour(),arc.info.getDepartureTime().getMinute());
                            src.Time auxtime = new Time(parseDay(day),arc.info.getDepartureTime().getHour()+arc.info.getDuration().getHour(), arc.info.getDepartureTime().getMinute()+arc.info.getDuration().getMinute());
                            pq.offer(new PQNode(arc.neighbor, aux.price + arc.info.getPrice(),aux.ft + arc.info.getDuration().getHour() * 60 + arc.info.getDuration().getMinute(),tiempoDeEspera(aux.time,salida)+arc.info.getDuration().getHour()*60+arc.info.getDuration().getMinute(), aux.itinerary, arc.info,aux.days,day, auxtime));
                        }
                    }
                }
           // }
        }
        return null;
    }

    public List<Flight> worldTripPrice(String airName, GetValue getValue) {
        Node n = null;
        for(Node node: nodeList)
            if(node.info.getName().equals(airName))
                n = node;
        if(n == null)
            return null;
        List<Node> l = new LinkedList<>();
        List<Flight> solution = new LinkedList<>();
        List<List<Flight>> solutions = new LinkedList<>();
        clearMarks();
        worldTripPriceRec(n, n, l, solution, solutions);
        List<Flight> bestPath = null;
        Double bestPerformance = null;
        //System.out.println("----------------------------WorldTrip----------------------------");
        for(List<Flight> list: solutions) {
            double localPerformance = getPerformance(list, getValue);
            //System.out.println(list.toString()+" "+localPerformance);
            if(bestPerformance == null || localPerformance<bestPerformance) {
                bestPath = list;
                bestPerformance = localPerformance;
            }
        }
        //System.out.println("Best: "+bestPath.toString()+" "+bestPerformance);
        //System.out.println("-----------------------------------------------------------------");

        return bestPath;
    }

    private double getPerformance(List<Flight> list, GetValue getValue) {
        double ret = 0;
        for(Flight flight: list) {
            if(flight == null)
                return -1;
            ret += getValue.get(flight);
        }
        return ret;
    }

    private void worldTripPriceRec(Node start, Node node, List<Node> l, List<Flight> sol, List<List<Flight>> solutions) {
        node.visited = true;
        l.add(node);
        for(Arc arc: node.adj) {
            if(l.size() == nodeList.size() && arc.neighbor.info.getName().equals(start.info.getName())) {
                l.add(start);
                sol.add(arc.info);
                solutions.add(new LinkedList<>(sol));
                sol.remove(sol.size()-1);
                l.remove(l.size()-1);
            }
            else if(!arc.neighbor.visited) {
                sol.add(arc.info);
                worldTripPriceRec(start, arc.neighbor, l, sol, solutions);
            }
        }
        l.remove(l.size()-1);
        if(sol.size() != 0)
            sol.remove(sol.size()-1);
        node.visited = false;
    }

    private int parseDay(String day) {
        switch (day) {
            case "Lu": return 1;
            case "Ma": return 2;
            case "Mi": return 3;
            case "Ju": return 4;
            case "Vi": return 5;
            case "Sa": return 6;
            case "Do": return 7;
        }
        throw new IllegalArgumentException();
    }
    private double tiempoDeEspera(Time arrivo,Time salida){
        int horas=salida.getHour()-arrivo.getHour();
        int minutos=salida.getMinute()-arrivo.getMinute();
        int dias=salida.getWeekDay()-arrivo.getWeekDay();
        int aux=0;
        if (dias<0){
            aux+=8;
        }
        if (horas==0){
            if (minutos == 0) {
                return (dias+aux)*60*24;
            }
        }
        if (dias==0){
            if (horas==0){
                if (minutos>0){
                    return minutos;
                }else {
                    return 7*60*24+minutos;
                }
            }else if (horas>0){
                if (minutos==0){
                    return horas*60;
                }else if (minutos<0){
                    return (horas-1)*60+60-arrivo.getMinute()+salida.getMinute();//other.minute+minute;
                }else{
                    return (horas-1)*60+60-arrivo.getMinute()+salida.getMinute();//-other.minute+minute;
                }
            }else {//horas<0
                if (minutos==0){
                    return 7*60*24+horas*60;
                }else if (minutos<0){
                    return 7*60*24+(horas+1)*60-(60-arrivo.getMinute()+salida.getMinute());
                }else {
                    return 7*60*24+(horas+1)*60+(60-arrivo.getMinute()+salida.getMinute());
                }
            }
        }else {
            if (horas==0){
                if (minutos>0){
                    return (dias+aux)*60*24-minutos;
                }else{
                    return (dias+aux)*60*24-minutos;
                }
            }else if (horas>0){
                if (minutos==0){
                    return (dias+aux)*60*24-horas*60;
                }else if (minutos>0){
                    return (dias+aux)*60*24-(horas-1)*60-(60-arrivo.getMinute()+salida.getMinute());
                }else {
                    return (dias+aux)*60*24-(horas-1)*60-(60-arrivo.getMinute()+salida.getMinute());
                }
            }else {//horas<0
                if (minutos==0){
                    return (dias+aux)*60*24+horas*60;
                }else if (minutos>0){
                    return (dias+aux)*60*24-(horas+1)*60+(60-arrivo.getMinute()+salida.getMinute());
                }else {
                    return (dias+aux)*60*24-(horas+1)*60-(60-arrivo.getMinute()+salida.getMinute());
                }
            }
        }
    }
}