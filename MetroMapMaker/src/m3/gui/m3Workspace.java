/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import static djf.settings.AppPropertyType.SAVE_AS_ICON;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import m3.data.m3Data;
import static m3.data.m3Data.WHITE_HEX;
import m3.data.m3State;
import m3.file.m3Files;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 * @author aaronsuen
 */


public class m3Workspace extends AppWorkspaceComponent{

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    // BUTTONS FOR OUR APPGUI
    Button saveAsButton;
    Button exportButton;
    Button undoButton;
    Button redoButton;
    Button aboutButton;

    // HAS ALL THE CONTROLS FOR EDITING
    VBox editToolbar;
    
    // FIRST ROW
    VBox metroLinesToolbar;
    
    HBox row1HBox1;
    Label lineLabel;
    ComboBox lineNameBox; 
    Button editLineButton; //show the editWindow 
    
    HBox row1HBox2;
    Button addLineButton;
    Button removeLineButton;
    Button addStationsToLineButton;
    Button removeStationsFromLineButton;
    Button stationsListButton; 
    
    Slider lineThicknessSlider;  
    
    // SECOND ROW
    VBox metroStationsToolbar;
    
    HBox row2HBox1;
    Label stationLabel;
    ComboBox stationNameBox;
    ColorPicker stationColorPicker;  
    
    HBox row2HBox2;
    Button addStationButton;
    Button removeStationButton;
    Button snapButton;
    Button moveStationLabelButton;
    Button rotateStationLabelButton;
    
    Slider stationRadiusSlider;

    // THIRD ROW
    HBox stationRouterToolbar;
    
    VBox row3VBox1;
    ComboBox stationSearchingBar1;
    ComboBox stationSearchingBar2;
    
    Button routeFindingButton;

    // FORTH ROW
    VBox decorToolbar;
    
    HBox row4HBox1;
    Label decorLabel;
    ColorPicker bgColorPicker;    
    
    HBox row4HBox2;
    Button imageBackgroundButton;
    Button addImageButton;
    Button addLabelButton;
    Button removeButton;

    // FIFTH ROW
    VBox navigationToolbar;
    
    HBox row5HBox1;
    Label fontLabel;
    ColorPicker fontColorPicker; 
    
    HBox row5HBox2;
    ToggleButton boldButton;
    ToggleButton italicButton;
    ComboBox fontSizeBox;
    ComboBox fontFamilyBox;   
        
    // SIXTH ROW
    VBox row6Box;
    
    HBox row6HBox1;
    Label navigationLabel;    
    CheckBox gridCheckBox;
    
    HBox row6HBox2;
    Button zoomInButton;
    Button zoomOutButton;
    Button mapSizeIncreaseButton;
    Button mapSizeDecreaseButton;
    
    // THIS IS WHERE WE'LL RENDER OUR DRAWING, NOTE THAT WE
    // CALL THIS A CANVAS, BUT IT'S REALLY JUST A Pane
    Pane canvas;
    ScrollPane canvasScrollPane; //USED TO SET CANVAS AS ITS CONTENT
    GridPane canvasGridPane; //USED TO SHOW THE GRID LINE
    
    // HERE ARE THE CONTROLLERS
    CanvasController canvasController;
    MapEditController MapEditController; 
    m3Files FileController;

    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    AppWelcomeDialogSingleton appWelcomeDialog; 
    
    // FOR DISPLAYING DEBUG STUFF
    Text debugText;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public m3Workspace(AppTemplate initApp) {
        
       // KEEP THIS FOR LATER
	app = initApp;
	// KEEP THE GUI FOR LATER
	gui = app.getGUI();
        // LAYOUT THE APP
        initLayout();
        // HOOK UP THE CONTROLLERS
        initControllers();
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();  
        
    }

    /**
     * Note that this is for displaying text during development.
     */
    public void setDebugText(String text) {
	debugText.setText(text);
    }
    
    // ACCESSOR METHODS FOR COMPONENTS THAT EVENT HANDLERS
    // MAY NEED TO UPDATE OR ACCESS DATA FROM
    
    public ColorPicker getStationColorPicker() {
	return stationColorPicker;
    }
    
    public ColorPicker getBackgroundColorPicker() {
	return bgColorPicker;
    }
    
    public ColorPicker getFontColorPicker(){
        return fontColorPicker;
    }
    
    public Slider getLineThicknessSlider() {
	return lineThicknessSlider;
    }
    
    public Slider getStationRadiusSlider() {
	return stationRadiusSlider;
    }
    
    public ComboBox getLineNameBox(){
        return lineNameBox;
    }
    
    public ComboBox getStationNameBox(){
        return stationNameBox;
    }
    
    public ComboBox getStationSearchingBar1(){
        return stationSearchingBar1;
    }
    
