/**
 * This class implements jTPS_Transaction. It's used for node adding
 * 
 * @author Kai 
 * @version 1.0
 */
package m3.transactions;

import javafx.scene.Node;
import jtps.jTPS_Transaction;
import m3.data.m3Data;

public class AddNode_Transaction implements jTPS_Transaction {
    private m3Data data;
    private Node node;
    
    public AddNode_Transaction(m3Data initData, Node initNode) {
        data = initData;
        node = initNode;
    }

/**
 * redo adding node
 */          
    @Override
    public void doTransaction() {
        data.addNode(node);
    }

/**
 * undo adding node
 */          
    @Override
    public void undoTransaction() {
        data.removeNode(node);    
    }
}
