import java.util.TreeSet;

public class Main {

    public Main()
    {
        // souf formatolás
        /*
            %s - String típusú változó behelyettesítése
            %d - Integer behelyettesítése
            %f - float vagy double behelyettesítése
            %.1f - float vagy double pontosságánák formatolása
                    pl: 1.34 -> %.1f = 1.3
                        1.34 -> %.0f = 1
         */
        System.out.printf("%.1f", 1.34);

        //treemap
        /*
            TreeMap<Kulcs, Érték> map = new TreeMap<>();
            A kulcs és érték bármilyen fajta vátozó lehet
            Általában konzolos feladatban akkor kell használni ha csoporosítani kell adatokat
            pl: állatos feladatban magasság alapjan csoportosítás (5. feladat)

            Ezt egy for loopal csináljuk:
            for (Adat a : Lista)
            {
                if ( map.containsKey(a.SzámolandóAdat) ) { map.put(a.SzámolandóAdat, map.get(a.SzámolandóAdat) + 1); }
                else { map.put(a.SzámolandóAdat, 1); }
            }
            Hogyha benne van mapben akkor csak hozzáad egyet, ha pedig nem akkor 1-el berakja a mapbe
         */


        //TreeSet
        /*
            TreeSet<String> set = new TreeSet<>();
            Bármilyen típusú változó lehet
            Akkor kell használni, mikor szeretnénk megszámolni adatokat azoknak az ismétlése nélkül
            pl: városnevek megszámolása egy feladatban ahol ismétlődnek a városok

            for ( Adat a : Lista )
            {
                set.add( a.valami )
            }
            System.out.println(set);
            //automatikusan nem rakja bele az adatot ha az már benne van

            set.remove(valami) //törlés a setből

        */

        //fáljbetöltés
        /*
        private void betolt(String fajlnev) {
            Scanner be = null;
            try {
                be = new Scanner(new File(fajlnev), "utf-8");
                while (be.hasNextLine()) lista.add(new Adat(be.nextLine()));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (be != null) be.close();
            }
        }
         */

        //fájlba írás
        /*
            PrintWriter ki = null;
            try {
                ki = new PrintWriter(new File("fájlnév.txt"), "utf-8");
                for (Adat a : lista) {
                    ki.printf("fájba irandó adat\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ki != null) ki.close();
            }
         */



    }

    public static void main(String[] args)
    {
        new Main();
    }
}