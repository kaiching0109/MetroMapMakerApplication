/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import java.util.List;

/**
 *
 * This class is used as a data type to store all the lines and stations
 * @author Kai
 */
public abstract class Map {
    private int numLines;
    private int numStations;
    
    /**
     * Constructor of map
     */
    public Map(){

    }
    
    /**
     * This is used to get the number of the lines.
     * 
     * @return numLines number of lines 
     */
    public int getNumLines(){
        return numLines;
    }

    /**
     * This is used to get the number of the stations.
     * 
     * @return numStations number of stations 
     */    
    public int getNumStations(){
        return numStations;
    }
    
    /**
     * This is used to add line into the existed graph.
     */
    public abstract void implementAddLine();
    
    /**
     * This is used to add station into the existed graph.
     */    
    public abstract void implementAddStation();
    
    /**
     * This is used to get the neihbors of the line or station
     * 
     * @param v index of the line/ station
     * @return all lines that own the station
     */
    public abstract List<Integer> getNeihbors(int v);

}
