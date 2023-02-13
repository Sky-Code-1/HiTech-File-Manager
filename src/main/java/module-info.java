module org.hitech.hitechfilemanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hitech.hitechfilemanager to javafx.fxml;
    exports org.hitech.hitechfilemanager;
}