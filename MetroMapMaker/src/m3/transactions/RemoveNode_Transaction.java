package m3.transactions;

import m3.data.m3Data;
import javafx.scene.Node;
import jtps.jTPS_Transaction;

/**
 * This class implements jTPS_Transaction. It's used for removing node.
 * 
 * @author Kai 
 * @version 1.0
 */
public class RemoveNode_Transaction implements jTPS_Transaction {
    private m3Data data;
    private Node node;
    private int nodeIndex;
    
    public RemoveNode_Transaction(m3Data initData, Node initNode) {
        data = initData;
        node = initNode;
        nodeIndex = data.getIndexOfNode(node);
    }
    
    /**
     * redo remove behavior.
     */
    @Override
    public void doTransaction() {
        data.removeNode(node);
    }
    
    /**
     * undo remove behavior.
     */
    @Override
    public void undoTransaction() {
        data.addNodeAtIndex(node, nodeIndex);
    }

}