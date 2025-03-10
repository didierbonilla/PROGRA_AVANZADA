module programacion.avanzada.programacion_avanzada_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;

    requires jackson.annotations;
    requires jackson.core;
    requires jackson.databind;

    opens programacion.avanzada.programacion_avanzada_project to javafx.fxml;
    exports programacion.avanzada.programacion_avanzada_project;
}