    public ComboBox getStationSearchingBar2(){
        return stationSearchingBar2;
    } 
    
    public ComboBox getFontSizeBox(){
        return fontSizeBox;
    }
    
    public ComboBox getFontFamilyBox(){
        return fontFamilyBox;
    }    

    public Pane getCanvas() {
	return canvas;
    }
        
    // HELPER SETUP METHOD
    private void initLayout() {
        
	// THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
	editToolbar = new VBox();
        createGrid();
        
        // FIRST ROW
        metroLinesToolbar = new VBox();

        row1HBox1 = new HBox();
        lineLabel = new Label("Metro Lines");
        lineNameBox = new ComboBox(); 
        editLineButton = gui.initChildButton(row1HBox1, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);
               
        row1HBox2 = new HBox();
        addLineButton = gui.initChildButton(row1HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);
        removeLineButton = gui.initChildButton(row1HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);
        addStationsToLineButton = gui.initChildButton(row1HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);
        stationsListButton = gui.initChildButton(row1HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);

        lineThicknessSlider = new Slider(0, 10, 1);
        
        metroLinesToolbar.getChildren().addAll(row1HBox1, row1HBox2, lineThicknessSlider);

        // SECOND ROW
        metroStationsToolbar = new VBox();

        row2HBox1 = new HBox();
        stationLabel = new Label("Metro Stations");
        stationNameBox = new ComboBox();
        stationColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));  

        row2HBox2 = new HBox();
        addStationButton = gui.initChildButton(row2HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);
        removeStationButton = gui.initChildButton(row2HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);
        snapButton = gui.initChildButton(row2HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);
        moveStationLabelButton = gui.initChildButton(row2HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);
        rotateStationLabelButton = gui.initChildButton(row2HBox2, SAVE_AS_ICON.toString(), SAVE_AS_ICON.toString(), true);

        stationRadiusSlider = new Slider(0, 10, 1);

        // THIRD ROW
        stationRouterToolbar = new HBox();

        VBox row3VBox1;
        ComboBox stationSearchingBar1;
        ComboBox stationSearchingBar2;

        Button routeFindingButton;

        // FORTH ROW
        VBox decorToolbar;

        HBox row4HBox1;
        Label decorLabel;
        ColorPicker bgColorPicker;    

        HBox row4HBox2;
        Button imageBackgroundButton;
        Button addImageButton;
        Button addLabelButton;
        Button removeButton;

        // FIFTH ROW
        VBox navigationToolbar;

        HBox row5HBox1;
        Label fontLabel;
        ColorPicker fontColorPicker; 

        HBox row5HBox2;
        ToggleButton boldButton;
        ToggleButton italicButton;
        ComboBox fontSizeBox;
        ComboBox fontFamilyBox;   

        // SIXTH ROW
        VBox row6Box;

        HBox row6HBox1;
        Label navigationLabel;    
        CheckBox gridCheckBox;

        HBox row6HBox2;
        Button zoomInButton;
        Button zoomOutButton;
        Button mapSizeIncreaseButton;
        Button mapSizeDecreaseButton;
        /*
	// ROW 1
	row1Box = new HBox();
	selectionToolButton = gui.initChildButton(row1Box, SELECTION_TOOL_ICON.toString(), SELECTION_TOOL_TOOLTIP.toString(), true);
	removeButton = gui.initChildButton(row1Box, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), true);
	rectButton = gui.initChildButton(row1Box, RECTANGLE_ICON.toString(), RECTANGLE_TOOLTIP.toString(), false);
	ellipseButton = gui.initChildButton(row1Box, ELLIPSE_ICON.toString(), ELLIPSE_TOOLTIP.toString(), false);

	// ROW 2
	row2Box = new HBox();
	moveToBackButton = gui.initChildButton(row2Box, MOVE_TO_BACK_ICON.toString(), MOVE_TO_BACK_TOOLTIP.toString(), true);
	moveToFrontButton = gui.initChildButton(row2Box, MOVE_TO_FRONT_ICON.toString(), MOVE_TO_FRONT_TOOLTIP.toString(), true);

	// ROW 3
	row3Box = new VBox();
	backgroundColorLabel = new Label("Background Color");
	backgroundColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
	row3Box.getChildren().add(backgroundColorLabel);
	row3Box.getChildren().add(backgroundColorPicker);

	// ROW 4
	row4Box = new VBox();
	fillColorLabel = new Label("Fill Color");
	fillColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
	row4Box.getChildren().add(fillColorLabel);
	row4Box.getChildren().add(fillColorPicker);
	
	// ROW 5
	row5Box = new VBox();
	outlineColorLabel = new Label("Outline Color");
	outlineColorPicker = new ColorPicker(Color.valueOf(BLACK_HEX));
	row5Box.getChildren().add(outlineColorLabel);
	row5Box.getChildren().add(outlineColorPicker);
	
	// ROW 6
	row6Box = new VBox();
	outlineThicknessLabel = new Label("Outline Thickness");
	outlineThicknessSlider = new Slider(0, 10, 1);
	row6Box.getChildren().add(outlineThicknessLabel);
	row6Box.getChildren().add(outlineThicknessSlider);
	
	// ROW 7
	row7Box = new HBox();
	snapshotButton = gui.initChildButton(row7Box, SNAPSHOT_ICON.toString(), SNAPSHOT_TOOLTIP.toString(), false);
	
	// NOW ORGANIZE THE EDIT TOOLBAR
	editToolbar.getChildren().add(row1Box);
	editToolbar.getChildren().add(row2Box);
	editToolbar.getChildren().add(row3Box);
	editToolbar.getChildren().add(row4Box);
	editToolbar.getChildren().add(row5Box);
	editToolbar.getChildren().add(row6Box);
	editToolbar.getChildren().add(row7Box);
	*/
        editToolbar.getChildren().add(metroLinesToolbar);
        //row6Box; navigationToolbar; decorToolbar; stationRouterToolbar; metroStationsToolbar
        
	// WE'LL RENDER OUR STUFF HERE IN THE CANVAS
	canvas = new Pane();
	debugText = new Text();
	canvas.getChildren().add(debugText);
	debugText.setX(100);
	debugText.setY(100);
	
	// AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
	m3Data data = (m3Data)app.getDataComponent();
	data.setShapes(canvas.getChildren());
        
        
	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
	((BorderPane)workspace).setLeft(editToolbar);
        canvasScrollPane = new ScrollPane();
        canvasScrollPane.setContent(canvas);
	((BorderPane)workspace).setCenter(canvasScrollPane);       
        
    }
    
    // USED TO CREATE THE GRID
    private GridPane createGrid(){
        return canvasGridPane;
    }
    
    private void setGrid(boolean bool){
        
    }
    
    public void setWorkspaceMoveable(){
        //https://stackoverflow.com/questions/38604780/javafx-zoom-scroll-in-scrollpane
    }
    
    public void setWorkspaceZoomable(){
        
    }
    
    // HELPER SETUP METHOD
    private void initControllers() {
        //MAKE THE TOPTOOLBAR CONTROLLER
        FileController = new m3Files();
        //GET THE SCENE AND STAGE
        Scene scene = gui.getPrimaryScene();
        Stage stage = gui.getWindow();
	// MAKE THE EDIT CONTROLLER
	MapEditController = new MapEditController(app);
        
        // CONNECT ThE SCENE WITH "WASD" BUTTON
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
             @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:  break;
                    case S:  break;
                    case A:  break;
                    case D:  break;
                }
            }
        });
        
        // PROMT TO SAVE FOR EXITTING APPLICATION
        stage.setOnCloseRequest(e->{
            //PROMPT_TO_SAVE
        });
        
        // CONNECT THE GUI BUTTONS TO THEIR HANDLERS FOR TOPTOOLBAR       
       /*
        saveAsButton.setOnAction(e->{
            MapEditController.processSaveAsRequest();
        });
        exportButton.setOnAction(e->{
            MapEditController.processExportRequest();
        });
        undoButton.setOnAction(e->{
            MapEditController.processUndo();
        });
        redoButton.setOnAction(e->{
            MapEditController.processRedo();
        });
        aboutButton.setOnAction(e->{
            MapEditController.processAboutButtonRequest();
        });            
        
	// NOW CONNECT THE BUTTONS TO THEIR HANDLERS
	lineNameBox.setOnAction(e->{
	    MapEditController.processSelectingLine();
	});
	editLineButton.setOnAction(e->{
	    MapEditController.processLineEditting();
	});
	addLineButton.setOnAction(e->{
	    MapEditController.processSelectLineToDraw();
	});
        removeLineButton.setOnAction(e->{
            MapEditController.processRemovingSelectedLine();
        });
        addStationsToLineButton.setOnAction(e->{
            MapEditController.processAddStationOnSelectedLine();
        });	
	removeStationsFromLineButton.setOnAction(e->{
	    MapEditController.processRemoveStationOnSelectedLine();
	});
	stationsListButton.setOnAction(e->{
	    MapEditController.processStationsList();
	});
	lineThicknessSlider.valueProperty().addListener(e-> {
	    MapEditController.processSelectLineThickness();
	});
	stationNameBox.setOnAction(e->{ 
	    MapEditController.processSelectingStation();
	});
	stationColorPicker.setOnAction(e->{
	    MapEditController.processFillingStationColor();
	});
	addStationButton.setOnAction(e->{
	    MapEditController.processAddingNewStation();
	});               
	removeStationButton.setOnAction(e->{
	    MapEditController.processRemovingSelectedStation();
	}); 
	snapButton.setOnAction(e->{
	    MapEditController.processSnappingToGrid();
	}); 
	moveStationLabelButton.setOnAction(e->{
	    MapEditController.processMovingLabel();
	}); 
	rotateStationLabelButton.setOnAction(e->{
	    MapEditController.processRotatingLabel();
	}); 
        stationRadiusSlider.valueProperty().addListener(e-> {
	    MapEditController.processSelectedStationRadius();
	});
	stationSearchingBar1.setOnAction(e->{
	    MapEditController.processSearchingSelection1();
	}); 
	stationSearchingBar2.setOnAction(e->{
	    MapEditController.processSearchingSelection2();
	}); 
	routeFindingButton.setOnAction(e->{
	    MapEditController.processFindingRoute();
	}); 
	bgColorPicker.setOnAction(e->{
	    MapEditController.processFillingBgColor();
	}); 
	imageBackgroundButton.setOnAction(e->{
	    MapEditController.processSettingImageBg();
	}); 
	addImageButton.setOnAction(e->{
	    MapEditController.processAddingImage();
	}); 
	addLabelButton.setOnAction(e->{
	    MapEditController.processAddingLabel();
	});      
        removeButton.setOnAction(e->{
	    MapEditController.processRemovingElement();
	}); 
	fontColorPicker.setOnAction(e->{
	    MapEditController.processFillingFontColor();
	});         
	boldButton.setOnAction(e->{
	    MapEditController.processBoldLabel();
	});  
	italicButton.setOnAction(e->{
	    MapEditController.processItalicLabel();
	}); 
	fontSizeBox.setOnAction(e->{
	    MapEditController.processLabelFontSize();
	});  
	fontFamilyBox.setOnAction(e->{
	    MapEditController.processLabelFontFamily();
	}); 
	gridCheckBox.setOnAction(e->{
	    MapEditController.processShowingGrid();
	});  
	zoomInButton.setOnAction(e->{
	    MapEditController.processZoomingIn();
	}); 
	zoomOutButton.setOnAction(e->{
	    MapEditController.processZoomingOut();
	});  
	mapSizeIncreaseButton.setOnAction(e->{
	    MapEditController.processIncreasingMapSize();
	}); 
	mapSizeDecreaseButton.setOnAction(e->{
	    MapEditController.processDecreasingMapSize();
	});  
        */
        
	// MAKE THE CANVAS CONTROLLER	
	canvasController = new CanvasController(app);
	canvas.setOnMousePressed(e->{
	    canvasController.processCanvasMousePress((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseReleased(e->{
	    canvasController.processCanvasMouseRelease((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseDragged(e->{
	    canvasController.processCanvasMouseDragged((int)e.getX(), (int)e.getY());
	});
        
        setWorkspaceMoveable();
        setWorkspaceZoomable();
        
    }

    // HELPER METHOD
    public void loadSelectedShapeSettings(Shape shape) {
	if (shape != null) {	    
	}
    }

    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
        
        /*
	canvas.getStyleClass().add(CLASS_RENDER_CANVAS);
	
	// COLOR PICKER STYLE
	fillColorPicker.getStyleClass().add(CLASS_BUTTON);
	outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
	backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);
	
	editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
	row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row2Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row3Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	backgroundColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	
	row4Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	fillColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row5Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	outlineColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row6Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	outlineThicknessLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row7Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        */
    }

    /**
     * This function reloads all the controls for editing logos
     * the workspace.
     */
    @Override
    public void reloadWorkspace(AppDataComponent data) {
	m3Data dataManager = (m3Data)data;
	if (dataManager.isInState(m3State.STARTING_LINE)) {           
	}
	else if (dataManager.isInState(m3State.ADDING_STATION)) {  
	}
        else if (dataManager.isInState(m3State.STARTING_IMAGE)) {
	}
	else if (dataManager.isInState(m3State.ADDING_LABEL)) {

	}                
	else if (dataManager.isInState(m3State.SELECTING_SHAPE) 
		|| dataManager.isInState(m3State.DRAGGING_SHAPE)
		|| dataManager.isInState(m3State.DRAGGING_NOTHING)) {
	    boolean shapeIsNotSelected = dataManager.getSelectedShape() == null;
	}
	
	//removeButton.setDisable(dataManager.getSelectedShape() == null);
	bgColorPicker.setValue(dataManager.getBackgroundColor());
    }
    
    @Override
    public void resetWorkspace() {
        // WE ARE NOT USING THIS, THOUGH YOU MAY IF YOU LIKE
    }
}
