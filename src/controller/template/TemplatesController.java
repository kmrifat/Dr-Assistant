/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.template;

import controller.patient.PatientsController;
import getway.TemplateGetway;
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
import model.Paginate;
import model.Template;
import view.common.Actions;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class TemplatesController implements Initializable {

    @FXML
    private TableView<Template> templateTable;
    @FXML
    private TableColumn<Template, Integer> clmSl;
    @FXML
    private TableColumn<Template, String> clmTemplateName;
    @FXML
    private TableColumn<Template, String> clmNote;
    @FXML
    private TableColumn<Template, String> clmNumberoFDrug;
    @FXML
    private TableColumn clmAction;
    @FXML
    private TextField tfSearch;

    ObservableList<Template> templates = FXCollections.observableArrayList();
    TemplateGetway templateGetway = new TemplateGetway();
    Paginate paginate = new Paginate();
    boolean templateDefault = true;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(this::loadData);
    }

    @FXML
    private void newTemplateOnAction(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/template/NewTemplate.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fXMLLoader.load());
        stage.setScene(scene);
        stage.setTitle("New Template");
        stage.show();
    }

    @FXML
    private void tfSearchOnAction(ActionEvent event) {
        if (tfSearch.getText() == null || tfSearch.getText().trim().length() == 0) {
            loadData();
        } else {
            searchData(tfSearch.getText().trim());
        }
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        if (tfSearch.getText() == null || tfSearch.getText().trim().length() == 0) {
            loadData();
        } else {
            searchData(tfSearch.getText().trim());
        }
    }

    private void loadData() {
       
        templates = templateGetway.templates();
        templateTable.getItems().clear();
        clmTemplateName.setCellValueFactory(new PropertyValueFactory<>("templateName"));
        clmNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        clmSl.setCellValueFactory(new PropertyValueFactory<>("sl"));
        clmNumberoFDrug.setCellValueFactory(new PropertyValueFactory<>("totalDrug"));
        clmAction.setCellFactory(action);
        templateTable.getItems().addAll(templates);
    }

    private void searchData(String query) {
        templates = templateGetway.searchTemplates(query);
        templateTable.getItems().clear();
        clmTemplateName.setCellValueFactory(new PropertyValueFactory<>("templateName"));
        clmNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        clmSl.setCellValueFactory(new PropertyValueFactory<>("sl"));
        clmNumberoFDrug.setCellValueFactory(new PropertyValueFactory<>("totalDrug"));
        clmAction.setCellFactory(action);
        templateTable.getItems().addAll(templates);
    }

    private void openDeleteDialog(Template template) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Are you sure ?");
        alert.setContentText("Are you sure, you want to delete " + template.getTemplateName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (templateGetway.deleteTemplate(template)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Template deleted");
                alert1.showAndWait();
                loadData();
            }
        }
    }

    private void openEditDialog(int templateId) {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/template/EditTemplate.fxml"));
        try {
            Parent root = fXMLLoader.load();
            EditTemplateController controller = fXMLLoader.getController();
            controller.loadTemplateDetails(templateId);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Template");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openViewDialog(int templateId) {
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/template/ViewTemplate.fxml"));
        try {
            Parent root = fXMLLoader.load();
            ViewTemplateController controller = fXMLLoader.getController();
            controller.loadTemplateDetails(templateId);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("View Template");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Callback<TableColumn<Template, String>, TableCell<Template, String>> action = (TableColumn<Template, String> param) -> {
        final TableCell<Template, String> cell = new TableCell<Template, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Actions actions = new Actions() {
                        Template template = getTableView().getItems().get(getIndex());

                        @Override
                        protected void handleEditButton(ActionEvent actionEvent) {
                            openEditDialog(template.getId());
                        }

                        @Override
                        protected void handleViewButton(ActionEvent actionEvent) {
                            openViewDialog(template.getId());
                        }

                        @Override
                        protected void handleDeleteButton(ActionEvent actionEvent) {
                            openDeleteDialog(template);
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
