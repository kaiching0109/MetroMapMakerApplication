/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.ADD_ICON;
import static djf.settings.AppPropertyType.ADD_IMAGE_TOOLTIP;
import static djf.settings.AppPropertyType.ADD_LABEL_TOOLTIP;
import static djf.settings.AppPropertyType.ADD_LINE_TOOLTIP;
import static djf.settings.AppPropertyType.ADD_STATION_ON_LINE_TOOLTIP;
import static djf.settings.AppPropertyType.ADD_STATION_TOOLTIP;
import static djf.settings.AppPropertyType.BOLD_ICON;
import static djf.settings.AppPropertyType.BOLD_TOOLTIP;
import static djf.settings.AppPropertyType.DEC_ICON;
import static djf.settings.AppPropertyType.DEC_TOOLTIP;
import static djf.settings.AppPropertyType.FIND_ICON;
import static djf.settings.AppPropertyType.FIND_TOOLTIP;
import static djf.settings.AppPropertyType.IMAGE_BACKGROUND_TOOLTIP;
import static djf.settings.AppPropertyType.INC_ICON;
import static djf.settings.AppPropertyType.INC_TOOLTIP;
import static djf.settings.AppPropertyType.ITALICS_ICON;
import static djf.settings.AppPropertyType.ITALICS_TOOLTIP;
import static djf.settings.AppPropertyType.LINE_EDITOR_TOOLTIP;
import static djf.settings.AppPropertyType.LIST_ICON;
import static djf.settings.AppPropertyType.LIST_TOOLTIP;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.MOVE_LABEL_TOOLTIP;
import static djf.settings.AppPropertyType.RADIUS_SLIDER_TOOLTIP;
import static djf.settings.AppPropertyType.REMOVE_ELEMENT_TOOLTOP;
import static djf.settings.AppPropertyType.REMOVE_ICON;
import static djf.settings.AppPropertyType.REMOVE_LINE_TOOLTIP;
import static djf.settings.AppPropertyType.REMOVE_STATION_FROM_LINE_TOOLTIP;
import static djf.settings.AppPropertyType.REMOVE_STATION_TOOLTIP;
import static djf.settings.AppPropertyType.ROTATE_ICON;
import static djf.settings.AppPropertyType.ROTATE_TOOLTIP;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import static djf.settings.AppPropertyType.SAVE_AS_ICON;
import static djf.settings.AppPropertyType.SNAP_TOOLTIP;
import static djf.settings.AppPropertyType.THICKNESS_SLIDER_TOOLTIP;
import static djf.settings.AppPropertyType.ZOOMIN_ICON;
import static djf.settings.AppPropertyType.ZOOMIN_TOOLTIP;
import static djf.settings.AppPropertyType.ZOOMOUT_ICON;
import static djf.settings.AppPropertyType.ZOOMOUT_TOOLTIP;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static m3.css.m3Style.CLASS_BUTTON;
import static m3.css.m3Style.CLASS_BUTTON_HGAP;
import static m3.css.m3Style.CLASS_BUTTON_HGAP_RIGHTMOST;
import static m3.css.m3Style.CLASS_COLOR_CHOOSER_CONTROL;
import static m3.css.m3Style.CLASS_EDIT_TOOLBAR;
//import static m3.css.m3Style.CLASS_EDIT_TOOLBAR_BUTTON_SIZE;
import static m3.css.m3Style.CLASS_EDIT_TOOLBAR_ROW;
import static m3.css.m3Style.CLASS_FIND_BUTTON_SIZE;
import static m3.css.m3Style.CLASS_RENDER_CANVAS;
import m3.data.m3Data;
import static m3.data.m3Data.WHITE_HEX;
import m3.data.m3State;
import m3.file.m3Files;
import properties_manager.PropertiesManager;

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

    // HAS ALL THE CONTROLS FOR EDITING
    ScrollPane editToolScrollbar;
    VBox editToolbar;
    
    // SPACING
    Pane space;
    
    // FIRST ROW
    VBox metroLinesToolbar;
    
    HBox row1HBox1;
    Label lineLabel;
    ComboBox lineNameBox; 
    StackPane stackPaneForLine;
    Text colorHexForLine;
    Ellipse ellipseForLine;
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
    StackPane stackPaneForStation;
    Ellipse ellipseForStation;
    Text colorHexForStation;
    
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
    StackPane stackPaneForBackground;
    Text colorHexForBackground;
    Ellipse ellipseForBackground;
    
    HBox row4HBox2;
    Button imageBackgroundButton;
    Button addImageButton;
    Button addLabelButton;
    Button removeButton;

    // FIFTH ROW
    VBox fontToolbar;
    
    HBox row5HBox1;
    Label fontLabel;
    ColorPicker fontColorPicker; 
    StackPane stackPaneForFont;
    Text colorHexForFont;    
    Ellipse ellipseForFont;
    
    HBox row5HBox2;
    ToggleButton boldButton;
    ToggleButton italicButton;
    ComboBox fontSizeBox;
    ComboBox fontFamilyBox;   
        
    // SIXTH ROW
    VBox navigationToolbar;
    
    HBox row6HBox1;
    Label navigationLabel;    
    CheckBox gridCheckBox;
    Label gridCheckBoxLabel;
    
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
     * 
     * @param text text that we want to show for debugging message.
     */
    public void setDebugText(String text) {
	debugText.setText(text);
    }
    
    // ACCESSOR METHODS FOR COMPONENTS THAT EVENT HANDLERS
    // MAY NEED TO UPDATE OR ACCESS DATA FROM
    
    /**
     * The accessor method for the station color.
     * 
     * @return stationColorPicker the colorPicker of the selected station
     */
    public ColorPicker getStationColorPicker() {
	return stationColorPicker;
    }
    
    /**
     * The accessor method for the background color.
     * 
     * @return bgColorPicker the colorPicker of the background
     */    
    public ColorPicker getBackgroundColorPicker() {
	return bgColorPicker;
    }
 
    /**
     * The accessor method for the font color.
     * 
     * @return fontColorPicker the colorPicker of the selected label
     */      
    public ColorPicker getFontColorPicker(){
        return fontColorPicker;
    }
    
    /**
     * The accessor method for the line thickness.
     * 
     * @return lineThicknessSlider the slider of the selected line's thickness
     */     
    public Slider getLineThicknessSlider() {
	return lineThicknessSlider;
    }

    /**
     * The accessor method for the line thickness.
     * 
     * @return lineThicknessSlider the slider of the selected line's thickness
     */      
    public Slider getStationRadiusSlider() {
	return stationRadiusSlider;
    }

    /**
     * The accessor method for the line name.
     * 
     * @return lineNameBox the comboBox of the selected line's name
     */        
    public ComboBox getLineNameBox(){
        return lineNameBox;
    }

    /**
     * The accessor method for the line name.
     * 
     * @return lineNameBox the comboBox of the selected line's name
     */       
    public ComboBox getStationNameBox(){
        return stationNameBox;
    }

    /**
     * The accessor method for the selected station for searching.
     * 
     * @return stationSearchingBar1 the comboBox of the searching station name
     */       
    public ComboBox getStationSearchingBar1(){
        return stationSearchingBar1;
    }
    
    /**
     * The accessor method for the selected station for searching.
     * 
     * @return stationSearchingBar1 the comboBox of the searching station name
     */           
    public ComboBox getStationSearchingBar2(){
        return stationSearchingBar2;
    } 
 
    /**
     * The accessor method for the font size of the selected label.
     * 
     * @return fontSizeBox the comboBox of the label's font size
     */      
    public ComboBox getFontSizeBox(){
        return fontSizeBox;
    }

    /**
     * The accessor method for the font family of the selected label.
     * 
     * @return fontFamilyBox the comboBox of the label's font family.
     */     
    public ComboBox getFontFamilyBox(){
        return fontFamilyBox;
    }    

    /**
     * The accessor method for the canvas.
     * 
     * @return canvas the pane for rendering our drawing.
     */      
    public Pane getCanvas() {
	return canvas;
    }
        
    // HELPER SETUP METHOD
    private void initLayout() {
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	// THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
	editToolbar = new VBox();
        createGrid();
        
        //GUI BUTTONS ON THE TOP 
        
        // FIRST ROW
        metroLinesToolbar = new VBox();

        row1HBox1 = new HBox();
        lineLabel = new Label("Metro Lines");
        lineNameBox = new ComboBox(); 
        space = new Pane();
        row1HBox1.setHgrow(space, Priority.ALWAYS);
        stackPaneForLine = new StackPane();
        editLineButton = gui.initChildButton(stackPaneForLine, "", LINE_EDITOR_TOOLTIP.toString(), true);
        ellipseForLine = new Ellipse(20, 20);
        colorHexForFont = new Text();
        stackPaneForLine.getChildren().addAll(ellipseForLine, colorHexForFont);
        editLineButton.getStyleClass().add(CLASS_BUTTON_HGAP_RIGHTMOST);
        row1HBox1.getChildren().addAll(lineLabel, lineNameBox, space, stackPaneForLine);
        
        row1HBox1.getStyleClass().add(CLASS_BUTTON_HGAP);
        
        row1HBox2 = new HBox();
        addLineButton = gui.initChildButton(row1HBox2, ADD_ICON.toString(), ADD_LINE_TOOLTIP.toString(), false);
        removeLineButton = gui.initChildButton(row1HBox2, REMOVE_ICON.toString(), REMOVE_LINE_TOOLTIP.toString(), true);
        addStationsToLineButton = gui.initChildButton(row1HBox2, "", ADD_STATION_ON_LINE_TOOLTIP.toString(), true);
        addStationsToLineButton.setText("Add\nStation");
        removeStationsFromLineButton = gui.initChildButton(row1HBox2, "", REMOVE_STATION_FROM_LINE_TOOLTIP.toString(), true);
        removeStationsFromLineButton.setText("Remove\nStation");
        stationsListButton = gui.initChildButton(row1HBox2, LIST_ICON.toString(), LIST_TOOLTIP.toString(), false);       
        row1HBox2.getStyleClass().add(CLASS_BUTTON_HGAP);
//        row1HBox2.getStyleClass().add(CLASS_EDIT_TOOLBAR_BUTTON_SIZE);
        
        lineThicknessSlider = new Slider(0, 10, 1);
        Tooltip lineThicknessSliderTooltip = new Tooltip(props.getProperty(THICKNESS_SLIDER_TOOLTIP.toString()));
        lineThicknessSlider.setTooltip(lineThicknessSliderTooltip);        
        
        metroLinesToolbar.getChildren().addAll(row1HBox1, row1HBox2, lineThicknessSlider);

        // SECOND ROW
        metroStationsToolbar = new VBox();

        row2HBox1 = new HBox();
        stationLabel = new Label("Metro Stations");
        stationNameBox = new ComboBox();
        stationColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX)); 
        space = new Pane();
        stackPaneForStation = new StackPane();
        ellipseForStation = new Ellipse(20, 20);
        ellipseForStation.setFill(stationColorPicker.getValue());
        colorHexForStation = new Text(stationColorPicker.getValue().toString());
        
        stackPaneForStation.getChildren().addAll(stationColorPicker, ellipseForStation, colorHexForStation);
        row2HBox1.setHgrow(space, Priority.ALWAYS);
        row2HBox1.getChildren().addAll(stationLabel, stationNameBox, space, stackPaneForStation);
        row2HBox1.getStyleClass().add(CLASS_BUTTON_HGAP);
        
        row2HBox2 = new HBox();
        addStationButton = gui.initChildButton(row2HBox2, ADD_ICON.toString(), ADD_STATION_TOOLTIP.toString(), false);
        removeStationButton = gui.initChildButton(row2HBox2, REMOVE_ICON.toString(), REMOVE_STATION_TOOLTIP.toString(), false);
        snapButton = gui.initChildButton(row2HBox2, "", SNAP_TOOLTIP.toString(), false);
        snapButton.setText("Snap");
        moveStationLabelButton = gui.initChildButton(row2HBox2, "", MOVE_LABEL_TOOLTIP.toString(), false);
        moveStationLabelButton.setText("Move\nLabel");
        rotateStationLabelButton = gui.initChildButton(row2HBox2, ROTATE_ICON.toString(), ROTATE_TOOLTIP.toString(), false);
        row2HBox2.getStyleClass().add(CLASS_BUTTON_HGAP);
