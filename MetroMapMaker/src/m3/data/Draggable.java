/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.scene.shape.Shape;

/**
 * This interface represents a family of draggable shapes.
 * @author Kai
 */
public interface Draggable {
    public static final String LINE = "LINE";
    public static final String STATION = "STATION";
    public static final String LABEL = "LABEL";
    public static final String IMAGE = "IMAGE";
    public m3State getStartingState();
    public void start(int x, int y);
    public void drag(int x, int y);
    public void size(int x, int y);
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight);
    public String getShapeType();
    public Shape clone();
}
