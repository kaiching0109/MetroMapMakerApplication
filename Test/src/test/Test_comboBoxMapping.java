/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class Test_comboBoxMapping extends Application {

    static class MyItem {
        String itemName;
        String itemDescription;

        public MyItem(String itemName, String itemDescription) {
            this.itemName = itemName;
            this.itemDescription = itemDescription;
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {    

        ComboBox comboBox = new ComboBox();
        ObservableList<MyItem> items = FXCollections.observableArrayList();
        comboBox.setItems(items);
        
        StringConverter<MyItem> converter = new StringConverter<MyItem>() {
            @Override
            public String toString(MyItem object) {
                return object.itemName;
            }

            @Override
            public MyItem fromString(String string) {
                return null;
            }
        };
        comboBox.setConverter(converter);

        VBox vbox = new VBox(comboBox);
        TextField input = new TextField();

        Button button = new Button("Add an item");
        button.setOnAction(e -> {
            MyItem item = new MyItem(input.getText(), "description");
            items.add(item);

        });
        vbox.getChildren().add(input);
        vbox.getChildren().add(button);

        Scene scene = new Scene(vbox, 200, 120);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}