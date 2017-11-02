/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
  ** This is a draggable label for our m3 application.
 * @author aaronsuen
 */
public class DraggableLabel extends Text implements Draggable{
    double startX;
    double startY;
    double fontSize;
    FontWeight fontWeight;
    FontPosture fontPosture;
    String fontFamily;
    String text;

    public DraggableLabel(){
        setFunction();
        setOpacity(1.0);
        startX = 50;
        startY = 50;
        fontSize = getFont().getSize();
        fontWeight = FontWeight.NORMAL;
        fontPosture = FontPosture.REGULAR;
        fontFamily = getFont().getFamily();
    }
    
     @Override
    public void start(int x, int y) {
        startX = x;
	startY = y;
    }

    @Override
    public void drag(int x, int y) {
        //double diffX = x - getX() + 1;
	//double diffY = y - getY() + 1;
        double diffX = x - startX;
	double diffY = y - startY;
	double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
        
    }

    @Override
    public void size(int x, int y) {	
    }
    
    public void setFunction(){
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2)
                showDialog();
        });
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
       	xProperty().set(initX);
	yProperty().set(initY);
    }
    
    public void setContent(String initText){
        text = initText;
        this.setText(text);  
    }
    
    public void setFontFamily(String initFontFamily){
        fontFamily = initFontFamily;
        setFont(Font.font(fontFamily, fontWeight, fontPosture, fontSize));
    }
    
    public void setFontSize(double initSize){
        fontSize = initSize;
        setFont(Font.font(fontFamily, fontWeight, fontPosture, fontSize));
    }
    
    public void setFontPosture(FontPosture initFontPosture){
        fontPosture = initFontPosture;
        setFont(Font.font(fontFamily, fontWeight, fontPosture, fontSize));
    }
    
    public void setFontWeight(FontWeight initFontWeight){
        fontWeight = initFontWeight;
        setFont(Font.font(getFont().getFamily(), fontWeight, fontPosture, fontSize));
    }
    
    public String getContent(){
        return text;
    }

    @Override
    public m3State getStartingState() {
        return m3State.ADDING_LABEL;
    }

    @Override
    public double getWidth() {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public FontWeight getFontWeight(){
        return fontWeight;
    }
    
    public FontPosture getFontPosture(){
        return fontPosture;
    }
    
    public String getFontFamily(){
        return fontFamily;
    }
    
    public double getFontSize(){
        return fontSize;
    }
    
    public void showDialog(){
        
        TextInputDialog dialog = new TextInputDialog("Text");
        dialog.setTitle("Text Insert");
        dialog.setContentText("Please enter text:");
       
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            setContent(result.get());
        }
    }    
    
    @Override
    public String getShapeType() {
	return LABEL;
    }
    
    @Override
    public Shape clone() {
        return null;
    }
    
}
