/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This is a draggable image for our m3 application.
 * @author aaronsuen
 */
public class DraggableImage extends Rectangle implements Draggable{
    
    double startX;
    double startY;
    String imagePath;
    
    public DraggableImage(){
	setX(0.0);
	setY(0.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;        
    }
    
    @Override
    public m3State getStartingState() {
        return m3State.STARTING_IMAGE;
    }

    @Override
    public void start(int x, int y) {
 	startX = x;
	startY = y;
    }

    @Override
    public void drag(int x, int y) {
	double diffX = x - (getX() + (getWidth()/2));
	double diffY = y - (getY() + (getHeight()/2));
	double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }
    
    public String cT(double x, double y) {
	return "(x,y): (" + x + "," + y + ")";
    }

    @Override
    public void size(int x, int y) {
	
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
 	xProperty().set(initX);
	yProperty().set(initY);
    }
    
    public void setImagePath(String initImagePath){
        imagePath = initImagePath;
        setImage();
    }
    
    public void setImage(){
         if(imagePath != null)
            setFill(new ImagePattern(new Image(imagePath)));
    }

    @Override
    public String getShapeType() {
        return IMAGE;
    }
    
    public String getImagePath(){
        return imagePath;
    }
    
    public DraggableImage clone(){
        return null;
    }
    
}
