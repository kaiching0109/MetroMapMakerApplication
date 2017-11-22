/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static m3.data.Draggable.LINE;
import static m3.data.m3Data.BLACK_HEX;
import static m3.data.m3Data.WHITE_HEX;

/**
 * This class serves to present custom pane to ask for user input
 * when the users want to create stations/ lines. 
 *
 * @author Kai
 * @version 1.0
 */

public class InfoRequireDialogSingleton extends Stage{
    
    static InfoRequireDialogSingleton singleton;
    
    VBox InfoReuirePane; 
    Button okButton;
    Button cancelButton;
    HBox inputBox;
    HBox buttonBox;
    Label nameLabel;
    Label messageLabel;
    TextField nameTextField;
    String name;
    ColorPicker colorPicker;
    Scene infoRequireScene;
    
    /**
     * Note that the constructor is private since it follows
     * the singleton design pattern.
     * 
     * @param primaryStage The owner of this modal dialog.
     */    
    private InfoRequireDialogSingleton(){}
    
    /**
     * The static accessor method for this singleton.
     * 
     * @return The singleton object for this type.
     */            
    public static InfoRequireDialogSingleton getSingleton(){
	if (singleton == null)
	    singleton = new InfoRequireDialogSingleton();
	return singleton;
    }
    
    /**
     * Accessor method for setting the type the user required.
     * 
     * @param initType Either LINE or STATION, depending on which
     * button the user selected when this dialog was presented.
         
    public void setType(String initType){
        type = initType;
    }
    */

    /**
     * This method initializes the singleton for use.
     * 
     * @param primaryStage The window above which this
     * dialog will be centered.
     */    
    public void init(Stage primaryStage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        inputBox = new HBox();
        InfoReuirePane = new VBox();
        nameLabel = new Label("Name: ");   
        messageLabel = new Label();
        nameTextField = new TextField();

        inputBox.getChildren().addAll(nameLabel, nameTextField);
        InfoReuirePane.getChildren().add(inputBox);
        
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.valueOf(BLACK_HEX));
        InfoReuirePane.getChildren().add(colorPicker);  
        
        //SET OKBUTTON
        okButton = new Button("OK");    
        okButton.setOnAction(e->{
            //CHECK TEXTFEILD IS NOT EMPTY
            //CHECK IF STATION NAME OR LINE EXIST
            //SET COLOR OR NAME
            //SAVE NAME AND MAYBE COLOR TO THE LINE
            name = nameTextField.getText();
            close();
        });        
        
        //SET CANCELBUTTON
        cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(e->{
            close();
        });
        
        buttonBox = new HBox();
        buttonBox.getChildren().addAll(okButton, cancelButton);         
        InfoReuirePane.getChildren().add(buttonBox); 
        infoRequireScene = new Scene(InfoReuirePane);
        this.setScene(infoRequireScene);        
    }

    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return Color, depending on which color the user selected 
     * in the ColorPicker when this dialog was presented.
     */    
    public ColorPicker getColorPicker(){
        return colorPicker;  
    }
    
    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return String, depending on input of the user 
     * in textField when this dialog was presented.
     */      
    public String getName(){
        return name;
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
	nameTextField.clear();
	// SET THE MESSAGE TO DISPLAY TO THE USER
        messageLabel.setText(message);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }     
}
