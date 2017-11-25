/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * This is a draggable label for our m3 application.
 * @author aaronsuen
 */
public class DraggableLabel extends Text implements Draggable{
    double startX;
    double startY;
    String text;
    Boolean isBinded;

    /**
     * Contrustor for initialing DraggableLabel with default data.
     * This should be called if the label doesn't belong
     */    
    public DraggableLabel(){
        setOpacity(1.0);
        setFill(Color.BLACK);
        isBinded = false;
    }
    
    /**
     * Contrustor for initialing DraggableLabel with default data.
     */
 
    /**
     * The method that helps creating an Label.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */    
     @Override
    public void start(int x, int y) {
        startX = x;
	startY = y;
    }

    /**
     * The method that helps sizing and dragging to move or create a Label.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */       
    @Override
    public void drag(int x, int y) {
        if(!isBinded){
            double diffX = x - startX;
            double diffY = y - startY;
            double newX = getX() + diffX;
            double newY = getY() + diffY;
            xProperty().set(newX);
            yProperty().set(newY);
            startX = x;
            startY = y; 
        }    
    }
    
    /**
     * NOT SUPPORT
     * @param x
     * @param y 
     */   
    @Override
    public void size(int x, int y) {	
    }

    /**
     *  This method is used to set the location and size of the label
     * @param initX x coordinate to set
     * @param initY y coordinate to set
     * @param initWidth width to set
     * @param initHeight height to set
     */    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
       	xProperty().set(initX);
	yProperty().set(initY);
    }

    /**
     * Accessor method that is used to set the font family of this DraggableLabel.
     * 
     * @param initText content to set.
     */               
    public void setContent(String initText){
        text = initText;
        this.setText(text);  
    }
    
    /**
     * Accessor method that is used to set if the label is binded.
     * 
     */               
    public void setIsBinded(){
        isBinded = true;
    }    
 
    /**
     * Accessor method that is used to get the content of this DraggableLabel.
     * 
     * @return text text is the content of this DraggableLabel.
     */    
    public String getContent(){
        return text;
    }
    
    /**
     * Accessor method that is used to get the starting state of a DraggableLabel.
     * 
     * @return ADDING_LABEL state for creating label
     */
    @Override
    public m3State getStartingState() {
        return m3State.ADDING_LABEL;
    }

    /**
     *  NOT SUPPORTED
     * @return 
     */    
    @Override
    public double getWidth() {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *  NOT SUPPORTED
     * @return 
     */
    @Override
    public double getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isBinded(){
        return isBinded;
    }
    
    /**
     * The Accessor method to get the shapeType.
     *
     * @return LABEL the shapeType of DraggableLabel
     */        
    @Override
    public String getNodeType() {
	return LABEL;
    }
 
    /**
     * This method clone all properties of this DraggableLabel.
     * @return the cloned DraggableLabel
     */  
    @Override
    public Shape clone() {
        return null;
    }

    /**
     * NOT SUPPORT
     * @return 
     */
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * NOT SUPPORT 
     * @param initName
     */        
    @Override
    public void setName(String initName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
