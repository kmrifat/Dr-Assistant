/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import getway.DrugGetway;
import getway.PatientGetway;
import getway.PrescriptionGetway;
import getway.TemplateGetway;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class HomeContentController implements Initializable {

    @FXML
    private Text lblTotalPatient;
    @FXML
    private Text lblTotalDrug;
    @FXML
    private Text lblTotalPrescription;
    @FXML
    private Text lblTotalTemplate;

    PrescriptionGetway prescriptionGetway = new PrescriptionGetway();
    PatientGetway patientGetway = new PatientGetway();
    DrugGetway drugGetway = new DrugGetway();
    TemplateGetway templateGetway = new TemplateGetway();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            lblTotalPatient.setText(String.valueOf(patientGetway.totalPatient()));
            lblTotalDrug.setText(String.valueOf(drugGetway.totalDrug()));
            lblTotalTemplate.setText(String.valueOf(templateGetway.total()));
            lblTotalPrescription.setText(String.valueOf(prescriptionGetway.totalPrescription()));
        });
    }

    @FXML
    private void handleNewPatient(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/patient/NewPatient.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.setTitle("New Patient");
        stage.show();
    }

    @FXML
    private void handleNewDrug(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/drug/NewDrug.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.setTitle("New Drug");
        stage.show();
    }

    @FXML
    private void handleNewPrescription(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/patient/Patients.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.setTitle("Select patient to write an prescription");
        stage.show();
    }

    @FXML
    private void handleNewPrescriptionTemplate(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/template/NewTemplate.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.setTitle("New Template");
        stage.show();
    }

}
