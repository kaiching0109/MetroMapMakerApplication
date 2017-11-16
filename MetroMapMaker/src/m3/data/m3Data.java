/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import djf.components.AppDataComponent;
import djf.AppTemplate;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jtps.jTPS;
import static m3.data.Draggable.LABEL;
import static m3.data.Draggable.LINE;
import static m3.data.Draggable.STATION;
import static m3.data.m3State.SELECTING_SHAPE;
import static m3.data.m3State.SIZING_SHAPE;
import m3.gui.InfoRequireDialogSingleton;
import m3.gui.LineEditDialogSingleton;
import m3.gui.LineStationListingDialogSingleton;
import m3.gui.m3Workspace;
import m3.transactions.AddNode_Transaction;
import m3.transactions.ChangeBackground_Transaction;
import m3.transactions.RemoveNode_Transaction;

/**
 * This class serves as the data management component for this application.
 * @author Kai
 */
public class m3Data implements AppDataComponent {
    // FIRST THE THINGS THAT HAVE TO BE SAVED TO FILES
    
    // THESE ARE THE m3Nodes TO DRAW
    ObservableList<Node> m3Nodes;
    
    // GRAPH
    Graph nodes;
    
    // THE BACKGROUND COLOR
    Color backgroundColor;
    
    // THE BACKGROUND IMAGE
    Image backgroundImage;
    
    // AND NOW THE EDITING DATA

    // THIS IS THE SHAPE CURRENTLY BEING SIZED BUT NOT YET ADDED
    Shape newShape;

    // THIS IS THE NODE CURRENTLY SELECTED
    Node selectedNode;

    // FOR FILL AND OUTLINE
    Color currentFillColor;

