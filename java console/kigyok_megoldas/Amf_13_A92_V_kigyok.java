import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

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

    public Main() {

        // 0. feladat (2p + 1p)
        betolt("kigyok.csv");
        System.out.printf("0) Összesen %d kígyó adata beolvasva\n", kigyok.size());
        int mergesDb = 0; for (Kigyo k : kigyok) if (k.merges) mergesDb++;
        System.out.printf("   Közülük %d mérges és %d nem mérges\n", mergesDb, kigyok.size()-mergesDb);

        // 1. feladat (1p)
        int szumHossz = 0;
        for (Kigyo k : kigyok) szumHossz += k.hossz;
        System.out.printf("1) A kígyók teljes hossza méterben: %.2fm\n", szumHossz/100.0);

        // 2. feladat (2p)
        Kigyo lhMerges = null; int i=0; do { lhMerges = kigyok.get(i); } while (!lhMerges.merges);
        for (Kigyo k : kigyok) if (k.merges && k.hossz > lhMerges.hossz) lhMerges = k;
        System.out.printf("2) A leghosszabb mérges kígyó: %s (%dcm)\n", lhMerges.fajta, lhMerges.hossz);

        // 3. feladat (1p + 1p)
        TreeSet<String> foldreszek = new TreeSet<>();
        for (Kigyo k : kigyok) foldreszek.add(k.foldresz);
        System.out.printf("3) A kígyók származási helye (abc): %s\n", String.join(", ", foldreszek));

        // 4. feladat (1p + 1p)
        int v = 0; do { v = (int)(Math.random()*kigyok.size()); } while (!kigyok.get(v).merges);
        System.out.printf("4) Egy véletlen kiválasztott mérges kígyó: %s\n", kigyok.get(v).fajta);
        System.out.printf("   Származási helye %s, hossza %dcm\n", kigyok.get(v).foldresz, kigyok.get(v).hossz);

        // 5. feladat (2p)
        TreeMap<String, Integer> fajtak = new TreeMap<>();
        for (Kigyo k : kigyok) {
            String fajta = k.fajta.split(" ")[k.fajta.split(" ").length-1];
            if (!fajtak.containsKey(fajta)) fajtak.put(fajta, 1);
            else fajtak.put(fajta, fajtak.get(fajta)+1);
        }
        System.out.printf("5) Adott fajhoz (abc) tartozó kígyók darabszáma:\n");
        for (String fajta : fajtak.keySet()) {
            System.out.printf("   %s : %d féle\n", fajta, fajtak.get(fajta));
        }

        // 6. feladat (1p)
        Kigyo uMamba = kigyok.get(0);
        for (Kigyo k : kigyok) if (k.fajta.contains("Mamba")) uMamba = k;
        System.out.printf("6) Az utolsó Mamba fajtája: %s\n", uMamba.fajta);

        // 7. feladat (2p)
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("kobra.txt"), "utf-8");
            for (Kigyo k : kigyok) {
                if (k.fajta.contains("Kobra")) ki.printf("%s (%dcm)\r\n", k.fajta, k.hossz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
        System.out.printf("7) Minden Kobra mentve a kobra.txt fájlba\n");

    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            be.nextLine();
            while (be.hasNextLine()) kigyok.add(new Kigyo(be.nextLine()));
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
