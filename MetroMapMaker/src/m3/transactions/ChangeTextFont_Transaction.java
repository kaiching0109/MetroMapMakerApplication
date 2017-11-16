/**
 * This class implements jTPS_Transaction. It's used for editing the text font.
 * 
 * @author Kai 
 * @version 1.0
 */
package m3.transactions;

import m3.data.m3Data;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

public class ChangeTextFont_Transaction implements jTPS_Transaction{
    private Text text;
    private Font font;
    private Font oldFont;
    
    public ChangeTextFont_Transaction(Text initText, Font initFont) {
        text = initText;
        font = initFont;
        oldFont = text.getFont();
    }

/**
 * redo font
 */    
    @Override
    public void doTransaction() {
        text.setFont(font);
    }

/**
 * undo font
 */        
    @Override
    public void undoTransaction() {
        text.setFont(oldFont);
    }    
}
