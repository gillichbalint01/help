import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

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

    public Main() {

        // 0. feladat (2p + 1p)
        betolt("leltar.csv");
        System.out.printf("0) A beolvasott leltári tételek száma: %d\n", eszkozok.size());
        int szumAr = 0; for (Eszkoz e : eszkozok) szumAr += e.darab * e.ar;
        System.out.printf("   A benne lévő eszközök összára: %,d,-Ft\n", szumAr);

        // 1. feladat (1p)
        Eszkoz legdragabb = eszkozok.get(0);
        for (Eszkoz e : eszkozok) if (e.ar > legdragabb.ar) legdragabb = e;
        System.out.printf("1) A legdrágább eszköz: %s (%,d,-Ft)\n", legdragabb.nev, legdragabb.ar);

        // 2. feladat (1p)
        int minEv = eszkozok.get(0).ev;
        int maxEv = eszkozok.get(0).ev;
        for (Eszkoz e : eszkozok) {
            if (e.ev < minEv) minEv = e.ev; else if (e.ev > maxEv) maxEv = e.ev;
        }
        System.out.printf("2) A leltár a %d-%d éveket tartalmazza\n", minEv, maxEv);

        // 3. feladat (2p+1p)
        TreeMap<Integer, Integer> darab = new TreeMap<>();
        for (int ev=minEv; ev<=maxEv; ev++) darab.put(ev,0);
        for (Eszkoz e : eszkozok) darab.put(e.ev, darab.get(e.ev)+e.darab);
        System.out.printf("3) Az egyes években vásárolt eszközök darabszáma:\n");
        for (int ev : darab.keySet()) {
            if (darab.get(ev) != 0) System.out.printf("   %d : %d darab eszköz\n", ev, darab.get(ev));
            else System.out.printf("   %d : Nincs eszköz\n", ev);
        }

        // 4. feladat (2p+1p+1p)
        TreeMap<Integer, Integer> ertek = new TreeMap<>();
        for (Eszkoz e : eszkozok) {
            if (!ertek.containsKey(e.ev)) ertek.put(e.ev, e.darab*e.ar);
            else ertek.put(e.ev, ertek.get(e.ev)+e.darab*e.ar);
        }
        int maxErtekEv = minEv;
        for (int ev : ertek.keySet()) if (ertek.get(ev) > ertek.get(maxErtekEv)) maxErtekEv = ev;
        System.out.printf("4) A legnagyobb összértékű beszerzés éve: %d\n", maxErtekEv);
        System.out.printf("   Ekkor a beszerzés összértéke: %,d,-Ft\n", ertek.get(maxErtekEv));

        // 5. feladat (1p)
        Eszkoz maxErtek = eszkozok.get(0);
        for (Eszkoz e : eszkozok) {
            if (e.darab*e.ar > maxErtek.darab*maxErtek.ar) maxErtek = e;
        }
        System.out.printf("5) A legnagyobb értékű beszerzés:\n   %d darab %s = %,d,-Ft\n", maxErtek.darab, maxErtek.nev, maxErtek.darab*maxErtek.ar);

        // 6. feladat (2p)
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("kezdes.txt"), "utf-8");
            for (Eszkoz e : eszkozok) {
                if (e.ev == minEv) ki.printf("%d x %s = %d,-Ft\r\n", e.darab, e.nev, e.darab*e.ar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
        System.out.printf("6) Az első év adatai kiírva a kezdes.txt fájlba\n");

    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            be.nextLine();
            while (be.hasNextLine()) eszkozok.add(new Eszkoz(be.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (be != null) be.close();
        }

    }

    public static void main(String[] args) {
	    new Main();
    }
}
