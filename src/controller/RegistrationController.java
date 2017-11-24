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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class RegistrationController implements Initializable {

    @FXML
    private Button btnSignUp;
    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField pfPass;
    @FXML
    private PasswordField pfRePass;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextArea taInfo;

    AuthGetway auth = new AuthGetway();
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
    }

    /**
     * Sign up
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void signUpOnAction(ActionEvent event) throws IOException {
        if (isValid() && isPassMatch() && isValidEmail()) {
            auth.save(new User(tfUserName.getText(), tfEmail.getText(), pfPass.getText(), tfFullName.getText(), taInfo.getText(), 0, 0));
            Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            root.getStylesheets().add("../css/main.css");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage nStage = (Stage) btnSignUp.getScene().getWindow();
            nStage.close();
        } else {
            System.out.println("Email address not valid");
        }

    }

    /**
     * Check required text field are not empty
     *
     * @return
     */
    private boolean isValid() {
        boolean valid = true;
        if (tfFullName.getText().isEmpty() || tfEmail.getText().isEmpty() || tfUserName.getText().isEmpty() || pfPass.getText().isEmpty() || pfRePass.getText().isEmpty() || taInfo.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("All field required");
            alert.setHeaderText("All firleds are required");
            alert.setContentText("All text fields are required. please fill all text field");
            alert.show();
            valid = false;
        }
        return valid;
    }

    /**
     * Check passwords are matched show alert if not match
     *
     * @return
     */
    private boolean isPassMatch() {
        boolean valid = false;
        if (pfPass.getText().matches(pfRePass.getText())) {
            valid = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password not match");
            alert.setHeaderText("Password not match");
            alert.show();
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
