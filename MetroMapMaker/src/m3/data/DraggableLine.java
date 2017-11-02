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
    
    @Override
    public m3State getStartingState() {
        return m3State.STARTING_LINE;
    }

    @Override
    public void start(int x, int y) {
        startX = x;
        startY = y;
        setStartX(x);
        setStartY(y);       
    }

    @Override
    public void drag(int x, int y) {
        setEndX(x);
        setEndY(y);
    }

    @Override
    public void size(int x, int y) {
        setStrokeWidth(y);
    }

    @Override
    public double getX() {
        return (startX + endX)/2;
    }

    @Override
    public double getY() {
        return (startY + endY)/2;
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        
    }
    
    public void setListOfStations(ArrayList<DraggableStation> initList){
        listOfStations = initList;
        //NEED TO CLONE BEFORE THIS
    }
    
    public ArrayList<DraggableStation> getListOfStations(){
        return listOfStations;
    }
    
    public void setName(String initName){
        name = initName;
    }
    
    public void setLineLabel1(){
        lineLabel1 = new DraggableLabel();
        lineLabel1.setContent(name);
        lineLabel1.setX(startX);
        lineLabel1.setY(startY);
    }
    
    public void setLineLabel2(){
        lineLabel2 = new DraggableLabel();
        lineLabel2.setContent(name);
        lineLabel2.setX(endX);
        lineLabel2.setY(endY);        
    }
    
    public void setColor(Color initColor){
        lineColor = initColor;
    }
    
    public Color getColor(){
        return lineColor;
    }
    
    public void addStation(DraggableStation newStation){
        
    }
    
    public void removeStation(DraggableStation removeStation){
        
    }
   
    @Override
    public String getShapeType() {
        return LINE;
    }

    @Override
    public double getWidth() {
        return getStrokeWidth();
    }
    
    public double getLength(){
        return Math.sqrt((endX-startX)*(endX-startX)+(endY-startY)*(endY-startY));
    }

    @Override
    public double getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Shape clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isStationOnTheLine(int x, int y){
        int slope = 0;
        if(endX - startX != 0)
            slope =  (int)((endY - startY) / (endX - startX));
        return (slope * (x - startX) == y - startY);   
    }
    
}
