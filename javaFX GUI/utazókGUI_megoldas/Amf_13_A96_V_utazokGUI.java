package com.example.utazokgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class HelloController {

    @FXML private ListView<String> lsVaros;
    @FXML private ListView<String> lsUtazas;

    private class Utazo {
        public String nev;
        public String varos;
        public String datum;
        public String ido;

        public Utazo(String sor) {
            String[] s = sor.split(";");
            nev = s[0];
            varos = s[1];
            datum = s[2];
            ido = s[3];
        }
    }

    private ArrayList<Utazo> utazok = new ArrayList<>();
    private FileChooser fc = new FileChooser();

    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
    }

    @FXML private void onMegnyitasClick() {
        File fbe = fc.showOpenDialog(lsVaros.getScene().getWindow());
        if (fbe != null) {
            utazok.clear(); lsVaros.getItems().clear(); lsUtazas.getItems().clear();
            betolt(fbe);
            TreeSet<String> varosok = new TreeSet<>();
            for (Utazo u : utazok) varosok.add(u.varos);
            for (String varos : varosok) lsVaros.getItems().add(varos);
            lsVaros.getSelectionModel().select(0);
            onVarosPressed();
        }
    }

    @FXML private void onVarosPressed() {
        int i = lsVaros.getSelectionModel().getSelectedIndex();
        if (i != -1) {
            lsUtazas.getItems().clear();
            String varos = lsVaros.getSelectionModel().getSelectedItem();
            for (Utazo u : utazok) if (u.varos.equals(varos)) lsUtazas.getItems().add(String.format("%s (%s %s)", u.nev, u.datum, u.ido));
        }
    }

    private void betolt(File fajl) {
        Scanner be = null;
        try {
            be = new Scanner(fajl, "utf-8");
            while (be.hasNextLine()) utazok.add(new Utazo(be.nextLine()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }

    @FXML private void onNevjegyClick() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Utazók v1.0.0\n(C) Kandó");
        info.showAndWait();
    }

    @FXML private void onKilepesClick() {
        Platform.exit();
    }

}