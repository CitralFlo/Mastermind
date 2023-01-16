module me.citralflo.mastermind {
    exports me.citralflo;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens me.citralflo.mastermind to javafx.fxml;
    exports me.citralflo.mastermind;
}