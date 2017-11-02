/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import m3.data.DraggableLine;

public class LineEditDialogSingleton extends Stage{
    static LineEditDialogSingleton singleton;
    
    VBox LineEditPane;
    DraggableLine line;
    ColorPicker lineColorPicker;
    Label lineDetailsLabel;
    HBox ButtonBox;
    Button okButton;
    Button cancelButton;
    String lineName;
    
    
    final String lineDetailsLabelName = "Metro Line Details";
    final String lineDetailsTitleName = "Metro Map Maker - Metro Line Edit";
    
    public LineEditDialogSingleton(DraggableLine initLine){
        setLine(initLine);
        
    }

     public static LineEditDialogSingleton getSingleton(){
	if (singleton == null)
	    singleton = new LineEditDialogSingleton(new DraggableLine());
	return singleton;
    }
     
     //TO SET THE SELECTED LINE
     public void setLine(DraggableLine initLine){
         line = initLine;
     }
     
     //TO SHOW THE CURRENT COLOR OF THE SELECteD LINE
     public void setLineColorPicker(){
         
     }
     
     //FOR SETTING UP A TEXTFIELD TO INPUT 
     public void setLineName(String lineName){
         
     }
    
    public void init(Stage primaryStage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        LineEditPane = new VBox();
        LineEditPane.setAlignment(Pos.CENTER);
        LineEditPane.getChildren().add(lineDetailsLabel);
        
    }
    
       /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param title The title to appear in the dialog window.
     * 
     * @param message Message to appear inside the dialog.
     */
    public void show(String title, String message) {
	// SET THE DIALOG TITLE BAR TITLE
	setTitle(title);
	
	// SET THE MESSAGE TO DISPLAY TO THE USER
        lineDetailsLabel.setText(message);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    } 
}
