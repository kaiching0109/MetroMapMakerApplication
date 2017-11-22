    package m3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import djf.AppTemplate;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import java.io.IOException;
import java.util.Locale;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import static m3.data.Draggable.LINE;
import m3.data.m3Data;
import m3.file.m3Files;
import m3.gui.AppWelcomeDialogSingleton;
import m3.gui.InfoRequireDialogSingleton;
import m3.gui.LineEditDialogSingleton;
import m3.gui.m3Workspace;
import properties_manager.PropertiesManager;

/**
 *
 * @author Kai
 */
public class MetroMapMakerApp extends AppTemplate{
     /**
     * This hook method must initialize all three components in the
     * proper order ensuring proper dependencies are respected, meaning
     * all proper objects are already constructed when they are needed
     * for use, since some may need others for initialization. Also,
     * welcome dialog will be initialized here in order to ask for
     * retrieving user recent work / creating new work.
     */
    @Override
        public void buildAppComponentsHook() {
        // CONSTRUCT ALL THREE COMPONENTS AND WELCOME DIALOG.
        // NOTE THAT FOR THIS APP, THE WORKSPACE NEEDS THE DATA COMPONENT 
        // TO EXIST ALREADY WHEN IT IS CONSTRUCTED, AND THE DATA COMPONENT NEEDS 
        // THE FILE COMPONENT SO WE MUST BE CAREFUL OF THE ORDER
        fileComponent = new m3Files();
        dataComponent = new m3Data(this);
        AppWelcomeDialogSingleton appWelcomeDialog = AppWelcomeDialogSingleton.getSingleton();
        appWelcomeDialog.init(gui.getWindow(), this);       
        workspaceComponent = new m3Workspace(this);
        LineEditDialogSingleton lineEditDialog = LineEditDialogSingleton.getSingleton();
        lineEditDialog.init(gui.getWindow());  
        InfoRequireDialogSingleton infoDialog = InfoRequireDialogSingleton.getSingleton();    
        infoDialog.init(gui.getWindow());
        appWelcomeDialog.show("Welcome to the Metro Map Maker", "");
        try {
            String selection = appWelcomeDialog.getSelection();
            if(selection != null){
                fileComponent.loadData(dataComponent, selection);
		workspaceComponent.activateWorkspace(getGUI().getAppPane());              
            }    
        } catch (IOException ex) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            appWelcomeDialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
        
    }
    
    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method inherited
     * from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }
    
}
