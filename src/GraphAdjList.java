package src;

import org.omg.CORBA.TIMEOUT;

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

    protected abstract boolean isDirected();

    public GraphAdjList() {
        this.nodes = new HashMap<Airport, Node>();
    }

    public boolean isEmpty() {
        return nodes.size() == 0;
    }


    public void addVertex(Airport vertex) {
        if (!nodes.containsKey(vertex)) {
            nodes.put(vertex, new Node(vertex));
        }
    }

    //HECHA POR SEBAS, NO SE SI ESTA BIEN
    public Airport getVertex(String vertex) {
        for(Node node : getNodes()) {
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
        for(Node node: getNodes()){
            for(Arc adj : node.adj){
                System.out.println(adj.info.toString());
            }
        }
    }

    //HECHA POR SEBAS, NO SE SI ESTA BIEN
    public Flight getArc(String flightName) {
        for(Node node : getNodes()) {
            for(Arc adj : node.adj) {
                if(adj.info.toString().equals(flightName))
                    return adj.info;
            }
        }
        return null;
    }

    public int arcCount() {
        int count = 0;
        for (Node n : getNodes())
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
        for (Node n : getNodes()) { // Recorremos lista de nodos
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
        for (Node n : getNodes()) {
            if (!n.equals(node))
                n.adj.remove(e);
        }

        // Eliminar el nodo
        nodes.remove(v);
    }

    public void removeAllVertex() {
        Iterator<Airport> it = nodes.keySet().iterator();
        while(it.hasNext()) {
            nodes.remove(it.next());
        }
    }

    public int vertexCount() {
        return nodes.size();
    }

    private List<Node> getNodes() {
        List<Node> l = new ArrayList<Node>(vertexCount());
        Iterator<Airport> it = nodes.keySet().iterator();
        while (it.hasNext()) {
            l.add(nodes.get(it.next()));
        }
        return l;
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
        for (Node n : getNodes()) {
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
        List<Node> l = getNodes();
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
        for(Node node : getNodes()) {
            if (! node.visited )
                return node;
        }
        return null;
    }

    public class PQNode{
        Node node;
        Double value;
        src.Time time;
        ArrayList<Flight> itinerary;
        public PQNode(Node node, Double value, src.Time time){
            this.node=node;
            this.value=value;
            this.time = time;
            itinerary=new ArrayList<>();
            //itinerary.push(flight);
        }
        public PQNode(Node node, Double value, Flight flight, src.Time time){
            this(node, value, time);
            itinerary.add(flight);
        }
        public PQNode(Node node, Double value, ArrayList<Flight> stack, Flight flight, src.Time time){
            this.node=node;
            this.value=value;
            this.time = time;
            itinerary=new ArrayList<>(stack);
            itinerary.add(flight);
        }
    }
    public ArrayList<Flight> minPrice(String from, String to, List<String> days){
        return minDistance(from, to, new GetValue() {
            @Override
            public double get(Flight nextFlight) {
                return nextFlight.getPrice();
            }
        },days);
    }
    public ArrayList<Flight> minFt(String from,String to,List<String> days){
        return minDistance(from, to, new GetValue() {
            @Override
            public double get(Flight nextFlight) {
                return nextFlight.getDuration().getHour()*60+nextFlight.getDuration().getMinute();
            }
        },days);
    }
    public ArrayList<Flight> minTt(String from, String to, List<String> days){
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
    }

    private ArrayList<Flight> minDistance(String from, String to, GetValue getValue, List<String> days){
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
            for(String day : arc.info.getWeekDay())
                if(!added && days.contains(day) && arc.info.getDeparture().getName().equals(f.info.getName())) {
                    src.Time time = new Time(parseDay(day), arc.info.getDepartureTime().getHour(), arc.info.getDepartureTime().getMinute());
                    pq.offer(new PQNode(arc.neighbor, getValue.get(arc.info), arc.info, time));
                    added = true;
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
                        Flight prevFlight = aux.itinerary.get(aux.itinerary.size() - 1);
                        for (String day:arc.info.getWeekDay()) {
                            pq.offer(new PQNode(arc.neighbor, aux.value + getValue.get(arc.info), aux.itinerary, arc.info, new Time(parseDay(day), arc.info.getDepartureTime().getHour()+arc.info.getDuration().getHour(), arc.info.getDepartureTime().getMinute()+arc.info.getDuration().getMinute())));
                        }
                    }
                }
           // }
        }
        return new ArrayList<>();
    }

    private int parseDay(String day) {
        switch (day) {
            case "Lun": return 1;
            case "Mar": return 2;
            case "Mie": return 3;
            case "Jue": return 4;
            case "Vie": return 5;
            case "Sab": return 6;
            case "Dom": return 7;
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
                    return (dias+aux)*60*24-horas*60;
                }else if (minutos>0){
                    return (dias+aux)*60*24-(horas+1)*60+(60-arrivo.getMinute()+salida.getMinute());
                }else {
                    return (dias+aux)*60*24-(horas+1)*60-(60-arrivo.getMinute()+salida.getMinute());
                }
            }
        }
    }
}