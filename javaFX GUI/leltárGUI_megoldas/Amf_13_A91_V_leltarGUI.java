package com.example.leltargui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class HelloController {

    @FXML private ListView<String> lsLista;
    @FXML private ComboBox<String> cmEv;
    @FXML private ListView<String> lsEv;
    @FXML private Label lbInfo;

    private class Eszkoz {
        public String nev;
        public int ev;
        public int darab;
        public int ar;

        public Eszkoz(String sor) {
            String [] s = sor.split(";");
            nev = s[0];
            ev = Integer.parseInt(s[1]);
            darab = Integer.parseInt(s[2]);
            ar = Integer.parseInt(s[3]);
        }
    }

    private ArrayList<Eszkoz> eszkozok = new ArrayList<>();

    private FileChooser fc = new FileChooser();

    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
    }

    @FXML private void onMegnyitasClick() {
        File fbe = fc.showOpenDialog(lsLista.getScene().getWindow());
        if (fbe != null) {
            betolt(fbe);
            cmEv.setDisable(false);
            lsLista.getItems().clear(); cmEv.getItems().clear();
            TreeSet<Integer> evek = new TreeSet<>();
            for (Eszkoz e : eszkozok) {
                lsLista.getItems().add(String.format("%d: %s (%d x %,d,-Ft)", e.ev, e.nev, e.darab, e.ar));
                evek.add(e.ev);
            }
            for (Integer ev : evek) cmEv.getItems().add(ev+ " adatai:");
            cmEv.getSelectionModel().select(0);
            onEvSelect();
        }
    }

    @FXML private void onEvSelect() {
        int ev = Integer.parseInt(cmEv.getSelectionModel().getSelectedItem().split(" ")[0]);
        lsEv.getItems().clear(); int darab = 0; int szumAr = 0;
        for (Eszkoz e : eszkozok) {
            if (e.ev == ev) {
                lsEv.getItems().add(String.format("%d x %s = %,d,-Ft", e.darab, e.nev, e.darab*e.ar));
                darab++; szumAr += e.darab*e.ar;
            }
        }
        lbInfo.setText(String.format("%d darab / %,d,-Ft", darab, szumAr));
    }

    @FXML private void onKilepesClick() {
        Platform.exit();
    }

    @FXML private void onNevjegyClick() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Leltár v1.0.0\n(C) Kandó");
        info.showAndWait();
    }

    private void betolt(File fajl) {
        Scanner be = null;
        try {
            be = new Scanner(fajl, "utf-8");
            be.nextLine();
            while (be.hasNextLine()) eszkozok.add(new Eszkoz(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }

}