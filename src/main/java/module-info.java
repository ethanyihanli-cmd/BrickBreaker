module com.macondo.brickbreaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens com.macondo.brickbreaker to javafx.fxml;
    exports com.macondo.brickbreaker;
    exports com.macondo.brickbreaker.controller;
    exports com.macondo.brickbreaker.input;
    exports com.macondo.brickbreaker.model;
    exports com.macondo.brickbreaker.view;
    exports com.macondo.brickbreaker.util;

}