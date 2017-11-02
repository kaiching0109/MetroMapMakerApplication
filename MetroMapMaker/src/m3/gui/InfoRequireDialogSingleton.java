/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static m3.data.Draggable.LINE;

/**
 * This class serves to present custom pane to ask for user input
 * when the users want to create stations/ lines. 
 *
 * @author Kai
 * @author ?
 * @version 1.0
 */

public class InfoRequireDialogSingleton extends Stage{
    
    static InfoRequireDialogSingleton singleton;
    
    VBox InfoReuirePane;
    String type;    
    Button okButton;
    Button cancelButton;
    HBox inputBox;
    HBox buttonBox;
    Label nameLabel;
    TextField nameTextField;
    String name;
    ColorPicker lineColorPicker;
    
    
    public InfoRequireDialogSingleton(String initType){
        type = initType;
        name = "";
    }
            
    public static InfoRequireDialogSingleton getSingleton(){
	if (singleton == null)
	    singleton = new InfoRequireDialogSingleton("");
	return singleton;
    }
    
    public void init(Stage primaryStage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        nameLabel = new Label();
        nameTextField = new TextField();
        nameTextField.setText("Name: "); 
        inputBox.getChildren().addAll(nameLabel, nameTextField);
        InfoReuirePane.getChildren().add(inputBox);
        
        if(type.equals(LINE)){          
            lineColorPicker = new ColorPicker();
            lineColorPicker.setOnAction(e->{
               //SET THE COLOR BACK TO DATA CLASS
            });
            InfoReuirePane.getChildren().add(lineColorPicker);       
        }  

        okButton = new Button();
        cancelButton = new Button();
        okButton.setOnAction(e->{
            //CHECK TEXTFEILD IS NOT EMPTY
            //CHECK IF STATION NAME OR LINE EXIST
            //SET COLOR OR NAME
            //SAVE NAME AND MAYBE COLOR TO THE LINE
            name = nameTextField.getText();
        });        
        cancelButton.setOnAction(e->{
            //CLOSE
        });
        
        buttonBox = new HBox();
        buttonBox.getChildren().addAll(okButton, cancelButton);
        
        InfoReuirePane.getChildren().add(buttonBox);          
    }
    
    public ColorPicker getLineColor(){
        if(lineColorPicker != null)
            return lineColorPicker;  
        else 
            return null;
    }
    
    public String getName(){
        return name;
    }
}
