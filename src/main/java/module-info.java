module com.guccigang.videostoremanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.guccigang.videostoremanager to javafx.fxml;
    opens com.guccigang.videostoremanager.controllers to javafx.fxml;
    opens com.guccigang.videostoremanager.scenes to javafx.fxml;
    opens com.guccigang.videostoremanager.core to javafx.base;
    opens com.guccigang.videostoremanager.auth to javafx.base;
    opens com.guccigang.videostoremanager.items to javafx.base;
    opens com.guccigang.videostoremanager.transactions to javafx.base;

    exports com.guccigang.videostoremanager;
    exports com.guccigang.videostoremanager.controllers;
    exports com.guccigang.videostoremanager.scenes;
}