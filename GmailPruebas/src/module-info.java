module GmailPruebas {

    requires java.mail;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires  javafx.graphics;
    exports Correo.view;
    exports Correo.model;
    exports Correo;
    exports Correo.logica;

}