/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class Test_path extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Path");
        
        Group root = new Group();
        Scene scene = new Scene(root, 700, 250, Color.GRAY);


        Path path = new Path();
        path.setStrokeWidth(3);
        
        MoveTo moveTo = new MoveTo();
        moveTo.setX(250);
        moveTo.setY(200);
        
        LineTo lineTo = new LineTo();
        lineTo.setX(150);
        lineTo.setY(50);
        
        LineTo lineTo2 = new LineTo();
        lineTo2.setX(50);
        lineTo2.setY(200);

        LineTo lineTo3 = new LineTo();
        lineTo3.setX(250);
        lineTo3.setY(200);        
        
        path.getElements().addAll(moveTo, lineTo, lineTo2, lineTo3);
        path.setFill(Color.PURPLE);
        
        root.getChildren().add(path);
        stage.setScene(scene);
        stage.show();
    }
}
