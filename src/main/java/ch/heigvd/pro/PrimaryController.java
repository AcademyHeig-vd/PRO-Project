package ch.heigvd.pro;

import javafx.fxml.FXML;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private void switchToPeriode() throws IOException {
        Tempus.setRoot("periodeAdd");
    }

    @FXML
    private void switchToRappel() throws IOException {
        Tempus.setRoot("rappelAdd");
    }

    @FXML
    private void switchToProf() throws IOException {
        Tempus.setRoot("profAdd");
    }
}
