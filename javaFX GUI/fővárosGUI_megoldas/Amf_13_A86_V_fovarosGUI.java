package com.example.fovarosgui;

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

    @FXML private ListView<String> lsLista;
    @FXML private TextField txFovaros;
    @FXML private TextField txFLakos;

    private class Orszag {
        public String orszag;
        public String jel;
        public int oLakos;
        public String fovaros;
        public int fLakos;

        public Orszag(String sor) {
            String[] s = sor.split(";");
            orszag = s[0];
            jel = s[1];
            oLakos = Integer.parseInt(s[2]);
            fovaros = s[3];
            fLakos = Integer.parseInt(s[4]);
        }
    }

    private ArrayList<Orszag> orszagok = new ArrayList<>();
    private FileChooser fc = new FileChooser();

    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
    }

    @FXML private void onListaPressed() {
        int i = lsLista.getSelectionModel().getSelectedIndex();
        if (i != -1) {
            txFovaros.setText(orszagok.get(i).fovaros);
            txFLakos.setText(String.format("%,d fő",orszagok.get(i).fLakos));
        }
    }

    @FXML private void onMegnyitasClick() {
        File fbe = fc.showOpenDialog(lsLista.getScene().getWindow());
        if (fbe != null) {
            orszagok.clear();
            lsLista.getItems().clear();
            betolt(fbe);
            for (Orszag orszag : orszagok) lsLista.getItems().add(String.format("%s (%,d fő): %s", orszag.orszag, orszag.oLakos, orszag.jel));
            lsLista.getSelectionModel().select(0);
        }
    }

    @FXML private void onKilepesClick() {
        Platform.exit();
    }

    @FXML private void onNevjegyClick() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Fővárosok v1.0.0\n(C) Kandó");
        info.showAndWait();
    }

    private void betolt(File fajl) {
        Scanner be = null;
        try {
            be = new Scanner(fajl, "utf-8");
            be.nextLine();
            while (be.hasNextLine()) orszagok.add(new Orszag(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }
}