//        row2HBox2.getStyleClass().add(CLASS_EDIT_TOOLBAR_BUTTON_SIZE);
        
        stationRadiusSlider = new Slider(0, 10, 1);
        Tooltip stationRadiusSliderTooltip = new Tooltip(props.getProperty(RADIUS_SLIDER_TOOLTIP.toString()));
        stationRadiusSlider.setTooltip(stationRadiusSliderTooltip);
        
        metroStationsToolbar.getChildren().addAll(row2HBox1, row2HBox2, stationRadiusSlider);

        // THIRD ROW
        stationRouterToolbar = new HBox();

        row3VBox1 = new VBox();
        stationSearchingBar1 = new ComboBox();
        stationSearchingBar2 = new ComboBox();
        row3VBox1.getChildren().addAll(stationSearchingBar1, stationSearchingBar2);
        row3VBox1.getStyleClass().add(CLASS_BUTTON_HGAP);
        
        space = new Pane();
        stationRouterToolbar.setHgrow(space, Priority.ALWAYS);
        stationRouterToolbar.getChildren().addAll(row3VBox1, space);
        routeFindingButton = gui.initChildButton(stationRouterToolbar, FIND_ICON.toString(), FIND_TOOLTIP.toString(), false);
        //routeFindingButton.getStyleClass().add(CLASS_BUTTON_HGAP);
        routeFindingButton.getStyleClass().add(CLASS_FIND_BUTTON_SIZE);
        
        // FORTH ROW
        decorToolbar = new VBox();

        row4HBox1 = new HBox();
        decorLabel = new Label("Decor");
        space = new Pane();
        row4HBox1.setHgrow(space, Priority.ALWAYS);      
        bgColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));   
        row4HBox1.getChildren().addAll(decorLabel, space, bgColorPicker);
        row4HBox1.getStyleClass().add(CLASS_BUTTON_HGAP);    

        row4HBox2 = new HBox();
        imageBackgroundButton = gui.initChildButton(row4HBox2, "", IMAGE_BACKGROUND_TOOLTIP.toString(), false);
        imageBackgroundButton.setText("Set Image\nBackground");
        addImageButton = gui.initChildButton(row4HBox2, "", ADD_IMAGE_TOOLTIP.toString(), false);
        addImageButton.setText("Add\nImage");
        addLabelButton = gui.initChildButton(row4HBox2, "", ADD_LABEL_TOOLTIP.toString(), false);
        addLabelButton.setText("Add\nLabel");
        removeButton = gui.initChildButton(row4HBox2, "", REMOVE_ELEMENT_TOOLTOP.toString(), false);
        removeButton.setText("Remove\nElement");
        row4HBox2.getStyleClass().add(CLASS_BUTTON_HGAP);
