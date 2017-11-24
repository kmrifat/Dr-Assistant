/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.drug;

import controller.patient.PatientsController;
import getway.DrugGetway;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Drug;
import model.Paginate;
import view.common.Actions;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class DrugsController implements Initializable {

    @FXML
    private TableView<Drug> drugTable;
    @FXML
    private TableColumn<Integer, Integer> clmSl;
    @FXML
    private TableColumn<Drug, String> clmDrugName;
    @FXML
    private TableColumn<Drug, String> clmGenericName;
    @FXML
    private TableColumn<Drug, String> clmNote;
    @FXML
    private TableColumn<Drug, String> clmUseInPrescription;
    @FXML
    private TableColumn<Drug, String> clmUseInTemplate;
    @FXML
    private TableColumn clmAction;
    @FXML
    private Button btnPrev;
    @FXML
    private Button btnNext;
    @FXML
    private TextField tfDrugSearch;
    @FXML
    private Label lblTotalDrug;
    @FXML
    private Label lblShowingDrug;

    private ObservableList<Drug> drugs = FXCollections.observableArrayList();
    DrugGetway drugGetway = new DrugGetway();
    Paginate paginate = new Paginate();
    private boolean drugDefault = true;
    int totalDurg;
    int totalSearchDrug;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblTotalDrug.setText("Processing........");
        Platform.runLater(() -> {
            totalDurg = drugGetway.totalDrug();
            loadDrugs();
            lblTotalDrug.setText("Total : " + String.valueOf(totalDurg));
            lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + paginate.getEnd());
        });

    }

    @FXML
    private void handleNewDrugOnAction(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/drug/NewDrug.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.setTitle("New Drug");
        stage.show();
    }

    @FXML
    private void handlePrevButton(ActionEvent event) {
        if (paginate.getStart() > 0) {
            paginate.setStart(paginate.getStart() - paginate.getPerPage());
            paginate.setEnd(paginate.getStart() + paginate.getPerPage());
            if (drugDefault) {
                loadDrugs();
                lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            } else {
                searchDrugs(tfDrugSearch.getText().trim());
                lblTotalDrug.setText("Total : " + String.valueOf(totalSearchDrug));
                lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
            }

        }
    }

    @FXML
    private void handleNextButton(ActionEvent event) {
        System.out.println(paginate.getTotal());
        System.out.println(paginate.getEnd());
        paginate.setStart(paginate.getStart() + paginate.getPerPage());
        paginate.setEnd(paginate.getStart() + paginate.getPerPage());
        if (drugDefault) {
            loadDrugs();
            lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
        } else {
            searchDrugs(tfDrugSearch.getText().trim());
            lblTotalDrug.setText("Total : " + String.valueOf(totalSearchDrug));
            lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + (paginate.getStart() + paginate.getPerPage()));
        }

    }

    @FXML
    private void tfSearchOnAction(ActionEvent event) {
        if (tfDrugSearch.getText() == null || tfDrugSearch.getText().trim().length() == 0) {
            paginate.setPerPage(10);
            paginate.setEnd(10);
            paginate.setStart(0);
            Platform.runLater(() -> {
                loadDrugs();
                lblTotalDrug.setText("Total : " + String.valueOf(totalDurg));
                lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + paginate.getEnd());
            });
        } else {
            Platform.runLater(() -> {
                searchDrugs(tfDrugSearch.getText().trim());
                lblTotalDrug.setText("Total : " + String.valueOf(totalSearchDrug));
                lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + paginate.getEnd());
            });
        }

    }

    @FXML
    private void btnSearchOnAction(ActionEvent event) {
        if (tfDrugSearch.getText() == null || tfDrugSearch.getText().trim().length() == 0) {
            paginate.setPerPage(10);
            paginate.setEnd(10);
            paginate.setStart(0);
            Platform.runLater(() -> {
                loadDrugs();
                lblTotalDrug.setText("Total : " + String.valueOf(totalDurg));
                lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + paginate.getEnd());
            });
        } else {
            Platform.runLater(() -> {
                searchDrugs(tfDrugSearch.getText().trim());
                lblTotalDrug.setText("Total : " + String.valueOf(totalSearchDrug));
                lblShowingDrug.setText("Showing " + paginate.getStart() + " To " + paginate.getEnd());
            });
        }
    }

    private void loadDrugs() {
        drugDefault = true;
        paginate.setTotal(totalDurg);
        paginate.setPerPage(10);
        paginate.setEnd(10);
        drugTable.getItems().clear();
        drugs = drugGetway.drugs(paginate);
        
        clmSl.setCellValueFactory(new PropertyValueFactory<>("sl"));
        clmDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmGenericName.setCellValueFactory(new PropertyValueFactory<>("genricName"));
        clmNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        clmUseInPrescription.setCellValueFactory(new PropertyValueFactory<>("useInPrescription"));
        clmUseInTemplate.setCellValueFactory(new PropertyValueFactory<>("useInTemplate"));
        clmAction.setCellFactory(action);
        drugTable.setItems(drugs);
    }

    private void searchDrugs(String query) {
        drugDefault = false;
        totalSearchDrug = drugGetway.totalSearchDrug(query);
        paginate.setTotal(totalSearchDrug);
        paginate.setStart(0);
        paginate.setPerPage(10);
        paginate.setEnd(10);
        drugTable.getItems().clear();
        drugs = drugGetway.searchDrug(paginate, query);
        clmSl.setCellValueFactory(new PropertyValueFactory<>("sl"));
        clmDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmGenericName.setCellValueFactory(new PropertyValueFactory<>("genricName"));
        clmNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        clmUseInPrescription.setCellValueFactory(new PropertyValueFactory<>("useInPrescription"));
        clmUseInTemplate.setCellValueFactory(new PropertyValueFactory<>("useInTemplate"));
        clmAction.setCellFactory(action);
        drugTable.setItems(drugs);
    }

    private void editDrug(Drug drug) {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/drug/EditDrug.fxml"));
        try {
            Parent root = fXMLLoader.load();
            EditDrugController controller = fXMLLoader.getController();
            controller.setDrugId(drug.getId());
            controller.taNote.setText(drug.getNote());
            controller.tfTradeName.setText(drug.getName());
            controller.tfGenericName.setText(drug.getGenricName());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Drug");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteDrug(int drugId, String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete patient ?");
        alert.setHeaderText("Delete " + name + " ?");
        alert.setContentText("Are you sure to delete this drug ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            drugGetway.delete(drugId);
            loadDrugs();
        }
    }

    Callback<TableColumn<Drug, String>, TableCell<Drug, String>> action = (TableColumn<Drug, String> param) -> {
        final TableCell<Drug, String> cell = new TableCell<Drug, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Actions actions = new Actions() {
                        Drug drug = getTableView().getItems().get(getIndex());

                        @Override
                        protected void handleEditButton(ActionEvent actionEvent) {
                            System.out.println("Edit button " + drug.getName());
                            editDrug(drug);
                        }

                        @Override
                        protected void handleViewButton(ActionEvent actionEvent) {
                            System.out.println("View button " + drug.getName());
                        }

                        @Override
                        protected void handleDeleteButton(ActionEvent actionEvent) {
                            if (drug.getUseInPrescription() > 0) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Drug is prescription");
                                alert.setHeaderText("Drug use in prescription");
                                alert.setContentText("This drug has been using by " + drug.getUseInPrescription() + " Prescription(s) \nYou cannot delete this drug");
                                alert.showAndWait();
                            } else if (drug.getUseInTemplate() > 0) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Drug is template");
                                alert.setHeaderText("Drug use in prescription template");
                                alert.setContentText("This drug has been using by " + drug.getUseInTemplate()+ " Template(s) \nYou cannot delete this drug");
                                alert.showAndWait();
                            } else {
                                System.out.println("Delete button " + drug.getName());
                                deleteDrug(drug.getId(), drug.getName());
                            }

                        }
                    };
                    setGraphic(actions);
                    setText(null);
                }
            }
        };
        return cell;
    };

}
