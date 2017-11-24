/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.drug;

import getway.DrugGetway;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Drug;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class EditDrugController implements Initializable {

    @FXML
    public TextField tfTradeName;
    @FXML
    public TextField tfGenericName;
    @FXML
    public TextArea taNote;

    private int drugId;

    DrugGetway drugGetway = new DrugGetway();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            System.out.println("Drug id is : " + getDrugId());
        });
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        resetForm();
    }

    @FXML
    private void saveDrugOnAction(ActionEvent event) {
        if (isVliadForm()) {
            String tradeName = tfTradeName.getText().substring(0, 1).toUpperCase() + tfTradeName.getText().substring(1).toLowerCase();
            if (drugGetway.update(new Drug(drugId, tradeName, tfGenericName.getText(), taNote.getText()))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Drug updated");
                alert.setHeaderText("Drug updated ");
                alert.showAndWait();
            }
        }

    }

    private void resetForm() {
        tfGenericName.setText(null);
        tfTradeName.setText(null);
        taNote.setText(null);
    }

    private void loadDrugDetails() {

    }

    private boolean isVliadForm() {
        if (tfTradeName.getText() == null || tfGenericName.getText() == null || tfTradeName.getText().length() == 0 || tfGenericName.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation error");
            alert.setHeaderText("Validation error");
            alert.setContentText("Trade Name is Required \nGenric Name is Required");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

}
