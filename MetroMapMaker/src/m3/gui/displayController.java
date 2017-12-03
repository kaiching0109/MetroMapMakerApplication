/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author aaronsuen
 */
public class displayController {
    private Timeline timeline;
    private Pane moveCanvas;
    private Pane zoomCanvas;

    public displayController(Pane initZoomCanvas, Pane initMoveCanvas) {         
         this.timeline = new Timeline(60);
         zoomCanvas = initZoomCanvas;
         moveCanvas = initMoveCanvas;
    }

    public void zoom(double factor) {    
        // determine scale
        double oldScale = zoomCanvas.getScaleX();
        double scale = oldScale * factor;
        double f = (scale / oldScale) - 1;

        // timeline that scales and moves the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.millis(200), new KeyValue(zoomCanvas.translateXProperty(), zoomCanvas.getTranslateX() - f )),
            new KeyFrame(Duration.millis(200), new KeyValue(zoomCanvas.translateYProperty(), zoomCanvas.getTranslateY() - f )),
            new KeyFrame(Duration.millis(200), new KeyValue(zoomCanvas.scaleXProperty(), scale)),
            new KeyFrame(Duration.millis(200), new KeyValue(zoomCanvas.scaleYProperty(), scale))    
        );
        timeline.play();
        
    }
    
        public void move(String key) {    
            
            // timeline that scales and moves the node
            timeline.getKeyFrames().clear();
            switch (key) {
                case "up":
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(200), new KeyValue(moveCanvas.translateXProperty(), moveCanvas.getTranslateX())),
                            new KeyFrame(Duration.millis(200), new KeyValue(moveCanvas.translateYProperty(), moveCanvas.getTranslateY() - 5))
                    );
                    break;
                case "down":
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(200), new KeyValue(moveCanvas.translateXProperty(), moveCanvas.getTranslateX())),
                            new KeyFrame(Duration.millis(200), new KeyValue(moveCanvas.translateYProperty(), moveCanvas.getTranslateY() + 5))
                    );
                    break;
                case "left":
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(200), new KeyValue(moveCanvas.translateXProperty(), moveCanvas.getTranslateX() - 5)),
                            new KeyFrame(Duration.millis(200), new KeyValue(moveCanvas.translateYProperty(), moveCanvas.getTranslateY()))
                    );
                    break;
                case "right":
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(200), new KeyValue(moveCanvas.translateXProperty(), moveCanvas.getTranslateX() + 5)),
                            new KeyFrame(Duration.millis(200), new KeyValue(moveCanvas.translateYProperty(), moveCanvas.getTranslateY()))
                    );
                    break;               
                default:
                    break;
            }
            timeline.play();
        }       
    
}
