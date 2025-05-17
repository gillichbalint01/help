import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

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

    public Main() {
        // 0. feladat
        betolt("nevnap.csv");
        System.out.printf("0) Összesen %d név beolvasva.\n", nevek.size());
        System.out.printf("   A tizedik név %s, akinek %d névnapja van.\n", nevek.get(9).nev, nevek.get(9).napok.size());

        // 1. feladat
        int i=0; while (i<nevek.size() && !nevek.get(i).nev.equals("János")) i++;
        System.out.printf("1) János névnapjai: ");
        System.out.printf("%s\n", String.join(", ", nevek.get(i).napok));

        // 2. feladat
        System.out.printf("2) Április elsején van névnapja: ");
        for (Nev nev : nevek) {
            for (String nap : nev.napok) {
                if (nap.equals("0401")) System.out.printf("%s ", nev.nev);
            }
        }
        System.out.printf("\n");

        // 3. feladat
        Nev max = nevek.get(0);
        for (Nev nev : nevek) if (nev.napok.size() > max.napok.size()) max = nev;
        System.out.printf("3) Legtöbb névnapja (%d) %s nevűeknek van!\n", max.napok.size(), max.nev);
        System.out.printf("   De ugyanennyi névnapja van még: ");
        for (Nev nev : nevek) if (nev.napok.size() == max.napok.size() && !nev.nev.equals(max.nev)) System.out.printf("%s ", nev.nev);
        System.out.printf("\n");

        // 4. feladat
        int db = 0; for (Nev nev : nevek) db += nev.napok.size();
        System.out.printf("4) Összesen %d nap van a nevekhez rendelve.\n", db);

        // 5. feladat
        TreeMap<String, Integer> honap = new TreeMap<>();
        for (Nev nev : nevek) {
            for (String nap : nev.napok) {
                String ho = nap.substring(0,2);
                if (!honap.containsKey(ho)) honap.put(ho, 1);
                else honap.put(ho, honap.get(ho)+1);
            }
        }
        String[] honev = { "", "Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December" };
        System.out.printf("5) Az egyes hónapokban tartott névnapok száma:\n");
        for (String ho : honap.keySet()) {
            System.out.printf("   %s: %d\n", honev[Integer.parseInt(ho)], honap.get(ho));
        }

        // 6. feladat
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("elseje.txt"), "utf-8");
            for (Nev nev : nevek) {
                for (String nap : nev.napok) {
                    if (nap.substring(2,4).equals("01")) ki.printf("%s: %s\r\n", nap, nev.nev);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
        System.out.printf("6) Elsejei névnapok kiírva az elseje.txt fájlba.\n");
    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            while (be.hasNextLine()) nevek.add(new Nev(be.nextLine()));
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
