/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drassistant;

import database.TableSchema;
import getway.AuthGetway;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author RIfat
 */
public class DrAssistant extends Application {

    AuthGetway auth = new AuthGetway();

    @Override
    public void start(Stage stage) throws Exception {
        if (auth.totalUser() == 0) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Registration.fxml"));
            root.getStylesheets().add("../css/main.css");
            Scene scene = new Scene(root);
            stage.setTitle("Dr.Assistant (Desktop Edition) - Registration");
            stage.setScene(scene);
            stage.show();

        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            root.getStylesheets().add("../css/main.css");
            Scene scene = new Scene(root);
            stage.setTitle("Dr.Assistant (Desktop Edition) - Login");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = System.getProperty("user.home");
        new File(path + "\\Documents\\DrAssistant").mkdir();
        TableSchema.connect();
        launch(args);
    }

}
