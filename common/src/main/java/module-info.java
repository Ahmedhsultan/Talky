module gov.iti.jets.common {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    requires com.google.common;
    requires lombok;
    requires java.sql;
    requires java.sql.rowset;
    exports gov.iti.jets.common;
    exports gov.iti.jets.common.dto;
    exports gov.iti.jets.common.dto.registration;

    exports gov.iti.jets.common.util;
    exports gov.iti.jets.common.network.client;
    exports gov.iti.jets.common.network.server;
    exports gov.iti.jets.common.dto.Interfaces;
}
