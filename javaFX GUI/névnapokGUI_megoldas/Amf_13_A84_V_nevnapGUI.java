package com.example.nevnapgui;

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
import java.util.TreeMap;

public class HelloController {

    @FXML private ComboBox<String> cmHonap;
    @FXML private ListView<String> lsLista;
    private class Nev {
        public String nev;
        public ArrayList<String> napok;

        public Nev(String sor) {
            String[] s = sor.split(";");
            nev = s[0];
            napok = new ArrayList<>();
            for (int i=1; i<s.length; i++) napok.add(s[i]);
        }
    }

    private ArrayList<Nev> nevek = new ArrayList<>();
    private FileChooser fc = new FileChooser();

    String[] honev = { "", "Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December" };

    public void initialize() {
        for (int i=1; i<=12; i++) cmHonap.getItems().add(honev[i]);
        cmHonap.getSelectionModel().select(0);
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
    }

    @FXML private void szures() {
        int h = cmHonap.getSelectionModel().getSelectedIndex();
        if (h != -1) {
            lsLista.getItems().clear();
            String hs = String.format("%02d", h+1);
            TreeMap<String, String> napMap = new TreeMap<>();
            for (Nev nev : nevek) {
                for (String nap : nev.napok) {
                    if (nap.substring(0,2).equals(hs)) {
                        if (!napMap.containsKey(nap)) napMap.put(nap, nev.nev);
                        else napMap.put(nap, napMap.get(nap) + ", " + nev.nev);
                    }
                }
            }
            for (String nap : napMap.keySet()) lsLista.getItems().add(nap + ":  " + napMap.get(nap));
        }
    }

    @FXML private void onMegnyitasClick() {
        File fbe = fc.showOpenDialog(cmHonap.getScene().getWindow());
        if (fbe != null) {
            betolt(fbe);
            cmHonap.setDisable(false); szures();
        }
    }

    private void betolt(File fajl) {
        Scanner be = null;
        try {
            be = new Scanner(fajl, "utf-8");
            while (be.hasNextLine()) nevek.add(new Nev(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }

    @FXML private void onNevjegyClick() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Névnap v1.0\n(C) Kandó");
        info.showAndWait();
    }

    @FXML private void onKilepesClick() {
        Platform.exit();
    }

}