/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This is a draggable image for our m3 application.
 * @author Kai
 */
public class DraggableImage extends Rectangle implements Draggable{
    
    double startX;
    double startY;
    String imagePath;
    
    /**
     * Contrustor for initialing DraggableImage with default data
     */
    public DraggableImage(){
	setX(0.0);
	setY(0.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;        
    }
    
    /**
     * Accessor method that is used to get the starting state of a DraggableImage.
     * 
     * @return STARTING_IMAGE state for creating image
     */
    @Override
    public m3State getStartingState() {
        return m3State.STARTING_IMAGE;
    }

    /**
     * The method that helps creating an Image.
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
     * The method that helps sizing and dragging to move or create an Image.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */    
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
    
    /**
     * The method that printing the x and y in coordinate form
     * 
     * @param x  x is the x coordinate.
     * @param y  y is the y coordinate.
     */    
    public String cT(double x, double y) {
	return "(x,y): (" + x + "," + y + ")";
    }

    /**
     * This method uses to size our image.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */    
    @Override
    public void size(int x, int y) {
	double width = x - getX();
	widthProperty().set(width);
	double height = y - getY();
	heightProperty().set(height);	
    }
    
    /**
     *  This method is used to set the location and size of the image
     * @param initX x coordinate to set
     * @param initY y coordinate to set
     * @param initWidth width to set
     * @param initHeight height to set
     */
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
 	xProperty().set(initX);
	yProperty().set(initY);
        widthProperty().set(initWidth);
        heightProperty().set(initHeight);	
        
    }
    
    /**
     * The Accessor method to set the path of the image that user wants
     * to insert.
     * 
     * @param initImagePath 
     */
    public void setImagePath(String initImagePath){
        imagePath = initImagePath;
        setImage();
    }

    /**
     * The Accessor method to set the image that user wants
     * to insert.
     */    
    public void setImage(){
         if(imagePath != null)
            setFill(new ImagePattern(new Image(FILE_PROTOCOL + imagePath)));
    }
    
    /**
     * The Accessor method to get the shapeType.
     *
     * @return IMAGE the shapeType of DraggableImage
     */    
    @Override
    public String getNodeType() {
        return IMAGE;
    }
    
    /**
     * The Accessor method to get the image path.
     *
     * @return imagePath the image path of the Image that user wants to insert.
     */      
    public String getImagePath(){
        return imagePath;
    }
 
    /**
     * This method clone all properties of this DraggableImage.
     * @return the cloned DraggableImage
     */
    public DraggableImage clone(){
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
