/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import djf.components.AppDataComponent;
import djf.AppTemplate;
import static djf.settings.AppPropertyType.DEFAULT_NODE_X;
import static djf.settings.AppPropertyType.DEFAULT_NODE_Y;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import static m3.data.m3State.SIZING_SHAPE;
import m3.gui.InfoRequireDialogSingleton;
import m3.gui.LineStationListingDialogSingleton;
import m3.gui.m3Workspace;
import m3.transactions.AddNode_Transaction;
import m3.transactions.ChangeBackground_Transaction;
import m3.transactions.RemoveNode_Transaction;
import properties_manager.PropertiesManager;

/**
 * This class serves as the data management component for this application.
 * @author Kai
 */
public class m3Data implements AppDataComponent {
    // FIRST THE THINGS THAT HAVE TO BE SAVED TO FILES
    
    // THESE ARE THE m3Nodes TO DRAW
    ObservableList<Node> m3Nodes;
    ArrayList<DraggableLine> m3Lines = new ArrayList<>();
    ArrayList<DraggableStation> m3Stations = new ArrayList<>();
    
    String mapName;
    
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
     * The accessor method for getting the collection of m3Stations.
     * 
     * @return m3Stations the collection of stations
     */    
    public ArrayList<DraggableStation> getM3Stations() {
	return m3Stations;
    }
    
    /**
     * The accessor method for getting the collection of m3Nodes.
     * 
     * @return m3Lines the collection of lines
     */    
    public ArrayList<DraggableLine> getM3Lines() {
	return m3Lines;
    }    
    
    /**
     * The accessor method for the background color.
     * 
     * @return backgroundColor the color of the background
     */          
    public Color getBackgroundColor() {
	return backgroundColor;
    }
    
    public void setMapName(String initMapName){
        mapName = initMapName;
    }
    
