/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import djf.AppTemplate;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Modality;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.m3Data;

/**
 * This class is used to show the route information between two stations that are
 * selected in the comboBox. The dialog will show all total stations number that
 * will take to arrive the location by their line(s), and also the Estimated time 
 * to arrive.
 * 
 * @author Kai
 */
public class RouteDialogSingleton extends Stage{

    static RouteDialogSingleton singleton;
    
    VBox LineEditPane;
    DraggableStation fromStation;
    DraggableStation toStation;
    ColorPicker lineColorPicker;
    Label lineDetailsLabel;
    Label messageLabe;
    HBox ButtonBox;
    Button okButton;
    Button cancelButton;
    String lineName;
    TextField inputFeild;
    AppTemplate app;
    
    final String lineDetailsLabelPrefix = "Route from ";

    /**
     * Note that the constructor is private since it follows
     * the singleton design pattern.
     * 
     * @param primaryStage The owner of this modal dialog.
     */        
    private RouteDialogSingleton(){};

    /**
     * The static accessor method for this singleton.
     * 
     * @return The singleton object for this type.
     */           
    public static RouteDialogSingleton getSingleton(){
	if (singleton == null){
	    singleton = new RouteDialogSingleton();
        }    
	return singleton;
    }
    
   /**
     * This method set the station that is selected
     * in the searching comboBox by user
     * 
     * @param initFromStation the searching station (FROM)
     * @param initToStation the searching station (TO)
     */     
     public void setStations(DraggableStation initFromStation, DraggableStation initToStation){
         fromStation = initFromStation;
         toStation = initToStation;
     }
     
    /**
     * This method is used to set the TextArea inside the dialog to show all the
     * information. Information includes the estimated time to take for arrival
     * and the total stops that would take.
     */
    public void setInfo(){
         
     }
     
    /**
     * Accessor method to get the total number of stations that would take to
     * arrive.
     * 
     * @return int number of the totalStops
     */
     public int getTotalStops(){
         return 0;
     }
     
    /**
     * This method initializes the singleton for use.
     * 
     * @param primaryStage The window above which this
     * dialog will be centered.
     */       
    public void init(Stage primaryStage, AppTemplate initApp){
        app = initApp;
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        messageLabe = new Label();
        lineDetailsLabel = new Label();
        
        LineEditPane = new VBox();
        LineEditPane.setAlignment(Pos.CENTER);  
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
