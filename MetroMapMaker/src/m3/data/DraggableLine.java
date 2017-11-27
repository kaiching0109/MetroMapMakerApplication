    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
/**
 * This is a draggable line for our m3 application.
 * @author aaronsuen
 */
public class DraggableLine extends Polyline implements Draggable{
    
    double startX;
    double startY;
    double endX;
    double endY;
    String name;
    Color color;
    DraggableLabel lineLabel1;
    DraggableLabel lineLabel2;
    ArrayList<String> listOfStations = new ArrayList<>();

    /**
     * Contrustor for initialing DraggableLine with default data.
     */    
    public DraggableLine(){
        startX = 0.0;
        startY= 0.0;
        endX = 0.0;
        endY = 0.0;
        this.setStrokeWidth(5);
        setLineLabel1();
        setLineLabel2();
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
    }

    /**
     * The method that helps sizing and dragging to move or create this Line.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */           
    @Override
    public void drag(int x, int y) { 
        
    }
    
    public void setBinding(){
        final ObservableList<Double> points = getPoints(); 
        
        if(!points.isEmpty()){
            for (int i = 2; i < points.size() - 2; i+=2) {
                 final int idx = i;

                DoubleProperty xProperty = new SimpleDoubleProperty(points.get(i));
                DoubleProperty yProperty = new SimpleDoubleProperty(points.get(i + 1));        

                xProperty.addListener(new ChangeListener<Number>() {
                       @Override public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {
                         points.set(idx, (double) x);
                       }     
                });
                yProperty.addListener(new ChangeListener<Number>() {
                  @Override public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {
                    points.set(idx + 1, (double) y);
                  }
                });            
            }
        }
    }    
    
    public void setBindingHeadEnd(){
        final ObservableList<Double> points = getPoints(); 
        int size = points.size();
        /*
        DoubleProperty startXProperty = new SimpleDoubleProperty(points.get(0));
        DoubleProperty startYProperty = new SimpleDoubleProperty(points.get(1));
        DoubleProperty endXProperty = new SimpleDoubleProperty(points.get(size - 2));
        DoubleProperty endYProperty = new SimpleDoubleProperty(points.get(size - 1));
        startXProperty.bind(lineLabel1.xProperty());
        startYProperty.bind(lineLabel1.yProperty());
        endXProperty.bind(lineLabel2.xProperty());
        endYProperty.bind(lineLabel2.yProperty()); 
        */
        lineLabel1.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                points.set(0, (double) newValue);
            }
        });
        lineLabel1.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                points.set(1, (double) newValue);
            }
        });
        lineLabel2.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                points.set(size - 2, (double) newValue);
            }
        });
        lineLabel2.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                points.set(size - 1, (double) newValue);
            }
        });
        /*
            startXProperty.addListener(new ChangeListener<Number>() {
                   @Override public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {
                     points.set(0, (double) x);
                   }     
            });
            startYProperty.addListener(new ChangeListener<Number>() {
              @Override public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {
                points.set(1, (double) y);
              }
            });    
            endXProperty.addListener(new ChangeListener<Number>() {
                   @Override public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {
                     points.set(size - 2, (double) x);
                   }     
            });
            endYProperty.addListener(new ChangeListener<Number>() {
              @Override public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {
                points.set(size - 1, (double) y);
              }
            });   
        */
    }
    
    /**
     * The method that helps creating a turning point of this Line.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */            
    public void turn(int x, int y){
        endX = x;
        endY = y;
        this.getPoints().addAll(new Double[]{endX, endY}); 
        lineLabel2.setX(endX);
        lineLabel2.setY(endY);     
    }

    /**
     * The method that helps setting the stroke width of the line.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */         
    @Override
    public void size(int x, int y) {
        endX = x;
        endY = y;
        int size = this.getPoints().size();
        
        if(size >= 4){
            this.getPoints().set(size - 2, endX);
            this.getPoints().set(size - 1, endY);
        } else {
            this.getPoints().setAll(startX, startY, endX, endY);
        }
        lineLabel1.setX(startX);
        lineLabel1.setY(startY);
        lineLabel2.setX(endX);
        lineLabel2.setY(endY);
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
    public void setListOfStations(ArrayList<String> initList){
        listOfStations = initList;
        //NEED TO CLONE BEFORE THIS
    }
 
    /**
     * This method is used to get the list of stations on this line.
     * 
     * @return listOfStations the list of stations on this line.
     */    
    public ArrayList<String> getListOfStations(){
        return listOfStations;
    }

    /**
     * This method is used to set the name of the line.
     * 
     * @param initName the list of stations to set.
     */    
    @Override
    public void setName(String initName){
        name = initName;
        lineLabel1.setContent(name);
        lineLabel2.setContent(name);
    }

    /**
     * This method is used to set the label of the line.
     */    
    private void setLineLabel1(){
        lineLabel1 = new DraggableLabel();
        lineLabel1.setX(startX);
        lineLabel1.setY(startY);  
    }
    
    /**
     * This method is used to set the label of the line.
     */   
    
    private void setLineLabel2(){
        lineLabel2 = new DraggableLabel();
        lineLabel2.setX(startX);
        lineLabel2.setY(startY);  
    }
    
    public DraggableLabel getLineLabel1(){
        return lineLabel1;
    }
    
    public DraggableLabel getLineLabel2(){
        return lineLabel2;
    }

    /**
     * This method is used to set the color of the line.
     * 
     * @param initColor color to set.
     */      
    public void setColor(Color initColor){
        color = initColor;
        this.setStroke(color);
    }

    /**
     * This method is used to get the color of the line.
     * 
     * @return lineColor the color of the line.
     */      
    public Color getColor(){
        return color;
    }
    
    /**
     * This method is used to add a station on the line.
     * 
     * @param newStation station to add
     */      
    public void addStation(String newStation){
        if(!listOfStations.contains(newStation))
            listOfStations.add(newStation);
    }
   
    /**
     * This method is used to remove a station on the line.
     * 
     * @param removeStation station to remove
     */     
    public void removeStation(String removeStation){
        listOfStations.remove(removeStation);
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
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return length of the Line
     */     
    public double getDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
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
    
    @Override
    public String toString(){
        return name;
    }
    
}
