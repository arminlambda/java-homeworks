import java.io.*;
import java.util.*;

public class DN09 {

    static Postaja[] postaje;
    static Linija[] linije;
    static Avtobus[] avtobusi;


    public static void main(String[] args) {
        try {

            preberi("viri/linije.csv");


            izpisi();


            //izpisiHelp("viri/izpis.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void preberi(String imeDatoteke) throws IOException {
        Scanner sc = new Scanner(new File(imeDatoteke));
        int cnt = 0;

        Map<Integer, Postaja> hashmapPostaj = new LinkedHashMap<>();
        Map<Integer, Avtobus> hashmapBusov = new LinkedHashMap<>();
        Map<Integer, Linija> hashmapLinij = new LinkedHashMap<>();
        List<Linija> vrstniRedLinij = new ArrayList<>();

        while (sc.hasNextLine()) {
            String vrstica = sc.nextLine();

            if (vrstica.isEmpty()) {
                cnt++;
                continue;
            }

            switch (cnt) {
                case 0:
                    //<ŠtPostaj>,<ŠtLinij>,<ŠtAvtobusov>
                    String[] a = vrstica.split(",");
                    int stPostaj = Integer.parseInt(a[0]);
                    int stLinij = Integer.parseInt(a[1]);
                    int stAvtobusov = Integer.parseInt(a[2]);

                    postaje = new Postaja[stPostaj];
                    linije = new Linija[stLinij];
                    avtobusi = new Avtobus[stAvtobusov];
                    break;

                case 1:
                    // <ID>,<Ime>,<X>,<Y>,<Linije(ID);>,<Avtobusi(ID(potniki);)>,<čakajoči>
                    String[] b = vrstica.split(",");

                    int id = Integer.parseInt(b[0]);
                    String ime = b[1];
                    int x = Integer.parseInt(b[2]);
                    int y = Integer.parseInt(b[3]);
                    String c = b[4];
                    String busi = b[5];
                    int cakajoci = Integer.parseInt(b[6]);

                    Postaja p = new Postaja(id, ime, x, y, cakajoci);
                    hashmapPostaj.put(id, p);

                    obdelajLinijeNaPostaji(c, p, hashmapLinij, vrstniRedLinij);
                    obdelajAvtobuseNaPostaji(busi, p, hashmapBusov);
                    break;

                case 2:
                    // <IDLinije>,<barva>,<IDAvtobusov>,<Postaje|>
                    String[] c1 = vrstica.split(",", 4);

                    int linijaID = Integer.parseInt(c1[0]);
                    String barva = c1[1];
                    String bus1 = c1[2];
                    String postajee = c1[3];

                    Linija l = hashmapLinij.get(linijaID);
                    if (l == null) {
                        l = new Linija(linijaID);
                        hashmapLinij.put(linijaID, l);
                        vrstniRedLinij.add(l);
                    }

                    l.setBarva(barva);

                    if (!bus1.isEmpty()) {
                        for (String aStr : bus1.split(";")) {
                            int busId = Integer.parseInt(aStr);
                            Avtobus avtobus4 = hashmapBusov.get(busId);
                            if (avtobus4 != null) {
                                l.dodajAvtobus(avtobus4);
                            }
                        }
                    }

                    if (!postajee.isEmpty()) {
                        for (String postajaId : postajee.split("\\|")) {
                            int postajaID = Integer.parseInt(postajaId);
                            Postaja postaja6 = hashmapPostaj.get(postajaID);
                            if (postaja6 != null) {
                                l.dodajPostajo(postaja6);
                            }
                        }
                    }
                    break;

                default:
                    break;
            }
        }

        postaje = hashmapPostaj.values().toArray(new Postaja[0]);
        avtobusi = hashmapBusov.values().toArray(new Avtobus[0]);
        linije = vrstniRedLinij.toArray(new Linija[0]);

    }
    //helper funkcija
    static void obdelajLinijeNaPostaji(String j, Postaja p,Map<Integer, Linija> linije1,List<Linija> vrstniRedLinij) {
        //string za linijo
        for (String lid : j.split(";")) {
            int linijaID = Integer.parseInt(lid);
            Linija l = linije1.get(linijaID);

            if (l == null) {
                l = new Linija(linijaID);
                linije1.put(linijaID, l);
                vrstniRedLinij.add(l);
            }

            l.dodajPostajo(p);
        }
    }
    //helper funkcija
    static void obdelajAvtobuseNaPostaji(String busiStr, Postaja p,Map<Integer, Avtobus> avtobusi1) {


        for (String b : busiStr.split(";")) {
            if (b.isEmpty()){
                continue;
            }
            int levo = b.indexOf('(');
            int desno = b.indexOf(')');
            int busID = Integer.parseInt(b.substring(0, levo));
            int stPotnikov = Integer.parseInt(b.substring(levo + 1, desno));

            Avtobus a = avtobusi1.get(busID);
            if (a == null) {
                a = new Avtobus(busID, stPotnikov);
                avtobusi1.put(busID, a);
            }
            a.setTrenutnaPostaja(p);
        }
    }

    static void izpisi() {
        for (Linija l : linije) {
            System.out.println(l);
        }
    }

    static void izpisiNajboljObremenjenePostaje(int kapaciteta) {


        //razmreje prosta_mesta/cakajoci_potniki

        //isces najbolj obremenjeno postajo (najmanse razmerje)
        //najdi najbolj obremenjeno
        //prosta_mesta so vsota prostih sedezev na vseh busih
        //ce cakajoci_potniki == 0 je razmerje = prosta_mesta
        //ce so razmerja enaka zberes najmanjsi id
        //prosi sedezi = kapacitete - Avtobus(stPotniko( ce je
            //rezultat negativen nastavis 0

        float razmerje;
        int cakajo;
        Postaja naj;




    }


    static void izpisiHelp(String imeDatoteke) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(imeDatoteke))) {


            writer.println("========POSTAJE=)=)=======");
            writer.println();
            writer.println("<ID> <ime> [x,y] čakajoči: N");
            writer.println();
            for (Postaja p : postaje) {
                writer.println(p);
            }

            writer.println();


            writer.println("====AVTOBUSI=?====");
            writer.println();
            writer.println("<ID> (stPotnikov) - <ime trenutne postaje>");
            writer.println();
            for (Avtobus a : avtobusi) {
                writer.println(a);
            }

            writer.println();


            writer.println("========LINIE=======");
            writer.println();
            writer.println("Linija ID - Ime_postaje [(bus)] -> Ime_postaje -> ...");
            writer.println();
            for (Linija l : linije) {
                writer.println(l);
            }



        }
    }


}


    class Postaja {
        private final int x ,y, ID , cakajoci;
        private final String ime;

        public Postaja(int ID, String ime, int x, int y, int cakajoci) {
            this.x = x;
            this.y = y;
            this.ID = ID;
            this.cakajoci = cakajoci;
            this.ime = ime;
        }

        int getID(){
            return ID;
        }
        String getIme(){
            return ime;
        }
        int getX(){
            return x;
        }
        int getY(){
            return y;
        }
        int getCakajoci(){
            return cakajoci;
        }

        @Override public String toString(){
            return String.format("%d %s [%d,%d] cakajoci: %d", ID, ime, x, y, cakajoci);
        }
    }

    class Avtobus {
        private final int ID,steviloPotnikov;
        private Postaja trenutnaPostaja;

        public Avtobus(int ID, int steviloPotnikov){
            this.ID = ID;
            this.steviloPotnikov = steviloPotnikov;
        }

        int getID(){
            return ID;
        }

        int getSteviloPotnikov(){
            return steviloPotnikov;
        }

        void setTrenutnaPostaja(Postaja p){
            this.trenutnaPostaja = p;
        }

        Postaja getTrenutnaPostaja(){
            return trenutnaPostaja;
        }

        public void setPostaja(Postaja p) {
            this.trenutnaPostaja = p;
        }

        public Postaja getPostaja() {
            return trenutnaPostaja;
        }

        public String getImePostaje() {
            return trenutnaPostaja.getIme();
        }

        @Override
        public String toString(){
            return String.format("%d (%d) - %s", getID(),getSteviloPotnikov(), getImePostaje());
        }
    }

    class Linija {
        private final int ID;
        private String barva;
        private final Postaja[] postaje = new Postaja[10];
        private final Avtobus[] avtobusi = new Avtobus[5];
        private int stPostaj = 0;
        private int stAvtobusov = 0;

        public Linija(int ID) {
            this.ID = ID;
        }

        boolean dodajPostajo(Postaja postaja) {
            if (stPostaj < postaje.length) {
                postaje[stPostaj] = postaja;
                stPostaj++;
                return true;
            } return false;
        }

        boolean dodajAvtobus(Avtobus avtobus) {
            if (stAvtobusov < avtobusi.length) {
                avtobusi[stAvtobusov] = avtobus;
                stAvtobusov++;
                return true;
            } return false;
        }

        public int getID() {
            return ID;
        }

        public Avtobus[] getAvtobusi() {
            return avtobusi;
        }

        public Postaja[] getPostaje() {
            return postaje;
        }

        public String getBarva() {
            return barva;
        }

        public void setBarva(String barva) {
            this.barva = barva;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Linija " + ID + " - ");
            for(int i = 0;i < stPostaj; i++){
                Postaja trenutna = postaje[i];
                sb.append(trenutna.getIme());
                for(int j = 0; j < stAvtobusov; j++) {
                    if (avtobusi[j].getPostaja().equals(trenutna)) {
                        sb.append(" (bus)");
                        break;
                    }
                }
                if(i <stPostaj-1) {
                    sb.append(" -> ");
                }
            }
            return sb.toString();
        }
    }
