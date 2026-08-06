module com.macondo.brickbreaker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.macondo.brickbreaker to javafx.fxml;
    exports com.macondo.brickbreaker;
    exports com.macondo.brickbreaker.controller;
    exports com.macondo.brickbreaker.input;
    exports com.macondo.brickbreaker.model;
    exports com.macondo.brickbreaker.view;

}