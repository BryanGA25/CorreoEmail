module GmailPruebas {

    requires java.mail;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires commons.email;
    requires ProyectoReloj;

    exports Correo.view;
    exports Correo.model;
    exports Correo;
    exports Correo.logica;

    opens Correo.view to javafx.fxml;


}