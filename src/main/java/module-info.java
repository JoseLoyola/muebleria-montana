module com.muebleriamontana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;
    requires java.desktop;
    requires javafx.swing;
    requires java.mail;

//    requires org.apache.pdfbox;
//    requires org.controlsfx.controls;
//    requires net.synedra.validatorfx;
//    requires com.google.api.client.auth;
//    requires com.google.api.client.extensions.java6.auth;
//    requires com.google.api.client.extensions.jetty.auth;
//    requires google.api.client;
//    requires com.google.api.client;
//    requires com.google.api.client.json.gson;
//    requires com.google.api.services.gmail;

    opens com.muebleriamontana to javafx.fxml;
    exports com.muebleriamontana;
    opens com.muebleriamontana.model to javafx.fxml;
    exports com.muebleriamontana.model;
    opens com.muebleriamontana.controller to javafx.fxml;
    exports com.muebleriamontana.controller;
    opens com.muebleriamontana.controller.alertas to javafx.fxml;
    exports com.muebleriamontana.controller.alertas;
    opens com.muebleriamontana.controller.pantallas to javafx.fxml;
    exports com.muebleriamontana.controller.pantallas;
}