import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Klasa odpowiadająca za logike węża
 * </p>
 *
 */

public class Snake {
    private List<Point> body= new ArrayList<>();
    private char direction;
    private boolean grow;
    public boolean gamerun=true;

    public Snake() {

        body.add(new Point(300, 300)); //wspolzedne startu gry
        direction = 'R'; //domyslny ruch weza w prawo
        grow = false;
    }

    public List<Point> getBody() {
        return body;
    }

    //odczytanie kierunku
    public char getDirection() {
        return direction;
    }

    //ustalenie nowego kierunku
    public void setDirection(char newDirection) {
        if ((newDirection == 'U' && direction != 'D') ||
                (newDirection == 'D' && direction != 'U') ||
                (newDirection == 'L' && direction != 'R') ||
                (newDirection == 'R' && direction != 'L')) {
            direction = newDirection;
        }
    }

    //ruch weza
    public void move() {
        if(gamerun) {
            Point head = body.get(0);
            Point newHead = new Point(head);

            switch (direction) {
                case 'U':
                    newHead.y -= 30;
                    break;
                case 'D':
                    newHead.y += 30;
                    break;
                case 'L':
                    newHead.x -= 30;
                    break;
                case 'R':
                    newHead.x += 30;
                    break;
            }

            body.add(0, newHead);

            if (!grow) {
                body.remove(body.size() - 1);
            } else {
                grow = false;
            }
        }
    }

    //czy ma wzrosnac liczba segmentow
    public void grow() {
        grow = true;
    }

    //sprawdzenie kolizji z prawidlowym owocem
    public boolean checkCollisionWithFruit(Point fruit) {
        Point head = body.get(0);
        return head.equals(fruit);
    }
    //sprawdzenie kolizji ze zlym wocowem
    public boolean checkCollisionWithWrongFruits(List<Point> wrongFruits) {
        Point head = body.get(0);
        for (Point wrongFruit : wrongFruits) {
            if (head.equals(wrongFruit)) {
                return true;
            }
        }
        return false;
    }

    //sprawdzenie kolizji
    public boolean checkCollision() {
        Point head = body.get(0);

       //kolizja ze sciana
        if (head.x < 0 || head.x >= 1260 || head.y < 90 || head.y >= 670) {
            return true;
        }
        //kolizja z samym soba
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

}
