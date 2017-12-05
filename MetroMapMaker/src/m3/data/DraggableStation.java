/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

/**
 * This is a draggable stop for our m3 application.
 * @author aaronsuen
 */
public class DraggableStation extends Ellipse implements Draggable{
    double startCenterX;
    double startCenterY;
    double radius;
    String name;
    Color stationColor;
    DraggableLabel nameLabel;
    ArrayList<String> listOfLines = new ArrayList<>();

    /**
     * Contrustor for initialing DraggableStation with default data.
     */       
    public DraggableStation() {
        setRadius(15.0);
	setOpacity(1.0);
	startCenterX = 0.0;
	startCenterY = 0.0;
        nameLabel = new DraggableLabel();
        nameLabel.xProperty().bind(this.centerXProperty().add(25));
        nameLabel.yProperty().bind(this.centerYProperty().add(25));
    }

    /**
     * Accessor method that is used to get the starting state of a DraggableStation.
     * 
     * @return ADDING_STATION state for creating station.
     */        
    @Override
    public m3State getStartingState() {
        return m3State.ADDING_STATION;
    }
 
    /**
     * The method that helps creating a station.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */      
    @Override
    public void start(int x, int y) {
	startCenterX = x;
	startCenterY = y;
	setCenterX(x);
	setCenterY(y);        
    }

    /**
     * The method that helps sizing and dragging to move or create this Station.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */     
    @Override
    public void drag(int x, int y) {
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
	double newX = getCenterX() + diffX;
	double newY = getCenterY() + diffY;
	setCenterX(newX);
	setCenterY(newY);
	startCenterX = x;
	startCenterY = y;
    }
    
    /**
     * The method that helps setting the stroke width of the station.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */         
    @Override
    public void size(int x, int y) {
	double width = x - startCenterX;
	double height = y - startCenterY;
	double centerX = startCenterX + (width / 2);
	double centerY = startCenterY + (height / 2);
	setCenterX(centerX);
	setCenterY(centerY);
	setRadiusX(width / 2);
	setRadiusY(height / 2);	
	
    }
 
    /**
     * Accessor method that is used to get the x coordinate of this DraggableStation.
     * 
     * @return x coordinate of the center of the station
     */     
    @Override
    public double getX() {
	return getCenterX() - getRadiusX();
    }

    /**
     * Accessor method that is used to get the y coordinate of this DraggableStation.
     * 
     * @return y coordinate of the center of the station
     */     
    @Override
    public double getY() {
	return getCenterY() - getRadiusY();
    }

    /**
     *  This Accessor method is used to get the width of the station.
     * 
     * @return  width station width
     */    
    @Override
    public double getWidth() {
	return getRadiusX() * 2;
    }
    
    /**
     *  This Accessor method is used to get the height of the station.
     * 
     * @return  height station height
     */
    @Override
    public double getHeight() {
	return getRadiusY() * 2;
    }    

   /**
     *  This method is used to set the location and size of the station.
     * 
     * @param initX x coordinate to set
     * @param initY y coordinate to set
     * @param initWidth width to set
     * @param initHeight height to set
     */        
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	setCenterX(initX + (initWidth/2));
	setCenterY(initY + (initHeight/2));
	setRadiusX(initWidth/2);
	setRadiusY(initHeight/2);
    }

    /**
     * The Accessor method to set the name of this station.
     * 
     * @param initName the name to set
     */    
    public void setName(String initName){
        name = initName;
        setNameLabel();
    }
    
    /**
     * The Accessor method to set the DraggableLable of this station.
     */
    private void setNameLabel(){
        if(name != null){
            nameLabel.setText(name);
            nameLabel.setIsBinded();
            nameLabel.setRotate(45);
        }    
    }
    
    public void setRadius(double initRadius){
        radius = initRadius;
        this.setRadiusX(radius);
        this.setRadiusY(radius);
    }
    
    /**
     * The Accessor method to set the color of this station.
     * 
     * @param initColor color to set
     */
    public void setColor(Color initColor){
        stationColor = initColor;
        this.setFill(stationColor);
    }
    
    public void moveLabel(){
        int labelQuadrant = nameLabel.getQuadrant();           
        switch (labelQuadrant) {
            case 1:
                nameLabel.xProperty().bind(this.centerXProperty().add(25));
                nameLabel.yProperty().bind(this.centerYProperty().add(-25));
                nameLabel.setQuadrant(2);
                break;
            case 2:
                nameLabel.xProperty().bind(this.centerXProperty().add(-25));
                nameLabel.yProperty().bind(this.centerYProperty().add(25));
                nameLabel.setQuadrant(3);
                break;
            case 3:
                nameLabel.xProperty().bind(this.centerXProperty().add(-25));
                nameLabel.yProperty().bind(this.centerYProperty().add(-25));        
                nameLabel.setQuadrant(4);
                break;
            default:
                nameLabel.xProperty().bind(this.centerXProperty().add(25));
                nameLabel.yProperty().bind(this.centerYProperty().add(25));
                nameLabel.setQuadrant(1);
                break;
        }           
    } 
    
    
    /**
     * The Accessor method to add the line into the collection for this station.
     * 
     * @param lineToAdd line to add
     */    
    public void addLine(DraggableLine lineToAdd){
        if(lineToAdd != null && !listOfLines.contains(lineToAdd.getName()))
            listOfLines.add(lineToAdd.getName());
    }
    
    /**
     * The Accessor method to remove the line into the collection for this station.
     * 
     * @param lineToRemove line to remove
     */        
    public void removeLine(DraggableLine lineToRemove){
        if(lineToRemove != null)
            listOfLines.remove(lineToRemove.getName());
    }
    
    /**
     * The Accessor method to set the list into the collection for this station.
     * 
     * @param listToSet list to set
     */      
    public void setListOfLine(ArrayList<String> listToSet){
        listOfLines = listToSet;
    }
    
    /**
     * The Accessor method to get this station color.
     * 
     * @return stationColor the color of this station
     */
    public Color getColor(){
        return stationColor;
    }
    
    /**
     * The Accessor method to set the DraggableLable of this station.
     * @return nameLabel
     */
    public DraggableLabel getNameLabel(){
        return nameLabel;
    }    
    
    /**
     * The Accessor method to get the shapeType.
     *
     * @return STATION the shapeType of DraggableLabel
     */           
    @Override
    public String getNodeType() {
	return STATION;
    }

    /**
     * This method clone all properties of this DraggableStation.
     * @return the cloned DraggableStation
     */      
    @Override
    public Shape clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * The Accessor method to get the name of this station.
     * 
     * @return name the name of this station
     */        
    @Override
    public String getName() {
        return name;
    }
 
    /**
     * The Accessor method to get the name of this station.
     * 
     * @return radius the radius of this station
     */     
    public double getRadius(){
        return radius;
    }
  
    /**
     * The Accessor method to get the line collection of this station.
     * 
     * @return listOfLines
     */        
    public ArrayList<String> getListOfLines(){
        return listOfLines;
    }
    
   @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DraggableStation other = (DraggableStation) obj;
        
        if (!name.equals(other.name))
            if(stationColor == other.getColor())
            if(radius != other.getRadius())
                return false;
        return true;
    }
    
}
