import java.util.Arrays;

public class DN02 {
    
    public static void main(String[] args) {


        if(args.length < 0) {
            return;
        }

        /*int vrstica;
        int stolpec;*/

        String niz = args[0];
        int dolzinaNiza = niz.length();
        //int koren = (int) Math.sqrt(dolzinaNiza);
        int sirina = (int) Math.ceil(Math.sqrt(dolzinaNiza)); 
        int znak = 0;
        //char[][] matrika = new char [vrstica][stolpec];
        
        for (int i = 0; i < sirina; i++) {
            for (int j = 0; j < sirina; j++) {
                if (znak < dolzinaNiza) {
                    System.out.print(niz.charAt(znak++) + "  ");
                } else {
                    System.out.print(".  ");
                }
            }System.out.println();
                //for (int k = 0; k < dolzinaNiza; k++) {
                    //matrika[i][j] = niz.charAt(k);
        }    
    }
    //System.out.println(Arrays.deepToString(matrika));
}
