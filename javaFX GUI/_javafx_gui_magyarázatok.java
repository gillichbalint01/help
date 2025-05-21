package com.example.javaguihelp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    //filechooser
    /*
        private FileChooser fc = new FileChooser();
        Fájlok megnyitásánál menüablak

        fc.setInitialDirectory(new File("./"));
        Azt állítja be hol fog megnyílni alapból, itt pl a projekt mappáját fogja megnyitni
        Ha pl a letöltéseket akarjuk alapból megnyitni akkor a 'new File' után C:\Users\[User]\Downloads jön

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        Azt állítja be milyen típusú fájlokat fog látni, nem kötelező, ha nincs egy filter se akkor minden fájlt lát
        Egyszerre többet is lehet használni

        pl:
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Szöveges Fájlok", "*.txt"));
        Ez így csak .csv és .txt fáljokat fog látni

     */

    //Platform.exit()
    /*
        Ezzel tudjuk az ablakot bezárni és leállítani a programot
        pl:
        @FXML private void onKilepesClick() {
            Platform.exit();
        }
     */

    //Alert
    /*
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        Általában a névjegyhez kell használni de előfordul hogy hibaüzenethez is kellhet
        Több fajtája van azt az 'AlertType.' után kell írni:
            - INFORMATION
            - WARNING
            - ERROR
            - CONFIRMATION

        info.setTitle("Cím"); Az ablak tetején lévő keretben állítja a szöveget
        info.setHeaderText(null); Az ablakban állítja a címet
        info.setContentText("Projekt neve v1.0.0\n\nKandó 2025"); Az ablakban a kisebb szöveget állítja
        info.showAndWait(); Megjeleníti az ablakot

        pl: ÁllatokGUI

     */

    

}