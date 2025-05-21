package com.example.diafilmgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class HelloController {

    @FXML private ListView<String> lsLista;
    @FXML private ComboBox<Integer> cmEvek;
    @FXML private CheckBox cbFF;
    @FXML private CheckBox cbSZ;
    @FXML private Label lbDarab;

    private class Film {
        public String cim;
        public int ev;
        public int kocka;
        public char szines;

        public Film(String sor) {
            String[] s = sor.split(";");
            cim = s[0];
            ev = Integer.parseInt(s[1]);
            kocka = Integer.parseInt(s[2]);
            szines = s[3].charAt(0);
        }
    }

    private ArrayList<Film> filmek = new ArrayList<>();
    private FileChooser fc = new FileChooser();

    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
    }

    @FXML private void onMegnyitasClick() {
        File fbe = fc.showOpenDialog(lsLista.getScene().getWindow());
        if (fbe != null) {
            filmek.clear(); cmEvek.getItems().clear();
            betolt(fbe);
            TreeSet<Integer> evek = new TreeSet<>();
            for (Film f : filmek) evek.add(f.ev);
            for (Integer ev : evek) cmEvek.getItems().add(ev);
            cmEvek.getSelectionModel().select(0);
            onEvekChange();
        }
    }

    @FXML private void onEvekChange() {
        Integer ev = cmEvek.getSelectionModel().getSelectedItem();
        lsLista.getItems().clear();
        for (Film f : filmek) {
            if (f.ev == ev && ((f.szines == 'I' && cbSZ.isSelected()) || (f.szines == 'N' && cbFF.isSelected()))) {
                lsLista.getItems().add(String.format("%s (%d, %d kocka, %s)", f.cim, f.ev, f.kocka, (f.szines == 'I' ? "színes" : "fekete-fehér")));
            }
        }
        lbDarab.setText(lsLista.getItems().size() + " darab");
        if (lsLista.getItems().size() == 0) lsLista.getItems().add("Nincs ilyen diafilm...");
    }

    @FXML private void onKilepesClick() {
        Platform.exit();
    }

    @FXML private void onNevjegyClick() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Diafilm v1.0.0\n(C) Kandó");
        info.showAndWait();
    }

    private void betolt(File fajl) {
        Scanner be = null;
        try {
            be = new Scanner(fajl, "utf-8");
            be.nextLine();
            while (be.hasNextLine()) filmek.add(new Film(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }
}