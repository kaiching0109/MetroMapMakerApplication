/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import djf.AppTemplate;
import static djf.settings.AppPropertyType.DEFAULT_NODE_X;
import static djf.settings.AppPropertyType.DEFAULT_NODE_Y;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import jtps.jTPS;
import m3.data.DraggableImage;
import m3.data.DraggableLabel;
import m3.data.m3Data;
import m3.transactions.AddNode_Transaction;
import m3.transactions.ChangeTextFont_Transaction;
import properties_manager.PropertiesManager;

/**
 *
 * This class is used to do all the works that are related to our label and image.
 * Works include adding and modifying.
 * @author Kai
 */

public class ImageAndLabelController {
    private AppTemplate app;
    
    /**
     * Constructor of the controller.
     * 
     * @param initApp AppTemplate
     */
    public ImageAndLabelController(AppTemplate initApp) {
        app = initApp;
    }
    
    /**
     * This function is used to add a new Image
     */
    public void processAddImage() {
        // ASK THE USER TO SELECT AN IMAGE
        String imagePathToAdd = promptForImage();
        if (imagePathToAdd != null) {
            DraggableImage imageViewToAdd = new DraggableImage();
            imageViewToAdd.setImagePath(imagePathToAdd);
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            imageViewToAdd.xProperty().set(Double.parseDouble(props.getProperty(DEFAULT_NODE_X)));
            imageViewToAdd.yProperty().set(Double.parseDouble(props.getProperty(DEFAULT_NODE_Y))); 
            
            // MAKE AND ADD THE TRANSACTION
            addNodeTransaction(imageViewToAdd);
        }        
    }    

    /**
     * This function helps creating a new Label.
     */    
    public void processAddLabel() {
        // MAKE AND ADD THE TRANSACTION  
        DraggableLabel labelToAdd = new DraggableLabel();
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        labelToAdd.showDialog();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        labelToAdd.xProperty().set(Double.parseDouble(props.getProperty(DEFAULT_NODE_X)));
        labelToAdd.yProperty().set(Double.parseDouble(props.getProperty(DEFAULT_NODE_Y)));
        labelToAdd.setFont(Font.font((String)workspace.getFontFamilyBox().getValue(),
                (double)workspace.getFontSizeBox().getValue()));        
        addNodeTransaction(labelToAdd);
    }    
    
    /**
     * This function helps changing the font setting of a Label.
     */ 
    public void processChangeFont() {
        m3Data data = (m3Data)app.getDataComponent();
        if (data.isTextSelected()) {
            Text selectedText = (Text)data.getSelectedNode();
            m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
            Font currentFont = workspace.getCurrentFontSettings();
            ChangeTextFont_Transaction transaction = new ChangeTextFont_Transaction(selectedText, currentFont);
            jTPS tps = app.getTPS();
            tps.addTransaction(transaction);
        }
    }        
    
    private void addNodeTransaction(Node nodeToAdd) {
        m3Data data = (m3Data)app.getDataComponent();
        AddNode_Transaction transaction = new AddNode_Transaction(data, nodeToAdd);
        jTPS tps = app.getTPS();
        tps.addTransaction(transaction);        
    }
    
   private String promptForImage() {
        // SETUP THE FILE CHOOSER FOR PICKING IMAGES
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images/"));
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.BMP");
        FileChooser.ExtensionFilter extFilterGIF = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterBMP, extFilterGIF, extFilterJPG, extFilterPNG);
        fileChooser.setSelectedExtensionFilter(extFilterPNG);
        
        // OPEN THE DIALOG
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            
            return file.getAbsolutePath();
        } catch (IOException ex) {
            //AppDialogs.showMessageDialog(app.getGUI().getWindow(), "ERROR LOADING IMAGE TITLE", "ERROR LOADING IMAGE CONTENT");
            return null;
        }
    }        
}
