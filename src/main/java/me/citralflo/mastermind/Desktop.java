package me.citralflo.mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Desktop extends Application {

    private final BorderPane panel = new BorderPane();
    private final GridPane pnlWybor = new GridPane();
    private final GridPane pnlNazwy = new GridPane();

    private final TextField nazwagracz = new TextField();

    private final MastermindKod kod = new MastermindKod(4);

    private final MenuBar pasekMenu = new MenuBar();
    private final ToolBar pasekNarzedzia = new ToolBar();
    private final ContextMenu menuPodreczne = new ContextMenu();

    private final StringBuilder fieldContent = new StringBuilder();
    private final Button btnZgadnij = new Button("Zgadnij");
    private final Button btnZapisz = new Button("Zapisz");

    private final TextArea txtWyniki = new TextArea();

    private int probaNr = 0;

    private void zrobMenu() {
        Menu menuOpcje = new Menu("Opcje");

        MenuItem mitNowa = new MenuItem("Nowa gra");
        mitNowa.setOnAction(wydarzenie -> {

            Alert dialog = new Alert(AlertType.CONFIRMATION);

            dialog.setTitle("Losowanie");
            dialog.setHeaderText("Czy Chcesz zacząć nową grę?");

            Optional<ButtonType> wynik = dialog.showAndWait();

            if (wynik.isEmpty()) {
                return;
            }

            if (wynik.get() == ButtonType.OK) Platform.runLater(() -> {
                try {
                    new Desktop().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        MenuItem mitWyniki = new MenuItem("Tablica wyników");
        SeparatorMenuItem mitSeparator = new SeparatorMenuItem();
        MenuItem mitKoniec = new MenuItem("Koniec");
        mitKoniec.setOnAction(wydarzenie -> {

            Alert dialog = new Alert(AlertType.CONFIRMATION);

            dialog.setTitle("Mastermind");
            dialog.setHeaderText("Czy potwierdzasz zakonczenie aplikacji?");

            Optional<ButtonType> wynik = dialog.showAndWait();

            if (wynik.isEmpty()) {
                return;
            }

            if (wynik.get() == ButtonType.OK) Platform.exit();
        });

        ObservableList<MenuItem> items = menuOpcje.getItems();

        items.add(mitNowa);
        items.add(mitWyniki);
        items.add(mitSeparator);
        items.add(mitKoniec);

        Menu menuPomoc = new Menu("Instrukcja");
        MenuItem mitPomoc = new MenuItem("Wyświetl");
        menuPomoc.getItems().add(mitPomoc);
        mitPomoc.setOnAction(wydarzenie -> {

            Alert dialog = new Alert(AlertType.CONFIRMATION);


            dialog.setTitle("Instrukcja");
            dialog.setHeaderText("Zasady gry:");
            dialog.setContentText("Celem rozgrywki jest odgadnięcie wygenerowanego przez komputer kodu.\n"
                    + "Następuje to poprzez wybór wartości kolorów dla czterech pól.\n"
                    + "Liczba prób: 10.\n"
                    + "Po każdej podjętej próbie gracz otrzymuje informację zwrotną o poprawnie dobranych wartościach oraz ich pozycji na planszy.\n"
                    + "Gra kończy się w momencie odganięcia kodu lub gdy skończą się pozostałe próby\n"
                    + "Minus oznacza, że dana liczba jest na niewłaściwym miejscu, ale znajduje się w kodzie\n"
                    + "Plus oznacza, że dana liczba znajduje się na właściwym miejscu w kodzie \n");

            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
        });

        //menu "O nas"
        Menu menuONas = new Menu("O autorach");
        MenuItem mitONas = new MenuItem("Wyświetl");
        menuONas.getItems().add(mitONas);
        mitONas.setOnAction(wydarzenie -> {

            Alert dialog = new Alert(AlertType.CONFIRMATION);


            dialog.setAlertType(AlertType.INFORMATION);
            dialog.setTitle("O autorach");
            dialog.setContentText("Wprowadzenie: \n" +
                    "Jesteśmy studentami Politechniki Warszawskiej \n" +
                    "W ramach projektu zaliczeniowego \n" +
                    "Przygotowaliśmy program Mastermind");
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
        });

        pasekMenu.getMenus().add(menuOpcje);
        pasekMenu.getMenus().add(menuPomoc);
        pasekMenu.getMenus().add(menuONas);
    }

    public void zrobNarzedzia() {

        btnZgadnij.setTooltip(new Tooltip("Zatwierdz i sprawdz ostatnią sekwencję"));

        pasekNarzedzia.getItems().add(btnZgadnij);

        btnZapisz.setDisable(true);
        btnZapisz.setTooltip(new Tooltip("Zapisz wyniki w pliku tekstowym"));

        pasekNarzedzia.getItems().add(btnZapisz);


        //Zgadywanie i zczytywanie
        btnZgadnij.setOnAction(exit -> odczytaj());
        btnZapisz.setOnAction(actionEvent -> {
            String nazwa = nazwagracz.getText();

            kod.zapisz(probaNr, nazwa);
            btnZapisz.setDisable(true);
        });

    }

    ComboBox<String> cmbKolor1 = new ComboBox<>();
    ComboBox<String> cmbKolor2 = new ComboBox<>();
    ComboBox<String> cmbKolor3 = new ComboBox<>();
    ComboBox<String> cmbKolor4 = new ComboBox<>();

    public void odczytaj() {
        String wybor1 = "", wybor2 = "", wybor3 = "", wybor4 = "";
        int[] proba = new int[4];

        wybor1 += cmbKolor1.getSelectionModel().getSelectedItem();
        wybor2 += cmbKolor2.getSelectionModel().getSelectedItem();
        wybor3 += cmbKolor3.getSelectionModel().getSelectedItem();
        wybor4 += cmbKolor4.getSelectionModel().getSelectedItem();


        List<String> probaSlownie = new ArrayList<>(4);

        probaSlownie.add(wybor1);
        probaSlownie.add(wybor2);
        probaSlownie.add(wybor3);
        probaSlownie.add(wybor4);

        for (int i = 0; i < 4; i++) {
            switch (probaSlownie.get(i)) {
                case "Biały":
                    proba[i] = 1;
                    break;
                case "Żółty":
                    proba[i] = 2;
                    break;
                case "Czerwony":
                    proba[i] = 3;
                    break;
                case "Niebieski":
                    proba[i] = 4;
                    break;
                case "Zielony":
                    proba[i] = 5;
                    break;
                case "Czarny":
                    proba[i] = 6;
                    break;
                default:
                    break;
            }
        }
        kod.zapiszPodejscie(proba);

        //kod sprawdzenia i wyslania elementow na ekran:
        fieldContent.append("Proba #" + (probaNr + 1) + " wynik:  " + kod.sprawdzenie() + "\n");


        if (kod.finalsprawdzenie()) {
            // tu dodać kod co sie stanie jak wygrywasz:
            fieldContent.append("Wygrałeś !!!!" + "\n" + "Zachęcamy do kolejnej rozgrywki" + "\n");
            btnZgadnij.setDisable(true);
            btnZapisz.setDisable(false);

        }

        probaNr++;
        if (probaNr == 10) {
            //tu kod co jak ci sie proby skoncza:
            fieldContent.append("Życia ci się skończyły ;c \n" + "Spróbuj ponownie!");
            btnZgadnij.setDisable(true);

        }
        txtWyniki.setText(fieldContent.toString());
    }

    private void zrobMenuPodreczne(Stage primaryStage) {
        MenuItem mitInstrukcja = new MenuItem("Instrukcja");
        mitInstrukcja.setOnAction(exit -> {

            Alert dialog = new Alert(AlertType.CONFIRMATION);

            dialog.setTitle("Mastermind");
            dialog.setHeaderText("Instrukcja do gry:");
            dialog.setContentText("Celem rozgrywki jest odgadnięcie wygenerowanego przez komputer kodu.\n"
                    + "Następuje to poprzez wybór wartości kolorów dla czterech pól.\n"
                    + "Liczba prób: 10.\n"
                    + "Po każdej podjętej próbie gracz otrzymuje informację zwrotną o poprawnie dobranych wartościach oraz ich pozycji na planszy.\n"
                    + "Gra kończy się w momencie odganięcia kodu lub gdy skończą się pozostałe próby\n");
            dialog.showAndWait();
        });

        SeparatorMenuItem mitSeparator = new SeparatorMenuItem();

        MenuItem mitKoniec = new MenuItem("Koniec");
        mitKoniec.setOnAction(wydarzenie -> {

            Alert dialog = new Alert(AlertType.CONFIRMATION);

            dialog.setTitle("Mastermind");
            dialog.setHeaderText("Czy potwierdzasz zakonczenie aplikacji?");

            Optional<ButtonType> wynik = dialog.showAndWait();

            if (wynik.isEmpty()) {
                return;
            }

            if (wynik.get() == ButtonType.OK) Platform.exit();
        });

        menuPodreczne.getItems().add(mitInstrukcja);
        menuPodreczne.getItems().add(mitSeparator);
        menuPodreczne.getItems().add(mitKoniec);

        panel.setOnContextMenuRequested(event -> menuPodreczne.show(primaryStage, event.getScreenX(), event.getScreenY()));

    }


    public void zrobPanelNazwy() {

        pnlNazwy.setVgap(15);
        pnlNazwy.setHgap(15);
        pnlNazwy.setPadding(new Insets(20, 20, 20, 20));
        Label lblNazwa = new Label("Podaj nazwę gracza");
        HBox nazwa = new HBox();
        nazwa.setSpacing(20);
        nazwa.setAlignment(Pos.CENTER);
        nazwa.getChildren().addAll(lblNazwa, nazwagracz);
        pnlNazwy.getChildren().addAll(nazwa);
    }

    public void zrobPanelWybor() {


        pnlWybor.setVgap(15);
        pnlWybor.setHgap(15);
        pnlWybor.setPadding(new Insets(20, 20, 20, 20));


        String[] kolory = { "Biały", "Żółty", "Czerwony", "Niebieski", "Zielony", "Czarny" };


        cmbKolor1.setEditable(false);
        cmbKolor1.getItems().addAll(kolory);
        cmbKolor1.setMaxWidth(100);
        cmbKolor1.getSelectionModel().select(0);


        cmbKolor2.setEditable(false);
        cmbKolor2.getItems().addAll(kolory);
        cmbKolor2.getSelectionModel().select(0);
        cmbKolor2.setMaxWidth(100);


        cmbKolor3.setEditable(false);
        cmbKolor3.getItems().addAll(kolory);
        cmbKolor3.getSelectionModel().select(0);
        cmbKolor3.setMaxWidth(100);


        cmbKolor4.setEditable(false);
        cmbKolor4.getItems().addAll(kolory);
        cmbKolor4.getSelectionModel().select(0);
        cmbKolor4.setMaxWidth(100);
        registerListener(cmbKolor1);
        registerListener(cmbKolor2);
        registerListener(cmbKolor3);
        registerListener(cmbKolor4);

        final HBox ustaw = new HBox();
        ustaw.setSpacing(20);
        ustaw.setAlignment(Pos.CENTER);
        ustaw.setId("ustaw");
        ustaw.getChildren().addAll(cmbKolor1, cmbKolor2, cmbKolor3, cmbKolor4);


        pnlWybor.getChildren().addAll(ustaw);

    }

    private void registerListener(ComboBox<String> cmbKolor1) {
        cmbKolor1.valueProperty().addListener((ov, oldValue, newValue) -> {
            // Perform actions when the value changes
        });
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gra Mastermind");

        zrobMenu();

        zrobNarzedzia();

        zrobMenuPodreczne(primaryStage);

        zrobPanelWybor();
        zrobPanelNazwy();

        panel.setTop(new VBox(pasekMenu, pasekNarzedzia));
        panel.setLeft(pnlWybor);
        panel.setBottom(pnlNazwy);
        panel.setRight(txtWyniki);

        pnlWybor.setStyle("-fx-background-color: silver;");
        pnlWybor.setMinHeight(120);
        pnlWybor.setMinWidth(400);

        pnlNazwy.setStyle("-fx-background-color: silver;");
        pnlNazwy.setMinHeight(120);
        pnlNazwy.setMinWidth(400);


        txtWyniki.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        txtWyniki.setMinHeight(240);
        txtWyniki.setMaxWidth(400);
        txtWyniki.setEditable(false);

        Scene scenka = new Scene(panel, 800, 400);

        primaryStage.setScene(scenka);
        primaryStage.show();
        //generacja kodu

        kod.generuj();
        kod.pokaz();
        //przejscie przez proby


    }

    public static void main(String[] args) {
        Application.launch(args);


    }

}
