/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

/**
 *
 * This class is used as a data type to store all the lines and stations
 * @author Kai
 */

import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetroGraphController {
    private final ArrayList<DraggableLine> lines;
    private final ArrayList<DraggableStation> stations;
    private List<Vertex> nodes;
    private List<Edge> edges; 
    private int matrix[][];  
    private m3Data dataManager;
    
    public MetroGraphController(AppDataComponent data){
        dataManager = (m3Data)data;
        stations = dataManager.getM3Stations();
        lines = dataManager.getM3Lines();
    }
    
    public void processFindingRoute(DraggableStation from, DraggableStation to){
        start();
        findRoute(from,  to);
    }
    
    /**
     * This method is used to build the matrix.
     */
    public void start(){ 
        if(!stations.isEmpty()){
            int size = stations.size();
            matrix = new int[size][size];
        } //endIf
        for(int i = 0; i < lines.size(); i++){
           ArrayList<String> listOfStations = lines.get(i).getListOfStations();  
           DraggableLine line = lines.get(i);
           dataManager.sortStations(line);
           int row;
           int col;
           for(int j = 0; j < listOfStations.size() - 1; j++){
                String temp = listOfStations.get(j);
                row = stations.indexOf(temp);
                if(j == 0){
                    col = stations.indexOf(listOfStations.get(j + 1));
                    matrix[row][col] = 1;
                } else if(j == listOfStations.size() - 1){
                    col = stations.indexOf(listOfStations.get(j - 1));
                    matrix[row][col] = 1;
                } else {
                    col = stations.indexOf(listOfStations.get(j + 1));
                    matrix[row][col] = 1;
                    col = stations.indexOf(listOfStations.get(j - 1));
                    matrix[row][col] = 1;
                } //endElse
            } //endFor
        } //endFor
        excute();
    }
    
    public void excute(){
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        for(int i = 0; i < stations.size(); i++) {
            Vertex location = new Vertex(stations.get(i).getName());
            nodes.add(location);
        } //endFor
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                 addLane(i, j, 0);
    }
    
    public void findRoute(DraggableStation from, DraggableStation to){
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(new Vertex(from.getName()));
        LinkedList<Vertex> path = dijkstra.getPath(new Vertex(to.getName()));
        path.forEach((vertex) -> {
            System.out.println(vertex.getStation());
        });        
    }
    
    private void addLane(int sourceLocNo, int destLocNo, int duration) {
        Edge lane = new Edge(nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }    
}

class Vertex{
    final private String station;

    public Vertex(String station) {
        this.station = station;
    }
    
    public Vertex(){
        this.station = "";
    }
    
    public String getStation() {
        return station;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (other.getStation() == null) {
            return true;
        } else if (other.getStation().equals(this.getStation()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return station;
    }    
}

class Edge{
    private final Vertex source;
    private final Vertex destination;
    private final int weight;    
    
    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }  
    
    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }
    
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }    
}

class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}

class DijkstraAlgorithm {

    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;

    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<>(graph.getVertexes());
        this.edges = new ArrayList<>(graph.getEdges());
    }

    public void execute(Vertex source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<>();
        edges.stream().filter((edge) -> (edge.getSource().equals(node)
                && !isSettled(edge.getDestination()))).forEachOrdered((edge) -> {
                    neighbors.add(edge.getDestination());
        });
        return neighbors;
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
