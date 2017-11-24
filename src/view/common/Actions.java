package view.common;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public abstract class Actions extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final Button button;
    protected final Button button0;
    protected final Button button1;

    public Actions() {

        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        button = new Button();
        button0 = new Button();
        button1 = new Button();

        setPrefHeight(32.0);
        setPrefWidth(186.0);
        setStyle("-fx-background-color: none;");
        getStylesheets().add("/view/common/../../css/main.css");

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMaxWidth(120.0);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(95.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMaxWidth(150.0);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(100.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMaxWidth(192.0);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(110.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        button.setDefaultButton(true);
        button.setMnemonicParsing(false);
        button.setOnAction(this::handleEditButton);
        button.setText("Edit");

        GridPane.setColumnIndex(button0, 1);
        button0.setMnemonicParsing(false);
        button0.setOnAction(this::handleViewButton);
        button0.getStyleClass().add("btn-primary");
        button0.setText("View");

        GridPane.setColumnIndex(button1, 2);
        button1.setCancelButton(true);
        button1.setMnemonicParsing(false);
        button1.setOnAction(this::handleDeleteButton);
        button1.setText("Delete");

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getColumnConstraints().add(columnConstraints1);
        getRowConstraints().add(rowConstraints);
        getChildren().add(button);
        getChildren().add(button0);
        getChildren().add(button1);

    }

    protected abstract void handleEditButton(javafx.event.ActionEvent actionEvent);

    protected abstract void handleViewButton(javafx.event.ActionEvent actionEvent);

    protected abstract void handleDeleteButton(javafx.event.ActionEvent actionEvent);

}
