import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 *     Klasa odpowiadająca za wyświetlanie elementów planszy
 * </p>
 *
 */
public class board extends JPanel {
    private Image boardimage;
    final Timer timer;
    public Snake snake;
    private Point fruit;
    private List<Point> wrongFruits = new ArrayList<>();
    private int countdown = 60; //licznik czasu
    final JLabel timeLabel;
    final JLabel pointsLabel;
    private int points=0; //liczba punktow
    public int op1; //liczba 1
    public int op2; //liczba 2
    private char operator; //działanie +/-
    private int wrongResult1=0; //zly wynik dzialania 1 owoca
    private int wrongResult2=0; //zly wynik dzialania 2 owoca
    private int wrongResult3=0;//zly wynik dzialania 3 owoca
    private String equation; //dzialanie
    final JLabel equLabel;
    private int goodresult=0; //poprawny wynik dzialania
    Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  25);
    public board(){
        //wyswietlanie tła
        try {
            boardimage = ImageIO.read(new File("img/board.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLayout(null);

        //odliczanie czasu
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown--;
                if (countdown <= 0) {
                    timer.stop();
                    gameover();
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

        //wyswietlanie czasu na ekranie
        timeLabel = new JLabel("Czas: " + countdown + "s");
        timeLabel.setBounds(680, 5, 155, 65);
        timeLabel.setFont(f2);
        add(timeLabel);
        exitbuttonmenu();
        snake = new Snake(); //tworzenie klasy snake

        //generowanie owocow
        spawnFruit();
        spawnWrongFruit();
        spawnWrongFruit();
        spawnWrongFruit();
        //wyswietlanie dzialania
        pointsLabel = new JLabel("Wynik: " + points + "s");
        pointsLabel.setBounds(370, 5, 155, 65);
        pointsLabel.setFont(f2);
        add(pointsLabel);
        equLabel = new JLabel(equation);
        equLabel.setBounds(70, 5, 155, 65);
        equLabel.setFont(f2);
        add(equLabel);

        //obsluga klawiatury
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        snake.setDirection('U');
                        break;
                    case KeyEvent.VK_DOWN:
                        snake.setDirection('D');
                        break;
                    case KeyEvent.VK_LEFT:
                        snake.setDirection('L');
                        break;
                    case KeyEvent.VK_RIGHT:
                        snake.setDirection('R');
                        break;
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();

        int speed=125; //predkosc weza
        Timer snakeTimer = new Timer(speed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(snake.gamerun) {
                    moveSnake();
                    checkCollision();
                    repaint();
                }
            }
        });
        snakeTimer.start();
    }

    //metoda opowiadajaca za ruch weza i rozgrywke (generowanie owocow,dodawanie punktow itp)
    private void moveSnake() {
        snake.move();
        if (snake.checkCollisionWithFruit(fruit)) {
            wrongFruits.clear();
            snake.grow();
            points++;
            updatePoints();
            spawnFruit();
            spawnWrongFruit();
            spawnWrongFruit();
            spawnWrongFruit();
            updateEquation();
        }else if(snake.checkCollisionWithWrongFruits(wrongFruits)){
            //gdy jest kolizja ze zlym owocem koniec gry
            timer.stop();
            gameover();
        }
    }
    //metoda odpowiadajaca za generowanie owocow
    private void spawnFruit() {
        Random random = new Random();
        op1 = random.nextInt(15) + 1; // Zakres od 1 do 30
        op2 = random.nextInt(op1) + 1;
        operator=randomOperator();
        goodresult=resultEquation(op1,op2,operator);
        int x = random.nextInt(40) * 30;
        int y = random.nextInt(18) * 30 + 90 ;
        equation = op1 + " " + operator + " " + op2+ "= ?" ;
        fruit = new Point(x, y);
    }
    //metoda odpowiadajaca za generowanie owocu z niepoprawnym wynikiem
    private void spawnWrongFruit() {
        Random random = new Random();
        int x = random.nextInt(40) * 30;
        int y = random.nextInt(18) * 30 + 90;
        wrongFruits.add(new Point(x, y));

        do {
            wrongResult1 = goodresult + random.nextInt(5) + 1;
            wrongResult2 = goodresult + random.nextInt(5) + 1;
            wrongResult3 = goodresult + random.nextInt(5) + 1;
        } while (wrongResult1 == wrongResult2 || wrongResult2 == wrongResult3 || wrongResult3 == wrongResult1 || goodresult == wrongResult1 || goodresult == wrongResult2 || goodresult == wrongResult3);
    }

    //losowanie typu dzialania
    private char randomOperator() {
        Random random = new Random();
        char[] operators = {'+', '-'};
        return operators[random.nextInt(operators.length)];
    }

    //rozwiazywanie dzialania
    private int resultEquation(int op1, int op2, char operator) {
        if(operator=='+'){
            return op1+op2;
        }else{
            return op1-op2;
        }
    }
    //sprawdzanie kolizji
    private void checkCollision() {
        if (snake.checkCollision()) {
            timer.stop();
            gameover();
        }
    }
    //rysowanie elementow planszy
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (boardimage != null) {
            g.drawImage(boardimage, 0, 0, getWidth(), getHeight(), this);
        }
        g.setColor(Color.pink);
        for (Point bodyPart : snake.getBody()) {
            g.fillRect(bodyPart.x, bodyPart.y, 30, 30);
        }
        // Rysowanie owocu
        g.setColor(Color.yellow);
        g.fillRect(fruit.x, fruit.y, 30, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        String resultString = String.valueOf(goodresult);
        FontMetrics fontMetrics = g.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(resultString);
        int stringHeight = fontMetrics.getAscent();
        int centerX = fruit.x + (30 - stringWidth) / 2;
        int centerY = fruit.y + (30 + stringHeight) / 2;
        g.drawString(resultString, centerX, centerY);

        //Rysowanie blednych owocow
        Point wrongFruit1 = wrongFruits.get(0);
        Point wrongFruit2 = wrongFruits.get(1);
        Point wrongFruit3 = wrongFruits.get(2);

        //1 bledny owoc
        g.setColor(Color.yellow);
        g.fillRect(wrongFruit1.x, wrongFruit1.y, 30, 30);
        g.setColor(Color.black);
        String wrongResultString2 = String.valueOf(wrongResult1);
        int stringWidth2 = fontMetrics.stringWidth(wrongResultString2);
        int stringHeight2 = fontMetrics.getAscent();
        int centerX2 = wrongFruit1.x + (30 - stringWidth2) / 2;
        int centerY2 = wrongFruit1.y + (30 + stringHeight2) / 2;
        g.drawString(wrongResultString2, centerX2, centerY2);
        //2 bledny owoc
        g.setColor(Color.yellow);
        g.fillRect(wrongFruit2.x, wrongFruit2.y, 30, 30);
        g.setColor(Color.black);
        String wrongResultString3 = String.valueOf(wrongResult2);
        int stringWidth3 = fontMetrics.stringWidth(wrongResultString3);
        int stringHeight3 = fontMetrics.getAscent();
        int centerX3 = wrongFruit2.x + (30 - stringWidth3) / 2;
        int centerY3 = wrongFruit2.y + (30 + stringHeight3) / 2;
        g.drawString(wrongResultString3, centerX3, centerY3);
        //3 bledny owoc
        g.setColor(Color.yellow);
        g.fillRect(wrongFruit3.x, wrongFruit3.y, 30, 30);
        g.setColor(Color.black);
        String wrongResultString4 = String.valueOf(wrongResult3);
        int stringWidth4 = fontMetrics.stringWidth(wrongResultString4);
        int stringHeight4 = fontMetrics.getAscent();
        int centerX4 = wrongFruit3.x + (30 - stringWidth4) / 2;
        int centerY4 = wrongFruit3.y + (30 + stringHeight4) / 2;
        g.drawString(wrongResultString4, centerX4, centerY4);
    }

    //odswiezanie czasu
    private void updatetime() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                timeLabel.setText("Czas: " + countdown + "s");
            }
        });
    }
    //aktualizowanie punktacji
    private void updatePoints(){
        pointsLabel.setText("Wynik: " + points);
    }
    //aktualizowanie punktacji
    private void updateEquation() {
        equLabel.setText(equation);
    }

    //powrot do menu gry po nacisnieciu przycisku
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

    //wyswietlanie konca gry
    private void gameover() {
        snake.gamerun=false;
        JFrame frame2 = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(frame2, "Koniec gry", true);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Koniec gry!");
        JLabel score = new JLabel("Wynik:"+points);
        JButton timeOverExit = new JButton("X");
        timeOverExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makemenuframe();
            }
        });
        panel.add(label);
        panel.add(score);
        panel.add(timeOverExit);
        dialog.add(panel);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(frame2);
        dialog.setVisible(true);
        dialog.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
    }

    //przycisk pozwalajacy na wyjscie z gry w czasie rozgrywki
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
                snake.gamerun=false;
                timer.stop();
                makemenuframe();
            }
        });
    }
}
