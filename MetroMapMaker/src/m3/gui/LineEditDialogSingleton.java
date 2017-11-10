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
import javafx.scene.control.TextField;
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
    TextField inputFeild;
    
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
     * @param initLine uses to set lineName,
     * Color
     */     
     public void setLine(DraggableLine initLine){
         line = initLine;
     }
     
   /**
     * This method set the name of the line getting
     * from the selected line
     * 
     * @param lineName will be shown in the TextField
     */      
     public void setLineName(String lineName){
         
     }
     
     //TO SHOW THE CURRENT COLOR OF THE SELECteD LINE
     private void setLineColorPicker(){
         
     }
     
     //FOR SETTING UP A TEXTFIELD TO INPUT 
     private void setInputField(){
        inputFeild = new TextField();
        inputFeild.setOnAction(e -> {
            setLineName(inputFeild.getText());
            //CHECK IF LINE EXISTS
            //SETLINE IF EXISTS
            //UPDATE LINE COLOR IF IT EXISTS
            //SET IT TO DEFAULT COLOR IF IT'S NOT
        });
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
     * This method initializes the singleton for use.
     * 
     * @param primaryStage The window above which this
     * dialog will be centered.
     */       
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
	setInputField();
	// SET THE MESSAGE TO DISPLAY TO THE USER
        lineDetailsLabel.setText(message);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    } 
}
