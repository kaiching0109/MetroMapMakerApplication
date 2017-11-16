/**
 * This class implements jTPS_Transaction. It's used for shape color editting.
 * 
 * @author Kai 
 * @version 1.0
 */
package m3.transactions;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import jtps.jTPS_Transaction;

public class ChangeShapeColor_Transaction  implements jTPS_Transaction {
    private Shape shape;
    private Color color;
    private Color oldColor;
    
    public ChangeShapeColor_Transaction(Shape initShape, Color initColor) {
        shape = initShape;
        color = initColor;
        oldColor = (Color)shape.getFill();
    }

/**
 * redo color changing
 */        
    @Override
    public void doTransaction() {
        shape.setFill(color);
    }

/**
 * undo color changing
 */        
    @Override
    public void undoTransaction() {
        shape.setFill(oldColor);
    }    
}
