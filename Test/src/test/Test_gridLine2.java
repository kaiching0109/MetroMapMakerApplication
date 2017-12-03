/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author aaronsuen
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

 
public class Test_gridLine2 extends Application{ 
    static GridPane grid;
    public void start(final Stage stage) throws Exception {
        int rows = 100;
        int columns = 100;

        stage.setTitle("Enjoy your game");
        grid = new GridPane();
        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(10);
            grid.getColumnConstraints().add(column);
        }

        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(10);
            grid.getRowConstraints().add(row);
        }

        grid.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
        Scene scene = new Scene(grid, (columns * 40) + 100, (rows * 40) + 100, Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] arguments) {
        Application.launch(arguments);
    }
    
public class Anims {

    public SubScene getAnim(final int number) throws Exception {
        Circle circle = new Circle(20, 20f, 7);
        circle.setFill(Color.RED);
        Group group = new Group();
        group.getChildren().add(circle);
        SubScene scene = new SubScene(group, 40, 40);
        scene.setFill(Color.WHITE);
        return scene;
    }
}    

} 

