/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.patient;

import controller.prescription.NewPrescriptionController;
import getway.PatientGetway;
import getway.PrescriptionGetway;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Paginate;
import model.Patient;
import model.Prescription;
import view.patient.PatientCard;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class PatientsController implements Initializable {

    @FXML
    private ScrollPane scrollPan;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Label lblTotalPatient;
    @FXML
    private Label lblShowingPatient;
    @FXML
    private TextField tfSearch;

    Paginate paginate = new Paginate();
    PatientGetway patientGetway = new PatientGetway();
    PrescriptionGetway prescriptionGetway = new PrescriptionGetway();

    ObservableList<Patient> patients = FXCollections.observableArrayList();
    ObservableList<Prescription> patientPrescriptions = FXCollections.observableArrayList();

    private boolean patientDefault = true;
    int totalPatient;
    int totalSearchPatient;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scrollPan.viewportBoundsProperty().addListener((ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) -> {
            flowPane.setPrefWidth(newValue.getWidth());
        });

        lblTotalPatient.setText("Loading..........");

        Platform.runLater(() -> {
            totalPatient = patientGetway.totalPatient();
            loadPatients();
            lblTotalPatient.setText("Total :" + totalPatient);
            lblShowingPatient.setText("Showing " + paginate.getStart() + " To " + paginate.getEnd());
            if (totalPatient == 0) {
                lblTotalPatient.setText("No patient found");
                lblShowingPatient.setVisible(false);
            }
        });

    }

    @FXML
    private void newPatientOnAction(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/patient/NewPatient.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.setTitle("New Patient");
        stage.show();
    }

    @FXML
    private void handlePrevButton(ActionEvent event) {
        if (paginate.getStart() > 0) {
            paginate.setStart(paginate.getStart() - paginate.getPerPage());
            paginate.setEnd(paginate.getStart() + paginate.getPerPage());
            System.out.println("Patient default : " + patientDefault);
            if (patientDefault) {
                loadPatients();
                lblShowingPatient.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            } else {
                searchPatient(tfSearch.getText().trim());
                lblTotalPatient.setText("Total : " + String.valueOf(totalSearchPatient));
                lblShowingPatient.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            }
        } else {
            handleNextButton(event);
        }
    }

    @FXML
    private void handleNextButton(ActionEvent event) {
        paginate.setStart(paginate.getStart() + paginate.getPerPage());
        paginate.setEnd(paginate.getStart() + paginate.getPerPage());
        System.out.println("Patient default : " + patientDefault);
        if (patientDefault) {
            loadPatients();
            lblShowingPatient.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            if (patients.isEmpty()) {
                paginate.setStart(0);
                paginate.setEnd(10);
                loadPatients();
                lblShowingPatient.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            }
        } else {
            searchPatient(tfSearch.getText().trim());
            lblTotalPatient.setText("Total : " + String.valueOf(totalSearchPatient));
            lblShowingPatient.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            if (patients.isEmpty()) {
                paginate.setStart(0);
                paginate.setEnd(10);
                searchPatient(tfSearch.getText().trim());
                lblShowingPatient.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            }
        }

    }

    @FXML
    private void searchPatientOnAction(ActionEvent event) {
        if (tfSearch.getText() == null || tfSearch.getText().trim().length() == 0) {
            patientDefault = true;
            loadPatients();
        } else {
            System.out.println("Hear");
            searchPatient(tfSearch.getText().trim());
        }
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        if (tfSearch.getText() == null || tfSearch.getText().trim().length() == 0) {
            patientDefault = true;
            loadPatients();

        } else {
            System.out.println("Hear");
            searchPatient(tfSearch.getText().trim());
        }
    }

    private void loadPatients() {
        patientDefault = true;
        flowPane.getChildren().clear();
        paginate.setPerPage(12);
        paginate.setEnd(12);
        patients = patientGetway.patients(paginate);
        patients.forEach((patient) -> {
            PatientCard card = new PatientCard(patient) {
                @Override
                protected void prescriptionOnAction(ActionEvent actionEvent) {
                    openPrescriptionStage(this.patientId);
                }

                @Override
                protected void editOnAction(ActionEvent actionEvent) {
                    openPatientEditStage(this.patientId);
                }

                @Override
                protected void historyOnAction(ActionEvent actionEvent) {
                    openPatientHistoryStage(patient);
                }

                @Override
                protected void deleteOnAction(ActionEvent actionEvent) {
                    openDeleteDialog(patient);
                }
            };
            flowPane.getChildren().add(card);
        });
    }

    private void searchPatient(String query) {
        patientDefault = false;
        totalSearchPatient = patientGetway.totalSearchPatient(query);
        paginate.setTotal(totalSearchPatient);
        lblTotalPatient.setText("Total : " + totalSearchPatient);
        lblShowingPatient.setText("Showing " + paginate.getStart() + " To " + paginate.getEnd());
        paginate.setStart(0);
        paginate.setPerPage(12);
        paginate.setEnd(12);
        patients = patientGetway.searchPatient(paginate, query);
        flowPane.getChildren().clear();
        patients.forEach((patient) -> {
            PatientCard card = new PatientCard(patient) {
                @Override
                protected void prescriptionOnAction(ActionEvent actionEvent) {
                    openPrescriptionStage(this.patientId);
                }

                @Override
                protected void editOnAction(ActionEvent actionEvent) {
                    openPatientEditStage(this.patientId);
                }

                @Override
                protected void historyOnAction(ActionEvent actionEvent) {
                    openPatientHistoryStage(patient);
                }

                @Override
                protected void deleteOnAction(ActionEvent actionEvent) {
                    openDeleteDialog(patient);
                }
            };
            flowPane.getChildren().add(card);
        });

    }

    private void openPrescriptionStage(int patientId) {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/prescription/NewPrescription.fxml"));
        try {
            Parent root = fXMLLoader.load();
            NewPrescriptionController controller = fXMLLoader.getController();
            controller.loadPatient(patientId);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Edit Patient");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openPatientEditStage(int patientId) {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/patient/EditPatient.fxml"));
        try {
            Parent root = fXMLLoader.load();
            EditPatientController controller = fXMLLoader.getController();
            controller.getPatientDetails(patientId);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Edit Patient");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openPatientHistoryStage(Patient patient) {
        if (patient.getNumberOfPrescription() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("There is no prescription");
            alert.setHeaderText("History not found");
            alert.setContentText("There is no prescription to show");
            alert.showAndWait();
        } else {
            System.out.println("Go on");
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/patient/PatientHistory.fxml"));
            try {
                Parent root = fXMLLoader.load();
                PatientHistoryController controller = fXMLLoader.getController();
                controller.setPatient(patient);
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setTitle(patient.getName() + " History");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void openDeleteDialog(Patient patient) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete patient ?");
        alert.setHeaderText("Are your sure ?");
        alert.setContentText("It will delete all the releted document along to " + patient.getName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            patientPrescriptions = prescriptionGetway.patientPrescriptions(patient);
            patientPrescriptions.forEach((Prescription prescription) -> {
                prescriptionGetway.deletePrescription(prescription);
            });
            if (patientGetway.delete(patient.getId())) {
                Alert nAlert = new Alert(Alert.AlertType.INFORMATION);
                nAlert.setTitle("Success");
                nAlert.setHeaderText("Patient has been deleted");
                nAlert.setContentText("Patient Deleted");
                nAlert.show();
                if (patientDefault) {
                    loadPatients();
                } else {
                    searchPatient(tfSearch.getText());
                }
            }

        } else {
            System.out.println("no");
        }
    }

}
