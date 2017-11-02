/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppWelcomeDialogSingleton {
    static AppWelcomeDialogSingleton singleton;
    
    BorderPane recentWorkPane;
    Scene recentWorkScene;
    Label recentWorkLabel;
    VBox recentWorkBar;
    VBox createNewWorkPane;
    Image m3Icon;
    String[] recentWorkPaths = new String[8];
    Hyperlink[] recentWorkLinks = new Hyperlink[8];
    Hyperlink creatNewWorkLink = new Hyperlink();
    String selection;
    
    public static final String RECENTWORK = "Recent Work";
    public static final String M3_ICON = "m3_icon";
    
    // read the recent data
    public AppWelcomeDialogSingleton(){
        
    }
    
    public static AppWelcomeDialogSingleton getSingleton(){
	if (singleton == null)
	    singleton = new AppWelcomeDialogSingleton();
	return singleton;
    }
    
    public void init(Stage primaryStage){
        EventHandler<ActionEvent> HyberLinkHandler = (ActionEvent ae) -> {

        };
        
        EventHandler<ActionEvent> cancelHandler = (ActionEvent ae) -> {

        };
        
        EventHandler<ActionEvent> creatNewHandler = (ActionEvent ae) -> {

        };        
    }    
    
    public void setRecentWorks(){
        
    }
    
    public Hyperlink[] getRecentWorks(){
        return recentWorkLinks;
    }
    
    public void addRecentWorkPath(String filePath){
        
    }
    
    public void removeRecentWorkPath(String filePath){
        
    }
    
    public String getRecentWorkPath(){
        return recentWorkPaths[recentWorkPaths.length - 1];
    }
    
    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return Either any RECENT WORK, CREATE NEW, or EXIT, depending on which
     * button/ link the user selected when this dialog was presented.
     */    
    public String getSelection() {
        return selection;
    }    
    
    
}
