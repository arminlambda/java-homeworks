import java.io.FileInputStream;
import java.io.IOException;

public class DN10 {

    public static void main(String[] args) {


        String fileName = args[0];
        try (FileInputStream fis = new FileInputStream(fileName)) {
            
            fis.skip(8);

            while (true) {
                
                byte[] lengthBytes = new byte[4];
                if (fis.read(lengthBytes) == -1) break;
                int length = byteArrayToInt(lengthBytes);

                
                byte[] typeBytes = new byte[4];
                if (fis.read(typeBytes) == -1) break;
                String type = new String(typeBytes);

               
                fis.skip(length + 4);

                
                System.out.printf("Chunk: %s, length: %d%n", type, length);

                
                if (type.equals("IEND")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int byteArrayToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
               ((bytes[1] & 0xFF) << 16) |
               ((bytes[2] & 0xFF) << 8)  |
               (bytes[3] & 0xFF);
    }
}
