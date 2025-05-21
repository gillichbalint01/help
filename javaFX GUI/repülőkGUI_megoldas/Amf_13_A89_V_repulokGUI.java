package com.example.repulokgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class HelloController {

    @FXML private ListView<String> lsGyartok;
    @FXML private ListView<String> lsTipusok;

    private class Repulo {
        public String tipus;
        public float hossz;
        public int suly;
        public int ferohely;
        public int tank;

        public Repulo(String sor) {
            String[] s = sor.split(";");
            tipus = s[0];
            hossz = Float.parseFloat(s[1]);
            suly = Integer.parseInt(s[2]);
            ferohely = Integer.parseInt(s[3]);
            tank = Integer.parseInt(s[4]);
        }
    }

    private ArrayList<Repulo> repulok = new ArrayList<>();
    private FileChooser fc = new FileChooser();

    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
    }

    @FXML private void onMegnyitasClick() {
        File fbe = fc.showOpenDialog(lsGyartok.getScene().getWindow());
        if (fbe != null) {
            betolt(fbe);
            lsGyartok.getItems().clear();
            TreeSet<String> gyartok = new TreeSet<>();
            for (Repulo repulo : repulok) gyartok.add(repulo.tipus.split(" ")[0]);
            for (String gyarto : gyartok) lsGyartok.getItems().add(gyarto);
            lsGyartok.getSelectionModel().select(0);
            onGyartoPressed();
        }
    }

    @FXML private void onGyartoPressed() {
        int i = lsGyartok.getSelectionModel().getSelectedIndex();
        if (i != -1) {
            String gyarto = lsGyartok.getSelectionModel().getSelectedItem();
            lsTipusok.getItems().clear();
            for (Repulo repulo : repulok) {
                if (repulo.tipus.split(" ")[0].equals(gyarto)) lsTipusok.getItems().add(repulo.tipus);
            }
        }
    }

    @FXML private void onKilepesClick() {
        Platform.exit();
    }

    @FXML private void onNevjegyClick() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Repülők v1.0.0\n(C) Kandó");
        info.showAndWait();
    }

    private void betolt(File fajl) {
        Scanner be = null;
        repulok.clear();
        try {
            be = new Scanner(fajl, "utf-8");
            be.nextLine();
            while (be.hasNextLine()) repulok.add(new Repulo(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }

}