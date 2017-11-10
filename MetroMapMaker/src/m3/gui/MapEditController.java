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
import javafx.stage.Stage;
import static m3.data.Draggable.LINE;
import static m3.data.Draggable.STATION;

/**
 * This class responds to interactions with other UI logo editing controls.
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
     * This method handles the request for saving as
     * 
     */
    public void processSaveAsRequest() {
        
    }
    
    /**
    * This method handles the request for exporting
     */
    public void processExportRequest() {
        
    }
    
    /**
     * This method handles request of showing our program information.
     */
    public void processAboutButtonRequest() {
        
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
        
    }
 
    /**
     * This method undo user behavior on the canvas.
     */    
    public void processUndo(){
        
    }

    /**
     * This method handles the line that is chosen by the user through lineComboBox.
     */
    public void processSelectingLine(String lineName){
        dataManager.ListSelectedLineStation(lineName);
    }
    
    /**
     * This method processes a user request to select a fill color for
     * the selected line through lineComboBox.
     */
     public void processLineEditting(){
         
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
        
    }
    
    /**
     * This method adds station on the selected line that 
     * is chosen by the user through lineComboBox.
     */
    public void processAddStationOnSelectedLine(){
        
    }    
    
    /**
     * This method remove station on the selected line that 
     * is chosen by the user through lineComboBox.
     */
    public void processRemoveStationOnSelectedLine(){
        
    }    
    
    /**
     * This method shows station(s) that is on the selected line 
     * that is chosen by the user through lineComboBox.
     */
    public void processStationsList(){
       
       dataManager.ListSelectedLineStation(LINE);
    }      
    
    /**
     * This method processes a user request to select the thickness
     * for line drawing / the selected line through thicknessSlider.
     */
    public void processSelectLineThickness() {
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        int lineThickness = (int)workspace.getLineThicknessSlider().getValue();
        //dataManager.setCurrentOutlineThickness(lineThickness);
        app.getGUI().updateToolbarControls(false);
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
     * This method fills the color of the selected label.
     */
    public void processFillingFontColor(){
        
    }
    
    /**
     * This method bolds the selected label.
     */
    public void processBoldLabel(){
        
    }
   
    /**
     * This method italics the selected label.
     */
    public void processItalicLabel(){
        
    }
    
    /**
     * This method changes the size of the selected label.
     */
    public void processLabelFontSize(){
        
    }
    
    /**
     * This method changes the font family of the selected label.
     */
    public void processLabelFontFamily(){
        
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