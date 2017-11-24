/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.template;

import getway.TemplateGetway;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import model.Template;
import model.TemplateDrug;
import view.html.TemplateMaker;

/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class ViewTemplateController implements Initializable {

    @FXML
    private WebView webView;

    TemplateGetway getway = new TemplateGetway();

    TemplateMaker maker = new TemplateMaker();

    Template template = new Template();
    WebEngine engine = new WebEngine();
    
    ObservableList<TemplateDrug> drugs = FXCollections.observableArrayList();

    File file = null;
    PrintStream out = null;
    Window owner;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("In init");

    }

    @FXML
    private void handlePrintButton(ActionEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL);
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        job.showPrintDialog(owner);
        if (job != null) {
            engine.print(job);
            job.endJob();
        }
    }

    public void loadTemplateDetails(int templateId) {
        template = getway.selectedTemplate(templateId);
        drugs = getway.getSelectedTemplateDrugs(templateId);
        try {
            file = new File("Hello.html");
            file.createNewFile();

            out = new PrintStream(file);
            out.print(maker.makeTemplate(template,drugs));
            
            engine = webView.getEngine();
            engine.load(file.toURI().toString());
            
//             Desktop.getDesktop().browse(URI.create("Hello.html"));
            
            System.out.println(file.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(ViewTemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
