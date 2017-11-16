/**
 * This class implements jTPS_Transaction. It's used for line thickness editting.
 * 
 * @author Kai 
 * @version 1.0
 */
package m3.transactions;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import jtps.jTPS_Transaction;

public class ChangeLineThickness_Transaction implements jTPS_Transaction{
    private Line line;
    private double lineThickness;
    private double oldLineThickness;
    
    public ChangeLineThickness_Transaction(Line initLine, double initLineThickness) {
        line = initLine;
        lineThickness = initLineThickness;
        oldLineThickness = line.getStrokeWidth();
    }
    
/**
 * redo line width changing
 */        
    @Override
    public void doTransaction() {
        line.setStrokeWidth(lineThickness);
    }

/**
 * undo line width changing
 */      
    @Override
    public void undoTransaction() {
        line.setStrokeWidth(oldLineThickness);
    }    
    
}
