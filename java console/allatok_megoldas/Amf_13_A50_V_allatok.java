import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

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
    }

    private ArrayList<Allat> allatok = new ArrayList<>();

    public Main() {
        // 0. feladat
        betolt("allatok.csv");
        System.out.printf("0) Összesen %d féle állatfajta adata beolvasva\n", allatok.size());

        // 1. feladat
        Allat legMagasabb = allatok.get(0);
        for (Allat a : allatok) if (a.magas > legMagasabb.magas) legMagasabb = a;
        System.out.printf("1) A legmagasabb állatfajta: %s, %dcm\n", legMagasabb.fajta, legMagasabb.magas);

        // 2. feladat
        double korOsszeg = 0; int korDb = 0; for (Allat a : allatok) if (a.suly > 20) { korOsszeg += a.kor; korDb++; }
        System.out.printf("2) A húsz kilónál nehezebb fajták átlagéletkora: %.2f év\n", korOsszeg/korDb);

        // 3. feladat
        int sulyOsszeg = 0; for (Allat a : allatok) sulyOsszeg += a.suly;
        int sulyAtlag = sulyOsszeg / allatok.size();
        Allat atlagos = allatok.get(0);
        for (Allat a : allatok) if (Math.abs(a.suly-sulyAtlag) < Math.abs(atlagos.suly-sulyAtlag)) atlagos = a;
        System.out.printf("3) Az átlagsúlyhoz (%dkg) legközelebbi fajta: %s (%dkg)\n", sulyAtlag, atlagos.fajta, atlagos.suly);

        // 4. feladat
        System.out.printf("4) Kettős betű van a fajta nevében:\n");
        for (Allat a : allatok) {
            boolean dupla = false;
            for (int i=0; i<a.fajta.length()-1; i++) if (a.fajta.charAt(i) == a.fajta.charAt(i+1)) dupla = true;
            if (dupla) System.out.printf("   * %s\n", a.fajta);
        }

        // 5. feladat
        TreeMap<Integer, Integer> magasDb = new TreeMap<>();
        for (Allat a : allatok) {
            int kat = a.magas/50;
            if (!magasDb.containsKey(kat)) magasDb.put(kat, 1);
            else magasDb.put(kat, magasDb.get(kat)+1);
        }
        System.out.printf("5) Magasság kategórák (50cm):\n");
        ArrayList<Integer> magasKat = new ArrayList<>();
        for (Integer k : magasDb.keySet()) {
            magasKat.add(k);
            System.out.printf("   * %03d-%03dcm: %d darab\n", k*50, k*50+49, magasDb.get(k));
        }

        // 6. feladat
        int v = magasKat.get((int)(Math.random()*magasKat.size()));
        System.out.printf("6) Ebből egy véletlen kategóriába (%d-%dcm) eső állatok:\n   * ", v*50, v*50+49);
        String vesszo = "";
        for (Allat a : allatok) {
            if (a.magas >= v*50 && a.magas <= v*50+49) { System.out.printf(vesszo + "%s", a.fajta); vesszo = ", "; }
        }

        // 7. feladat
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("kicsi.csv"), "utf-8");
            for (Allat a : allatok) {
                if (a.magas < 100) ki.printf("%s;%d;%d;%d\r\n", a.fajta, a.magas, a.suly, a.kor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
    }

    public void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            be.nextLine();
            while (be.hasNextLine()) allatok.add(new Allat(be.nextLine()));
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
