module com.example.lsd {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.lsd to javafx.fxml;
    opens com.example.lsd.Controllers to javafx.fxml;
    opens com.example.lsd.Controllers.ServiceSeeker to javafx.fxml;
    opens com.example.lsd.Controllers.Admin to javafx.fxml;

    exports com.example.lsd;
    exports com.example.lsd.Controllers;
    exports com.example.lsd.Controllers.ServiceSeeker;
    exports com.example.lsd.Controllers.Admin;

    exports com.example.lsd.Models;
    exports com.example.lsd.DBUtils;
    exports com.example.lsd.Views;
}