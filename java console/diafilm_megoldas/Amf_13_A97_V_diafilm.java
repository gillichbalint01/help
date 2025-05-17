import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

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

    public Main() {

        // 0. feladat (2p + 1p)
        betolt("diafilm.csv");
        System.out.printf("0) %d diafilm adata beolvasva\n", filmek.size());
        int ff = 0; for (Film f : filmek) if (f.szines == 'N') ff++;
        System.out.printf("   Közülük %d még fekete-fehér\n", ff);

        // 1. feladat (1p + 1p + 1p)
        Film legregebbi = filmek.get(0);
        for (Film f : filmek) if (f.ev < legregebbi.ev) legregebbi = f;
        System.out.printf("1) A legrégebbi diafilm: %s (%d)\n", legregebbi.cim, legregebbi.ev);
        System.out.printf("   De ugyanebben az évben készült még:\n");
        for (Film f : filmek) if (f.ev == legregebbi.ev && !f.cim.equals(legregebbi.cim)) System.out.printf("   - %s (%d)\n", f.cim, f.ev);

        // 2. feladat (1p + 1p)
        int szumElott = 0, szumUtan = 0, dbElott = 0, dbUtan = 0;
        for (Film f : filmek) if (f.ev < 2000) { szumElott += f.kocka; dbElott++; } else { szumUtan += f.kocka; dbUtan++; }
        System.out.printf("2) A 2000 előtt készült diafilmek átlagos kockaszáma: %.1f\n", (float)szumElott/dbElott);
        System.out.printf("   A később készült diafilmeknél az áltag: %.1f\n", (float)szumUtan/dbUtan);

        // 3. feladat (2p)
        TreeMap<Integer, Integer> evDarab = new TreeMap<>();
        for (Film f : filmek) {
            int evtized = f.ev / 10;
            if (!evDarab.containsKey(evtized)) evDarab.put(evtized, 1); else evDarab.put(evtized, evDarab.get(evtized)+1);
        }
        System.out.printf("3) Az egyes évtizedekben készült diafilmek száma:\n");
        for (Integer evtized : evDarab.keySet()) {
            System.out.printf("   %d0-%d9 : %d darab\n", evtized, evtized, evDarab.get(evtized));
        }

        // 4. (2p + 1p)
        TreeMap<Integer, Integer> evKocka = new TreeMap<>();
        for (Film f : filmek) {
            if (!evKocka.containsKey(f.ev)) evKocka.put(f.ev, f.kocka); else evKocka.put(f.ev, evKocka.get(f.ev)+f.kocka);
        }
        int legtobbEv = filmek.get(0).ev;
        for (Integer ev : evKocka.keySet()) if (evKocka.get(ev) > evKocka.get(legtobbEv)) legtobbEv = ev;
        System.out.printf("4) A legtöbb kocka (%d db) készítésének éve: %d\n", evKocka.get(legtobbEv), legtobbEv);
        int masodikEv = legtobbEv; evKocka.put(legtobbEv, 0);
        for (Integer ev : evKocka.keySet()) if (evKocka.get(ev) > evKocka.get(masodikEv)) masodikEv = ev;
        System.out.printf("   A második legtöbb kocka (%d db) éve: %d\n", evKocka.get(masodikEv), masodikEv);

        // 5. (2p)
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("200x.txt"), "utf-8");
            for (Film f : filmek) if (f.ev / 10 == 200) ki.printf("%s;%d;%d;%c\r\n", f.cim, f.ev, f.kocka, f.szines);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
        System.out.printf("5) A 200x évben megjelent diák adatai elmentve (200x.txt)\n");

    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            be.nextLine();
            while (be.hasNextLine()) filmek.add(new Film(be.nextLine()));
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
