package view.common;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public abstract class PrescriptionAction extends HBox {

    protected final Button button;
    protected final Button button0;

    public PrescriptionAction() {

        button = new Button();
        button0 = new Button();

        setSpacing(3.0);
        setStyle("-fx-background-color: none;");
        getStylesheets().add("/view/common/../../css/main.css");

        button.setDefaultButton(true);
        button.setMnemonicParsing(false);
        button.setOnAction(this::handleEditButton);
        button.setText("Edit");

        button0.setCancelButton(true);
        button0.setMnemonicParsing(false);
        button0.setOnAction(this::handleDeleteButton);
        button0.setText("Delete");

        getChildren().add(button);
        getChildren().add(button0);

    }

    protected abstract void handleEditButton(javafx.event.ActionEvent actionEvent);

    protected abstract void handleDeleteButton(javafx.event.ActionEvent actionEvent);

}
