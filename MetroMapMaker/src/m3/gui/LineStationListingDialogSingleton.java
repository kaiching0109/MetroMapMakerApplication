/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import m3.data.DraggableLine;

/**
 *
 * @author aaronsuen
 */
public class LineStationListingDialogSingleton extends Stage{
    Button okButton;
    VBox infoPane;
    VBox infoArea;
    Label routeLabel;

    final String lineDetailsTitleName = "Metro Map Maker - Route";
    
    public LineStationListingDialogSingleton(DraggableLine initLine){
        
    }
    
    public void setLine(DraggableLine initLine){
        
        
    }
    
    public void printRouteInfo(){
        
    }
    
    public void init(Stage primaryStage){
        
        okButton.setOnAction(e->{
        });
        
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
        routeLabel.setText(message);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }  
}
