/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.patient;

import getway.PatientGetway;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class NewPatientController implements Initializable {

    @FXML
    private ImageView imageViewPatientImage;
    @FXML
    private TextField tfName;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private RadioButton radioMale;
    @FXML
    private RadioButton radioFeMale;
    @FXML
    private RadioButton radioOther;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextArea taAddress;

    PatientGetway patientGetway = new PatientGetway();

    File file = null;
    File copyFile = null;
    FileChooser chooser = new FileChooser();
    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
    String path = System.getProperty("user.home");
    Random rand = new Random();
    int n = rand.nextInt(999999999) + 1;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup toggleGroup = new ToggleGroup();
        radioMale.setToggleGroup(toggleGroup);
        radioFeMale.setToggleGroup(toggleGroup);
        radioOther.setToggleGroup(toggleGroup);
        radioMale.setSelected(true);
    }

    @FXML
    private void selectPatientImageOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Choos a thumbnail (Image only)");
        chooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        file = chooser.showOpenDialog(stage);
        String imagePath = file.getPath();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageViewPatientImage.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(NewPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        resetForm();
    }

    @FXML
    private void patientSaveOnAction(ActionEvent event) {
        System.out.println("save patient");
        LocalDate date = dpDateOfBirth.getValue();
        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int sex = radioMale.isSelected() ? 1 : radioFeMale.isSelected() ? 2 : 0;
        if (patientGetway.save(new Patient(0, copyImage(), tfName.getText(), date, sex, tfEmail.getText(), tfPhone.getText(), taAddress.getText(), dateFormat.format(today)))) {
            resetForm();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Patient saved successfully");
            alert.setHeaderText("Patient saved");
            alert.setContentText("Patient saved successfullly");
            alert.showAndWait();
        }
    }

    private void resetForm() {
        tfName.setText("");
        tfEmail.setText("");
        tfPhone.setText("");
        radioMale.setSelected(true);
        taAddress.setText("");
        dpDateOfBirth.setValue(null);
        Image image = new Image("/image/avater.jpg");
        imageViewPatientImage.setImage(image);
    }

    private String copyImage() {
        if (file != null) {
            copyFile = new File(path + "\\Documents\\DrAssistant\\" + n + file.getName());
        } else {
            return null;
        }
        System.out.println(copyFile.getPath());
        try {
            Files.copy(Paths.get(file.getPath()), Paths.get(copyFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(NewPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return copyFile.getName();

    }

}
