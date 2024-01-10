import javax.swing.*;
/**
 *     Klasa głowna
 */
public class Main{
    public static void main(String[] args) {
            JFrame menu = new JFrame("SnakeGame");
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // tworzenie panelu menu i dodanie go do głównego okna
            Menu menuPanel = new Menu();
            menu.add(menuPanel);
            menu.setSize(1280, 720);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            menu.setResizable(false);
    };
}
