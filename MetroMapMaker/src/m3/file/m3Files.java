package m3.file;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import m3.data.Draggable;
import static m3.data.Draggable.IMAGE;
import static m3.data.Draggable.LINE;
import static m3.data.Draggable.STATION;
import static m3.data.Draggable.LABEL;
import m3.data.DraggableImage;
import m3.data.DraggableLabel;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.m3Data;
import m3.gui.m3Workspace;
import djf.AppTemplate;
import static djf.settings.AppStartupConstants.DEFAULT_FILENAME;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigDecimal;
import javax.json.JsonString;

public class m3Files implements AppFileComponent{

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
    AppTemplate app;
    
    // FOR JSON LOADING
    static final String JSON_TITLE = "title";
    static final String JSON_BG_COLOR = "background_color";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_ALPHA = "alpha";
    static final String JSON_SHAPES = "shapes";
    static final String JSON_LINES = "lines";
    static final String JSON_CIRCULAR = "circular";
    static final String JSON_STATIONS = "stations";
    static final String JSON_RADIUS = "radius";
    static final String JSON_LIST_OF_NAMES = "listOfNames";
    static final String JSON_STATION_NAMES = "station_names";
    static final String JSON_LINE_POINTS = "points";
    static final String JSON_SHAPE = "shape";
    static final String JSON_TYPE = "type";
    static final String JSON_NAME = "name";
    static final String JSON_X = "x";
    static final String JSON_Y = "y";
    static final String JSON_WIDTH = "width";
    static final String JSON_HEIGHT = "height";
    static final String JSON_COLOR = "color";
    static final String JSON_OUTLINE_THICKNESS = "outline_thickness";
    
