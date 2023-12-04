import javax.swing.*;
public class Main{
    public static void main(String[] args) {
            JFrame menu = new JFrame("SnakeGame");
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Tworzymy panel menu i dodajemy go do głównego okna
            Menu menuPanel = new Menu();
            menu.add(menuPanel);
            menu.setSize(1280, 720);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            menu.setResizable(false);

    };
}
