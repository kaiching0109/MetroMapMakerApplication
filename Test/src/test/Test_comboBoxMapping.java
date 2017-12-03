/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.event.KeyEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Test_comboBoxMapping extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {    

        GridPane zoomPane = new GridPane();
        zoomPane.add(new Circle(100, 100, 10), 0, 0, 10, 19);
        zoomPane.getChildren().add(new Circle(200, 200, 20));
        Button zoomIn = new Button("zoomIn");
        Button zoomOut = new Button("zoomOut");
        HBox buttonsBox = new HBox();
        buttonsBox.getChildren().addAll(zoomIn, zoomOut);
        zoomPane.add(buttonsBox, 10, 10);
        
        
        // Create operator
        AnimatedZoomOperator zoomOperator = new AnimatedZoomOperator(zoomPane);
        
        zoomIn.setOnAction(e->{
            double zoomFactor = 1.5;
            zoomOperator.zoom(zoomFactor); 
        });
        
        zoomOut.setOnAction(e->{
            double zoomFactor = 1.5;
            zoomFactor = 1 / zoomFactor;
            zoomOperator.zoom(zoomFactor);
        });

        
        Scene scene = new Scene(zoomPane, 600, 600);
        scene.setOnKeyPressed(
         keyEvent -> {
                KeyCode key = keyEvent.getCode();
                if (key == KeyCode.W)
                    zoomOperator.move("up");
                else if (key == KeyCode.S)
                    zoomOperator.move("down");
                else if (key == KeyCode.A)
                    zoomOperator.move("left");
                else if (key == KeyCode.D)
                    zoomOperator.move("right");
         }); 
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    

    public static void main(String[] args) {
        Application.launch(args);
    }
    
    public class AnimatedZoomOperator {

    private Timeline timeline;
    private GridPane pane;

        public AnimatedZoomOperator(GridPane initPane) {         
            this.timeline = new Timeline(60);
            pane = initPane;
        }

        public void zoom(double zoomFactor) {    
            // determine scale
            double oldScale = pane.getScaleX();
            double scale = oldScale * zoomFactor;
            double f = (scale / oldScale) - 1;

            // timeline that scales and moves the node
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(pane.translateXProperty(), pane.getTranslateX() - f )),
                new KeyFrame(Duration.millis(200), new KeyValue(pane.translateYProperty(), pane.getTranslateY() - f )),
                new KeyFrame(Duration.millis(200), new KeyValue(pane.scaleXProperty(), scale)),
                new KeyFrame(Duration.millis(200), new KeyValue(pane.scaleYProperty(), scale))
            );
            timeline.play();
        }

        public void move(String key) {    
            
            // timeline that scales and moves the node
            timeline.getKeyFrames().clear();
            switch (key) {
                case "up":
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(200), new KeyValue(pane.translateXProperty(), pane.getTranslateX())),
                            new KeyFrame(Duration.millis(200), new KeyValue(pane.translateYProperty(), pane.getTranslateY() - 5))
                    );
                    break;
                case "down":
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(200), new KeyValue(pane.translateXProperty(), pane.getTranslateX())),
                            new KeyFrame(Duration.millis(200), new KeyValue(pane.translateYProperty(), pane.getTranslateY() + 5))
                    );
                    break;
                case "left":
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(200), new KeyValue(pane.translateXProperty(), pane.getTranslateX() - 5)),
                            new KeyFrame(Duration.millis(200), new KeyValue(pane.translateYProperty(), pane.getTranslateY()))
                    );
                    break;
                case "right":
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(200), new KeyValue(pane.translateXProperty(), pane.getTranslateX() + 5)),
                            new KeyFrame(Duration.millis(200), new KeyValue(pane.translateYProperty(), pane.getTranslateY()))
                    );
                    break;               
                default:
                    break;
            }
            timeline.play();
        }        
        
    }
}

