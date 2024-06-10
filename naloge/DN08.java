interface Lik {
    public double obseg();
}

public class DN08 {
    private static Lik[] Liki;

    public static double skupniObseg(Lik[] liki) {
        double obseg = 0;
        for (Lik lik : liki) {
            obseg += lik.obseg();
        }
        return obseg;
    }

    public static void main(String[] args) {
        Liki = new Lik[args.length];
        for (int i = 0; i < args.length; i++) {
            String[] podatki = args[i].split(":");
            String lik = podatki[0];
            if (lik.equals("pravokotnik")) {
                double a = Double.parseDouble(podatki[1]);
                double b = Double.parseDouble(podatki[2]);
                Liki[i] = new Pravokotnik(a, b);
            } else if (lik.equals("kvadrat")) {
                double a = Double.parseDouble(podatki[1]);
                Liki[i] = new Kvadrat(a);
            } else if (lik.equals("nkotnik")) {
                int n = Integer.parseInt(podatki[1]);
                double a = Double.parseDouble(podatki[2]);
                Liki[i] = new NKotnik(n, a);
            }
        }
        double skupniObseg = skupniObseg(Liki);
        System.out.println((int) skupniObseg);
    }
}

class Pravokotnik implements Lik {
    private double a, b;

    public Pravokotnik(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double obseg() {
        return 2 * (a + b);
    }
}

class Kvadrat implements Lik {
    private double a;

    public Kvadrat(double a) {
        this.a = a;
    }

    @Override
    public double obseg() {
        return 4 * a;
    }
}

class NKotnik implements Lik {
    private int n;
    private double a;

    public NKotnik(int n, double a) {
        this.n = n;
        this.a = a;
    }

    @Override
    public double obseg() {
        return n * a;
    }
}
