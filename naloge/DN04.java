public class DN04 {
    public static void main(String[] args) {
        
        
        String zapis = args[0];
        String sporocilo = "";
        
    for (int i = 0; i < zapis.length(); i += 8) {
        
        String crka = zapis.substring(i, i + 8);
        int dvojiskaCrka = Integer.parseInt(crka, 2);
        char asciiCrka = (char) dvojiskaCrka;
        sporocilo += asciiCrka;

        
        }
        System.out.println(sporocilo);
    }
}