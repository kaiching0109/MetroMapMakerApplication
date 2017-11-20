/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

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
    String name;
    Color stationColor;
    
    DraggableLabel nameLabel;

    /**
     * Contrustor for initialing DraggableStation with default data.
     */       
    public DraggableStation() {
	setRadiusX(5.0);
	setRadiusY(5.0);
	setOpacity(1.0);
	startCenterX = 0.0;
	startCenterY = 0.0;
        nameLabel = new DraggableLabel();
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
            nameLabel.setX(startCenterX + getRadiusX());
            nameLabel.setY(startCenterY+ getRadiusY());
        }    
    }
    
    /**
     * The Accessor method to set the color of this station.
     * 
     * @param initColor color to set
     */
    public void setColor(Color initColor){
        stationColor = initColor;
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
     */
    public DraggableLabel getNameLabel(){
        return nameLabel;
    }    
    
    /**
     * This method moves the label clockwisely.
     */
    public void moveLabel(){
        //https://stackoverflow.com/questions/12161277/how-to-rotate-a-vertex-around-a-certain-point
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
}