    public String getMapName(){
        return mapName;
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
     * @param stationName name of the line
     */      

    /**
     * This function is used to search through the collection of node by the 
     * value that is selected from the name comboBox
     * 
     * @param lineName name of the line
     * @return Boolean
     */           
     public Boolean searchLine(String lineName){     
        Node foundNode = null;
        for(DraggableLine temp: m3Lines){ 
            if(temp.getName().equals(lineName))
                foundNode = temp;   
        }        
        if(foundNode != null){
            if(selectedNode != null)
                unhighlightNode(selectedNode);
            highlightNode(foundNode);
            selectedNode = foundNode;
            return true;
        }   
        return false;
     }     
     
    /**
     * This function is used to search through the collection of node by the 
     * value that is selected from the name comboBox
     * 
     * @param stationName name of the station
     * @return Boolean
     */           
     public Boolean searchStation(String stationName){
        //COMPARE NODE FROM THE COLLECTION    
        Node foundNode = null;
        for(DraggableStation temp: m3Stations){ 
            if(temp.getName().equals(stationName))
                foundNode = temp;   
        }        
        if(foundNode != null){
            if(selectedNode != null)
                unhighlightNode(selectedNode);
            highlightNode(foundNode);
            selectedNode = foundNode;
            return true;
        }   
        return false;
     }
    
    /**
     * This function is used to search through the collection of node by the 
     * value that is selected from the name comboBox
     * 
     * @param initName name of the node
     * @return Boolean
     */       
    public Boolean searchNode(String initName){
        //COMPARE NODE FROM THE COLLECTION    
        Node foundNode = null;
        for(Node node: m3Lines){
            Draggable temp = (Draggable)node;
            if(temp.getName().equals(initName))
                foundNode = node;   
        }
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
            if(selectedNode instanceof DraggableLine)
                m3Lines.remove((DraggableLine)selectedNode);
            else if(selectedNode instanceof DraggableStation)
                m3Stations.remove((DraggableStation)selectedNode);
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
        m3Workspace workspace =  (m3Workspace)app.getWorkspaceComponent();
	setState(SELECTING_SHAPE);
	newShape = null;
	selectedNode = null;

	// INIT THE COLORS
	currentFillColor = Color.web(WHITE_HEX);
	
	m3Nodes.clear();
        m3Lines.clear();
        m3Stations.clear();
        workspace.resetWorkspace();
	workspace.getCanvas().getChildren().clear();
    }

    /**
     * This function helps sizing the selected shape\and change our state to 
     * SIZING_SHAPE
     */       
    public void selectSizedShape() {
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();    
	if (selectedNode != null)
	    unhighlightNode(selectedNode);
	selectedNode = newShape;
        workspace.loadSelectedNodeSettings(selectedNode);
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
        String lineName = InfoRequireDialogSingleton.getSingleton().getName();
	newLine.start(x, y); 
        newLine.getPoints().setAll(new Double[]{(double)x, (double)y}); 
        newLine.setColor(InfoRequireDialogSingleton.getSingleton().getColorPicker().getValue());
        newLine.setName(lineName);
        setLineLabels(newLine);
        updateShapeComboBox(lineName, newLine.getNodeType());
	newShape = newLine;
	initNewShape();
    }
    
    public void setLineLabels(DraggableLine newLine){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        DraggableLabel label1 = newLine.getLineLabel1();
        DraggableLabel label2 = newLine.getLineLabel2();
        label1.setOnMouseClicked(e->{
            workspace.loadSelectedNodeSettings(newLine);
        });
        label2.setOnMouseClicked(e->{
            workspace.loadSelectedNodeSettings(newLine);            
        });
        m3Nodes.add(label1);
        m3Nodes.add(label2);
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
        String stationName = InfoRequireDialogSingleton.getSingleton().getName();
        newStation.setName(stationName);
        newStation.setColor(InfoRequireDialogSingleton.getSingleton().getColorPicker().getValue());
        newStation.setStroke(STROKE_COLOR);
         m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        
        workspace.getStationNameBox().getItems().add(stationName);
        DraggableLabel nameLabel = newStation.getNameLabel();
        nameLabel.setOnMouseClicked(e->{
            workspace.loadSelectedNodeSettings(newStation);
        });        
        addNode(newStation.getNameLabel());
        
       // setStationLabel(newStation);
        updateShapeComboBox(stationName, newStation.getNodeType());
	newShape = newStation;
	initNewShape();
    }
    
    public void setStationLabel(DraggableStation station){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        DraggableLabel nameLabel = station.getNameLabel();
        nameLabel.setOnMouseClicked(e->{
            workspace.loadSelectedNodeSettings(station);
        });        
        addNode(station.getNameLabel());        
    }
    
    public void updateShapeComboBox(String name, String type){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        if(type.equals(LINE)){
            workspace.getLineNameBox().getItems().add(name);
        } else if (type.equals(STATION)){
            workspace.getStationNameBox().getItems().add(name);
        }               
    }
    
    /**
     * This function helps creating a new Image.
     * 
     * @param x  x is the x coordinate of the clicked position of the cursor.
     * @param y  y is the y coordinate of the clicked position of the cursor.
     */    
    public void startNewImage(int x, int y) {   
        DraggableImage newImage = new DraggableImage();
        newImage.start(x, y);
        //newImage.setImagePath(path);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        newImage.xProperty().set(Double.parseDouble(props.getProperty(DEFAULT_NODE_X)));
        newImage.yProperty().set(Double.parseDouble(props.getProperty(DEFAULT_NODE_Y)));
        newShape = newImage;
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
        
        // GO INTO SHAPE SIZING MODE
        state = m3State.SIZING_SHAPE;
        
	// ADD THE SHAPE TO THE CANVAS
	addNode(newShape);
	
	// GO INTO SHAPE SIZING MODE
	//state = m3State.SIZING_SHAPE;
	// FINALLY, ADD A TRANSACTION FOR ADDING THE NEW SHAPE
       // jTPS tps = app.getTPS();
      //  m3Data data = (m3Data)app.getDataComponent();
      //  AddNode_Transaction newTransaction = new AddNode_Transaction(data, newShape);
       // tps.addTransaction(newTransaction);        
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
        if(searchLine((String)workspace.getLineNameBox().getValue())){
            DraggableLine line = (DraggableLine)selectedNode;
            sortStations(line);
            stationListingDialog.setLine(line);
            stationListingDialog.show("Metro Map Maker - Metro Line Stops", "");
        }
        
        /*
        if(searchNode(lineName)){
            DraggableLine line = (DraggableLine)selectedNode;
            stationListingDialog.setLine(line);
            stationListingDialog.show("Metro Map Maker - Metro Line Stops", "");
        } //endIf
        */
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
    
    public void addStationsToLine(int x, int y, DraggableStation stationToAdd){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        if(searchLine((String)workspace.getLineNameBox().getValue())){
            DraggableLine selectedLine = (DraggableLine)selectedNode;
            System.out.println("START");
            if(selectedLine.contains(x, y)){   
                selectedLine.addStation(stationToAdd.getName());
                stationToAdd.centerXProperty().set(x);
                stationToAdd.centerYProperty().set(y);
                stationToAdd.addLine(selectedLine);
            } //endIf
        }    
    }
    
    public void removeStationFromLine(DraggableStation stationToRemove){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        String name = (String)workspace.getLineNameBox().getValue();
        if(searchLine(name)){
            DraggableLine selectedLine = (DraggableLine)selectedNode;
            ArrayList<String> list = selectedLine.getListOfStations();
            if(list.contains(name)){
                list.remove(name);
                stationToRemove.removeLine(selectedLine);
            }    
        } //endIf
    }
    
    public void sortStations(DraggableLine line){
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        ArrayList<String> listOfStations = line.getListOfStations();
        for(int i = 0; i < listOfStations.size(); i++){
            String station = listOfStations.get(i);
            searchStation(station);
            DraggableStation selectedStation = (DraggableStation)selectedNode;
            double lineX = line.getPoints().get(0);
            double lineY = line.getPoints().get(1);
            
            double distance = line.getDistance(lineX, lineY, selectedStation.getCenterX(), selectedStation.getCenterY());
            hashMap.put(listOfStations.get(i), (int)distance);
        }
        
        Set<Entry<String,Integer>> mapEntries = hashMap.entrySet();
        List<Entry<String,Integer>> list = new LinkedList<Entry<String,Integer>>(mapEntries);
        Collections.sort(list, new Comparator<Entry<String,Integer>>() {

            @Override
            public int compare(Entry<String, Integer> ele1, Entry<String, Integer> ele2) {
               return ele1.getValue().compareTo(ele2.getValue());
            }
        }); 
        
        for(int i = 0; i < list.size(); i++){
            String station = list.get(i).getKey();
            listOfStations.set(i, station);
        } //endFor
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
        if (currentIndex < 0) 
	    m3Nodes.add(nodeToAdd);
        if(nodeToAdd instanceof DraggableStation)
            m3Stations.add((DraggableStation)nodeToAdd);
        else if(nodeToAdd instanceof DraggableLine){
           DraggableLine lineToAdd = (DraggableLine)nodeToAdd;
           m3Lines.add(lineToAdd);
        }    
        System.out.println(app.getGUI().getWindow().toString());
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
