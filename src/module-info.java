module Etudiants {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ma.projet.model to javafx.base;
    exports application;
    exports ma.projet.controller;
    opens ma.projet.controller to javafx.fxml;
}
