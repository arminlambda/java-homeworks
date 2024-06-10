import java.io.*;
import java.util.*;

public class DN05 {

    public static void main(String[] args) {
        try {
            String nacin = args[0];
            String dat = args[1];

            switch (nacin) {
                case "izpisi":
                    int[][] slika = preberiSliko(dat);
                    izpisiSliko(slika);
                    break;
                case "histogram":
                    int[][] slikaHistogram = preberiSliko(dat);
                    izpisiHistogram(slikaHistogram);
                    break;
                case "svetlost":
                    int[][] slikaSvetlost = preberiSliko(dat);
                    double svetlost = svetlostSlike(slikaSvetlost);
                    System.out.printf("Srednja vrednost sivine na sliki %s je: %.2f%n", dat, svetlost);
                    break;
                /*case "zmanjsaj":
                    int[][] zmanjsanaSlika = zmanjsajSliko(preberiSliko(dat));
                    izpisiSliko(zmanjsanaSlika);
                    break;*/
                case "rotiraj":
                    int[][] rotiranaSlika = rotirajSliko(preberiSliko(dat));
                    izpisiSliko(rotiranaSlika);
                    break;
                case "zrcali":
                    int[][] zrcaljenaSlika = zrcaliSliko(preberiSliko(dat));
                    izpisiSliko(zrcaljenaSlika);
                    break;
                case "vrstica":
                    int[][] slikaVrstica = preberiSliko(dat);
                    int maxRazlikaVrstica = poisciMaxVrstico(slikaVrstica);
                    System.out.println("Max razlika svetlo - temno je v " + (maxRazlikaVrstica + 1) + ". vrstici.");
                    break;

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static int[][] preberiSliko(String ime) throws IOException {

        File dat = new File(ime);

        // prva
        if (!dat.exists()) {
            throw new FileNotFoundException("Napaka: datoteka " + ime + " ne obstaja.");
        }
        // druga
        if (dat.length() == 0) {
            throw new IOException("Napaka: Datoteka " + ime + " je prazna.");
        }

        Scanner sc = new Scanner(dat);
        String a = sc.nextLine().trim();

        // tri
        if (!(a.contains("x") && a.contains("P2"))) {
            sc.close();
            throw new IOException("Napaka: datoteka " + ime + " ni v formatu P2.");
        }

        String[] b = a.split("\\s+");
        int sirina = 0;
        int visina = 0;

        try {
            sirina = Integer.parseInt(b[1]);
            visina = Integer.parseInt(b[b.length - 1]);
        } catch (NumberFormatException e) {
            sc.close(); // stir
            throw new IOException("Napaka: datoteka " + ime + " ni v formatu P2 (velikost slike ni pravilna).");
        }

        if (sirina <= 0 || visina <= 0) {
            sc.close(); // pet
            throw new IOException("Napaka: datoteka " + ime + " ni v formatu P2 (velikost slike je 0 ali negativna).");
        }

        if (dat.exists()) {
            for (int i = 0; i < visina; i++) {
                for (int j = 0; j < sirina; j++) {
                    if (!sc.hasNextInt()) {
                        sc.close(); // sest
                        throw new IOException("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                    }
                    int value = sc.nextInt();
                    if (value < 0 || value > 255) {
                        sc.close(); // sedm
                        throw new IOException("Napaka: datoteka " + ime + " vsebuje podatke izven obsega 0 do 255.");
                    }

                }
            }
        }

        sc.close();

        int[][] slika = new int[visina][sirina];

        Scanner scanner = new Scanner(dat);
        String vrstica = scanner.nextLine();

        if (vrstica.contains("P2") || vrstica.contains("x")) {
            vrstica = scanner.nextLine();
            String[] neki = vrstica.split(" "); //to sem spreminjal, ker nisem mogel zagnat itestov na ucilnici
            int index = 0;			//nisem vedel ali je prav ali ne. upam da je prav saj se ne spomnem kako sem imel prej
            for (int i = 0; i < visina; i++) {
                for (int j = 0; j < sirina; j++) {
                    int piksel = Integer.parseInt(neki[index]);
                    slika[i][j] = piksel;
                    index++;
                }
            }
        }

        scanner.close();
        return slika;

    }

    static void izpisiSliko(int[][] slika) {
        System.out.println("velikost slike: " + slika[0].length + " x " + slika.length);
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[i].length; j++) {
                System.out.printf("%4d ", slika[i][j]);
            }
            System.out.println();
        }
    }

    /*public static void izpisiHistogram(int[][] slika) {

        ArrayList<Integer> histogram = new ArrayList<>(); //nisem mogel testirati te metode

        for (int[] vrstica : slika) {
            for (int piksel : vrstica) {

                while (histogram.size() <= piksel) {
                    histogram.add(0);
                }

                histogram.set(piksel, histogram.get(piksel) + 1);
            }
        }

        System.out.println("sivina : # pojavitev");

        for (int i = 0; i < histogram.size(); i++) {
            if (histogram.get(i) > 0) {
                System.out.printf("%5d : %4d%n", i, histogram.get(i));
            }
        }
    }*/

    static void izpisiHistogram(int[][] slika) {
        int[] histogram = new int[256];

        for (int[] vrstica : slika) {
            for (int piksel : vrstica) {
                histogram[piksel]++;
            }
        }
	System.out.println("sivina : # pojavitev");
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0) {
                System.out.printf("%5d : %4d%n", i, histogram[i]);
            }
        }
    }

    static double svetlostSlike(int[][] slika) {
        double vsota = 0;
        int st = 0;
        for (int[] vrstica : slika) {
            for (int piksel : vrstica) {
                vsota += piksel;
                st++;
            }
        }

        return vsota / st;
    }
        
    
    
    
        //druga naloga ima pomanjkljaje saj nisem mogel zagnati testov na spletni ucilnici
	//mislim da ne prestane 3-4 teste, nisem preprican




    /*static int[][] zmanjsajSliko(int[][] slika) {
        if (slika.length < 3 || slika[0].length < 3) {
            return slika;
        }
        int novaVisina = slika.length / 2;
        int novaSirina = slika[0].length / 2;

        int[][] novaSlika = new int[novaVisina][novaSirina];

        for (int i = 0; i < novaVisina; ++i) {
            for (int j = 0; j < novaSirina; ++j) {

                int povprecje = (slika[2 * i][2 * j] +
                        slika[2 * i + 1][2 * j] +
                        slika[2 * i][2 * j + 1] +
                        slika[2 * i + 1][2 * j + 1]) / 4;
                novaSlika[i][j] = povprecje;
            }
        }

        return novaSlika;
    }*/

    static int[][] rotirajSliko(int[][] slika) {
        int novaVisina = slika[0].length;
        int novaSirina = slika.length;
        int[][] novaSlika = new int[novaVisina][novaSirina];

        for (int i = 0; i < novaVisina; ++i) {
            for (int j = 0; j < novaSirina; ++j) {
                novaSlika[i][j] = slika[novaSirina - j - 1][i];
            }
        }
        return novaSlika;
    }

    static int[][] zrcaliSliko(int[][] slika) {
        int visina = slika.length;
        int sirina = slika[0].length;
        int[][] novaSlika = new int[visina][sirina];

        for (int i = 0; i < visina; ++i) {
            for (int j = 0; j < sirina; ++j) {
                novaSlika[i][j] = slika[i][sirina - j - 1];
            }
        }
        return novaSlika;
    }

    static int poisciMaxVrstico(int[][] slika) {
        int maxRazlika = -1;
        int neki = -1;

        for (int i = 0; i < slika.length; ++i) {
            int min = 255;
            int naj = 0;
            for (int j = 0; j < slika[i].length; ++j) {
                if (slika[i][j] < min)
                    min = slika[i][j];
                if (slika[i][j] > naj)
                    naj = slika[i][j];
            }
            int razlika = naj - min;
            if (razlika > maxRazlika) {
                maxRazlika = razlika;
                neki = i;
            }
        }
        return neki;
    }

}

