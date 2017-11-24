package view.patient;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import model.Patient;

public abstract class PatientCard extends AnchorPane {

    protected final HBox hBox;
    protected final VBox vBox;
    protected final ImageView imageView;
    protected final VBox vBox0;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final Label label3;
    protected final Button button;
    protected final ButtonBar buttonBar;
    protected final Button button0;
    protected final Button button1;
    protected final Button button2;

    protected final int patientId;

    String path = System.getProperty("user.home");

    private String patientSex = "Male";

    public PatientCard(Patient patient) {
        patientId = patient.getId();
        hBox = new HBox();
        vBox = new VBox();
        imageView = new ImageView();
        vBox0 = new VBox();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        button = new Button();
        buttonBar = new ButtonBar();
        button0 = new Button();
        button1 = new Button();
        button2 = new Button();

        setPrefHeight(196.0);
        setPrefWidth(451.0);
        getStyleClass().add("card");
        getStylesheets().add("/view/patient/../../css/main.css");

        AnchorPane.setBottomAnchor(hBox, 0.0);
        AnchorPane.setLeftAnchor(hBox, 0.0);
        AnchorPane.setRightAnchor(hBox, 0.0);
        AnchorPane.setTopAnchor(hBox, 0.0);
        hBox.setPrefHeight(182.0);
        hBox.setPrefWidth(325.0);
        hBox.setSpacing(7.0);

        vBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        vBox.setPrefHeight(182.0);
        vBox.setPrefWidth(172.0);

        imageView.setFitHeight(121.0);
        imageView.setFitWidth(150.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
       
        showImage(patient);

        vBox0.setPrefHeight(162.0);
        vBox0.setPrefWidth(275.0);

        label.getStyleClass().add("card-header");
        label.setText(patient.getName());

        label0.setText("Age : " + age(patient) + " Years");

        patientSex = patient.getSex() == 1 ? "Male" : patient.getSex() == 2 ? "Fe-Male" : "Other";

        label1.setText("Sex : " + patientSex);
        label2.setText("Patient from : " + patient.getCreatedAt());
        label3.setText("No. of Prescription : " + patient.getNumberOfPrescription());

        button.setMnemonicParsing(false);
        button.setOnAction(this::prescriptionOnAction);
        button.getStyleClass().add("btn-primary");
        button.setText("Write new prescription");
        VBox.setMargin(button, new Insets(5.0, 0.0, 0.0, 0.0));

        buttonBar.setPrefHeight(40.0);
        buttonBar.setPrefWidth(200.0);

        button0.setDefaultButton(true);
        button0.setMnemonicParsing(false);
        button0.setOnAction(this::editOnAction);
        button0.setText("Edit");

        button1.setMnemonicParsing(false);
        button1.setOnAction(this::historyOnAction);
        button1.getStyleClass().add("btn-secondary");
        button1.setText("History");
        VBox.setMargin(buttonBar, new Insets(7.0, 0.0, 0.0, 0.0));

        button2.setMnemonicParsing(false);
        button2.setOnAction(this::deleteOnAction);
        button2.setPrefHeight(41.0);
        button2.setPrefWidth(69.0);
        button2.getStyleClass().add("card-btn-delete");
        button2.setText("✘");
        HBox.setMargin(button2, new Insets(-25.0, -25.0, 0.0, 0.0));

        vBox.getChildren().add(imageView);
        hBox.getChildren().add(vBox);
        vBox0.getChildren().add(label);
        vBox0.getChildren().add(label0);
        vBox0.getChildren().add(label1);
        vBox0.getChildren().add(label2);
        vBox0.getChildren().add(label3);
        vBox0.getChildren().add(button);
        buttonBar.getButtons().add(button0);
        buttonBar.getButtons().add(button1);
        vBox0.getChildren().add(buttonBar);
        hBox.getChildren().add(vBox0);
        hBox.getChildren().add(button2);
        getChildren().add(hBox);

    }
    
    private void showImage(Patient patient){
            if (patient.getThumbnail() == null) {
               imageView.setImage(new Image(getClass().getResource("/image/avater.jpg").toExternalForm()));
           } else {
               File file = new File(path + "\\Documents\\DrAssistant\\" + patient.getThumbnail());
               try {
                   BufferedImage bufferedImage = ImageIO.read(file);
                   Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                   imageView.setImage(image);
               } catch (IOException ex) {
                   Logger.getLogger(PatientCard.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
    }
    
    private String age(Patient patient) {
        String age = "";
        LocalDate birthdate = patient.getDateOrBirth();
        LocalDate now = LocalDate.now();
        age = String.valueOf(ChronoUnit.YEARS.between(birthdate, now));
        return age;
    }

    protected abstract void prescriptionOnAction(javafx.event.ActionEvent actionEvent);

    protected abstract void editOnAction(javafx.event.ActionEvent actionEvent);

    protected abstract void historyOnAction(javafx.event.ActionEvent actionEvent);

    protected abstract void deleteOnAction(javafx.event.ActionEvent actionEvent);

}