//        row4HBox2.getStyleClass().add(CLASS_EDIT_TOOLBAR_BUTTON_SIZE);
        
        decorToolbar.getChildren().addAll(row4HBox1, row4HBox2);
        
        // FIFTH ROW
        fontToolbar = new VBox();

        row5HBox1 = new HBox();
        fontLabel = new Label("Font");
        fontColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX)); 
        space = new Pane();
        row4HBox1.setHgrow(space, Priority.ALWAYS); 
        row5HBox1.getChildren().addAll(fontLabel, space, fontColorPicker);
        row5HBox1.getStyleClass().add(CLASS_BUTTON_HGAP);

        row5HBox2 = new HBox();
        boldButton = gui.initChildToggleButton(row5HBox2, BOLD_ICON.toString(), BOLD_TOOLTIP.toString(), false);
        italicButton = gui.initChildToggleButton(row5HBox2, ITALICS_ICON.toString(), ITALICS_TOOLTIP.toString(), false);
        fontSizeBox = new ComboBox();
        fontFamilyBox = new ComboBox();
        row5HBox2.getChildren().addAll(fontSizeBox, fontFamilyBox);
        row5HBox2.getStyleClass().add(CLASS_BUTTON_HGAP);
        //row5HBox2.getStyleClass().add(CLASS_EDIT_TOOLBAR_BUTTON_SIZE);
        
        fontToolbar.getChildren().addAll(row5HBox1, row5HBox2);

        // SIXTH ROW
        navigationToolbar = new VBox();

        row6HBox1 = new HBox();
        navigationLabel = new Label("Navigation");    
        gridCheckBox = new CheckBox();
        gridCheckBoxLabel = new Label("Show Label");
        space = new Pane();
        row6HBox1.setHgrow(space, Priority.ALWAYS);  
        row6HBox1.getChildren().addAll(navigationLabel, space, gridCheckBox, gridCheckBoxLabel);

        row6HBox2 = new HBox();
        zoomInButton = gui.initChildButton(row6HBox2, ZOOMIN_ICON.toString(), ZOOMIN_TOOLTIP.toString(), false);
        zoomOutButton = gui.initChildButton(row6HBox2, ZOOMOUT_ICON.toString(), ZOOMOUT_TOOLTIP.toString(), false);
        mapSizeIncreaseButton = gui.initChildButton(row6HBox2, INC_ICON.toString(), INC_TOOLTIP.toString(), false);
        mapSizeDecreaseButton = gui.initChildButton(row6HBox2, DEC_ICON.toString(), DEC_TOOLTIP.toString(), false);
        row6HBox2.getStyleClass().add(CLASS_BUTTON_HGAP);