    static final String DEFAULT_DOCTYPE_DECLARATION = "<!doctype html>\n";
    static final String DEFAULT_ATTRIBUTE_VALUE = "";
    
 
    /**
     * This method is for saving user work, which in the case of this
     * application means the data that together draws the map.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    
    public m3Files(AppTemplate initApp){
        app = initApp;
    }
    
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	m3Data dataManager = (m3Data)data;
	
	// FIRST THE BACKGROUND COLOR
	Color bgColor = dataManager.getBackgroundColor();
	//JsonObject bgColorJson = makeJsonColorObject(bgColor);

	// NOW BUILD THE JSON OBJECTS TO SAVE
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	ObservableList<Node> shapes = dataManager.getM3Nodes();
        ArrayList<DraggableLine> lines = dataManager.getM3Lines();
        ArrayList<DraggableStation> stations = dataManager.getM3Stations(); 
        String title = app.getGUI().getWindow().getTitle();
        
        // BUILD THE LINE JSON OBJECT TO SAVE
        for(DraggableLine line : lines){
            //Create an arrayBuilder for points
            JsonArrayBuilder pointsArrayBuilder = Json.createArrayBuilder();
            //Create an arrayBuilder for list of stations
            JsonArrayBuilder listOfStationsArrayBuilder = Json.createArrayBuilder();
            String type = line.getNodeType();
            String name = line.getName();
            Boolean circular = line.getCircular();
            ArrayList<String> listOfStations = line.getListOfStations();
            ObservableList<Double> points = line.getPoints();           
            JsonObject colorJson = makeJsonColorObject(line.getColor());
            double outlineThickness = line.getStrokeWidth();
            
            //Get the list of Points
            for(double point : points){
                JsonObject pointJson = Json.createObjectBuilder()
                        .add(JSON_LINE_POINTS, point).build(); 
                pointsArrayBuilder.add(pointJson);   
            } //endFor  
            JsonArray pointsArray = pointsArrayBuilder.build();
            
            //Get the list of Stations
	    for(String station : listOfStations){
                JsonObject stationNameJson = Json.createObjectBuilder()
                        .add(JSON_LIST_OF_NAMES, station).build(); 
                listOfStationsArrayBuilder.add(stationNameJson);   
            } //endFor
            JsonArray listOfStationsArray = listOfStationsArrayBuilder.build();
            
            JsonObject lineJson = Json.createObjectBuilder()
		    .add(JSON_TYPE, type)
                    .add(JSON_NAME, name)
                    .add(JSON_CIRCULAR, circular)
                    .add(JSON_LINE_POINTS, pointsArray)
                    .add(JSON_LIST_OF_NAMES, listOfStationsArray)
		    .add(JSON_COLOR, colorJson)
		    .add(JSON_OUTLINE_THICKNESS, outlineThickness).build();
	    arrayBuilder.add(lineJson);  
        }
        
        //BUILD THE STATION JSON OBJECT TO SAVE
        for(DraggableStation station : stations){
            //Create an arrayBuilder for list of stations
            JsonArrayBuilder listOfLinesArrayBuilder = Json.createArrayBuilder();
            String type = station.getNodeType();
            String name = station.getName();
            double x = station.getCenterX();
            double y = station.getCenterY();
            double radius = station.getRadius();
            ArrayList<String> listOfLines = station.getListOfLines();
            JsonObject colorJson = makeJsonColorObject(station.getColor());
            double outlineThickness = station.getStrokeWidth();
            
            //Get the list of Lines
	    for(String line : listOfLines){
                JsonObject lineNameJson = Json.createObjectBuilder()
                        .add(JSON_LIST_OF_NAMES, line).build(); 
                listOfLinesArrayBuilder.add(lineNameJson);   
            } //endFor
            JsonArray listOfLinesArray = listOfLinesArrayBuilder.build();
            
            JsonObject stationJson = Json.createObjectBuilder()
		    .add(JSON_TYPE, type)
                    .add(JSON_NAME, name)
                    .add(JSON_X, x)
                    .add(JSON_Y, y)
                    .add(JSON_RADIUS, radius)
                    .add(JSON_LIST_OF_NAMES, listOfLinesArray)
		    .add(JSON_COLOR, colorJson)
		    .add(JSON_OUTLINE_THICKNESS, outlineThickness).build();
	    arrayBuilder.add(stationJson);   
        }        
             
	JsonArray shapesArray = arrayBuilder.build();        
        /*
	for (Node node : shapes) {
	    Shape shape = (Shape)node;
	    Draggable draggableShape = ((Draggable)shape);
	    String type = draggableShape.getNodeType();
            
	    double x = draggableShape.getX();
	    double y = draggableShape.getY();
	    double width = draggableShape.getWidth();
	    double height = draggableShape.getHeight();
            
	    JsonObject fillColorJson = makeJsonColorObject((Color)shape.getFill());
	    JsonObject outlineColorJson = makeJsonColorObject((Color)shape.getStroke());
	    double outlineThickness = shape.getStrokeWidth();
	    
	    JsonObject shapeJson = Json.createObjectBuilder()
		    .add(JSON_TYPE, type)
		    .add(JSON_X, x)
                    .add(JSON_Y, y)
		    .add(JSON_WIDTH, width)
		    .add(JSON_HEIGHT, height)
		    .add(JSON_FILL_COLOR, fillColorJson)
		    .add(JSON_OUTLINE_COLOR, outlineColorJson)
		    .add(JSON_OUTLINE_THICKNESS, outlineThickness).build();
	    arrayBuilder.add(shapeJson);
	}
	JsonArray shapesArray = arrayBuilder.build();
	*/
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		//.add(JSON_BG_COLOR, bgColorJson)
                .add(JSON_TITLE, title)
		.add(JSON_SHAPES, shapesArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
        // SET THE TITLE
        //jsonWriter.writeObject();
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath+"m3");
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath+"m3");
	pw.write(prettyPrinted);
	pw.close();
    }
    
    private JsonObject makeJsonColorObject(Color color) {
	JsonObject colorJson = Json.createObjectBuilder()
		.add(JSON_RED, color.getRed())
		.add(JSON_GREEN, color.getGreen())
		.add(JSON_BLUE, color.getBlue())
		.add(JSON_ALPHA, color.getOpacity()).build();
	return colorJson;
    }
      
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	m3Data dataManager = (m3Data)data;
	dataManager.resetData();
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// LOAD THE BACKGROUND COLOR
	//Color bgColor = loadColor(json, JSON_BG_COLOR);
	//dataManager.setBackgroundColor(bgColor);

	// AND NOW LOAD ALL THE SHAPES
	JsonArray jsonShapeArray = json.getJsonArray(JSON_SHAPES);
        String jsonTitle = json.getJsonString(JSON_TITLE).getString();
        app.getGUI().getWindow().setTitle(jsonTitle);
        
	for (int i = 0; i < jsonShapeArray.size(); i++) {
	    JsonObject jsonShape = jsonShapeArray.getJsonObject(i);
	    Shape shape = loadShape(jsonShape);
            if(shape instanceof DraggableLine){
                DraggableLine line = (DraggableLine) shape;
                dataManager.setLineLabels(line);
                dataManager.updateShapeComboBox(line.getName(), line.getNodeType());
            } else if(shape instanceof DraggableStation){
                DraggableStation station = (DraggableStation) shape;
                dataManager.setStationLabel(station);
                dataManager.updateShapeComboBox(station.getName(), station.getNodeType());
            }
	    dataManager.addNode(shape);
	}
        
    }
    
    /**
     * This method is for saving user work in other place than the default path, 
     * which in the case of this application means the data that together draws 
     * the map.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */    
    public void saveAsData(AppDataComponent data, String filePath) throws IOException{
        
    }
    
    private double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    private Shape loadShape(JsonObject jsonShape) {
	// FIRST BUILD THE PROPER SHAPE TYPE
	String type = jsonShape.getString(JSON_TYPE);
	Shape shape;
        
	if (type.equals(LINE)) {
	    DraggableLine line = new DraggableLine();
            String lineName = jsonShape.getString(JSON_NAME);
            Boolean circular = jsonShape.getBoolean(JSON_CIRCULAR);
            JsonArray jsonPointsArray = jsonShape.getJsonArray(JSON_LINE_POINTS);
            loadPointsList(jsonPointsArray, line);
            JsonArray jsonStationArray = jsonShape.getJsonArray(JSON_LIST_OF_NAMES);
            ArrayList<String> listOfStations = loadNameList(jsonStationArray);
            Color color = loadColor(jsonShape, JSON_COLOR);
            double outlineThickness = getDataAsDouble(jsonShape, JSON_OUTLINE_THICKNESS);
            
            //SET LINE
            line.setName(lineName);
            line.setCircular(circular);
            line.setListOfStations(listOfStations);
            line.setColor(color);
            line.setStrokeWidth(outlineThickness);
            shape = line;
	}
	else if(type.equals(STATION)){
	    DraggableStation station = new DraggableStation();
            String stationName = jsonShape.getString(JSON_NAME);
            double centerX = getDataAsDouble(jsonShape, JSON_X);
            double centerY = getDataAsDouble(jsonShape, JSON_Y);
            JsonArray jsonLinesArray = jsonShape.getJsonArray(JSON_LIST_OF_NAMES);
            ArrayList<String> listOfLines = loadNameList(jsonLinesArray);
            Color color = loadColor(jsonShape, JSON_COLOR);
            double radius = getDataAsDouble(jsonShape, JSON_RADIUS);
            double outlineThickness = getDataAsDouble(jsonShape, JSON_OUTLINE_THICKNESS);
            
            //SET STATION
            station.setName(stationName);
            station.setCenterX(centerX);
            station.setCenterY(centerY);
            station.setListOfLine(listOfLines);
            station.setColor(color);
            station.setRadius(radius);
            station.setStrokeWidth(outlineThickness);
            shape = station;
	} 
        else if(type.equals(IMAGE)){
            shape = new DraggableImage();
        }
        else {
            shape = new DraggableLabel();
        }
	
	// ALL DONE, RETURN IT
	return shape;
    }
    
    private void loadPointsList(JsonArray jsonPointsArray, DraggableLine line){
	for (int i = 0; i < jsonPointsArray.size(); i+=2) {
	    JsonObject jsonPointX = jsonPointsArray.getJsonObject(i);
            JsonObject jsonPointY = jsonPointsArray.getJsonObject(i+1);
	    double x = getDataAsDouble(jsonPointX, JSON_LINE_POINTS);
            double y = getDataAsDouble(jsonPointY, JSON_LINE_POINTS);
            if(i == 0){
                line.getLineLabel1().setX(x);
                line.getLineLabel1().setY(y);
                line.getPoints().setAll(new Double[]{x, y});
            }else
                line.turn((int)x, (int)y);
	} //endFor    
        line.setBindingHeadEnd();
    }
    
    private ArrayList<String> loadNameList(JsonArray jsonListArray){
        ArrayList<String> list = new ArrayList<>();
        
        if(jsonListArray != null){
            for (int i = 0; i < jsonListArray.size(); i++) {
                JsonObject jsonListObject = jsonListArray.getJsonObject(i);
                String listObject = jsonListObject.getString(JSON_LIST_OF_NAMES);
                list.add(listObject);
            } //endFor
        }
        
        return list;
    }
    
    private Color loadColor(JsonObject json, String colorToGet) {
	JsonObject jsonColor = json.getJsonObject(colorToGet);
	double red = getDataAsDouble(jsonColor, JSON_RED);
	double green = getDataAsDouble(jsonColor, JSON_GREEN);
	double blue = getDataAsDouble(jsonColor, JSON_BLUE);
	double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
	Color loadedColor = new Color(red, green, blue, alpha);
	return loadedColor;
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     */
    @Override
    public void exportData(AppDataComponent data, String filePath) throws FileNotFoundException{
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        String fileName = ((m3Data)app.getDataComponent()).getMapName();
	File file = new File((PATH_WORK + fileName + "/" + fileName + "" + " Metro.png"));
	try {
	    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	}
	catch(IOException ioe) {
	    ioe.printStackTrace();
	}
        // GET THE DATA
	m3Data dataManager = (m3Data)data;

	// NOW BUILD THE JSON OBJECTS TO SAVE
	//JsonArrayBuilder mapArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder lineArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder stationArrayBuilder = Json.createArrayBuilder();
        
        ArrayList<DraggableLine> lines = dataManager.getM3Lines();
        ArrayList<DraggableStation> stations = dataManager.getM3Stations(); 
        String title = app.getGUI().getWindow().getTitle();
        
        // BUILD THE LINE JSON OBJECT TO SAVE
        for(DraggableLine line : lines){
            String lineName = line.getName();
            Boolean circular = line.getCircular();
            JsonObject colorJson = makeJsonColorObject(line.getColor());
            ArrayList<String>listOfStations = line.getListOfStations();
            JsonArrayBuilder listOfStationsArrayBuilder = Json.createArrayBuilder();

            //Get the list of Stations
	    for(String station : listOfStations) 
                listOfStationsArrayBuilder.add(station);   
            JsonArray listOfStationsArray = listOfStationsArrayBuilder.build();
            JsonObject lineJson = Json.createObjectBuilder()
                    .add(JSON_NAME, lineName)
                    .add(JSON_CIRCULAR, circular)
                    .add(JSON_COLOR, colorJson)
                    .add(JSON_STATION_NAMES, listOfStationsArray).build();
	    lineArrayBuilder.add(lineJson); 
        } //LINES
        JsonArray linesArray = lineArrayBuilder.build();
        //mapArrayBuilder.add(lineArrayBuilder);
        
        for(DraggableStation station : stations){
            String stationName = station.getName();
            double x = station.getCenterX();
            double y = station.getCenterY();

            JsonObject stationJson = Json.createObjectBuilder()
                    .add(JSON_NAME, stationName)
                    .add(JSON_X, x)
                    .add(JSON_Y, y).build();
	    stationArrayBuilder.add(stationJson); 
        }    
        JsonArray stationsArray = stationArrayBuilder.build();
        
        //mapArrayBuilder.add(stationArrayBuilder);
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_NAME, title)
                .add(JSON_LINES, linesArray)
                .add(JSON_STATIONS, stationsArray).build();
        
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
        // SET THE TITLE
        //jsonWriter.writeObject();
        try (JsonWriter jsonWriter = writerFactory.createWriter(sw)) {
            // SET THE TITLE
            //jsonWriter.writeObject();
            jsonWriter.writeObject(dataManagerJSO);
        }
	// INIT THE WRITER
	OutputStream os = new FileOutputStream((PATH_WORK + fileName + "/" + fileName + "" + " Metro.json"));
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter((PATH_WORK + fileName + "/" + fileName + "" + " Metro.json"));
	pw.write(prettyPrinted);
	pw.close();
    }
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     * 
     *@param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
	// AGAIN, WE ARE NOT USING THIS IN THIS ASSIGNMENT
    }
}
