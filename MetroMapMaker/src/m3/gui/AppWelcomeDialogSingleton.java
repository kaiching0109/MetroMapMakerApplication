/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import static djf.settings.AppPropertyType.APP_ICON;
import static djf.settings.AppPropertyType.LOAD_WORK_TITLE;
import static djf.settings.AppPropertyType.PREF_HEIGHT;
import static djf.settings.AppPropertyType.PREF_WIDTH;
import static djf.settings.AppStartupConstants.CLOSE_BUTTON_LABEL;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import m3.data.m3Data;
import properties_manager.PropertiesManager;

/**
 * This class serves to present our welcome dialog with user's recent created or
 * modified works when the given Hyperlink is clicked. Note that it always
 * provides the same controls, a label with a "Recent Work" message, and a
 * Hyperlink to create a new metro map.
 *
 * @author Kai
 * @version 1.0
 */

public class AppWelcomeDialogSingleton extends Stage {

    static AppWelcomeDialogSingleton singleton = null;

    Stage primaryStage;
    AppTemplate app;
    BorderPane recentWorkPane;
    Label recentWorkLabel;
    VBox recentWorkBar;
    BorderPane createNewWorkPane;
    Image m3Icon;
    String filePath;
    String selection;
    Hyperlink createNewLink;
    Scene welcomeScene;
    File directory = new File(PATH_WORK);
    Label messageLabel;
    ArrayList<File> recentWork;

    public static final String RECENTWORK = "Recent Work";
    public static final String M3_ICON = "m3Icon.png";

    private AppWelcomeDialogSingleton() {
    }

    /**
     * Note that the constructor is private since it follows the singleton
     * design pattern.
     *
     * @return primaryStage The owner of this modal dialog.
     */
    public static AppWelcomeDialogSingleton getSingleton() {
        if (singleton == null) {
            singleton = new AppWelcomeDialogSingleton();
        }
        return singleton;
    }

    /**
     * This method initializes the singleton for use.
     *
     * @param primaryStage The window above which this dialog will be centered.
     * @param initApp The AppTemplate for getting
     */
    public void init(Stage primaryStage, AppTemplate initApp) {
        // MAKE IT MODAL
        recentWork = new ArrayList<File>();
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        app = initApp;
        messageLabel = new Label();
        this.primaryStage = primaryStage;

        // SET RECENTWORKBAR
        recentWorkBar = new VBox();
        recentWorkBar.setPadding(new Insets(50, 100, 50, 100));
        setRecentWorkBar();

        // SET CREATENEWLINK BUTTON
        createNewLink = new Hyperlink("Create New Metro Map");
        createNewLink.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create New Metro Map");
            dialog.setContentText("Please enter the name of the map:");
            boolean successful = false;
            int i = 0;
            dialog.showAndWait();
            String path = PATH_WORK + dialog.getResult();
            String title = "Metro Map Maker - " +dialog.getResult();
            ((m3Data)app.getDataComponent()).setMapName(dialog.getResult());
            File file = new File(path);
            while (!file.mkdir()) {
                i++;
                path = PATH_WORK  + dialog.getResult() + i;
                title = "Metro Map Maker - " + dialog.getResult() + i;
                file = new File(path);
            }
            app.getGUI().getFileController().handleNewRequest();
            PropertiesManager props = PropertiesManager.getPropertiesManager();	
            // AND NOW ASK THE USER FOR THE FILE TO OPEN
            primaryStage.setTitle(title);
            close();
        });

        // SET IMAGE
        m3Icon = new Image(FILE_PROTOCOL + PATH_IMAGES + M3_ICON);
        ImageView m3IconImageView = new ImageView(m3Icon);

        // SET CREATENEWWORKPANE
        VBox createNewWorkBar = new VBox();
        createNewWorkBar.setAlignment(Pos.CENTER);
        createNewWorkBar.getChildren().addAll(m3IconImageView, createNewLink);
        createNewWorkPane = new BorderPane();
        createNewWorkPane.setCenter(createNewWorkBar);
        createNewWorkPane.setPadding(new Insets(50, 75, 50, 75));

        // SET RECENTWORKPANE
        recentWorkPane = new BorderPane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        double prefWidth = Double.parseDouble(props.getProperty(PREF_WIDTH));
        double prefHeight = Double.parseDouble(props.getProperty(PREF_HEIGHT));
        recentWorkPane.setPrefWidth(prefWidth);
        recentWorkPane.setPrefHeight(prefHeight);
        recentWorkBar.setAlignment(Pos.CENTER);
        recentWorkPane.setLeft(recentWorkBar);
        recentWorkPane.setRight(createNewWorkPane);
        welcomeScene = new Scene(recentWorkPane);
        this.setScene(welcomeScene);
    }

    // SET RECENTWORKBAR 
    private void setRecentWorkBar() {
        recentWorkLabel = new Label(RECENTWORK);
        recentWorkBar.getChildren().add(recentWorkLabel);
        listFiles(directory);
        if (!recentWork.isEmpty()) {
            recentWork.sort((File o1, File o2) -> new Long(o2.lastModified()).compareTo(o1.lastModified()));
        }
        for (int i = 0; i < recentWork.size(); i++) {
            if (i == 8) {
                break;
            }
            File file = recentWork.get(i);
            filePath = file.getAbsolutePath();
            Hyperlink recentWorkLink = new Hyperlink(file.getName());
            recentWorkLink.setOnAction(e -> {
                selection = filePath;
                int size = file.getName().length();
                ((m3Data)app.getDataComponent()).setMapName(file.getName().substring(0, size-5));
                close();
            });
            recentWorkBar.getChildren().add(recentWorkLink);
        } //endFor
    }

    /**
     * Accessor method for getting the recentWorkPane
     *
     * @return recentWorkPane
     */
    public BorderPane getRecentWorkPane() {
        return recentWorkPane;
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
    
    /**
     * Accessor method for getting the recentWorkLabel for workspace to style.
     * 
     * @return recentWorkLabel the label of our recent work bar.
     */
    public Label getRecentWorkLabel() {
        return recentWorkLabel;
    }
    
    /**
     * Accessor method is used to get the recentWorkBar, mainly for workspace to add
     * style.
     * 
     * @return recentWorkBar the VBox that is used to store recentWork links
     */
    public VBox getRecentWorkBar() {
        return recentWorkBar;
    }

    private Boolean checkType(File dir, String name) {
        if (name.endsWith(".m3")) {
            return true;
        } else {
            return false;
        }
    }

    private void listFiles(File initFile) {
        File[] files = initFile.listFiles();
        File file;
        String fileName;
        if (files.length != 0) {
            for (int i = 0; i < files.length; i++) {
                file = files[i];
                fileName = file.getName();
                if (checkType(file, fileName)) {
                    recentWork.add(file);
                } else {
                    if (file.isDirectory()) {
                        listFiles(file);
                    }
                } //endElse  
            } //endFor
        }
    }

    /**
     * This method loads a custom message into the label and then pops open the
     * dialog.
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