//        row6HBox2.getStyleClass().add(CLASS_EDIT_TOOLBAR_BUTTON_SIZE);
        navigationToolbar.getChildren().addAll(row6HBox1, row6HBox2);
  
	// WE'LL RENDER OUR STUFF HERE IN THE CANVAS
	canvas = new Pane();
	debugText = new Text();
	canvas.getChildren().add(debugText);
	debugText.setX(100);
	debugText.setY(100);
	
	// AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
	m3Data data = (m3Data)app.getDataComponent();
	data.setM3Nodes(canvas.getChildren());
        
	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
        editToolScrollbar = new ScrollPane();
	((BorderPane)workspace).setLeft(editToolScrollbar);
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
       
    private void setWorkspaceMoveable(){
        //https://stackoverflow.com/questions/38604780/javafx-zoom-scroll-in-scrollpane
    }
    
    private void setWorkspaceZoomable(){
        
    }
    
    /**
     * This function is used to detected the changes that user makes for the font of a label
     * 
     * @return newFont newFont is the changed font
     */
    public Font getCurrentFontSettings() {
        String fontFamily = fontFamilyBox.getSelectionModel().getSelectedItem().toString();
        int fontSize = Integer.valueOf(fontFamilyBox.getSelectionModel().getSelectedItem().toString());
        FontWeight weight = FontWeight.NORMAL;
        if (boldButton.isPressed()) weight = FontWeight.BOLD;
        FontPosture posture = FontPosture.REGULAR;
        if (italicButton.isPressed()) posture = FontPosture.ITALIC;
        Font newFont = Font.font(fontFamily, weight, posture, fontSize);
        return newFont;
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
        // MAKE THE IMAGE AND LABEL CONTROLLER
        ImageAndLabelController ImageAndTextController = new ImageAndLabelController(app);
        
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
            //if unsave
           app.getGUI().getFileController().handleExitRequest();
        });
        
        // CONNECT THE GUI BUTTONS TO THEIR HANDLERS FOR TOPTOOLBAR   
        Button undoButton = (Button)app.getGUI().getUndoToolbar().getChildren().get(0);
        Button redoButton = (Button)app.getGUI().getUndoToolbar().getChildren().get(1);
        Button aboutButton = (Button)app.getGUI().getAboutToolbar().getChildren().get(0);
        
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
	    ImageAndTextController.processAddImage();                
        });
        
        addLabelButton.setOnAction(e->{ 
            ImageAndTextController.processAddLabel();
        });  
        
        removeButton.setOnAction(e->{
	    MapEditController.processRemovingElement();
	}); 
        
	fontColorPicker.setOnAction(e->{
	    ImageAndTextController.processChangeFont();
	});         
        
	boldButton.setOnAction(e->{
	    ImageAndTextController.processChangeFont();
	});  
        
	italicButton.setOnAction(e->{
	    ImageAndTextController.processChangeFont();
	}); 
        
	fontSizeBox.setOnAction(e->{
	    ImageAndTextController.processChangeFont();
	});  
        
	fontFamilyBox.setOnAction(e->{
	    ImageAndTextController.processChangeFont();
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

    /**
     * This function is used to load the info of the shape.
     * eg: Color
     * The loading info depends on the type of the shape,
     * @param shape shape is the selected shape
     */
    public void loadSelectedNodeSettings(Node node) {
	if (node != null) {	    
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
        app.getGUI().getFileToolbar().setStyle("-fx-spacing: 10");
        app.getGUI().getUndoToolbar().setStyle("-fx-spacing: 10");
        app.getGUI().getAboutToolbar().setStyle("-fx-spacing: 10");
        
        editToolbar.getChildren().add(metroLinesToolbar);
        editToolbar.getChildren().add(metroStationsToolbar);
	editToolbar.getChildren().add(stationRouterToolbar);
	editToolbar.getChildren().add(decorToolbar);
        editToolbar.getChildren().add(fontToolbar);
	editToolbar.getChildren().add(navigationToolbar);  
        editToolScrollbar.setContent(editToolbar);
	canvas.getStyleClass().add(CLASS_RENDER_CANVAS);
	
	// COLOR PICKER STYLE
	//stationColorPicker.getStyleClass().add(CLASS_BUTTON);
	//bgColorPicker.getStyleClass().add(CLASS_BUTTON);
	//fontColorPicker.getStyleClass().add(CLASS_BUTTON);
	
	editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
	metroLinesToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	metroStationsToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        stationRouterToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	decorToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	fontToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	navigationToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        
        // WELCOME DIALOG
        appWelcomeDialog = AppWelcomeDialogSingleton.getSingleton();
       // ..appWelcomeDialog.getRecentWorkPane().getLeft()
        appWelcomeDialog.getRecentWorkBar().setStyle("-fx-background-color: #ddddff");
        appWelcomeDialog.getRecentWorkLabel().setStyle("-fx-font-weight:bold;");
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
	    boolean shapeIsNotSelected = dataManager.getSelectedNode() == null;
	}
	
	//removeButton.setDisable(dataManager.getSelectedShape() == null);
	bgColorPicker.setValue(dataManager.getBackgroundColor());
    }
    
    @Override
    /**
     * We are not using this.
     */
    public void resetWorkspace() {
        // WE ARE NOT USING THIS, THOUGH YOU MAY IF YOU LIKE
    }
}
