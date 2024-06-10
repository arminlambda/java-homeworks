import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DN11 {
    public static void main(String[] args) {
        
        
        JFrame okno = new JFrame("VELIKE ČRKE");
        okno.setSize(600, 400);
        okno.setLayout(new GridLayout(1, 3));
        
        
        
        JTextArea levo = new JTextArea();
        JTextArea desno = new JTextArea();
        JButton pretvori = new JButton("-->pretvori");
        desno.setEditable(false);
        
       
       
        pretvori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 String besedilo = levo.getText();
                String caps = besedilo.toUpperCase();
                desno.setText(caps);
            }
        });
        
        
        okno.add(new JScrollPane(levo));
        okno.add(pretvori);
        okno.add(new JScrollPane(desno));
        okno.setVisible(true);
    }
}
