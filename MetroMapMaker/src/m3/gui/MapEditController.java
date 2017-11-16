/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;
/*
import djf.AppTemplate;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
*/

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import m3.data.m3Data;
import m3.data.m3State;
import djf.AppTemplate;
import static djf.settings.AppPropertyType.APP_LOGO;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import jtps.jTPS;
import static m3.data.Draggable.LINE;
import static m3.data.Draggable.STATION;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import static m3.data.m3State.ADD_STATION_MODE;
import static m3.data.m3State.REMOVE_STATION_MODE;
import properties_manager.PropertiesManager;

/**
 * This class responds to interactions with other UI logo editing controls.
 * 
 * @author Kai
 */
public class MapEditController {
    
    AppTemplate app;
    m3Data dataManager;

    public MapEditController(AppTemplate initApp) {
        app = initApp;
        dataManager = (m3Data)app.getDataComponent();
    }

    /**
     * This method handles request of showing our program information.
     */
    public void processAboutButtonRequest() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Alert aboutDialog = new Alert(AlertType.INFORMATION);
        aboutDialog.setTitle("About Us");
        Image image = new Image(FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_LOGO));
        ImageView logo = new ImageView(image);        
        aboutDialog.setGraphic(logo);
        aboutDialog.setHeaderText("Information:");
        aboutDialog.setContentText("Metro Map Maker\n"
                                   + "Version: 0.0.1\n"
                                   + "Published date: 11/12/2017\n"
                                   + "Build id: I-WANT-TO-GET-A\n");
        aboutDialog.showAndWait();        
    }
    /**
     * This method handles the response for selecting either the
     * selection or removal tool.
     */
    public void processSelectSelectionTool() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.DEFAULT);

        // CHANGE THE STATE
        dataManager.setState(m3State.SELECTING_SHAPE);	

        // ENABLE/DISABLE THE PROPER BUTTONS
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }
    
    /**
     * This method redo user behavior on the canvas.
     */    
    public void processRedo(){
        jTPS tps = app.getTPS();
        tps.doTransaction();
        app.getGUI().updateToolbarControls(false);
    }
 
    /**
     * This method undo user behavior on the canvas.
     */    
    public void processUndo(){
        jTPS tps = app.getTPS();
        tps.undoTransaction();
        app.getGUI().updateToolbarControls(false);        
    }

    /**
     * This method handles the line that is chosen by the user through lineComboBox.
     */
    public void processSelectingLine(){
        dataManager.ListSelectedLineStation();
    }
    
    /**
     * This method processes a user request to select a fill color for
     * the selected line through lineComboBox.
     */
     public void processLineEditting(){
         dataManager.showLineEditDialog();
     }
    
    /**
     * This method processes a user request to start drawing a line.
     */
    public void processSelectLineToDraw() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        Stage stage = app.getGUI().getWindow();
        scene.setCursor(Cursor.CROSSHAIR);
        InfoRequireDialogSingleton infoDialog = InfoRequireDialogSingleton.getSingleton();    
        infoDialog.setType(LINE);
        infoDialog.init(stage);
        infoDialog.show("Add New Line", "");
        // CHANGE THE STATE
        if(infoDialog.getName() != null){
            dataManager.setState(m3State.STARTING_LINE);
            
        }
        // ENABLE/DISABLE THE PROPER BUTTONS
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }
    
    /**
     * This method removes the line that is chosen by the user through lineComboBox.
     */
    public void processRemovingSelectedLine(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        m3Data data = (m3Data)app.getDataComponent();
        String lineName = (String)workspace.getLineNameBox().getValue();
        if(data.searchNode(lineName))
            data.removeSelectedNode();     
    }
    
    /**
     * This method change the state to ADD_STATION_MODE and helps adding station 
     * on the selected line that is chosen by the user through lineComboBox.
     */
    public void processAddStationOnSelectedLine(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        m3Data data = (m3Data)app.getDataComponent();
        String lineName = (String)workspace.getLineNameBox().getValue();        
        if(data.searchNode(lineName)){
            data.setState(ADD_STATION_MODE);
        }                 
    }    
    
    /**
     * This method change the state to REMOVE_STATION_MOVE and helps
     * remove station on the selected line that is chosen by the user through 
     * lineComboBox.
     */
    public void processRemoveStationOnSelectedLine(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        m3Data data = (m3Data)app.getDataComponent();
        String lineName = (String)workspace.getLineNameBox().getValue();        
        if(data.searchNode(lineName)){
            data.setState(REMOVE_STATION_MODE);
        }               
    }    
    
    /**
     * This method shows station(s) that is on the selected line 
     * that is chosen by the user through lineComboBox.
     */
    public void processStationsList(){      
       dataManager.ListSelectedLineStation();
    }      
    
    /**
     * This method processes a user request to select the thickness
     * for line drawing / the selected line through thicknessSlider.
     */
    public void processSelectLineThickness() {
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        m3Data data = (m3Data)app.getDataComponent();
        int lineThickness = (int)workspace.getLineThicknessSlider().getValue();  
        String lineName = (String)workspace.getLineNameBox().getValue(); 
        if(data.searchNode(lineName)){   
            DraggableLine selectLine = (DraggableLine)data.getSelectedNode();
            selectLine.setStrokeWidth(lineThickness);
        } 
        app.getGUI().updateToolbarControls(false);
        workspace.reloadWorkspace(dataManager);
    }  
    
    /**
     * This method handles the station that is chosen by the user through 
     * stationComboBox.
     */
    public void processSelectingStation(){
        
    }    
    
    /**
     * This method processes a user request to select a fill color for
     * the selected station through stationComboBox.
     */
     public void processFillingStationColor(){
         
     }    
    
    /**
     * This method adds a new station and update the stationComboBox list 
     * and selecting station.
     */
    public void processAddingNewStation(){
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        Stage stage = app.getGUI().getWindow();
        scene.setCursor(Cursor.CROSSHAIR);
        InfoRequireDialogSingleton infoDialog = InfoRequireDialogSingleton.getSingleton(); 
        infoDialog.setType(STATION);
        infoDialog.init(stage);
        infoDialog.show("Add New Station", "");        
        // CHANGE THE STATE
        if(infoDialog.getName() != null)       
            dataManager.setState(m3State.ADDING_STATION);

        // ENABLE/DISABLE THE PROPER BUTTONS
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }    
    
    /**
     * This method adds a new station and update the stationComboBox list 
     * and selecting station.
     */
    public void processRemovingSelectedStation(){
        
    }    
    
    /**
     * This method snaps the selected station to the grid.
    */
    public void processSnappingToGrid(){
        
    }       
 
    /**
     * This method allows user to move the selected station label.
     */    
    public void processMovingLabel(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        m3Data data = (m3Data)app.getDataComponent();
        String stationName = (String)workspace.getStationNameBox().getValue();        
        if(data.searchNode(stationName)){
            DraggableStation selectedStation = (DraggableStation)data.getSelectedNode();
            selectedStation.moveLabel();
        }                       
        //get selected station and do moveLabel()
   }
  
    /**
     * This method rotates the selected station label clockwise 45 degree.
     */     
    public void processRotatingLabel(){
        
    }
    
    /**
     * This method allows user to modify the radius of the selected station.
     */
    public void processSelectedStationRadius(){
       m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        m3Data data = (m3Data)app.getDataComponent();
        int radius = (int)workspace.getStationRadiusSlider().getValue();  
        String stationName = (String)workspace.getStationNameBox().getValue(); 
        if(data.searchNode(stationName)){   
            DraggableStation selectStation = (DraggableStation)data.getSelectedNode();
            selectStation.setRadiusX(radius);
            selectStation.setRadiusY(radius);
        } 
        app.getGUI().updateToolbarControls(false);
        workspace.reloadWorkspace(dataManager);        
    }
    
    /**
     * This method handles the selected line through searchingComboBox1.
     */    
    public void processSearchingSelection1(){
          
    }
  
    /**
     * This method handles the selected line through searchingComboBox2.
     */      
    public void processSearchingSelection2(){
        
        
    }
 
    /**
     * This method lists the route from the result of 
     * searchingComboBox1 to searchingComboBox2.
     */      
    public void processFindingRoute(){
        DraggableStation fromStation = null;
        DraggableStation toStation = null;
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        m3Data data = (m3Data)app.getDataComponent();
        String searchStation1Name = (String)workspace.getStationSearchingBar1().getValue();
        String searchStation2Name = (String)workspace.getStationSearchingBar1().getValue();
        if(data.searchNode(searchStation1Name)){
            DraggableStation searchingStation1 = (DraggableStation)data.getSelectedNode();
        }    
         if(data.searchNode(searchStation2Name)){
            DraggableStation searchingStation2 = (DraggableStation)data.getSelectedNode();
        }
        RouteDialogSingleton routeDialog = RouteDialogSingleton.getSingleton();
        routeDialog.setStations(fromStation, toStation);
        routeDialog.setInfo();
        routeDialog.show("Metro Map Maker - Route", "");
    }
 
    /**
     * This method fills the color of the background.
     */      
    public void processFillingBgColor(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Color selectedColor = workspace.getBackgroundColorPicker().getValue();
        if (selectedColor != null) {
            dataManager.setBackgroundColor(selectedColor);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * This method sets an Image as the background.
     */      
    public void processSettingImageBg(){
        
    }

     /**
     * This method provides a response to the user requesting to start
     * inserting and sizing an Image.
     */     
    public void processAddingImage(){
         // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(m3State.STARTING_IMAGE);

        // ENABLE/DISABLE THE PROPER BUTTONS
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);     
    }

    /**
     * This method adds a label.
     */      
    public void processAddingLabel(){
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(m3State.ADDING_LABEL);

        // ENABLE/DISABLE THE PROPER BUTTONS
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * This method removes the any selected element.
     */      
    public void processRemovingElement(){
        
    }
    
    /**
     * This method shows/unshows the grid.
     */
    public void processShowingGrid(){
        
    }
    
    /**
     * This method zooms in the workspace.
     */
    public void processZoomingIn(){
        
    }
    
    /**
     * This method zooms out the workspace.
     */      
    public void processZoomingOut(){
        
    } 

    /**
     * This method increase the size of the map.
     */      
    public void processIncreasingMapSize(){
        
    } 

    /**
     * This method decrease the size of the map.
     */      
    public void processDecreasingMapSize(){
        
    }     
}