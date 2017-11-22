/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import djf.AppTemplate;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import m3.data.m3Data;
import static m3.data.m3Data.BLACK_HEX;

/**
 * This class is used to show a dialog for user, in order to get the information
 * of the line. The information will include the selected line color, name and 
 * two buttons - ok and cancel.
 * 
 * @author Kai
 */

public class LineEditDialogSingleton extends Stage{
    static LineEditDialogSingleton singleton;
    
    Scene LineEditPaneScene;
    VBox LineEditPane;
    DraggableLine line;
    ColorPicker lineColorPicker;
    Label lineDetailsLabel;
    Label messageLabel;
    HBox ButtonBox;
    Button okButton;
    Button cancelButton;
    String lineName;
    TextField inputField;
    
    final String lineDetailsLabelName = "Metro Line Details";
    final String lineDetailsTitleName = "Metro Map Maker - Metro Line Edit";

    /**
     * Note that the constructor is private since it follows
     * the singleton design pattern.
     * 
     * @param primaryStage The owner of this modal dialog.
     */        
    private LineEditDialogSingleton(){};

    /**
     * The static accessor method for this singleton.
     * 
     * @return The singleton object for this type.
     */           
    public static LineEditDialogSingleton getSingleton(){
	if (singleton == null){
	    singleton = new LineEditDialogSingleton();
        }    
	return singleton;
    }
   /**
     * This method set the line that is selected
     * in the comboBox by user
     * 
     * @param initLine uses to set lineName
     */     
     public void setLine(DraggableLine initLine){
         line = initLine;
         setLineName();
         setLineColor(line.getColor());
     }
     
   /**
     * This method set the name of the line getting
     * from the selected line
     * 
     */      
     private void setLineName(){
         lineName = line.getName();
         inputField.setText(lineName);
     }
     
     //TO SHOW THE CURRENT COLOR OF THE SELECteD LINE
     private void setLineColor(Color initColor){
        if(lineColorPicker != null)
            lineColorPicker.setValue(initColor);     
        else{
            lineColorPicker = new ColorPicker();
            lineColorPicker.setValue(Color.valueOf(BLACK_HEX));
        }
     }
     
    /**
     * This method return the color of the selected line 
     * for use.
     * 
     * @return Color the selected line color
     */          
     public ColorPicker getLineColorPicker(){
         return lineColorPicker;
     }
     
    /**
     * This method return the name of the selected line 
     * for use.
     * 
     * @return String the selected line name
     */          
     public String getLineName(){
         return lineName;
     }     
     
    /**
     * This method initializes the singleton for use.
     * 
     * @param primaryStage The window above which this
     * dialog will be centered.
     * @param initApp
     */       
    public void init(Stage primaryStage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        messageLabel = new Label();
        
        LineEditPane = new VBox();
        LineEditPane.setAlignment(Pos.CENTER);
        lineDetailsLabel = new Label(lineDetailsLabelName);
        
        inputField = new TextField();
        lineColorPicker = new ColorPicker();   
        
        LineEditPaneScene = new Scene(LineEditPane);
        this.setScene(LineEditPaneScene); 
        
        ButtonBox = new HBox();
        okButton = new Button();
        cancelButton = new Button();
        okButton.setOnAction(e->{
            lineName = inputField.getText();
            close();
        });
        cancelButton.setOnAction(e->{
            close();
        });
        ButtonBox.getChildren().addAll(okButton, cancelButton);
        LineEditPane.getChildren().addAll(lineDetailsLabel, inputField, lineColorPicker, ButtonBox);        
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
