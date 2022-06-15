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

    opens com.cyrex.client.gui to javafx.fxml;
    exports com.cyrex.client;
    exports com.cyrex.client.gui;
}