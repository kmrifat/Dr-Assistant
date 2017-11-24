/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.patient;

import controller.prescription.PrescriptionsController;
import controller.prescription.ViewPrescriptionController;
import getway.PrescriptionGetway;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Patient;
import model.Prescription;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class PatientHistoryController implements Initializable {

    @FXML
    private ListView<Prescription> prescriptionList;
    @FXML
    private StackPane spPrescription;

    ObservableList<Prescription> prescriptions = FXCollections.observableArrayList();

    PrescriptionGetway prescriptionGetway = new PrescriptionGetway();

    Patient patient = new Patient();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            loadPatientHistory();
        });
    }

    @FXML
    private void handle(MouseEvent event) {
        showPrescription();
    }

    @FXML
    private void handleKeyboard(KeyEvent event) {
        showPrescription();
    }

    private void loadPatientHistory() {
        prescriptions = prescriptionGetway.patientPrescriptions(patient);
        prescriptionList.getItems().addAll(prescriptions);
        prescriptionList.getSelectionModel().select(0);

        prescriptionList.setCellFactory(param -> new ListCell<Prescription>() {
            @Override
            protected void updateItem(Prescription item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getDate() == null) {
                    setText(null);
                } else {
                    setText(item.getDate());
                }
            }
        });

        showPrescription();
    }

    private void showPrescription() {
        int prescriptionId = prescriptionList.getSelectionModel().getSelectedItem().getId();
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/prescription/ViewPrescription.fxml"));
        try {
            Parent root = fXMLLoader.load();
            ViewPrescriptionController controller = fXMLLoader.getController();
            spPrescription.getChildren().clear();
            spPrescription.getChildren().add(root);
            controller.loadPrescription(prescriptionId);
        } catch (IOException ex) {
            Logger.getLogger(PrescriptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
