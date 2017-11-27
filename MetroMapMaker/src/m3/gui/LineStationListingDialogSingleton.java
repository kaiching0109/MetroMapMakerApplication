/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import m3.data.DraggableLine;
import m3.data.DraggableStation;

/**
 * This class serves to present our Line Station Listing Dialog 
 * with the selected line that is chosen from the comboBox by
 * users. It shows all stop(s) that are contained in the line.
 * Note that it always provides the same controls, a label
 * with a message, and a single Ok button.
 * 
 * @author Kai 
 * @version 1.0
 */
public class LineStationListingDialogSingleton extends Stage{
    
    static LineStationListingDialogSingleton singleton;
    Button okButton;
    VBox infoPane;
    Label routeLabel;
    Label messageLabel;
    DraggableLine line;
    TextArea routeInfo;
    Scene infoScene;

    final String routeLabelTitleSuffix = " Line Stops";
    /**
     * Note that the constructor is private since it follows
     * the singleton design pattern.
     * 
     * @param primaryStage The owner of this modal dialog.
     */       
    private LineStationListingDialogSingleton(){}

   /**
     * The static accessor method for this singleton.
     * 
     * @return The singleton object for this type.
     */     
    public static LineStationListingDialogSingleton getSingleton(){
	if (singleton == null)
	    singleton = new LineStationListingDialogSingleton();
	return singleton;
    }
    
   /**
     * set the line for use and call setRouteLabel to
     * set the title of the dialog
     * 
     * @param initLine the selected line from comboBox
     */        
    public void setLine(DraggableLine initLine){
        line = initLine;
        if(line != null){
            setRouteLabel();
            setRouteInfo();
        }
    }
    
    private void setRouteLabel(){
        String lineName = line.getName();
        routeLabel.setText(lineName + routeLabelTitleSuffix);
    }
    
   /**
     * This method prints out all the station(s)
     * that are on the line.
     */      
    public void setRouteInfo(){
        ArrayList<String> listOfStations = line.getListOfStations();
        String stationList = "";
        for(String station: listOfStations){
            stationList += "\u2022" + station + "\n";
        }
        routeInfo.setText(stationList);
    }
    
   /**
     * This method initializes the singleton for use.
     * 
     * @param primaryStage The window above which this
     * dialog will be centered.
     */    
    public void init(Stage primaryStage){
        
        messageLabel = new Label();  
        
        routeLabel = new Label();
        routeLabel.setFont(Font.font(FontWeight.BOLD.toString(), 24));
        
        okButton = new Button("ok");
        okButton.setAlignment(Pos.CENTER);
        okButton.setOnAction(e->{
            close();
        });
        
        routeInfo = new TextArea();
        routeInfo.setEditable(false);
        
        infoPane = new VBox();
        infoPane.setPadding(new Insets(10, 100, 100, 50));
        infoPane.getChildren().addAll(routeLabel, routeInfo, okButton);
        
        infoScene = new Scene(infoPane);
        this.setScene(infoScene);          
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
        messageLabel.setText(message);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }  
}
