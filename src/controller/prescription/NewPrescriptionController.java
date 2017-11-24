/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.prescription;

import getway.DrugGetway;
import getway.PatientGetway;
import getway.PrescriptionGetway;
import getway.TemplateGetway;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import model.Drug;
import model.Patient;
import model.Prescription;
import model.Template;
import model.TemplateDrug;
import view.common.PrescriptionAction;
import view.patient.PatientCard;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class NewPrescriptionController implements Initializable {

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
    private TableColumn<?, ?> clmSl;
    @FXML
    private TableColumn<?, ?> clmDrugType;
    @FXML
    private TableColumn<?, ?> clmDrugName;
    @FXML
    private TableColumn<?, ?> clmStrength;
    @FXML
    private TableColumn<?, ?> clmDose;
    @FXML
    private TableColumn<?, ?> clmDuration;
    @FXML
    private TableColumn<?, ?> clmAdvice;
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
    @FXML
    private ComboBox<Template> comboBoxTemplate;
    @FXML
    private ComboBox<Prescription> comboBoxHistory;
    @FXML
    private ImageView imagePatientView;
    @FXML
    private Label lblPatientName;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblSex;
    @FXML
    private TextField tfNextVisit;

    ObservableList<Drug> drugs = FXCollections.observableArrayList();
    ObservableList<Template> templates = FXCollections.observableArrayList();
    ObservableList<TemplateDrug> templateDrugList = FXCollections.observableArrayList();
    ObservableList<Prescription> prescriptionList = FXCollections.observableArrayList();

    DrugGetway drugGetway = new DrugGetway();
    TemplateGetway templateGetway = new TemplateGetway();
    PatientGetway patientGetway = new PatientGetway();
    PrescriptionGetway prescriptionGetway = new PrescriptionGetway();

    Template template = new Template();
    TemplateDrug templateDrug = new TemplateDrug();
    Patient patient = new Patient();
    Prescription prescription = new Prescription();

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
            loadTemplate();
            loadPrescription();
        });
    }

    @FXML
    private void handleSavePrescription(ActionEvent event) {
        if (patient.getId() != 0) {
            prescription.setPatientId(patient.getId());
            prescription.setDate(LocalDate.now().toString());
            prescription.setCc(taCC.getText());
            prescription.setOe(taOE.getText());
            prescription.setDd(taDD.getText());
            prescription.setLabWorkUp(taLabWorkup.getText());
            prescription.setAdvice(taAdvice.getText());
            prescription.setPd(taPD.getText());
            prescription.setNextVisit(tfNextVisit.getText());
            if (prescriptionGetway.save(prescription, templateDrugList)) {
                loadPrintablePrescription(prescription.getId(), true);
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
        templateDrug = new TemplateDrug(templateDrugList.size() + 1, durgId, drugName, tfDrugType.getText(), tfDrugStrength.getText(), tfDrugDose.getText(), tfDrugDuration.getText(), tfDrugAdvice.getText());
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

    @FXML
    private void handleTemplateOnAction(ActionEvent event) {
        loadTemplateDetails(comboBoxTemplate.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    private void handlePrescriptonHistory(ActionEvent event) {
        System.out.println("Prescription id : " + comboBoxHistory.getSelectionModel().getSelectedItem().getId());
        loadPrintablePrescription(comboBoxHistory.getSelectionModel().getSelectedItem().getId(), false);
    }

    public void loadPatient(int patientId) {
        patient = patientGetway.selectedPatient(patientId);
        lblPatientName.setText(patient.getName());
        String sex = patient.getSex() == 1 ? "Male" : patient.getSex() == 2 ? "Fe-male" : "Other";
        LocalDate birthdate = patient.getDateOrBirth();
        LocalDate now = LocalDate.now();

        System.out.println(birthdate);
        System.out.println(ChronoUnit.YEARS.between(birthdate, now));
        lblAge.setText("Age : " + ChronoUnit.YEARS.between(birthdate, now) + " Years");
        lblSex.setText("Sex :" + sex);
        showImage(patient);
    }

    public void loadTemplateDetails(int templateId) {
        template = templateGetway.selectedTemplate(templateId);
        taCC.setText(template.getCc());
        taDD.setText(template.getDd());
        taOE.setText(template.getOe());
        taPD.setText(template.getPd());
        taLabWorkup.setText(template.getLab_workup());
        taAdvice.setText(template.getAdvice());
        templateDrugList = templateGetway.getSelectedTemplateDrugs(templateId);
        loadDrugTable();

    }

    public void loadDrugTable() {
        drugTable.getItems().clear();
        drugTable.getItems().addAll(templateDrugList);
        clmSl.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmDrugName.setCellValueFactory(new PropertyValueFactory<>("drug_name"));
        clmDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmAdvice.setCellValueFactory(new PropertyValueFactory<>("advice"));
        clmDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        clmStrength.setCellValueFactory(new PropertyValueFactory<>("strength"));
        clmDose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        clmAction.setCellFactory(action);
    }

    private void resetDrugForm() {
        tfDrugType.setText("");
        tfDrugAdvice.setText("");
        tfDrugDose.setText("");
        tfDrugDuration.setText("");
        tfDrugStrength.setText("");
        comboBoxDrug.getSelectionModel().selectFirst();
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

    private void loadTemplate() {
        templates = templateGetway.templates();
        comboBoxTemplate.getItems().addAll(templates);
        comboBoxTemplate.setConverter(new StringConverter<Template>() {
            @Override
            public String toString(Template object) {
                return object.getTemplateName();
            }

            @Override
            public Template fromString(String string) {
                return comboBoxTemplate.getItems().stream().filter(ap -> ap.getTemplateName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void loadPrescription() {
        prescriptionList = prescriptionGetway.patientPrescriptions(patient);
        comboBoxHistory.getItems().addAll(prescriptionList);
        comboBoxHistory.setConverter(new StringConverter<Prescription>() {
            @Override
            public String toString(Prescription object) {
                return object.getDate();
            }

            @Override
            public Prescription fromString(String string) {
                return comboBoxHistory.getItems().stream().filter(ap -> ap.getDate().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void loadPrintablePrescription(int prescriptionId, boolean stageClose) {
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
            if (stageClose) {
                Stage nStage = (Stage) lblAge.getScene().getWindow();
                nStage.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(PrescriptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                            System.out.println(drug.getDrug_id());
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

    private void showImage(Patient patient) {
        String path = System.getProperty("user.home");
        if (patient.getThumbnail() == null) {
            imagePatientView.setImage(new Image(getClass().getResource("/image/avater.jpg").toExternalForm()));
        } else {
            File file = new File(path + "\\Documents\\DrAssistant\\" + patient.getThumbnail());
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imagePatientView.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(PatientCard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
