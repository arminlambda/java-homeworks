import java.util.*;
import java.io.*;

interface Kriterij {
    int steviloTock(Tekmovanje tekmovanje, String drzava);
}


class Tekmovalec {
    private String drzava;
    private String izvajalec;
    private String naslovPesmi;

    public Tekmovalec(String drzava, String izvajalec, String naslovPesmi) {
        this.drzava = drzava;
        this.izvajalec = izvajalec;
        this.naslovPesmi = naslovPesmi;
    }

    public String getDrzava() {
        return drzava;
    }

    public String getIzvajalec() {
        return izvajalec;
    }

    public String getNaslovPesmi() {
        return naslovPesmi;
    }

    public String toString() {
        return "(" + drzava + ") " + izvajalec + " - " + naslovPesmi;
    }
}

class Glas {
    private String odDrzave;
    private String zaDrzavo;
    private int stTock;

    public Glas(String odDrzave, String zaDrzavo, int stTock) {
        this.odDrzave = odDrzave;
        this.zaDrzavo = zaDrzavo;
        this.stTock = stTock;
    }

    public String getOdDrzave() {
        return odDrzave;
    }

    public String getZaDrzavo() {
        return zaDrzavo;
    }

    public int getStTock() {
        return stTock;
    }

    public String toString() {
        return odDrzave + " --" + stTock + "t-> " + zaDrzavo;
    }
}

class Tekmovanje {
    private ArrayList<Tekmovalec> seznamTekmovalcev;
    private ArrayList<Glas> seznamGlasov;
    private Kriterij kriterij;
    private boolean urejeno;

    public Tekmovanje(ArrayList<Tekmovalec> seznamTekmovalcev, ArrayList<Glas> seznamGlasov) {
        this.seznamTekmovalcev = seznamTekmovalcev;
        this.seznamGlasov = seznamGlasov;
        this.kriterij = new OsnovniKriterij();
        this.urejeno = false;
    }

    public ArrayList<Tekmovalec> getSeznamTekmovalcev() {
        return seznamTekmovalcev;
    }

    public ArrayList<Glas> getSeznamGlasov() {
        return seznamGlasov;
    }

    public void setKriterij(Kriterij kriterij) {
        this.kriterij = kriterij;
        this.urejeno = false;
    }

    public int steviloTock(String drzava) {
        return kriterij.steviloTock(this, drzava);
    }

    public Tekmovalec getZmagovalec() {
        if (seznamTekmovalcev.isEmpty()) {
            return null;
        }
        if (true) {
            urediPoTockah();
        }
        return seznamTekmovalcev.get(0);
    }

    private void urediPoTockah() {
        if (!urejeno) {
            Collections.sort(seznamTekmovalcev, new Comparator<Tekmovalec>() {
                public int compare(Tekmovalec t1, Tekmovalec t2) {
                    return Integer.compare(steviloTock(t2.getDrzava()), steviloTock(t1.getDrzava()));
                }
            });
            urejeno = true;
        }
    }
    
    public int getMesto(String drzava) {
        urediPoTockah();
        for (int i = 0; i < seznamTekmovalcev.size(); i++) {
            if (seznamTekmovalcev.get(i).getDrzava().equals(drzava)) {
                return i + 1;
            }
        }
        return -1;
    }

    public void izpisiTekmovalce() {
        if (seznamTekmovalcev.isEmpty()) {
            System.out.println("Seznam tekmovalcev je prazen.");
        } else {
            System.out.println("Seznam tekmovalcev:");
            for (Tekmovalec tekmovalec : seznamTekmovalcev) {
                System.out.println(tekmovalec);
            }
        }
    }

    public void izpisiGlasove() {
        if (seznamGlasov.isEmpty()) {
            System.out.println("Seznam glasov je prazen.");
        } else {
            System.out.println("Seznam glasov:");
            for (Glas glas : seznamGlasov) {
                System.out.println(glas);
            }
        }
    }

