package com.example.allatokgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloController {

    @FXML private ListView<String> lsLista;
    @FXML private TextField txMin;
    @FXML private TextField txMax;
    @FXML private Button btSzures;

    private class Allat {
        public String fajta;
        public int magas;
        public int suly;
        public int kor;

        public Allat(String sor) {
            String[] s = sor.split(";");
            fajta = s[0];
            magas = Integer.parseInt(s[1]);
            suly = Integer.parseInt(s[2]);
            kor = Integer.parseInt(s[3]);
        }

        public String getInfo() {
            return fajta + " (" + magas + "cm , " + suly + "kg)";
        }
    }

    private ArrayList<Allat> allatok = new ArrayList<>();

    private FileChooser fc = new FileChooser();

    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
    }

    @FXML private void onMegnyitasClick() {
        File fbe = fc.showOpenDialog(lsLista.getScene().getWindow());
        if (fbe != null) { betolt(fbe); listazMind(); btSzures.setDisable(false); }
    }

    private void betolt(File fbe) {
        Scanner be = null;
        try {
            be = new Scanner(fbe, "utf-8");
            be.nextLine();
            while (be.hasNextLine()) allatok.add(new Allat(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }

    private void listazMind() {
        lsLista.getItems().clear();
        for (Allat a : allatok) lsLista.getItems().add(a.getInfo());
    }

    @FXML private void onSzuresClick() {
        lsLista.getItems().clear();
        int min = Integer.parseInt(txMin.getText());
        int max = Integer.parseInt(txMax.getText());
        for (Allat a : allatok) {
            if (a.magas >= min && a.magas <= max) lsLista.getItems().add(a.getInfo());
        }
        if (lsLista.getItems().size() == 0) lsLista.getItems().add("Nincs a szűrésnek megfelelő állatfajta!");
    }

    @FXML private void onKilepesClick() {
        Platform.exit();
    }

    @FXML private void onNevjegyClick() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Állatok");
        info.setHeaderText(null);
        info.setContentText("Állatok v1.0.0\n\nKandó 2025");
        info.showAndWait();
    }
}