    // CURRENT STATE OF THE APP
    m3State state;

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;

    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 3;
    public static final Paint STROKE_COLOR = Paint.valueOf(BLACK_HEX);

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public m3Data(AppTemplate initApp) {
	// KEEP THE APP FOR LATER
	app = initApp;

	// NO SHAPE STARTS OUT AS SELECTED
	newShape = null;
	selectedNode = null;

	// INIT THE COLORS
	currentFillColor = Color.web(WHITE_HEX);
	
	// THIS IS FOR THE SELECTED SHAPE
	DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.YELLOW);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(15);
	highlightedEffect = dropShadowEffect;
    }

    /**
     * The accessor method for getting the collection of m3Nodes.
     * 
     * @return m3Nodes the collection of nodes
     */    
    public ObservableList<Node> getM3Nodes() {
	return m3Nodes;
    }

    /**
     * The accessor method for the background color.
     * 
     * @return backgroundColor the color of the background
     */          
    public Color getBackgroundColor() {
	return backgroundColor;
    }

    /**
     * The accessor method for setting the collection of m3Nodes.
     * 
     * @param initM3Nodes the collection of m3Nodes to set
     */      
    public void setM3Nodes(ObservableList<Node> initM3Nodes) {
	m3Nodes = initM3Nodes;
    }

    /**
     * The accessor method for setting the background color of our workspace.
     * 
     * @param initBackgroundColor the color to set for the background
     */        
    public void setBackgroundColor(Color initBackgroundColor) {
	backgroundColor = initBackgroundColor;
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
	Background background = new Background(fill);
	canvas.setBackground(background);
	// FINALLY, ADD A TRANSACTION FOR CHANGING BACKGROUND
        jTPS tps = app.getTPS();
        m3Data data = (m3Data)app.getDataComponent();
        ChangeBackground_Transaction newTransaction = new ChangeBackground_Transaction(canvas, backgroundColor);
        tps.addTransaction(newTransaction);        
    }

    /**
     * The accessor method for setting the background image of our workspace.
     * 
     * @param initBackgroundImage the image to set for the background
     */          
    public void setBackgroundImage(Image initBackgroundImage){
	backgroundImage = initBackgroundImage;
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,BackgroundRepeat.REPEAT, 
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
        Background background = new Background(bgImage);
	canvas.setBackground(background);
	// FINALLY, ADD A TRANSACTION FOR CHANGING BACKGROUND
        jTPS tps = app.getTPS();
        //m3Data data = (m3Data)app.getDataComponent();
        ChangeBackground_Transaction newTransaction = new ChangeBackground_Transaction(canvas, backgroundImage);
        tps.addTransaction(newTransaction);             
    }

    /**
     * This function is used to search through the collection of node by the 
     * value that is selected from the name comboBox
     * 
     * @param initName name of the node
     */       
    public Boolean searchNode(String initName){
        //COMPARE NODE FROM THE COLLECTION    
        Node foundNode = null;
        if(foundNode != null){
            unhighlightNode(selectedNode);
            highlightNode(foundNode);
            selectedNode = foundNode;
            return true;
        }   
        return false;
    }
    
    /**
     * This function removes the selected shape
     */       
    public void removeSelectedNode() {
	if (selectedNode != null) {
 	// FINALLY, ADD A TRANSACTION FOR REMOVING NODE
            jTPS tps = app.getTPS();
            m3Data data = (m3Data)app.getDataComponent();
            RemoveNode_Transaction newTransaction = new RemoveNode_Transaction(data, selectedNode);
            tps.addTransaction(newTransaction);            
	    m3Nodes.remove(selectedNode);
	    selectedNode = null;
	}
    }

    /**
     * This function moves the selected shape to the back
     */     
    public void moveselectedNodeToBack() {
	if (selectedNode != null) {
	    m3Nodes.remove(selectedNode);
	    if (m3Nodes.isEmpty()) {
		m3Nodes.add(selectedNode);
	    }
	    else {
		ArrayList<Node> temp = new ArrayList<>();
		temp.add(selectedNode);
		for (Node node : m3Nodes)
		    temp.add(node);
		m3Nodes.clear();
		for (Node node : temp)
		    m3Nodes.add(node);
	    }
	}
    }
 
    /**
     * This function moves the selected shape to the front
     */         
    public void moveselectedNodeToFront() {
	if (selectedNode != null) {
	    m3Nodes.remove(selectedNode);
	    m3Nodes.add(selectedNode);
	}
    }
 
    /**
     * This function clears out the HTML tree and reloads it with the minimal
     * tags, like html, head, and body such that the user can begin editing a
     * page.
     */
    @Override
    public void resetData() {
	setState(SELECTING_SHAPE);
	newShape = null;
	selectedNode = null;

	// INIT THE COLORS
	currentFillColor = Color.web(WHITE_HEX);
	
	m3Nodes.clear();
	((m3Workspace)app.getWorkspaceComponent()).getCanvas().getChildren().clear();
    }

    /**
     * This function helps sizing the selected shape\and change our state to 
     * SIZING_SHAPE
     */       
    public void selectSizedShape() {
	if (selectedNode != null)
	    unhighlightNode(selectedNode);
	selectedNode = newShape;
	highlightNode(selectedNode);
	newShape = null;
	if (state == SIZING_SHAPE) {
	    state = ((Draggable)selectedNode).getStartingState();
	}
    }

    /**
     * This function removes the highlight effect of the shape.
     * 
     * @param shape the shape that user wants to unhighlight
     */
    public void unhighlightNode(Node node) {
	node.setEffect(null);
    }

    /**
     * This function proves the highlight effect for the shape.
     * 
     * @param shape the shape that user wants to highlight
     */    
    public void highlightNode(Node node) {
	node.setEffect(highlightedEffect);
    }
    
    /**
     * This function helps creating a new line.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */
    public void startNewLine(int x, int y) {
	DraggableLine newLine = new DraggableLine();
	newLine.start(x, y);
        newLine.setColor(InfoRequireDialogSingleton.getSingleton().getLineColorPicker().getValue());
        newLine.setName(InfoRequireDialogSingleton.getSingleton().getName());
	newShape = newLine;
	initNewShape();
    }

    /**
     * This function helps creating a new Station.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */    
    public void startNewStation(int x, int y) {
	DraggableStation newStation = new DraggableStation();
	newStation.start(x, y);
        newStation.setName(InfoRequireDialogSingleton.getSingleton().getName());
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        newStation.setColor(workspace.getStationColorPicker().getValue());
        newStation.setStroke(STROKE_COLOR);
	newShape = newStation;
	initNewShape();
    }
    
    /**
     * This function helps creating a new shape by setting all the
     * required variable for each specific shape
     */ 
    public void initNewShape() {
	// DESELECT THE SELECTED SHAPE IF THERE IS ONE
	if (selectedNode != null) {
	    unhighlightNode(selectedNode);
	    selectedNode = null;
	}

	// USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	// ADD THE SHAPE TO THE CANVAS
	m3Nodes.add(newShape);
	
	// GO INTO SHAPE SIZING MODE
	state = m3State.SIZING_SHAPE;
	// FINALLY, ADD A TRANSACTION FOR ADDING THE NEW SHAPE
        jTPS tps = app.getTPS();
        m3Data data = (m3Data)app.getDataComponent();
        AddNode_Transaction newTransaction = new AddNode_Transaction(data, newShape);
        tps.addTransaction(newTransaction);        
    }
    
    /**
     * This method search for the selected line in the our collection 
     * of lines, and place the line as an argument into our dialog.
     * Finally show our diagram.
     * 
     */   
    public void ListSelectedLineStation(){
        LineStationListingDialogSingleton stationListingDialog = LineStationListingDialogSingleton.getSingleton();
        //SEARCH FOR LINE
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        String lineName = (String)workspace.getLineNameBox().getValue();
        if(searchNode(lineName)){
            DraggableLine line = (DraggableLine)selectedNode;
            stationListingDialog.setLine(line);
            stationListingDialog.show("Metro Map Maker - Metro Line Stops", "");
        } //endIf
    }
    
    /**
     * This method is used to show the line edit dialog for prompting the user
     * for information of the selected line.
     */
    public void showLineEditDialog(){
        LineEditDialogSingleton lineEditDialog = LineEditDialogSingleton.getSingleton();
        //SEARCH FOR LINE
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        String lineName = (String)workspace.getLineNameBox().getValue();
        if(searchNode(lineName)){
            DraggableLine line = (DraggableLine)selectedNode;
            lineEditDialog.setLine(line);
            lineEditDialog.show("Metro Map Maker - Metro Line Stops", "");
        } //endIf        
    }
 
    /**
     * The accessor method for getting the new created shape.
     * 
     * @return newShape the shape that is was last created.
     */      
    public Shape getNewShape() {
	return newShape;
    }

    /**
     * The accessor method for the selected node.
     * 
     * @return selectedNode the shape that is currently selected.
     */      
    public Node getSelectedNode() {
	return selectedNode;
    }

    /**
     * The accessor method for setting the selected shape.
     * 
     * @param initselectedNode initselectedNode is the shape to select
     */       
    public void setselectedNode(Shape initselectedNode) {
	selectedNode = initselectedNode;
    }

    /**
     * The accessor method for getting the top shape according to the clicked
     * position by cursor
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     * @return shape shape is the selected top shape
     */      
    public Node selectTopNode(int x, int y) {
	Node node = getTopNode(x, y);
	if (node == selectedNode)
	    return node;
	
	if (selectedNode != null) {
	    unhighlightNode(selectedNode);
	}
	if (node != null) {
	    highlightNode(node);
	    m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	    workspace.loadSelectedNodeSettings(node);
	}
	selectedNode = node;
	if (node != null) {
	    ((Draggable)node).start(x, y);
	}
	return node;
    }  

    /**
     * The accessor method helps getting the top shape according to the clicked
     * position by cursor
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     * @return shape
     */      
    public Node getTopNode(int x, int y) {
	for (int i = m3Nodes.size() - 1; i >= 0; i--) {
	    Node node = m3Nodes.get(i);
	    if (node.contains(x, y)) {
		return node;
	    }
	}
	return null;
    }

    /**
     * The accessor method for getting the current state of our dataManager
     * 
     * @return state state to show user action and how our system should react.
     */  
    public m3State getState() {
	return state;
    }
    
    /**
     * The accessor method for setting the current state of our dataManager.
     * 
     * @param initState state to change
     */
    public void setState(m3State initState) {
	state = initState;
    }

    /**
     * This method is to check if our current state is same as our testState.
     * 
     * @param testState state to check
     * @return true/ false if depending on the result of their equality
     */
    public boolean isInState(m3State testState) {
	return state == testState;
    }
    
    /**
     * The method remove a shape from our collection of m3Nodes
     * 
     * @param nodeToRemove node to remove
     */       
    public void removeNode(Node nodeToRemove) {
        int currentIndex = m3Nodes.indexOf(nodeToRemove);
        if (currentIndex >= 0) {
	    m3Nodes.remove(currentIndex);
        }
    }    
    
    /**
     * The method adds a shape into our collection of m3Nodes
     * 
     * @param nodeToAdd node to add
     */        
    public void addNode(Node nodeToAdd) {
        int currentIndex = m3Nodes.indexOf(nodeToAdd);
        if (currentIndex < 0) {
	    m3Nodes.add(nodeToAdd);
        }
    }

    /**
     * This method is used to get the index of the node
     * 
     * @param node nodeToSearch
     * @return int index of the node
     */
    public int getIndexOfNode(Node node) {
        return m3Nodes.indexOf(node);
    }
    
    /**
     * This method is used to add a node at the specific index of our collection.
     * 
     * @param node nodeToAdd
     * @param nodeIndex  indexToAdd
     */
    public void addNodeAtIndex(Node node, int nodeIndex) {
        m3Nodes.add(nodeIndex, node);    
    }    
    
    /**
     * This method is used to check if the selectedNode is Text.
     * 
     * @return Boolean 
     */
    public boolean isTextSelected() {
        if (selectedNode == null)
            return false;
        else
            return (selectedNode instanceof Text);
    }    
}
