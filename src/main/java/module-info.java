module com.guccigang.videostoremanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.guccigang.videostoremanager to javafx.fxml;
    exports com.guccigang.videostoremanager;
    exports com.guccigang.videostoremanager.controllers;
    opens com.guccigang.videostoremanager.controllers to javafx.fxml;
    exports com.guccigang.videostoremanager.scenes;
    opens com.guccigang.videostoremanager.scenes to javafx.fxml;
}