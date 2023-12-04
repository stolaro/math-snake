import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

public class board extends JPanel {
    private Image boardimage;
    final Timer timer;
    private int countdown = 30;
    final JLabel timeLabel;
    Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  25);
    public board(){
        try {
            boardimage = ImageIO.read(new File("C:/Users/Stolaro/Desktop/board.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLayout(null);
        // Utwórz timer w konstruktorze
        timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                countdown--;

                if (countdown <= 0) {
                    timer.stop();
                    timeover();
                }

                updatetime();
            }
        });

        Timer startTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        startTimer.setRepeats(false);
        startTimer.start();

        // Dodaj etykietę czasu
        timeLabel = new JLabel("Czas: " + countdown + "s");
        timeLabel.setBounds(680, 5, 155, 65);
        timeLabel.setFont(f2);
        add(timeLabel);
        exitbuttonmenu();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (boardimage != null) {
            g.drawImage(boardimage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    private void updatetime() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                timeLabel.setText("Czas: " + countdown + "s");
            }
        });
    }
    private void makemenuframe(){

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(board.this);
        frame.dispose();
        JFrame menu = new JFrame("SnakeGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Menu menuPanel = new Menu();
        menu.add(menuPanel);
        menu.setSize(1280, 720);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        menu.setResizable(false);

    }
    private void timeover() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        JDialog dialog = new JDialog(parentFrame, "Koniec gry", true);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Koniec czasu!");

        JButton timeOverExit = new JButton("X");
        timeOverExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dodaj kod obsługi dla własnego przycisku
                makemenuframe();
            }
        });
        panel.add(label);
        panel.add(timeOverExit);
        dialog.add(panel);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
        dialog.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

    }
    private void exitbuttonmenu(){
        JButton exitButton = new JButton("Wyjście");
        setComponentZOrder(exitButton, 1);
        exitButton.setBounds(1000, 5, 155, 65);
        add(exitButton);
        exitButton.setFont(f2);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusable(false);
        exitButton.setForeground(Color.black);
        exitButton.setBorderPainted(false);
        exitButton.setFocusable(false);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makemenuframe();
                timer.stop();
            }
        });
    }
}
