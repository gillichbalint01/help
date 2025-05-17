import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

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

    public Main() {

        // 0. feladat (2p)
        betolt("utazok.csv");
        System.out.printf("0) %d utazó adata beolvasva\n", utazok.size());

        // 1. feladat (1p + 1p + 1p)
        ArrayList<String> varosok = new ArrayList<>();
        for (Utazo u : utazok) if (!varosok.contains(u.varos)) varosok.add(u.varos);
        System.out.printf("1) Összesen %d városba utaztak\n", varosok.size());
        int v = (int)(Math.random()*varosok.size());
        System.out.printf("   Közülük egy véletlenszerűen kiválasztott: %s\n", varosok.get(v));
        int db = 0; for (Utazo u : utazok) if (u.varos.equals(varosok.get(v))) db++;
        System.out.printf("   Ebbe a városba %d utazó érkezett\n", db);

        // 2. feladat (1p + 1p)
        Utazo minIdo = utazok.get(0);
        int delelott = 0;
        for (Utazo u : utazok) {
            if (u.ido.compareTo(minIdo.ido) < 0) minIdo = u;
            if (u.ido.compareTo("12:00") < 0) delelott++;
        }
        System.out.printf("2) Legkorábbi indulás: %s\n", minIdo.ido);
        System.out.printf("   Összesen %d utazás kezdődött délelőtt\n", delelott);

        // 3. feladat (2p)
        TreeMap<Integer, Integer> hostat = new TreeMap<>();
        for (Utazo u : utazok) {
            int ho = Integer.parseInt(u.datum.substring(0,2));
            if (!hostat.containsKey(ho)) hostat.put(ho, 1); else hostat.put(ho, hostat.get(ho)+1);
        }
        System.out.printf("3) Utazások száma hónaponként:\n");
        for (Integer ho : hostat.keySet()) {
            System.out.printf("   %02d.hó : %d utazás\n", ho, hostat.get(ho));
        }

        // 4. feladat (2p)
        TreeMap<String, Integer> nevek = new TreeMap<>();
        for (Utazo u : utazok) {
            String nev = u.nev.split(" ")[0];
            if (!nevek.containsKey(nev)) nevek.put(nev, 1); else nevek.put(nev, nevek.get(nev)+1);
        }
        System.out.printf("4) Többször szereplő vezetéknevek:\n   ");
        for (String nev : nevek.keySet()) {
            if (nevek.get(nev) > 1) System.out.printf("%s ", nev);
        }
        System.out.printf("\n");

        // 5. feladat
        TreeMap<String, Integer> napok = new TreeMap<>();
        for (Utazo u : utazok) {
            if (!napok.containsKey(u.datum)) napok.put(u.datum, 1); else napok.put(u.datum, napok.get(u.datum)+1);
        }
        System.out.printf("5) Ugyanazon a napon kettőnél több utazás: ");
        for (String nap : napok.keySet()) {
            if (napok.get(nap) > 2) System.out.printf("%s(%d) ", nap, napok.get(nap));
        }
        System.out.printf("\n");

        // 6. feladat
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("szeged.txt"), "utf-8");
            for (Utazo u : utazok) {
                if (u.varos.equals("Szeged")) ki.printf("%s (%s %s)\r\n", u.nev, u.datum, u.ido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
        System.out.printf("6) Szegedre utazók kiírva a szeged.txt fájlba\n");

    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            while (be.hasNextLine()) utazok.add(new Utazo(be.nextLine()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }
    }

    public static void main(String[] args) {
	    new Main();
    }
}
