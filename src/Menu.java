import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * <p>
 *     Klasa odpowiadająca za wyświetlanie Menu gry
 * </p>
 *
 */
public class Menu extends JPanel {
    private Image backgroundImage;

    public Menu() {
        try {
            backgroundImage = ImageIO.read(new File("img/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLocation(200,300);
        this.setLayout(null);
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  45);
    // Przyciski odpowiedzialne za GRAJ
        JButton playButton = new JButton("Graj");
        add(playButton);
        playButton.setForeground(Color.black);
        playButton.setFocusable(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFont(f1);
        playButton.setBounds(460, 210, 320, 140);
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Menu.this);
                frame.dispose();
                JFrame board = new JFrame("SnakeGame");
                board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                board game = new board();
                board.add(game);
                board.setSize(1280, 720);
                board.setLocationRelativeTo(null);
                board.setVisible(true);
                board.setResizable(false);
            }
        });
/// Przyciski odpowiedzialne za WYJŚCIE
        JButton exitButton = new JButton("Wyjście");
        exitButton.setBounds(455, 410, 320, 140);
        add(exitButton);
        exitButton.setFont(f1);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusable(false);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}