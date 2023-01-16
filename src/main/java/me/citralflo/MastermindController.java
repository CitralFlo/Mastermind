package me.citralflo;

import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;


public class MastermindController {

    //Uzupełnienie choiceboxa
    @FXML
        public ChoiceBox<String> cbmPole1;
    @FXML
    public void initialize() {
        cbmPole1.getItems().addAll("Biały", "Czarny", "Czerwony", "Zielony", "Różowy");
    }


}
