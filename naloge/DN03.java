import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DN03 {
    public static void main(String[] args) throws Exception{
        if (args.length != 3) {
            System.out.println("Napačno število argumentov!");
            return;
        }

        String imeDatoteke = args[0];
        int dolzinaGesla = Integer.parseInt(args[1]);
        int seme = Integer.parseInt(args[2]);

        ArrayList<String> besede = new ArrayList<>();

        
        Scanner sc = new Scanner(new File(imeDatoteke));

        int steviloBesed = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < steviloBesed; i++) {
            String beseda = sc.nextLine();
            besede.add(beseda);
        }

        sc.close();
        

        Random random = new Random(seme);

        StringBuilder geslo = new StringBuilder();

        for (int i = 0; i < dolzinaGesla; i++) {
            int nakljucniIndeksBesede = random.nextInt(besede.size());
            String nakljucnaBeseda = besede.get(nakljucniIndeksBesede);
            int nakljucniIndeksCrke = random.nextInt(nakljucnaBeseda.length());
            char nakljucnaCrka = nakljucnaBeseda.charAt(nakljucniIndeksCrke);
            geslo.append(nakljucnaCrka);
        }

        System.out.println(geslo.toString());
    }
}