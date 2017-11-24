/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.template;

import getway.DrugGetway;
import getway.TemplateGetway;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Drug;
import model.Template;
import model.TemplateDrug;
import view.common.PrescriptionAction;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class NewTemplateController implements Initializable {

    @FXML
    private TextField tfTemplateName;
    @FXML
    private TextField tfTemplateNote;
    @FXML
    private TextArea taCC;
    @FXML
    private TextArea taOE;
    @FXML
    private TextArea taPD;
    @FXML
    private TextArea taDD;
    @FXML
    private TextArea taLabWorkup;
    @FXML
    private TextArea taAdvice;
    @FXML
    private TableView<TemplateDrug> drugTable;
    @FXML
    private TableColumn<TemplateDrug, Integer> clmSl;
    @FXML
    private TableColumn<TemplateDrug, String> clmDrugType;
    @FXML
    private TableColumn<TemplateDrug, String> clmDrugName;
    @FXML
    private TableColumn<TemplateDrug, String> clmStrength;
    @FXML
    private TableColumn<TemplateDrug, String> clmDose;
    @FXML
    private TableColumn<TemplateDrug, String> clmDuration;
    @FXML
    private TableColumn<TemplateDrug, String> clmAdvice;
    @FXML
    private TableColumn clmAction;
    @FXML
    private TextField tfDrugType;
    @FXML
    private ComboBox<Drug> comboBoxDrug;
    @FXML
    private TextField tfDrugStrength;
    @FXML
    private TextField tfDrugDose;
    @FXML
    private TextField tfDrugDuration;
    @FXML
    private TextField tfDrugAdvice;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;

    ObservableList<Drug> drugs = FXCollections.observableArrayList();
    ObservableList<TemplateDrug> templateDrugList = FXCollections.observableArrayList();
    DrugGetway drugGetway = new DrugGetway();
    TemplateGetway templateGetway = new TemplateGetway();
    Template template = new Template();
    TemplateDrug templateDrug = new TemplateDrug();
    int index = 0;
    int sl = 1;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnUpdate.setVisible(false);
        Platform.runLater(() -> {
            loadDrug();
        });
    }

    @FXML
    private void handleSaveTemplate(ActionEvent event) {
        System.out.println("Clickd");
        if (templateDrugList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add drug first");
            alert.setHeaderText("You didn't add any drug");
            alert.show();
        } else if (tfTemplateName.getText() == null || tfTemplateName.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Template name empty");
            alert.setHeaderText("You didn't give your template a name");
            alert.show();
        } else {
            template.setTemplateName(tfTemplateName.getText());
            template.setNote(tfTemplateNote.getText());
            template.setCc(taCC.getText());
            template.setOe(taOE.getText());
            template.setDd(taDD.getText());
            template.setPd(taPD.getText());
            template.setAdvice(taAdvice.getText());
            template.setLab_workup(taLabWorkup.getText());
            if (templateGetway.save(template, templateDrugList)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Template added");
                alert.setHeaderText("Template saved");
                alert.setContentText("Template saved");
                alert.show();
            }
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        resetDrugForm();
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        int durgId = comboBoxDrug.getSelectionModel().getSelectedItem().getId();
        String drugName = comboBoxDrug.getSelectionModel().getSelectedItem().getName();
        templateDrug = new TemplateDrug(sl++, durgId, drugName, tfDrugType.getText(), tfDrugStrength.getText(), tfDrugDose.getText(), tfDrugDuration.getText(), tfDrugAdvice.getText());
        templateDrugList.add(templateDrug);
        loadDrugTable();
        resetDrugForm();
    }

    @FXML
    private void handleUpdateButton(ActionEvent event) {
        int durgId = comboBoxDrug.getSelectionModel().getSelectedItem().getId();
        String drugName = comboBoxDrug.getSelectionModel().getSelectedItem().getName();
        sl = templateDrugList.get(index).getId();
        templateDrug = new TemplateDrug(sl, durgId, drugName, tfDrugType.getText(), tfDrugStrength.getText(), tfDrugDose.getText(), tfDrugDuration.getText(), tfDrugAdvice.getText());
        templateDrugList.set(index, templateDrug);
        loadDrugTable();
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        resetDrugForm();
    }

    @FXML
    private void handleAddNewDrug(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/drug/NewDrug.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Drug");
        stage.show();

        stage.setOnCloseRequest((WindowEvent event1) -> {
            drugs.clear();
            loadDrug();
        });

    }

    private void loadDrug() {
        drugs = drugGetway.allDrugs();
        comboBoxDrug.getItems().addAll(drugs);
        comboBoxDrug.setConverter(new StringConverter<Drug>() {
            @Override
            public String toString(Drug object) {
                return object.getName();
            }

            @Override
            public Drug fromString(String string) {
                return comboBoxDrug.getItems().stream().filter(ap -> ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
        comboBoxDrug.getSelectionModel().selectFirst();
        comboBoxDrug.setOnKeyReleased((KeyEvent event) -> {
            drugs.forEach((drug) -> {
                if (String.valueOf(event.getText()).matches(Character.toString(drug.getName().charAt(0)))) {
                    comboBoxDrug.setValue(drug);
                }
            });
        });
        comboBoxDrug.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Drug> observable, Drug oldValue, Drug newValue) -> {
            System.out.println("Selected drug id : " + newValue.getId() + " Name : " + newValue.getName());
        });
    }

    private void loadDrugTable() {
        drugTable.getItems().clear();
        clmSl.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmDrugName.setCellValueFactory(new PropertyValueFactory<>("drug_name"));
        clmDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmAdvice.setCellValueFactory(new PropertyValueFactory<>("advice"));
        clmDose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        clmDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        clmStrength.setCellValueFactory(new PropertyValueFactory<>("strength"));
        clmAction.setCellFactory(action);
        drugTable.getItems().addAll(templateDrugList);
    }

    private void resetDrugForm() {
        tfDrugType.setText("");
        tfDrugAdvice.setText("");
        tfDrugDose.setText("");
        tfDrugDuration.setText("");
        tfDrugStrength.setText("");
        comboBoxDrug.getSelectionModel().selectFirst();
    }

    private void resetDrugList() {

    }

    private void resetAll() {

    }

    private void deleteDrugFromList(TemplateDrug drug) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Are you sure");
        alert.setContentText("Click ok if you want to delete this drug");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            drugTable.getItems().remove(drug);
            templateDrugList.remove(drug);
        }
    }

    Callback<TableColumn<TemplateDrug, String>, TableCell<TemplateDrug, String>> action = (TableColumn<TemplateDrug, String> param) -> {
        final TableCell<TemplateDrug, String> cell = new TableCell<TemplateDrug, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    PrescriptionAction action = new PrescriptionAction() {

                        TemplateDrug drug = getTableView().getItems().get(getIndex());

                        @Override
                        protected void handleEditButton(ActionEvent actionEvent) {
                            btnSave.setVisible(false);
                            btnUpdate.setVisible(true);
                            index = templateDrugList.indexOf(drug);
                            tfDrugType.setText(drug.getType());
                            tfDrugAdvice.setText(drug.getAdvice());
                            tfDrugDose.setText(drug.getDose());
                            tfDrugDuration.setText(drug.getDuration());
                            tfDrugStrength.setText(drug.getStrength());
                            comboBoxDrug.setValue(new Drug(drug.getDrug_id(), drug.getDrug_name()));
                        }

                        @Override
                        protected void handleDeleteButton(ActionEvent actionEvent) {
                            deleteDrugFromList(drug);
                        }
                    };
                    setGraphic(action);
                    setText(null);
                }
            }
        };
        return cell;
    };

}
