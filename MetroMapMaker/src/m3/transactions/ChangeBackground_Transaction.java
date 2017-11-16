/**
 * This class implements jTPS_Transaction. It's used for changingBackground.
 * 
 * @author Kai 
 * @version 1.0
 */
package m3.transactions;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;

public class ChangeBackground_Transaction implements jTPS_Transaction{
    private Pane pane;
    private Background background;
    private Background oldBackground;
    
    public ChangeBackground_Transaction(Pane initPane, Color initColor) {
        pane = initPane;
        BackgroundFill fill = new BackgroundFill(initColor, null, null);
        background = new Background(fill);  
        oldBackground = pane.getBackground();
    }
    
    public ChangeBackground_Transaction(Pane initPane, Image initImage) {
        pane = initPane;
        BackgroundImage bgImage = new BackgroundImage(initImage,BackgroundRepeat.REPEAT, 
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
        background = new Background(bgImage);
        oldBackground = pane.getBackground();
    }

/**
 * redo background changing
 */      
    @Override
    public void doTransaction() { 
        pane.setBackground(background);
    }

/**
 * undo background changing
 */           
    @Override
    public void undoTransaction() {
        pane.setBackground(oldBackground);     
    }    
}
