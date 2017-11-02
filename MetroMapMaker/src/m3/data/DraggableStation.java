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
    
    public DraggableStation() {
	setCenterX(0.0);
	setCenterY(0.0);
	setRadiusX(0.0);
	setRadiusY(0.0);
	setOpacity(1.0);
	startCenterX = 0.0;
	startCenterY = 0.0;
    }
    
    @Override
    public m3State getStartingState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void start(int x, int y) {
	startCenterX = x;
	startCenterY = y;
    }
    
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
        
    @Override
    public double getX() {
	return getCenterX() - getRadiusX();
    }

    @Override
    public double getY() {
	return getCenterY() - getRadiusY();
    }

    @Override
    public double getWidth() {
	return getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
	return getRadiusY() * 2;
    }    
  
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	setCenterX(initX + (initWidth/2));
	setCenterY(initY + (initHeight/2));
	setRadiusX(initWidth/2);
	setRadiusY(initHeight/2);
    }
    
    public void setName(String initName){
        name = initName;
    }
    
    public void setDraggableLable(){
        if(name != null){
            nameLabel = new DraggableLabel();
            nameLabel.setText(name);
        }
    }
    
    public void setColor(Color initColor){
        stationColor = initColor;
    }
    
    public Color getColor(){
        return stationColor;
    }
    
    public void moveLabel(){
        //https://stackoverflow.com/questions/12161277/how-to-rotate-a-vertex-around-a-certain-point
    }
    
    @Override
    public String getShapeType() {
	return STATION;
    }

    @Override
    public Shape clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
