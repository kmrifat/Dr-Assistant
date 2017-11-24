/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.html;

import getway.AuthGetway;
import static j2html.TagCreator.b;
import static j2html.TagCreator.body;
import static j2html.TagCreator.br;
import static j2html.TagCreator.div;
import static j2html.TagCreator.each;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.hr;
import static j2html.TagCreator.html;
import static j2html.TagCreator.i;
import static j2html.TagCreator.li;
import static j2html.TagCreator.ol;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;
import static j2html.TagCreator.ul;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.collections.ObservableList;
import model.Patient;
import model.Prescription;
import model.PrescriptionDrug;
import model.User;

/**
 *
 * @author RIfat
 */
public class PrescriptionMaker {
    
    User user = new User();
    
    AuthGetway auth = new AuthGetway();

    public String makePrescription(Prescription prescription, ObservableList<PrescriptionDrug> drugs, Patient patient) {
        
        user = auth.getUser();
        String phone = user.getShoePhoneInPrescription() == 1 ? "Phone : "+ user.getPhoneNumber() : "";
        String address = user.getShowAddressInPrescription() == 1 ? user.getAddress() : "";
        String sex = patient.getSex() == 1 ? "Male" : patient.getSex() == 2 ? "Fe-Male" : "Other";
        String string = html(
                head(
                        title("Prescription")
                ),
                body(
                        h2(user.getFullName()),
                        p(
                                b(user.getInfo()),
                                br(),
                                span(phone),
                                br(),
                                span(address)
                        ),
                        hr(),
                        table().with(
                                tr().with(
                                        td().with(
                                                span("Name : " + patient.getName())
                                        ),
                                        td().with(
                                                span("Sex : " + sex)
                                        ),
                                        td().with(
                                                span("Age : " + age(patient, prescription.getDate()) + " Years")
                                        ),
                                        td().with(
                                                span("Date : " + prescription.getDate())
                                        )
                                )
                        ).withStyle("width:100%;"),
                        hr(),
                        div(
                                p(
                                        b("Chief Complain"),
                                        br(),
                                        span(prescription.getCc())
                                ),
                                p(
                                        b("On Examination"),
                                        br(),
                                        span(prescription.getOe())
                                ),
                                p(
                                        b("Provisional Diagnosis"),
                                        br(),
                                        span(prescription.getDd())
                                ),
                                p(
                                        b("Differential  Diagnosis"),
                                        br(),
                                        span(prescription.getDd())
                                ),
                                p(
                                        b("Lab workup"),
                                        br(),
                                        span(prescription.getLabWorkUp())
                                ),
                                p(
                                        b("Prescription Advice"),
                                        br(),
                                        span(prescription.getAdvice())
                                ),
                                p(
                                        b("Next visit"),
                                        br(),
                                        span(prescription.getNextVisit())
                                )
                        ).withStyle("display:inline;float:left;width:40%;"),
                        div(
                                h1("Rx"),
                                ol(
                                        each(drugs, drug -> tr(
                                        li(i(drug.getDrugType() + " "), b(" " + drug.getDrugName()), span(" " + drug.getDrugStrength() + " "), ul(
                                                li(drug.getDrugDose() + " " + drug.getDrugDuration()).withStyle("list-style: none"),
                                                li(drug.getDrugAdvice()).withStyle("list-style: none")
                                        ).withStyle("padding-left: 10px"))
                                ))
                                )
                        ).withStyle("display:inline;float:left;width:60%;"),
                        div(
                                p(
                                        p("This Prescription is generate with Dr.Assistant (Prescription Writing and patient management software). Developed by K M Rifat ul alom").withStyle("font-size:9px;"),
                                        p("Get your copy from : http://www.binarycastle.net/dr-assistant").withStyle("font-size:9px;")
                                )
                        ).withStyle("position: absolute;bottom:0")
                )
        ).render();

        return string;
    }

    private String age(Patient patient, String date) {
        String age = "";
        LocalDate birthdate = patient.getDateOrBirth();
        LocalDate now = LocalDate.parse(date);
        age = String.valueOf(ChronoUnit.YEARS.between(birthdate, now));
        return age;
    }
}
