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
import static djf.settings.AppPropertyType.LINE_EXISTED_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LINE_EXISTED_ERROR_TITLE;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.STATION_EXISTED_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.STATION_EXISTED_ERROR_TITLE;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppMessageDialogSingleton;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
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
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        dataManager.searchLine((String)workspace.getLineNameBox().getValue());
        workspace.loadSelectedNodeSettings(dataManager.getSelectedNode());
        //LineEditDialogSingleton lineEditDialog = LineEditDialogSingleton.getSingleton();
    }
    
    /**
     * This method processes a user request to select a fill color for
     * the selected line through lineComboBox.
     */
     public void processLineEditting(){
         Platform.runLater(() -> {
            m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
            ComboBox lineNameBox = workspace.getLineNameBox();
            LineEditDialogSingleton lineEditDialog = LineEditDialogSingleton.getSingleton();
            lineEditDialog.show("Metro Map Maker - Metro Line Stops", "");  

            //Get the selected Line from data class.
            dataManager.searchLine((String)lineNameBox.getValue());
            DraggableLine selectedLine = (DraggableLine)dataManager.getSelectedNode();
            String selectedLineName = selectedLine.getName();

            //Get the color and name that might be changed.
            Color editedColor = lineEditDialog.getLineColorPicker().getValue();
            String editedName = lineEditDialog.getLineName();

            //Set new color
            selectedLine.setColor(editedColor);

            //Check if name has modified
            if(!selectedLineName.equals(editedName)){

                //Check if new name existed and replace the name if it doesn't
                if(!dataManager.searchLine(editedName)){
                    selectedLine.setName(editedName);
                    int index = lineNameBox.getItems().indexOf(selectedLineName);
                    lineNameBox.getItems().set(index, editedName);
                    //lineNameBox.getSelectionModel().select(index);
                } //endIf
                else{
                    PropertiesManager props = PropertiesManager.getPropertiesManager();    
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show(props.getProperty(LINE_EXISTED_ERROR_TITLE), props.getProperty(LINE_EXISTED_ERROR_MESSAGE));                  
                } //endElse       
            } //endIf
            workspace.loadSelectedNodeSettings(selectedLine);
        });
     }     
    
    /**
     * This method processes a user request to start drawing a line.
     */
    public void processSelectLineToDraw() {
        // CHANGE THE CURSOR
        m3Data data = (m3Data)app.getDataComponent();
        Scene scene = app.getGUI().getPrimaryScene();
        InfoRequireDialogSingleton infoDialog = InfoRequireDialogSingleton.getSingleton();    
        infoDialog.show("Add New Line", "");
        scene.setCursor(Cursor.CROSSHAIR);
        // CHANGE THE STATE
        String name = infoDialog.getName();
        if(name != null){
            if(!data.searchLine(name))
                dataManager.setState(m3State.STARTING_LINE);  
            else{
                PropertiesManager props = PropertiesManager.getPropertiesManager();    
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LINE_EXISTED_ERROR_TITLE), props.getProperty(LINE_EXISTED_ERROR_MESSAGE));  
            } //endElse
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
        if(data.searchLine(lineName)){
            DraggableLine selectedLine = (DraggableLine)data.getSelectedNode();
            data.removeNode(selectedLine.getLineLabel1());
            data.removeNode(selectedLine.getLineLabel2());
            data.removeSelectedNode();  
            workspace.getLineNameBox().getItems().remove(lineName);
        } //endIf
    }
    
    /**
     * This method change the state to ADD_STATION_MODE and helps adding station 
     * on the selected line that is chosen by the user through lineComboBox.
     */
    public void processAddStationOnSelectedLine(){
        m3Data data = (m3Data)app.getDataComponent();
        data.setState(ADD_STATION_MODE);    
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
        if(data.searchLine(lineName)){
            data.setState(REMOVE_STATION_MODE);    
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.HAND);             
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
        Platform.runLater(() -> {
            m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
            m3Data data = (m3Data)app.getDataComponent();
            int lineThickness = (int)workspace.getLineThicknessSlider().getValue();  
            String lineName = (String)workspace.getLineNameBox().getValue(); 
            if(data.searchLine(lineName)){   
                DraggableLine selectLine = (DraggableLine)data.getSelectedNode();
                selectLine.setStrokeWidth(lineThickness);
            } 
            app.getGUI().updateToolbarControls(false);
            workspace.reloadWorkspace(dataManager);
        });
    }  
    
    /**
     * This method handles the station that is chosen by the user through 
     * stationComboBox.
     */
    public void processSelectingStation(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        dataManager.searchStation((String)workspace.getStationNameBox().getValue());
        workspace.loadSelectedNodeSettings(dataManager.getSelectedNode());
    }    
    
    /**
     * This method processes a user request to select a fill color for
     * the selected station through stationComboBox.
     */
     public void processFillingStationColor(){
         m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
         System.out.println((String)workspace.getStationNameBox().getValue());
         if(dataManager.searchStation((String)(workspace.getStationNameBox().getValue()))){
             DraggableStation station = (DraggableStation)dataManager.getSelectedNode();
             station.setColor(workspace.stationColorPicker.getValue());
             workspace.loadSelectedNodeSettings(station);         
         } else {
             workspace.updateStationColorPickerStyle(workspace.getStationColorPicker().getValue());
         }
         
     }    
    
    /**
     * This method adds a new station and update the stationComboBox list 
     * and selecting station.
     */
    public void processAddingNewStation(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene(); 
        InfoRequireDialogSingleton infoDialog = InfoRequireDialogSingleton.getSingleton();
        infoDialog.show("Add New Station", "");  
        scene.setCursor(Cursor.CROSSHAIR);
        // CHANGE THE STATE
        String name = infoDialog.getName();
        if(name != null) {
            if(!dataManager.searchStation(name))
                  dataManager.setState(m3State.ADDING_STATION);  
            else{
                PropertiesManager props = PropertiesManager.getPropertiesManager();    
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(STATION_EXISTED_ERROR_TITLE), props.getProperty(STATION_EXISTED_ERROR_MESSAGE));  
            } //endElse
        } //endIf
        
        // ENABLE/DISABLE THE PROPER BUTTONS
        workspace.reloadWorkspace(dataManager);
    }    
    
    /**
     * This method adds a new station and update the stationComboBox list 
     * and selecting station.
     */
    public void processRemovingSelectedStation(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        m3Data data = (m3Data)app.getDataComponent();
        String stationName = (String)workspace.getStationNameBox().getValue();
        if(data.searchStation(stationName)){
            DraggableStation stationToRemove = (DraggableStation)data.getSelectedNode();
            ArrayList<String> list = stationToRemove.getListOfLines();
            for(String line: list){
                data.searchLine(line);
                ((DraggableLine)data.getSelectedNode()).getListOfStations().remove(stationToRemove.getName());
            } //endFor
            data.removeNode(stationToRemove.getNameLabel());
            data.removeSelectedNode();  
            workspace.getStationNameBox().getItems().remove(stationName);
        } //endIf
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
        if(data.searchStation(stationName)){
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
        Platform.runLater(() -> {
            m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
            m3Data data = (m3Data)app.getDataComponent();
            int radius = (int)workspace.getStationRadiusSlider().getValue();  
            String stationName = (String)workspace.getStationNameBox().getValue(); 
            if(data.searchStation(stationName)){   
                DraggableStation selectStation = (DraggableStation)data.getSelectedNode();
                selectStation.setRadius(radius);
            } 
            app.getGUI().updateToolbarControls(false);
            workspace.reloadWorkspace(dataManager);     
        });    
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
        if(data.searchStation(searchStation1Name)){
            DraggableStation searchingStation1 = (DraggableStation)data.getSelectedNode();
        }    
         if(data.searchStation(searchStation2Name)){
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