/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import getway.AuthGetway;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class SettingController implements Initializable {

    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextArea taAddress;
    @FXML
    private TextArea taInfo;
    @FXML
    private CheckBox cbPhone;
    @FXML
    private CheckBox cbAddress;

    AuthGetway auth = new AuthGetway();

    User user = new User();

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @FXML
    private TextField tfEmail;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfUserName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches("^[^\\d\\s]+$")) {
                    tfUserName.setText(oldValue);
                }
            }
        });

        Platform.runLater(() -> {
            loadAuthUser();
        });
    }

    @FXML
    private void handleUpdateButton(ActionEvent event) {

        if (isValid() && isValidEmail()) {
            int showPhone = cbPhone.isSelected() ? 1 : 0;
            int showAddress = cbAddress.isSelected() ? 1 : 0;
            user.setUserName(tfUserName.getText());
            user.setEmailAddress(tfEmail.getText());
            user.setFullName(tfFullName.getText());
            user.setInfo(taInfo.getText());
            user.setAddress(taAddress.getText());
            user.setShoePhoneInPrescription(showPhone);
            user.setShowAddressInPrescription(showAddress);
            if (auth.update(user)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User info updated");
                alert.setHeaderText("Updated");
                alert.setContentText("User info has been updated");
                alert.show();
            }
        }
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/auth/ChangePassword.fxml"));
        root.getStylesheets().add("../css/main.css");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    void loadAuthUser() {
        user = auth.getUser();
        tfUserName.setText(user.getUserName());
        tfFullName.setText(user.getFullName());
        tfPhone.setText(user.getPhoneNumber());
        taInfo.setText(user.getInfo());
        tfEmail.setText(user.getEmailAddress());
        taAddress.setText(user.getAddress());
        cbPhone.setSelected(setSelected(user.getShoePhoneInPrescription()));
        cbAddress.setSelected(setSelected(user.getShowAddressInPrescription()));
    }

    boolean setSelected(int value) {
        boolean selected;
        selected = value == 1;
        return selected;
    }

    /**
     * Check required text field are not empty
     *
     * @return
     */
    private boolean isValid() {
        boolean valid = true;
        if (tfFullName.getText().isEmpty() || tfEmail.getText().isEmpty() || tfUserName.getText().isEmpty() || taInfo.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Text Field Required");
            alert.setHeaderText("These text field are reuired");
            alert.setContentText("User Name \nEmail Address \nFull Name \nInfo");
            alert.show();
            valid = false;
        }
        return valid;
    }

    private boolean isValidEmail() {
        boolean valid = false;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(tfEmail.getText());
        if (matcher.find()) {
            valid = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email not valid");
            alert.setHeaderText("Email address is not valid");
            alert.setContentText("Email address is not valid please enter a valid email address");
            alert.show();
            valid = false;
        }
        return valid;
    }

}
