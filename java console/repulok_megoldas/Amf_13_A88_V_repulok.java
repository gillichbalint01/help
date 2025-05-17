import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {

    private class Repulo {
        public String tipus;
        public float hossz;
        public int suly;
        public int ferohely;
        public int tank;

        public Repulo(String sor) {
            String[] s = sor.split(";");
            tipus = s[0];
            hossz = Float.parseFloat(s[1]);
            suly = Integer.parseInt(s[2]);
            ferohely = Integer.parseInt(s[3]);
            tank = Integer.parseInt(s[4]);
        }
    }

    private ArrayList<Repulo> repulok = new ArrayList<>();

    public Main() {

        // 0. feladat (3p)
        betolt("repulok.csv");
        System.out.printf("0) Összesen %d repülő adata beolvasva.\n", repulok.size());
        Repulo v = repulok.get((int)(Math.random()*repulok.size()));
        System.out.printf("   Közülük egy véletlen kiválasztott: %s\n", v.tipus);

        // 1. feladat (2p)
        Repulo maxFerohely = repulok.get(0);
        for (Repulo repulo : repulok) if (repulo.ferohely > maxFerohely.ferohely) maxFerohely = repulo;
        System.out.printf("1) Legtöbb férőhellyel rendelkezik: %s (%d hely)\n", maxFerohely.tipus, maxFerohely.ferohely);
        Repulo maxMasodik = repulok.get(0);
        for (Repulo repulo : repulok) if (repulo.ferohely < maxFerohely.ferohely && repulo.ferohely > maxMasodik.ferohely) maxMasodik = repulo;
        System.out.printf("   A második legtöbb férőhely: %s (%d hely)\n", maxMasodik.tipus, maxMasodik.ferohely);

        // 2. feladat (1p)
        int osszSuly = 0; int db = 0;
        for (Repulo repulo : repulok) if (repulo.suly < 100_000) { osszSuly += repulo.suly; db++; }
        System.out.printf("2) A 100000kg súlynál kisebb gépek (%d darab) átlagsúlya: %.2fkg\n", db, (float)osszSuly/db);

        // 3. feladat
        System.out.printf("3) Típusok, amelyikben nincs szám: ");
        for (Repulo repulo : repulok) {
            boolean vanSzam = false;
            for (int i=0; i<repulo.tipus.length(); i++) {
                char betu = repulo.tipus.charAt(i);
                if (betu >= '0' && betu <= '9') vanSzam = true;
            }
            if (!vanSzam) System.out.printf("%s", repulo.tipus);
        }
        System.out.printf("\n");

        // 4. feladat
        TreeSet<String> gyartok = new TreeSet<>();
        for (Repulo repulo : repulok) gyartok.add(repulo.tipus.split(" ")[0]);
        System.out.printf("4) Gyártók: %s\n", String.join(", ", gyartok));
        ArrayList<String> gyartoLista = new ArrayList<>();
        for (String gyarto : gyartok) gyartoLista.add(gyarto);
        String gyarto = gyartoLista.get((int)(Math.random()*gyartoLista.size())); // gyarto = "Boeing";
        System.out.printf("   Közülük egy véletlen kiválasztott: %s\n", gyarto);
        System.out.printf("   Termékeik:\n");
        for (Repulo repulo : repulok) if (repulo.tipus.split(" ")[0].equals(gyarto)) System.out.printf("   - %s\n", repulo.tipus);

        // 5. feladat
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("sokutas.txt"), "utf-8");
            for (Repulo repulo : repulok) {
                if (repulo.ferohely > 300) ki.printf("%s / %d hely\r\n", repulo.tipus, repulo.ferohely);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
        System.out.printf("5) A 300 főnél több férőhelyű gépek adatai a sokutas.txt fájlba mentve.\n");


    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            be.nextLine();
            while (be.hasNextLine()) repulok.add(new Repulo(be.nextLine()));
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
