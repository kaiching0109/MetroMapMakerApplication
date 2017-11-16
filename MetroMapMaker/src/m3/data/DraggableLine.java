    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
/**
 * This is a draggable line for our m3 application.
 * @author aaronsuen
 */
public class DraggableLine extends Line implements Draggable{
    
    double startX;
    double startY;
    double endX;
    double endY;
    String name;
    Color lineColor;
    DraggableLabel lineLabel1;
    DraggableLabel lineLabel2;
    ArrayList<DraggableStation> listOfStations= new ArrayList<>();

    /**
     * Contrustor for initialing DraggableLine with default data.
     */    
    public DraggableLine(){
        setStartX(0.0);
        setStartY(0.0);
        setEndX(0.0);
        setEndY(0.0);
        startX = 0.0;
        startY= 0.0;
        endX = 0.0;
        endY = 0.0;
    }

    /**
     * Accessor method that is used to get the starting state of a DraggableLine.
     * 
     * @return STARTING_LINE state for creating line
     */    
    @Override
    public m3State getStartingState() {
        return m3State.STARTING_LINE;
    }

    /**
     * The method that helps creating a line.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */        
    @Override
    public void start(int x, int y) {
        startX = x;
        startY = y;
        setStartX(x);
        setStartY(y);       
    }

    /**
     * The method that helps sizing and dragging to move or create this Line.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */           
    @Override
    public void drag(int x, int y) {
        setEndX(x);
        setEndY(y);
    }

    /**
     * The method that helps setting the stroke width of the line.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */         
    @Override
    public void size(int x, int y) {
        setStrokeWidth(y);
    }

    /**
     * Accessor method that is used to get the x coordinate of this DraggableLine.
     * 
     * @return x coordinate of the center of the line
     */    
    @Override
    public double getX() {
        return (startX + endX)/2;
    }

    /**
     * Accessor method that is used to get the y coordinate of this DraggableLine.
     * 
     * @return y coordinate of the center of the line
     */        
    @Override
    public double getY() {
        return (startY + endY)/2;
    }
    
    /**
     * NOT SUPPORT
     * 
     * @param initX
     * @param initY
     * @param initWidth
     * @param initHeight 
     */
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        
    }
    
    /**
     * This method is used to set the list of stations.
     * 
     * @param initList the list of stations to set.
     */
    public void setListOfStations(ArrayList<DraggableStation> initList){
        listOfStations = initList;
        //NEED TO CLONE BEFORE THIS
    }
 
    /**
     * This method is used to get the list of stations on this line.
     * 
     * @return listOfStations the list of stations on this line.
     */    
    public ArrayList<DraggableStation> getListOfStations(){
        return listOfStations;
    }

    /**
     * This method is used to set the name of the line.
     * 
     * @param initName the list of stations to set.
     */    
    public void setName(String initName){
        name = initName;
    }

    /**
     * This method is used to set the label of the line.
     */        
    public void setLineLabel1(){
        lineLabel1 = new DraggableLabel();
        lineLabel1.setContent(name);
        lineLabel1.setX(startX);
        lineLabel1.setY(startY);
        lineLabel1.setOnMousePressed(e->{
            setStartX(lineLabel1.getX());
            setStartY(lineLabel1.getY());
        });
    }
    
    /**
     * This method is used to set the label of the line.
     */         
    public void setLineLabel2(){
        lineLabel2 = new DraggableLabel();
        lineLabel2.setContent(name);
        lineLabel2.setX(endX);
        lineLabel2.setY(endY);     
        lineLabel2.setOnMousePressed(e->{
            setEndX(lineLabel2.getX());
            setEndY(lineLabel2.getY());
        });        
    }
 
    /**
     * This method is used to set the color of the line.
     * 
     * @param initColor color to set.
     */      
    public void setColor(Color initColor){
        lineColor = initColor;
    }

    /**
     * This method is used to get the color of the line.
     * 
     * @return lineColor the color of the line.
     */      
    public Color getColor(){
        return lineColor;
    }
    
    /**
     * This method is used to add a station on the line.
     * 
     * @param newStation station to add
     */      
    public void addStation(DraggableStation newStation){
        
    }
   
    /**
     * This method is used to remove a station on the line.
     * 
     * @param removeStation station to remove
     */     
    public void removeStation(DraggableStation removeStation){
        
    }
   
    /**
     * The Accessor method to get the shapeType.
     *
     * @return LINE the shapeType of DraggableLabel
     */        
    @Override
    public String getNodeType() {
        return LINE;
    }

    /**
     * The Accessor method to get the width.
     *
     * @return strokeWidth of the Line
     */        
    @Override
    public double getWidth() {
        return getStrokeWidth();
    }
 
    /**
     * The Accessor method to get the length.
     *
     * @return length of the Line
     */     
    public double getLength(){
        return Math.sqrt((endX-startX)*(endX-startX)+(endY-startY)*(endY-startY));
    }
    
    /**
     * NOT SUPPORT
     * 
     * @return 
     */
    @Override
    public double getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * The Accessor method to get the name of this line.
     * 
     * @return name the name of this line.
     */        
    @Override
    public String getName(){
        return name;
    }
    
   /**
     * This method clone all properties of this DraggableLine.
     * @return the cloned DraggableLine
     */  
    @Override
    public Shape clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method is used to check if the station is on this line
     * @param x the x coordinate of the station
     * @param y the y coordinate of the station
     * @return true / false
     */
    public boolean isStationOnTheLine(int x, int y){
        int slope = 0;
        if(endX - startX != 0)
            slope =  (int)((endY - startY) / (endX - startX));
        return (slope * (x - startX) == y - startY);   
    }
    
}
