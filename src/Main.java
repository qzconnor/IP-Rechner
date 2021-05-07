import javax.swing.*;
/*
    Dieses Projekt ist in Zusammenarbeit mit
    Connor Breuer, Claas Diedrich, Lasse Knauff
    entstanden.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        new View();
    }
}