    public void izpisiTocke() {
        if (seznamTekmovalcev.isEmpty()) {
            System.out.println("Seznam tekmovalcev je prazen.");
        } else {
            System.out.println("Seznam tekmovalcev in njihovih tock:");
            for (Tekmovalec tekmovalec : seznamTekmovalcev) {
                int tocke = steviloTock(tekmovalec.getDrzava());
                //System.out.println("11111");
                System.out.println(tekmovalec + ": " + tocke + "t");
            }
        }
    }

    public void izpisiRezultateUrejeno(int topK) {
        urediPoTockah();
        System.out.println("Najboljse uvrsceni tekmovalci:");
        for (int i = 0; i < Math.min(topK, seznamTekmovalcev.size()); i++) {
            Tekmovalec tekmovalec = seznamTekmovalcev.get(i);
            System.out.println((i+1) + ". " + tekmovalec+ ": "+ steviloTock(tekmovalec.getDrzava()) + "t");
        }
    }

    public static Tekmovanje izDatotek(String datotekaTekmovalci, String datotekaGlasovi) {
        ArrayList<Tekmovalec> tekmovalci = new ArrayList<>();
        ArrayList<Glas> glasovi = new ArrayList<>();

        
        
        
        try {
            Scanner preberTekmovalce = new Scanner(new File(datotekaTekmovalci));
            preberTekmovalce.nextLine(); // 1drzava 2izvajalc 3pesem prva vrsta prazna
            while (preberTekmovalce.hasNextLine()) {
                String vrstica = preberTekmovalce.nextLine();
                String[] podatki = vrstica.split(";");
                tekmovalci.add(new Tekmovalec(podatki[1], podatki[2], podatki[3]));
            }
            preberTekmovalce.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        try {
            Scanner preberGlasove = new Scanner(new File(datotekaGlasovi));
            preberGlasove.nextLine(); // 2od drzave, 3za drazavo, 4sttock prva vrsta prazna
            while (preberGlasove.hasNextLine()) {
                String vrstica = preberGlasove.nextLine();
                String[] podatki = vrstica.split(";");
                glasovi.add(new Glas(podatki[2], podatki[3], Integer.parseInt(podatki[4])));
            }
            preberGlasove.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return new Tekmovanje(tekmovalci, glasovi);
    }
}


class OsnovniKriterij implements Kriterij {
    public int steviloTock(Tekmovanje tekmovanje, String drzava) {
        int totalTock = 0;
        for (Glas glas : tekmovanje.getSeznamGlasov()) {
            if (glas.getZaDrzavo().equals(drzava)) {
                totalTock += glas.getStTock();
            }
        }
        return totalTock;
    }
}

public class DN09 {
    public static void main(String[] args) {
        switch (args[0]) {
            case "izpisiTekmovanje":
                Tekmovanje tekmovanje = Tekmovanje.izDatotek(args[1], args[2]);
                if (tekmovanje != null) {
                    tekmovanje.izpisiTekmovalce();
                    System.out.println();
                    tekmovanje.izpisiGlasove();
                }
                break;
            case "izpisiTocke":
                Tekmovanje tekmovanje2 = Tekmovanje.izDatotek(args[1], args[2]);
                if (tekmovanje2 != null) {
                    tekmovanje2.izpisiTocke();
                }
                break;
            case "najboljse":
                Tekmovanje tekmovanje3 = Tekmovanje.izDatotek(args[1], args[2]);
                if (tekmovanje3 != null) {
                    int topK = Integer.parseInt(args[3]);
                    tekmovanje3.izpisiRezultateUrejeno(topK);
                }
                break;

            /*case "utezeno":
                
                    Tekmovanje tekmovanje4 = Tekmovanje.izDatotek(args[1], args[2]);
                    if (tekmovanje4 != null) {
                        int topK = Integer.parseInt(args[3]);
                        float utezGlasovanja = Float.parseFloat(args[4]);
                        float utezZirija = Float.parseFloat(args[5]);
                        Kriterij utezeniKriterij = new UtezeniKriterij(utezGlasovanja, utezZirija);
                        tekmovanje4.setKrit
                    }
                
                break;*/
        }   
    }
    
}
