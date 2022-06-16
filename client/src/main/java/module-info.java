module com.cyrex.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires common;
    requires java.desktop;
    requires org.json;

    exports com.cyrex.client;
    exports com.cyrex.client.gui.controllers;
    opens com.cyrex.client.gui.controllers to javafx.fxml;
    exports com.cyrex.client.gui.views;
    opens com.cyrex.client.gui.views to javafx.fxml;
}