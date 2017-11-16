/**
 * This class implements jTPS_Transaction. It's used for changing the name of the shape.
 * 
 * @author Kai 
 * @version 1.0
 */
package m3.transactions;

import javafx.scene.shape.Shape;
import jtps.jTPS_Transaction;
import m3.data.Draggable;

public class ChangeShapeName_Transaction implements jTPS_Transaction{
    private Draggable shape;
    private String name;
    private String oldName;    
    
    public ChangeShapeName_Transaction(Shape initShape, String initName) {
        shape = (Draggable)initShape;
        name = initName;
        oldName = shape.getName();    
    }

/**
 * redo name changing
 */        
    @Override
    public void doTransaction() {
        shape.setName(name);
    }

/**
 * undo name changing
 */        
    @Override
    public void undoTransaction() {
        shape.setName(oldName);
    }        
    
}
