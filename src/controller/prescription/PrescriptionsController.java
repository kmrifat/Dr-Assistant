/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.prescription;

import getway.PatientGetway;
import getway.PrescriptionGetway;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Paginate;
import model.Patient;
import model.Prescription;
import view.prescription.PatientCardPrescription;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class PrescriptionsController implements Initializable {

    @FXML
    private TextField tfSearch;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblShowing;
    @FXML
    private TableView<Prescription> prescriptionTable;
    @FXML
    private TableColumn<?, ?> clmSl;
    @FXML
    private TableColumn clmPatientName;
    @FXML
    private TableColumn<?, ?> clmPrescriptionDate;
    @FXML
    private TableColumn<?, ?> clmCheifComplain;
    @FXML
    private TableColumn clmAction;

    Paginate paginate = new Paginate();
    Prescription prescription = new Prescription();

    PrescriptionGetway prescriptionGetway = new PrescriptionGetway();
    PatientGetway patientGetway = new PatientGetway();

    ObservableList<Prescription> prescriptions = FXCollections.observableArrayList();

    int total = 0;
    int searchTotal = 0;
    boolean prescriptionDefault = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblTotal.setText("Loading......");

        Platform.runLater(() -> {
            total = prescriptionGetway.totalPrescription();
            paginate.setStart(0);
            paginate.setPerPage(10);
            paginate.setEnd(10);
            paginate.setTotal(total);
            loadPrescriptions();
            lblTotal.setText("Total : " + total);
            lblShowing.setText("Showing " + paginate.getStart() + " to " + paginate.getEnd());
        });

    }

    @FXML
    private void newPrescriptionOnAction(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/patient/Patients.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.setTitle("Select patient to write an prescription");
        stage.show();
    }

    @FXML
    private void tfSearchOnAction(ActionEvent event) {
        if (tfSearch.getText() == null || tfSearch.getText().length() == 0) {
            lblTotal.setText("Total : " + total);
            paginate = new Paginate(total, 0, 10, 10);
            loadPrescriptions();
        } else {
            System.out.println("Hear");
            searchTotal = prescriptionGetway.totalSearchPrescription(tfSearch.getText().trim());
            paginate = new Paginate(searchTotal, 0, 10, 10);
            lblShowing.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            lblTotal.setText("Total : " + searchTotal);
            loadSearchPrescription(tfSearch.getText().trim());
        }
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        if (tfSearch.getText() == null || tfSearch.getText().length() == 0) {
            lblTotal.setText("Total : " + total);
            paginate = new Paginate(total, 0, 10, 10);
            loadPrescriptions();
        } else {
            System.out.println("Hear");
            searchTotal = prescriptionGetway.totalSearchPrescription(tfSearch.getText().trim());
            paginate = new Paginate(searchTotal, 0, 10, 10);
            lblShowing.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            lblTotal.setText("Total : " + searchTotal);
            loadSearchPrescription(tfSearch.getText().trim());
        }
    }

    @FXML
    private void handlePrevButton(ActionEvent event) {
        if (paginate.getStart() > 0) {
            paginate.setStart(paginate.getStart() - paginate.getPerPage());
            paginate.setEnd(paginate.getStart() + paginate.getPerPage());
            if (prescriptionDefault) {
                lblTotal.setText("Loading....");
                Platform.runLater(() -> {
                    loadPrescriptions();
                    lblShowing.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
                    lblTotal.setText("Total : " + total);
                });
            } else {
                loadSearchPrescription(tfSearch.getText().trim());
                lblShowing.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            }
        }
    }

    @FXML
    private void handleNextButton(ActionEvent event) {
        paginate.setStart(paginate.getStart() + paginate.getPerPage());
        paginate.setEnd(paginate.getStart() + paginate.getPerPage());
        System.out.println("Start : " + paginate.getStart());
        System.out.println("End : " + paginate.getEnd());
        if (prescriptionDefault) {
            lblTotal.setText("Loading....");
            Platform.runLater(() -> {
                loadPrescriptions();
                lblShowing.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
                lblTotal.setText("Total : " + total);
            });
        } else {
            loadSearchPrescription(tfSearch.getText().trim());
            lblShowing.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
        }
    }

    private void loadPrescriptions() {
        prescriptionDefault = true;

        prescriptionTable.getItems().clear();

        prescriptions = prescriptionGetway.prescriptions(paginate);
        clmSl.setCellValueFactory(new PropertyValueFactory<>("sl"));
        clmPrescriptionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmCheifComplain.setCellValueFactory(new PropertyValueFactory<>("cc"));
        clmPatientName.setCellFactory(patientCar);
        clmAction.setCellFactory(action);

        prescriptionTable.getItems().addAll(prescriptions);
    }

    private void loadSearchPrescription(String query) {
        prescriptionDefault = false;
        prescriptionTable.getItems().clear();

        prescriptions = prescriptionGetway.searchPrescriptions(paginate, query);
        clmSl.setCellValueFactory(new PropertyValueFactory<>("sl"));
        clmPrescriptionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmCheifComplain.setCellValueFactory(new PropertyValueFactory<>("cc"));
        clmPatientName.setCellFactory(patientCar);
        clmAction.setCellFactory(action);

        prescriptionTable.getItems().addAll(prescriptions);
    }

    private void openPrintablePrescription(int prescriptionId) {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/prescription/ViewPrescription.fxml"));
        try {
            Parent root = fXMLLoader.load();
            ViewPrescriptionController controller = fXMLLoader.getController();
            controller.loadPrescription(prescriptionId);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Print Prescription");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(PrescriptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deletePrescription(Prescription prescription) {
        if (prescriptionGetway.deletePrescription(prescription)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Prescription has been deleted");
            alert.setContentText("Prescription has been deleted successfully");
            alert.show();
            if (prescriptionDefault) {
                loadPrescriptions();
            } else {
                loadSearchPrescription(tfSearch.getText());
            }
        }
    }

    Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> patientCar = (TableColumn<Prescription, String> param) -> {
        final TableCell<Prescription, String> cell = new TableCell<Prescription, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Patient patient = patientGetway.selectedPatient(getTableView().getItems().get(getIndex()).getPatientId());
                    PatientCardPrescription base = new PatientCardPrescription(patient) {
                    };
                    setGraphic(base);
                    setText(null);
                }
            }
        };
        return cell;
    };

    Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> action = (TableColumn<Prescription, String> param) -> {
        final TableCell<Prescription, String> cell = new TableCell<Prescription, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {

                    Prescription prescription = getTableView().getItems().get(getIndex());

                    Button button = new Button("View Prescription");
                    Button delete = new Button("x");
                    delete.setCancelButton(true);

                    button.setDefaultButton(true);
                    button.setOnAction((ActionEvent event) -> {
                        openPrintablePrescription(getTableView().getItems().get(getIndex()).getId());
                        System.out.println("Click : Prescription ID : " + getTableView().getItems().get(getIndex()).getId());
                    });
                    delete.setOnAction((ActionEvent event) -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm");
                        alert.setHeaderText("You want to delete this prescription ?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            deletePrescription(prescription);
                            System.out.println("delete");
                        }

                    });

                    HBox hBox = new HBox(button, delete);
                    setGraphic(hBox);
                    setText(null);
                }
            }
        };
        return cell;
    };
}
