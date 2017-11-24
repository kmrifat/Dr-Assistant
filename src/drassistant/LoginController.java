/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drassistant;

import getway.AuthGetway;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class LoginController implements Initializable {

    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnLogin;

    AuthGetway auth = new AuthGetway();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnLoginOnAction(ActionEvent event) {
        if (isValid()) {
            if (auth.authenticate(tfUserName.getText(), pfPassword.getText())) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
                    root.getStylesheets().add("../css/main.css");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Dr Assistant (Desktop Edition) - Home");
                    stage.setScene(scene);
                    stage.show();

                    Stage nStage = (Stage) btnLogin.getScene().getWindow();
                    nStage.close();

                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Username or password doesn't match");
                alert.setHeaderText("Username or password doesn't match");
                alert.show();
            }
        }
    }

    @FXML
    private void hlForgetPassword(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/auth/ForgetPassword.fxml"));
            root.getStylesheets().add("../css/main.css");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Dr Assistant (Desktop Edition) - Forget Password");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleTryButton(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(URI.create("http://preview.codecanyon.net/item/drassistant-patient-and-prescription-management-system-in-laravel/full_screen_preview/20260606"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isValid() {
        boolean valid = false;
        if (tfUserName.getText().isEmpty() || pfPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All field required");
            alert.setHeaderText("Please enter your username and password to login");
            alert.show();
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

}
