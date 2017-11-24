/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.drug;

import getway.DrugGetway;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
public class NewDrugController implements Initializable {

    @FXML
    private TextField tfTradeName;
    @FXML
    private TextField tfGenericName;
    @FXML
    private TextArea taNote;

    DrugGetway drugGetway = new DrugGetway();
    
    Drug drug = new Drug();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        resetForm();
    }

    @FXML
    private void saveDrugOnAction(ActionEvent event) {
        if (isVliadForm()) {
            String tradeName = tfTradeName.getText().substring(0, 1).toUpperCase() + tfTradeName.getText().substring(1).toLowerCase();
            String date = LocalDate.now().toString();
            drug = new Drug(tradeName, tfGenericName.getText(), taNote.getText(), date);
            if (drugGetway.save(drug)) {
                System.out.println("Drug id : " +drug.getId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Srug saved");
                alert.setHeaderText("Drug Saved ");
                alert.showAndWait();
                resetForm();
            }
        }

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

    private void resetForm() {
        tfGenericName.setText(null);
        tfTradeName.setText(null);
        taNote.setText(null);
    }

}
