package m3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import djf.AppTemplate;
import java.util.Locale;
import static javafx.application.Application.launch;
import m3.data.m3Data;
import m3.file.m3Files;
import m3.gui.m3Workspace;

/**
 *
 * @author aaronsuen
 */
public class MetroMapMakerApp extends AppTemplate{
     /**
     * This hook method must initialize all three components in the
     * proper order ensuring proper dependencies are respected, meaning
     * all proper objects are already constructed when they are needed
     * for use, since some may need others for initialization.
     */
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL THREE COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, AND THE DATA COMPONENT NEEDS THE
        // FILE COMPONENT SO WE MUST BE CAREFUL OF THE ORDER
        fileComponent = new m3Files();
        dataComponent = new m3Data(this);
        workspaceComponent = new m3Workspace(this);
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
