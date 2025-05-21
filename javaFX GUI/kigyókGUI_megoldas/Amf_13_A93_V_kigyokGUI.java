package com.example.kigyokgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloController {

    @FXML private ListView<String> lsKigyok;
    @FXML private ListView<String> lsSzures;
    @FXML private TextField txSzures;

    private class Kigyo {
        public String fajta;
        public int hossz;
        public String foldresz;
        public boolean merges;

        public Kigyo(String sor) {
            String[] s = sor.split(";");
            fajta = s[0];
            hossz = Integer.parseInt(s[1]);
            foldresz = s[2];
            merges = s[3].equals("Igen");
        }
    }

    private ArrayList<Kigyo> kigyok = new ArrayList<>();
    private FileChooser fc = new FileChooser();

    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
    }

    @FXML private void onMegnyitasClick() {
        File fbe = fc.showOpenDialog(lsKigyok.getScene().getWindow());
        if (fbe != null) {
            kigyok.clear(); lsKigyok.getItems().clear();
            betolt(fbe);
            for (Kigyo k : kigyok) lsKigyok.getItems().add(String.format("%s (%dcm), %s", k.fajta, k.hossz, k.foldresz));
        }
    }

    private void betolt(File fajl) {
        Scanner be = null;
        try {
            be = new Scanner(fajl, "utf-8");
            be.nextLine();
            while (be.hasNextLine()) kigyok.add(new Kigyo(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }

    @FXML private void onSzuresClick() {
        lsSzures.getItems().clear();
        String szuro = txSzures.getText().toLowerCase();
        for (Kigyo k : kigyok) if (k.fajta.toLowerCase().contains(szuro)) lsSzures.getItems().add(k.fajta);
    }

    @FXML private void onKilepesClick() {
        Platform.exit();
    }

    @FXML private void onNevjegyClick() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Kígyók v1.0.0\n(C) Kandó");
        info.showAndWait();
    }

}